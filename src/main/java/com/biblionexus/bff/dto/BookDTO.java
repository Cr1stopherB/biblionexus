package com.biblionexus.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDTO {

    private String id;

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("autor")
    private String autor;

    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("copias")
    private Integer copias;

    @JsonProperty("disponible")
    private Boolean disponible;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCopias() {
        return copias;
    }

    public void setCopias(Integer copias) {
        this.copias = copias;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}