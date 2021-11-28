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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class RecipeToRecipeCommandTest {

    @Mock
    private IngredientToIngredientCommand ingredientConverter;
    @Mock
    private CategoryToCategoryCommand categoryConverter;
    @Mock
    private NotesToNotesCommand notesConverter;

    @InjectMocks
    private RecipeToRecipeCommand recipeConverter;

    private final Long ID = 1L;
    private final String DESCRIPTION = "description";
    private final Integer PREP_TIME = 1;
    private final Integer COOK_Time = 2;
    private final Integer SERVINGS = 3;
    private final String SOURCE = "source";
    private final String URL = "url";
    private final String DIRECTIONS = "directions";
    private final Set<Ingredient> INGREDIENTS = new HashSet<>();
    private final Byte[] IMAGE = new Byte[5];
    private final Difficulty DIFFICULTY = Difficulty.HARD;
    private final Notes NOTES = new Notes();
    private final Set<Category> CATEGORIES = new HashSet<>();
    private final IngredientCommand ingredientCommand = new IngredientCommand();
    private final CategoryCommand categoryCommand = new CategoryCommand();
    private final NotesCommand notesCommand = new NotesCommand();

    @BeforeEach
    void setUp() {
        lenient().when(ingredientConverter.convert(any())).thenReturn(ingredientCommand);
        lenient().when(categoryConverter.convert(any())).thenReturn(categoryCommand);
        lenient().when(notesConverter.convert(any())).thenReturn(notesCommand);
    }

    @Test
    void convert() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_Time);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setImage(IMAGE);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setNotes(NOTES);
        recipe.setCategories(CATEGORIES);

        // When
        RecipeCommand command = recipeConverter.convert(recipe);

        // Then
        assertNotNull(command);
        assertThat(command.getId()).isEqualTo(ID);
        assertThat(command.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(command.getPrepTime()).isEqualTo(PREP_TIME);
        assertThat(command.getCookTime()).isEqualTo(COOK_Time);
        assertThat(command.getServings()).isEqualTo(SERVINGS);
        assertThat(command.getSource()).isEqualTo(SOURCE);
        assertThat(command.getUrl()).isEqualTo(URL);
        assertThat(command.getDirections()).isEqualTo(DIRECTIONS);
        assertThat(command.getImage()).isEqualTo(IMAGE);

        for (Ingredient ingredient: INGREDIENTS) {
            then(ingredientConverter).should().convert(ingredient);
        }

        assertThat(command.getDifficulty()).isEqualTo(DIFFICULTY);

        then(notesConverter).should().convert(NOTES);

        for (Category  category : CATEGORIES) {
            then(categoryConverter).should().convert(category);
        }


    }

    @Test
    void convertNullObject() {
        // When
        RecipeCommand command = recipeConverter.convert(null);

        // Then
        assertNull(command);

    }

    @Test
    void convertEmptyObject() {

        // When
        RecipeCommand command = recipeConverter.convert(new Recipe());

        // Then
        assertNotNull(command);
    }
}