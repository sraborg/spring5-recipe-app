package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.NotesCommand;
import com.example.spring5recipeapp.domain.Notes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    private final NotesToNotesCommand converter = new NotesToNotesCommand();
    private final Notes note = new Notes();
    private final Long ID = 1L;
    private final String RECIPE_NOTES = "recipe notes";

    @Test
    void convert() {

        // Given
        note.setId(ID);
        note.setRecipeNotes(RECIPE_NOTES);

        // When
        NotesCommand command = converter.convert(note);

        // Then
        assertNotNull(command);
        assertThat(command.getId()).isEqualTo(ID);
        assertThat(command.getRecipeNotes()).isEqualTo(RECIPE_NOTES);
    }

    @Test
    void convertNullObject() {
        NotesCommand command = converter.convert(null);

        assertNull(command);
    }

    @Test
    void convertEmptyOject() {
        NotesCommand command = converter.convert(new Notes());

        assertNotNull(command);
    }
}