package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.UnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private final String DESCRIPTION = "description";
    private final Long ID = 1L;

    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convert() throws Exception {

        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(ID);
        command.setDescription(DESCRIPTION);

        // When
        UnitOfMeasure uom = converter.convert(command);

        // Then
        assertNotNull(uom);
        assertThat(uom.getId()).isEqualTo(ID);
        assertThat(uom.getDescription()).isEqualTo(DESCRIPTION);

    }

    @Test
    void convertEmptyObject() throws Exception {

        // When
        UnitOfMeasure uom = converter.convert(new UnitOfMeasureCommand());

        // Then
        assertNotNull(uom);

    }

    @Test
    void convertNullObject() throws Exception {

        // When
        UnitOfMeasure uom = converter.convert(null);

        // Then
        assertNull(uom);

    }
}