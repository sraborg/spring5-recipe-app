package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.converters.RecipeCommandToRecipe;
import com.example.spring5recipeapp.converters.RecipeToRecipeCommand;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.exceptions.NotFoundException;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    private RecipeToRecipeCommand recipeToRecipeCommand;
    private RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    private final Recipe recipe;
    private final Long RECIPE_ID = 1L;

    // Static block to initialize recipe
    {
       recipe = new Recipe();
       recipe.setId(RECIPE_ID);
    }
    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipeById() throws Exception {

        // Given
        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(recipe));

        // When
        Recipe returnedRecipe = recipeService.findById(RECIPE_ID);

        // Then
        then(recipeRepository).should().findById(RECIPE_ID);
        assertNotNull(returnedRecipe);

    }

    @Test
    public void getRecipeByIdTestNotFound() throws Exception {

        // Given
        Optional<Recipe> recipeOptional = Optional.empty();
        given(recipeRepository.findById(anyLong())).willReturn(recipeOptional);

        // Then - Should throw exception
        assertThrows(NotFoundException.class, () -> recipeService.findById(RECIPE_ID));

    }

    @Test
    void getRecipes() {

        // Given
        Recipe recipe_2 = new Recipe();
        recipe_2.setId(2L);

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        recipes.add(recipe_2);

        given(recipeRepository.findAll()).willReturn(recipes);

        // When
        Set<Recipe> returnedRecipes = recipeService.getRecipes();

        // Then
        then(recipeRepository).should().findAll();
        assertThat(returnedRecipes.size()).isEqualTo(2);

    }


    @Test
    void deleteById() {

        // when
        recipeService.deleteById(RECIPE_ID);

        // Then
        then(recipeRepository).should().deleteById(RECIPE_ID);
    }
}