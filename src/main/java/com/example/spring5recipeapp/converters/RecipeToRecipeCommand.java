package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.RecipeCommand;
import com.example.spring5recipeapp.domain.*;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final IngredientToIngredientCommand ingredientConverter;
    private final CategoryToCategoryCommand categoryConverter;

    public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientConverter, CategoryToCategoryCommand categoryConverter, NotesToNotesCommand notesConverter) {
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
    }

    private final NotesToNotesCommand notesConverter;

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        command.setPrepTime(source.getPrepTime());
        command.setCookTime(source.getCookTime());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setDirections(source.getDirections());
        command.setIngredients(new HashSet<>());

        for (Ingredient ingredient : source.getIngredients()) {
            command.getIngredients().add(ingredientConverter.convert(ingredient));
        }
        command.setImage(source.getImage());
        command.setDifficulty(source.getDifficulty());
        command.setNotes(notesConverter.convert(source.getNotes()));
        command.setCategory(new HashSet<>());
        for (Category category : source.getCategories()) {
            command.getCategory().add(categoryConverter.convert(category));
        }


        return command;
    }
}
