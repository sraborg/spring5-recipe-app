package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    private final Recipe recipe;
    private final Long recipe_id = 1L;

    // Static block to initialize recipe
    {
       recipe = new Recipe();
       recipe.setId(recipe_id);
    }
    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipeById() throws Exception {

        // Given
        given(recipeRepository.findById(recipe_id)).willReturn(Optional.of(recipe));

        // When
        Recipe returnedRecipe = recipeService.findById(recipe_id);

        // Then
        then(recipeRepository).should().findById(recipe_id);
        assertNotNull(returnedRecipe);

    }

    @Test
    void getRecipeByIdNotFound() throws Exception {

        final Long invalid_id = 2L;

        // Given
        given(recipeRepository.findById(recipe_id)).willReturn(Optional.of(recipe));

        // When
        Recipe returnedRecipe = recipeService.findById(invalid_id);

        // Then
        then(recipeRepository).should().findById(invalid_id);
        assertNull(returnedRecipe);
    }

    @Test
    void getRecipes() {

        // Given

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        recipes.add(recipe);

        given(recipeRepository.findAll()).willReturn(recipes);

        // When
        Set<Recipe> returnedRecipes = recipeService.getRecipes();

        // Then
        then(recipeRepository).should().findAll();
        assertThat(returnedRecipes.size()).isEqualTo(2);

    }
}