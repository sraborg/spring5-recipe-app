package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    private RecipeController recipeController;

    private MockMvc mockMvc;

    private final Recipe recipe;
    private final Long recipe_id = 1L;
    {
        recipe = new Recipe();
        recipe.setId(recipe_id);
    }
    @BeforeEach
    void setUp() {
        recipeController = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();


    }

    @Test
    void showById() throws Exception {

        given(recipeService.findById(recipe_id)).willReturn(recipe);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"));
    }
}