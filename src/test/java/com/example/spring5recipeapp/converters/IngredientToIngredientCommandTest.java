package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import com.example.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientToIngredientCommandTest {

    private final UnitOfMeasureToUnitOfMeasureCommand UomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
    private final IngredientToIngredientCommand converter = new IngredientToIngredientCommand(UomConverter);

    private final Long INGREDIENT_ID = 1L;
    private final String INGREDIENT_DESCRIPTION = "description";
    private final BigDecimal AMOUNT = new BigDecimal(20.0);
    private final UnitOfMeasure UNIT_OF_MEASURE = new UnitOfMeasure();
    private final Long UOM_ID = 2L;
    private final String UOM_DESCRIPTION = "uom_description";
    //private Recipe RECIPE = new Recipe();
    {
        UNIT_OF_MEASURE.setId(UOM_ID);
        UNIT_OF_MEASURE.setDescription(UOM_DESCRIPTION);
    }

    @Test
    void convert() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setDescription(INGREDIENT_DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUom(UNIT_OF_MEASURE);
        //ingredient.setRecipe(RECIPE);

        IngredientCommand command = converter.convert(ingredient);

        assertNotNull(command);
        assertThat(command.getId()).isEqualTo(INGREDIENT_ID);
        assertThat(command.getDescription()).isEqualTo(INGREDIENT_DESCRIPTION);
        assertThat(command.getAmount()).isEqualTo(AMOUNT);

        // TEST UOM
        assertThat(command.getUom().getId()).isEqualTo(UOM_ID);
        assertThat(command.getUom().getDescription()).isEqualTo(UOM_DESCRIPTION);
        //assertThat(command.getRecipe()).isEqualTo(RECIPE);
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