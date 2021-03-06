package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.IngredientCommand;

public interface IngredientService {

    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    public IngredientCommand saveIngredientCommand(IngredientCommand command);

    public void deleteById(Long recipeId, Long ingredientId);


}
