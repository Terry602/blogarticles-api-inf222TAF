package com.blog.blogarticlesapi.controller;

import com.blog.blogarticlesapi.model.Article;
import com.blog.blogarticlesapi.repository.ArticleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    private final ArticleRepository repository;

    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article){
        Article savedArticle = repository.save(article);
        return ResponseEntity.status(201).body(savedArticle);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticles(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String auteur,
            @RequestParam(required = false) String date
    ) {

        if (categorie != null && date != null) {
            return ResponseEntity.ok(
                    repository.findByCategorieAndDate(categorie, LocalDate.parse(date))
            );
        }

        if (categorie != null) {
            return ResponseEntity.ok(repository.findByCategorie(categorie));
        }

        if (auteur != null) {
            return ResponseEntity.ok(repository.findByAuteur(auteur));
        }

        if (date != null) {
            return ResponseEntity.ok(repository.findByDate(LocalDate.parse(date)));
        }

        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return repository.findById(id)
                .map(article -> ResponseEntity.ok(article))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody Article updatedArticle
    )
    {

        return repository.findById(id).map(article -> {

            article.setTitre(updatedArticle.getTitre());
            article.setContenu(updatedArticle.getContenu());
            article.setCategorie(updatedArticle.getCategorie());
            article.setTags(updatedArticle.getTags());
            article.setAuteur(updatedArticle.getAuteur());
            article.setDate(updatedArticle.getDate());

            Article saved = repository.save(article);
            return ResponseEntity.ok(saved);

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.status(404).body("Article non trouvé");
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Article supprimé avec succès");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Article>> searchArticles(@RequestParam String query) {
        return ResponseEntity.ok(
                repository.findByTitreContainingIgnoreCaseOrContenuContainingIgnoreCase(query, query)
        );
    }
}


















