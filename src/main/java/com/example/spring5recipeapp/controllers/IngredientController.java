package com.example.spring5recipeapp.controllers;

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

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show/ingredients")
    public String listIngredients(@PathVariable Long id, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/ingredient/list";
    }
}
