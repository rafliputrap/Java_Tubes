package com.authdemo.controller;

import com.authdemo.model.Recipe;
import com.authdemo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/resep")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/latest")
    public List<Recipe> getLatestRecipes() {
        return recipeService.getLatestRecipes();
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestParam("q") String q) {
        return recipeService.searchByTitleOrIngredient(q);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe saved = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File kosong");
        }
        String uploadDir = "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), path);
        String imageUrl = "/uploads/" + filename;
        return ResponseEntity.ok(imageUrl);
    }
}
