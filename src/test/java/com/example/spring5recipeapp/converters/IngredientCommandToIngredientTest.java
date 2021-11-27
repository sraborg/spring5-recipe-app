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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

    @Mock
    private UnitOfMeasureCommandToUnitOfMeasure uomCommandConverter;

    @InjectMocks
    private IngredientCommandToIngredient converter;
    private final Ingredient ingredient = new Ingredient();
    private final Long INGREDIENT_ID = 1L;
    private final String INGREDIENT_DESCRIPTION = "ingredient description";
    private final BigDecimal AMOUNT = new BigDecimal(20.0);
    private final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    private final UnitOfMeasure UNIT_OF_MEASURE = new UnitOfMeasure();

    @Test
    void convert() {

        // Given
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setDescription(INGREDIENT_DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setUom(unitOfMeasureCommand);

        given(uomCommandConverter.convert(any(UnitOfMeasureCommand.class))).willReturn(UNIT_OF_MEASURE);

        // When
        Ingredient ingredient = converter.convert(ingredientCommand);

        // Then
        then(uomCommandConverter).should().convert(unitOfMeasureCommand);
        assertNotNull(ingredient);
        assertThat(ingredient.getId()).isEqualTo(INGREDIENT_ID);
        assertThat(ingredient.getDescription()).isEqualTo(INGREDIENT_DESCRIPTION);
        assertThat(ingredient.getAmount()).isEqualTo(AMOUNT);
        assertThat(ingredient.getUom()).isEqualTo(UNIT_OF_MEASURE);

    }

    @Test
    public void convertNullObject() {
        Ingredient ingredient = converter.convert(null);

        assertNull(ingredient);
    }

    @Test
    public void convertEmptyObject() {
        Ingredient ingredient = converter.convert(new IngredientCommand());

        assertNotNull(ingredient);
    }
}