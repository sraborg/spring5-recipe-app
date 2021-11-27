package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.NotesCommand;
import com.example.spring5recipeapp.domain.Notes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    private final NotesCommandToNotes converter = new NotesCommandToNotes();
    private final NotesCommand command = new NotesCommand();
    private final Long ID = 1L;
    private final String RECIPE_NOTES = "recipe notes";

    @Test
    void convert() {
        // Given
        command.setId(ID);
        command.setRecipeNotes(RECIPE_NOTES);

        // When
        Notes notes = converter.convert(command);

        // Then
        assertNotNull(notes);
        assertThat(notes.getId()).isEqualTo(ID);
        assertThat(notes.getRecipeNotes()).isEqualTo(RECIPE_NOTES);

    }

    @Test
    void convertNullObject() {

        // When
        Notes notes = converter.convert(null);

        // Then
        assertNull(notes);
    }

    @Test
    void convertEmptyObject() {

        // When
        Notes notes = converter.convert(new NotesCommand());

        // Then
        assertNotNull(notes);
    }
}