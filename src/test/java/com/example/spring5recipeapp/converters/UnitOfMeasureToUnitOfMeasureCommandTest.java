package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.UnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    private final String DESCRIPTION = "description";
    private final Long ID = 1L;

    private UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID);
        uom.setDescription(DESCRIPTION);

        UnitOfMeasureCommand command = converter.convert(uom);

        assertNotNull(command);
        assertThat(command.getId()).isEqualTo(ID);
        assertThat(command.getDescription()).isEqualTo(DESCRIPTION);

    }

    @Test
    void convertNullObject() {

        UnitOfMeasureCommand command = converter.convert(null);

        assertNull(command);
    }

    @Test
    void convertEmptyObject() {

        UnitOfMeasureCommand command = converter.convert(new UnitOfMeasure());

        assertNotNull(command);
    }
}