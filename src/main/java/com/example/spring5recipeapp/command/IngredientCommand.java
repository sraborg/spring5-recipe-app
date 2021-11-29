package com.example.spring5recipeapp.command;

import com.example.spring5recipeapp.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipe_id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
    private Recipe recipe;
}
