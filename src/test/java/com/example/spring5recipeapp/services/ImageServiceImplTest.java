package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @BeforeEach
    void setUp() {
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws Exception{
        // Given
        final Long RECIPE_ID = 1L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Example".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        given(recipeRepository.findById(RECIPE_ID)).willReturn(recipeOptional);

        imageService.saveImageFile(RECIPE_ID, multipartFile);

        // Then
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        then(recipeRepository).should().save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertThat(multipartFile.getBytes().length).isEqualTo(savedRecipe.getImage().length);

    }
}