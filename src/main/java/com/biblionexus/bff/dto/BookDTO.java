package com.biblionexus.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDTO {

    private Long id;
    private String titulo;
    private String autor;
    private String categoria;
    private boolean disponible;

    @JsonProperty("created_at")
    private String createdAt;

    public BookDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
