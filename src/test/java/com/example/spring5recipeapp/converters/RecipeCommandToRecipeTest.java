package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.CategoryCommand;
import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.command.NotesCommand;
import com.example.spring5recipeapp.command.RecipeCommand;
import com.example.spring5recipeapp.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class RecipeCommandToRecipeTest {

    @Mock
    private IngredientCommandToIngredient ingredientConverter;

    @Mock
    private NotesCommandToNotes notesConverter;

    @Mock
    private CategoryCommandToCategory categoryConverter;


    @InjectMocks
    private RecipeCommandToRecipe recipeConverter;

    private final Long ID = 1L;
    private final String DESCRIPTION = "description";
    private final Integer PREP_TIME = 1;
    private final Integer COOK_Time = 2;
    private final Integer SERVINGS = 3;
    private final String SOURCE = "source";
    private final String URL = "url";
    private final String DIRECTIONS = "directions";
    private final Set<IngredientCommand> INGREDIENT_COMMANDS = new HashSet<>();
    private final Byte[] IMAGE = new Byte[5];
    private final Difficulty DIFFICULTY = Difficulty.HARD;
    private final NotesCommand NOTES_COMMAND = new NotesCommand();
    private final Set<CategoryCommand> CATEGORY_COMMANDS = new HashSet<>();
    private final Notes notes = new Notes();
    private final Category category = new Category();
    private final Ingredient ingredient = new Ingredient();

    @BeforeEach
    void setUp() {
        lenient().when(notesConverter.convert(any())).thenReturn(notes);
        lenient().when(categoryConverter.convert(any())).thenReturn(category);
        lenient().when(ingredientConverter.convert(any())).thenReturn(ingredient);
    }

    @Test
    void convert() {

        // Given
        RecipeCommand command = new RecipeCommand();
        command.setId(ID);
        command.setDescription(DESCRIPTION);
        command.setPrepTime(PREP_TIME);
        command.setCookTime(COOK_Time);
        command.setServings(SERVINGS);
        command.setSource(SOURCE);
        command.setUrl(URL);
        command.setDirections(DIRECTIONS);
        command.setIngredients(INGREDIENT_COMMANDS);
        command.setImage(IMAGE);
        command.setDifficulty(DIFFICULTY);
        command.setNotes(NOTES_COMMAND);
        command.setCategory(CATEGORY_COMMANDS);

        // When
        Recipe recipe = recipeConverter.convert(command);

        // Then
        assertNotNull(recipe);
        assertThat(recipe.getId()).isEqualTo(1L);
        assertThat(recipe.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(recipe.getPrepTime()).isEqualTo(PREP_TIME);
        assertThat(recipe.getCookTime()).isEqualTo(COOK_Time);
        assertThat(recipe.getServings()).isEqualTo(SERVINGS);
        assertThat(recipe.getSource()).isEqualTo(SOURCE);
        assertThat(recipe.getUrl()).isEqualTo(URL);
        assertThat(recipe.getDirections()).isEqualTo(DIRECTIONS);

        for (IngredientCommand ingredientCommand : INGREDIENT_COMMANDS) {
            then(ingredientConverter).should().convert(ingredientCommand);
        }
        assertThat(recipe.getImage()).isEqualTo(IMAGE);
        assertThat(recipe.getDifficulty()).isEqualTo(DIFFICULTY);

        then(notesConverter).should().convert(NOTES_COMMAND);

        for (CategoryCommand categoryCommand : CATEGORY_COMMANDS) {
            then(categoryConverter).should().convert(categoryCommand);
        }
    }

    @Test
    void convertNullObject() {
        // When
        Recipe recipe = recipeConverter.convert(null);

        // Then
        assertNull(recipe);
    }

    @Test
    void convertEmptyObject() {

        // When
        Recipe recipe = recipeConverter.convert(new RecipeCommand());

        // Then
        assertNotNull(recipe);
    }
}