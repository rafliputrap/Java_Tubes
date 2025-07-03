package com.authdemo.repository;

import com.authdemo.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(r.ingredients) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Recipe> searchByTitleOrIngredients(String q);

    List<Recipe> findTop5ByOrderByCreatedAtDesc();
}
