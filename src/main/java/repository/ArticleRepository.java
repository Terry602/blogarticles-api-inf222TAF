package com.blog.blogarticlesapi.repository;

import com.blog.blogarticlesapi.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitreContainingIgnoreCaseOrContenuContainingIgnoreCase(String titre, String contenu);
    List<Article> findByCategorie(String categorie);

    List<Article> findByAuteur(String auteur);

    List<Article> findByDate(LocalDate date);

    List<Article> findByCategorieAndDate(String categorie, LocalDate date);

}





