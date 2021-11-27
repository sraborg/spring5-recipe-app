package com.example.spring5recipeapp.command;

import com.example.spring5recipeapp.domain.Difficulty;
import com.example.spring5recipeapp.domain.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredientCommands = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notesCommand;
    private Set<CategoryCommand> categoryCommands = new HashSet<>();
}
