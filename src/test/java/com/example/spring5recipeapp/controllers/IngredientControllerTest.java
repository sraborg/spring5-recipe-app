package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.command.RecipeCommand;
import com.example.spring5recipeapp.services.IngredientService;
import com.example.spring5recipeapp.services.RecipeService;
import com.example.spring5recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private RecipeService recipeService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;
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


    @Test
    void updateRecipeIngredient() throws Exception {
        // Given
        given(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).willReturn(ingredientCommand);
        given(unitOfMeasureService.listAllUoms()).willReturn(new HashSet<>());

        // When / Then
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void saveOrUpdate() throws Exception {

        // Given
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setRecipeId(RECIPE_ID);
        given(ingredientService.saveIngredientCommand(any())).willReturn(ingredientCommand);

        mockMvc.perform(post("/recipe/"+ RECIPE_ID +"/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        ).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/"+RECIPE_ID+"/ingredient/"+INGREDIENT_ID+"/show"));



    }

    @Test
    void testNewIngredientForm() throws Exception {

        given(recipeService.findCommandById(RECIPE_ID)).willReturn(recipeCommand);
        given(unitOfMeasureService.listAllUoms()).willReturn(new HashSet<>());

        // When / Then
        final String url = "/recipe/" + RECIPE_ID + "/ingredient/new";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        then(recipeService).should().findCommandById(RECIPE_ID);
    }
}