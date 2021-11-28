package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.RecipeCommand;
import com.example.spring5recipeapp.converters.RecipeCommandToRecipe;
import com.example.spring5recipeapp.converters.RecipeToRecipeCommand;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RecipeServiceIT {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    private final String DESCRIPTION = "new description";

    @Test
    @Transactional
    void saveRecipeCommand() {

        // Given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        // When
        testRecipeCommand.setDescription(DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        // Then
        assertThat(savedRecipeCommand.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(savedRecipeCommand.getId()).isEqualTo(testRecipeCommand.getId());
        assertThat(savedRecipeCommand.getCategory().size()).isEqualTo(testRecipeCommand.getCategory().size());
        assertThat(savedRecipeCommand.getIngredients().size()).isEqualTo(testRecipeCommand.getIngredients().size());
    }
}