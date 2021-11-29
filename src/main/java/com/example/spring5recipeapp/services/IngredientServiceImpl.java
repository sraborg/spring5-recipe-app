package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<IngredientCommand> ingredientsCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

            if (ingredientsCommandOptional.isPresent()) {
                return ingredientsCommandOptional.get();
            }

            log.error("Ingredient ID not found. ID " + ingredientId);
            return null;
        }


        log.error("Recipe ID not found. ID " + recipeId);
        return null;

//        if (recipeOptional.isPresent()) {
//            Optional<IngredientCommand> ingredientCommandOptional = recipeOptional.get().getIngredients().stream()
//                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
//                    .map(ingredientToIngredientCommand::convert)
//                    .findFirst();
//
//            return ingredientCommandOptional.orElseThrow(() -> {
//                log.error("Ingredient ID not found. ID " + ingredientId);
//                return new Exception();
//                });
//        } else {
//            log.error("Recipe ID not found. ID " + recipeId);
//        }
    }
}
