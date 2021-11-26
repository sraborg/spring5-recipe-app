package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.command.UnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientCommandToIngredientTest {

    private final UnitOfMeasureCommandToUnitOfMeasure uomCommandConverter = new UnitOfMeasureCommandToUnitOfMeasure();
    private final IngredientCommandToIngredient converter = new IngredientCommandToIngredient(uomCommandConverter);

    private final Long INGREDIENT_ID = 1L;
    private final String INGREDIENT_DESCRIPTION = "ingredient description";
    private final BigDecimal AMOUNT = new BigDecimal(20.0);
    private final UnitOfMeasureCommand UNIT_OF_MEASURE = new UnitOfMeasureCommand();
    private final Long UOM_ID = 2L;
    private final String UOM_DESCRIPTION = "uom description";
    //private Recipe recipe;

    {
        UNIT_OF_MEASURE.setId(UOM_ID);
        UNIT_OF_MEASURE.setDescription(UOM_DESCRIPTION);
    }

    @Test
    void convert() {

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setDescription(INGREDIENT_DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setUom(UNIT_OF_MEASURE);

        Ingredient ingredient = converter.convert(ingredientCommand);

        assertNotNull(ingredient);
        assertThat(ingredient.getId()).isEqualTo(INGREDIENT_ID);
        assertThat(ingredient.getDescription()).isEqualTo(INGREDIENT_DESCRIPTION);
        assertThat(ingredient.getAmount()).isEqualTo(AMOUNT);

        // UOM
        assertThat(ingredient.getUom().getId()).isEqualTo(UOM_ID);
        assertThat(ingredient.getUom().getDescription()).isEqualTo(UOM_DESCRIPTION);

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