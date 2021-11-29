package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.command.UnitOfMeasureCommand;
import com.example.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.UnitOfMeasure;
import com.example.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    private UnitOfMeasureServiceImpl service;
    private Set<UnitOfMeasure> unitsOfMeasure;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        unitsOfMeasure = new HashSet<>();

        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,unitOfMeasureToUnitOfMeasureCommand);

    }

    @Test
    void listAllUoms() {

        // Given
        UnitOfMeasure uom1 = new UnitOfMeasure();
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom1.setId(1L);
        uom2.setId(2L);

        unitsOfMeasure.add(uom1);
        unitsOfMeasure.add(uom2);

        given(unitOfMeasureRepository.findAll()).willReturn(unitsOfMeasure);

        // When
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        // Then
        then(unitOfMeasureRepository).should().findAll();
        assertThat(commands.size()).isEqualTo(2);
    }
}