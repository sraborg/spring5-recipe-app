package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    private final RecipeRepository recipeRepository;

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("Image file recieved");

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] byteObject = new Byte[file.getBytes().length];

            int i =0;
            for (byte b : file.getBytes()) {
                byteObject[i] = b;
                i++;
            }

            recipe.setImage(byteObject);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            // todo handle better
            log.error("Error Occurred ", e);

            e.printStackTrace();
        }
    }
}
