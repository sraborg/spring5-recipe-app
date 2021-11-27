package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.CategoryCommand;
import com.example.spring5recipeapp.domain.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private final CategoryToCategoryCommand converter = new CategoryToCategoryCommand();
    private final Long ID = 1L;
    private final String DESCRIPTION = "description";

    @Test
    void convert() {

        // Given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        // When
        CategoryCommand command  = converter.convert(category);

        // Then
        assertNotNull(command);
        assertThat(command.getId()).isEqualTo(ID);
        assertThat(command.getDescription()).isEqualTo(DESCRIPTION);

    }

    @Test
    void convertNullObjecte() {
        // When
        CategoryCommand command  = converter.convert(null);

        // Then
        assertNull(command);
    }

    @Test
    void convertEmptyObjecte() {
        // When
        CategoryCommand command  = converter.convert(new Category());

        // Then
        assertNotNull(command);
    }
}