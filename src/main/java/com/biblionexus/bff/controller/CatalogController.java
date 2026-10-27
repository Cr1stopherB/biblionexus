package com.biblionexus.bff.controller;

import com.biblionexus.bff.dto.BookDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.DELETE, RequestMethod.OPTIONS })
public class CatalogController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon-key}")
    private String supabaseAnonKey;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks() {
        String url = supabaseUrl.replaceAll("/+$", "") + "/rest/v1/libros?select=*&order=created_at.desc";

        HttpHeaders headers = createSupabaseHeaders();

        try {
            ResponseEntity<List<BookDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<List<BookDTO>>() {
                    });
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody BookDTO book) {
        String url = supabaseUrl.replaceAll("/+$", "") + "/rest/v1/libros";

        HttpHeaders headers = createSupabaseHeaders();
        headers.set("Prefer", "return=representation");

        // Map exacto con las columnas que existen en tu tabla de Supabase
        Map<String, Object> payload = new HashMap<>();
        payload.put("titulo", book.getTitulo());
        payload.put("autor", book.getAutor());
        payload.put("categoria", book.getCategoria() != null ? book.getCategoria() : "General");
        // Convertimos las copias a boolean para la columna 'disponible' (si copias > 0
        // -> true)
        payload.put("disponible", book.getCopias() == null || book.getCopias() > 0);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
        } catch (Exception e) {
            System.err.println("Error en Supabase: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Error al guardar libro en Supabase: " + e.getMessage());
        }
    }

    private HttpHeaders createSupabaseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseAnonKey);
        headers.set("Authorization", "Bearer " + supabaseAnonKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}