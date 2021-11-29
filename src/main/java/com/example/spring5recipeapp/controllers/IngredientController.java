package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.services.IngredientService;
import com.example.spring5recipeapp.services.RecipeService;
import com.example.spring5recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable Long recipe_id, @PathVariable Long id, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipe_id, id));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipe_id}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("Saved Recipe ID: " + savedCommand.getRecipeId());
        log.debug("Saved Ingredient ID: " + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
}
