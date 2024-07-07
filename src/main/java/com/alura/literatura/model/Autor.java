package com.alura.literatura.model;

import java.util.List;
import java.util.stream.Collectors;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;


@Entity
public class Autor {
    private String nombre;
    private int anoDeNacimiento;
    private int anoDeFalleciminto;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> Libros;


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getAnoDeNacimiento() {
        return anoDeNacimiento;
    }
    public void setAnoDeNacimiento(int anoDeNacimiento) {
        this.anoDeNacimiento = anoDeNacimiento;
    }
    public int getAnoDeFalleciminto() {
        return anoDeFalleciminto;
    }
    public void setAnoDeFalleciminto(int anoDeFalleciminto) {
        this.anoDeFalleciminto = anoDeFalleciminto;
    }





    public Autor(){

    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.anoDeNacimiento = datosAutor.anoDeNacimiento();
        this.anoDeFalleciminto = datosAutor.anoDeFalleciminto();
    }

    @Override
    public String toString() {
        return "*** Autor***" + "\n" +
        "Nombre: " + nombre + "\n" +
        "Año de nacimiento: " + anoDeNacimiento + "\n" +
        "Año de fallecimiento: " + anoDeFalleciminto + "\n" +
        "Libros: " + Libro.stream()
    }


}
