package com.alura.literatura.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idiomas;
    private Integer numeroDeDescargas;

    // Getters and Setters

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public String getIdiomas() {
        return idiomas;
    }
    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }
    public int getNumeroDeDescargas() {
        return numeroDeDescargas;
    }
    public void setNumeroDeDescargas(int numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Libro(){

    }

    public Libro(DatosLibro datosLibro, Autor autor){
        this.titulo = datosLibro.titulo();
        this.autor = autor;
        this.idiomas = datosLibro.idiomas().get(0);
        this. numeroDeDescargas = datosLibro.numeroDeDescargas();

    }
    @Override
    public String toString() {
        return "*** Libro ***\n" +
        "Titulo: " + titulo + "\n" +
        "Autor: " + autor.getNombre() + "\n" +
        "Idioma: " + idiomas + "\n" + 
        "Numero de descargas: " + numeroDeDescargas + "\n";
    }
    



}
