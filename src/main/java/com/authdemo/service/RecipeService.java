package com.authdemo.service;

import com.authdemo.model.Recipe;
import com.authdemo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe createRecipe(Recipe recipe) {
        recipe.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getLatestRecipes() {
        return recipeRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public List<Recipe> searchByTitleOrIngredient(String q) {
        return recipeRepository.searchByTitleOrIngredients(q);
    }
}
