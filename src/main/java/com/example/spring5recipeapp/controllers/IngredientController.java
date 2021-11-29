package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.services.IngredientService;
import com.example.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{recipe_id}/show/ingredients")
    public String listIngredients(@PathVariable Long recipe_id, Model model) {
        log.debug("Getting ingredient list for recipe " + recipe_id);

        // Use command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(recipe_id));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/show")
    public String showIngredient(@PathVariable Long recipe_id, @PathVariable Long id, Model model) {


        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipe_id, id));
        return "recipe/ingredient/show";
    }
}
