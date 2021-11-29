package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.RecipeCommand;
import com.example.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findCommandById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
