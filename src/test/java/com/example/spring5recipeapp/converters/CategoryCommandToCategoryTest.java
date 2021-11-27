package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.CategoryCommand;
import com.example.spring5recipeapp.domain.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private final CategoryCommandToCategory converter = new CategoryCommandToCategory();
    private final Long ID = 1L;
    private final String DESCRIPTION = "description";
    @Test
    void convert() {
        // Given
        CategoryCommand command = new CategoryCommand();
        command.setId(ID);
        command.setDescription(DESCRIPTION);


        // When
        Category category = converter.convert(command);

        // Then
        assertNotNull(category);
        assertThat(category.getId()).isEqualTo(ID);
        assertThat(category.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void convertNullObject() {

        // When
        Category category = converter.convert(null);

        assertNull(category);

    }

    @Test
    void convertEmptyObject() {

        // When
        Category category = converter.convert(new CategoryCommand());

        assertNotNull(category);
    }
}