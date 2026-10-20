package com.biblionexus.bff.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @GetMapping
    public List<Map<String, Object>> getBooks() {
        List<Map<String, Object>> books = new ArrayList<>();

        Map<String, Object> b1 = new HashMap<>();
        b1.put("id", 1);
        b1.put("title", "Cien Años de Soledad");
        b1.put("author", "Gabriel García Márquez");
        b1.put("available", true);

        Map<String, Object> b2 = new HashMap<>();
        b2.put("id", 2);
        b2.put("title", "El Principito");
        b2.put("author", "Antoine de Saint-Exupéry");
        b2.put("available", true);

        Map<String, Object> b3 = new HashMap<>();
        b3.put("id", 3);
        b3.put("title", "Don Quijote de la Mancha");
        b3.put("author", "Miguel de Cervantes");
        b3.put("available", false);

        books.add(b1);
        books.add(b2);
        books.add(b3);

        return books;
    }
}