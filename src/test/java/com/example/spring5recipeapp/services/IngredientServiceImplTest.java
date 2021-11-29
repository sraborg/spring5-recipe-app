package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.example.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    private IngredientToIngredientCommand ingredientToIngredientCommand;

    private IngredientService ingredientService;


    @BeforeEach
    void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
    }

    private final Long RECIPE_ID = 1L;
    private final Long INGREDIENT_ID = 2L;

    @Test
    void findByRecipeIdAndIngredientId() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(4L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        given(recipeRepository.findById(RECIPE_ID)).willReturn(recipeOptional);

        // When
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID);

        // Then
        assertThat(ingredientCommand.getId()).isEqualTo(INGREDIENT_ID);
        assertThat(ingredientCommand.getRecipe_id()).isEqualTo(RECIPE_ID);
        then(recipeRepository).should().findById(RECIPE_ID);



    }
}