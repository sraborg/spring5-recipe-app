package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.command.RecipeCommand;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.services.ImageService;
import com.example.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    private final Long RECIPE_ID = 1L;

    @BeforeEach
    void setUp() {
        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void getImageForm() throws Exception {
        // Given
        RecipeCommand recipeCommand = new RecipeCommand();

        recipeCommand.setId(RECIPE_ID);
        given(recipeService.findCommandById(RECIPE_ID)).willReturn(recipeCommand);

        // When
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk());
                //.andExpect(model().attributeExists("recipe"));

        then(recipeService).should().findCommandById(RECIPE_ID);
    }

    @Test
    void handleImagePost() throws Exception {

        // Given
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt",
                        "text/plain", "example".getBytes());
        // When
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
        // Then
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        then(imageService).should().saveImageFile(anyLong(), any());
    }

    @Test
    void renderImageFromDB() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;
        for (byte b : s.getBytes()) {
            bytesBoxed[i] = b;
            i++;
        }


        recipeCommand.setImage(bytesBoxed);
        given(recipeService.findCommandById(RECIPE_ID)).willReturn(recipeCommand);

        // When / Then
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        System.out.println("-------------");
        System.out.println(new String(responseBytes, StandardCharsets.UTF_8));
        assertThat(responseBytes.length).isEqualTo(s.getBytes().length);
    }
}