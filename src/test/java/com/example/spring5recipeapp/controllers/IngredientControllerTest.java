package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.command.RecipeCommand;
import com.example.spring5recipeapp.services.IngredientService;
import com.example.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private IngredientController controller;

    MockMvc mockMvc;

    private final RecipeCommand recipeCommand = new RecipeCommand();
    private final Long RECIPE_ID = 1L;
    private final IngredientCommand ingredientCommand = new IngredientCommand();
    private final Long INGREDIENT_ID = 2L;

    {
        recipeCommand.setId(RECIPE_ID);
        ingredientCommand.setId(INGREDIENT_ID);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listIngredients() throws Exception {

        // Given
        given(recipeService.findCommandById(RECIPE_ID)).willReturn(recipeCommand);

        // When - Then
        mockMvc.perform(get("/recipe/1/show/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        then(recipeService).should().findCommandById(RECIPE_ID);

    }

    @Test
    void showIngredient() throws Exception {

        // Given
        given(ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID)).willReturn(ingredientCommand);

        // When/Then
        String url = "/recipe/" + RECIPE_ID + "/ingredient/" + INGREDIENT_ID + "/show";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));


    }
}