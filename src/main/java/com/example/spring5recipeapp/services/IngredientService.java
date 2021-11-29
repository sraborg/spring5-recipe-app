package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);


}
