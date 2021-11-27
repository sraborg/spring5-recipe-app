package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.command.UnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import com.example.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class IngredientToIngredientCommandTest {

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand UomConverter;

    @InjectMocks
    private IngredientToIngredientCommand converter;

    private final Long INGREDIENT_ID = 1L;
    private final String INGREDIENT_DESCRIPTION = "description";
    private final BigDecimal AMOUNT = new BigDecimal(20.0);
    private final UnitOfMeasure UNIT_OF_MEASURE = new UnitOfMeasure();
    private final UnitOfMeasureCommand UNIT_OF_MEASURE_COMMAND = new UnitOfMeasureCommand();

    @Test
    void convert() {

        // Given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setDescription(INGREDIENT_DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUom(UNIT_OF_MEASURE);
        given(UomConverter.convert(UNIT_OF_MEASURE)).willReturn(UNIT_OF_MEASURE_COMMAND);


        //ingredient.setRecipe(RECIPE);

        // When
        IngredientCommand command = converter.convert(ingredient);

        // Then
        then(UomConverter).should().convert(UNIT_OF_MEASURE);
        assertNotNull(command);
        assertThat(command.getId()).isEqualTo(INGREDIENT_ID);
        assertThat(command.getDescription()).isEqualTo(INGREDIENT_DESCRIPTION);
        assertThat(command.getAmount()).isEqualTo(AMOUNT);

    }

    @Test
    void convertNullObject() {
        IngredientCommand command = converter.convert(null);

        assertNull(command);
    }

    @Test
    void convertEmptyObject() {
        IngredientCommand command = converter.convert(new Ingredient());
        assertNotNull(command);
    }
}