package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.command.IngredientCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {

        if(source == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        command.setAmount(source.getAmount());
        command.setUom(uomConverter.convert(source.getUom()));
        //command.setRecipe(source.getRecipe());

        return command;
    }
}
