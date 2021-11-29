package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
