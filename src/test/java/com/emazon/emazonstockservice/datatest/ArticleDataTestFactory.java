package com.emazon.emazonstockservice.datatest;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Brand;

import java.util.HashSet;

public class ArticleDataTestFactory {

    public static Article createValidArticle() {

        return new Article(
                1L,
                "Samsung Galaxy S21",
                "Smartphone con pantalla AMOLED y cámaras avanzadas.",
                10,
                200.0,
                new HashSet<>(),
                new Brand());
    }

    public static Article createArticleWithInvalidDescription() {
        return new Article(
                1L,
                "Samsung LED TV",
                "",
                10,
                200.0,
                new HashSet<>(),
                new Brand()
        );
    }

    public static Article createArticleWithInvalidName() {
        return new Article(
                1L,
                "",
                "Televisor con tecnología Quantum Dot para una calidad de imagen superior.",
                10,
                200.0,
                new HashSet<>(),
                new Brand()
        );
    }

    public static Article createArticleWithQuantityIsNegative() {
        return new Article(
                1L,
                "Samsung Galaxy Note 20",
                "Smartphone con lápiz óptico y alto rendimiento.",
                -10,
                200.0,
                new HashSet<>(),
                new Brand()
        );
    }


    public static Article createArticleWithPriceIsNegative() {
        return new Article(
                1L,
                "Samsung Galaxy Note 20",
                "Smartphone con lápiz óptico y alto rendimiento.",
                10,
                -200.0,
                new HashSet<>(),
                new Brand()
        );
    }



}
