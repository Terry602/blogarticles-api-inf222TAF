package com.blog.blogarticlesapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "Le contenu est obligatoire")
    @Column(length = 1000)
    private String contenu;

    @NotBlank(message = "L'auteur est obligatoire")
    private String auteur;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotBlank(message = "La catégorie est obligatoire")
    private String categorie;

    private String tags;
}
