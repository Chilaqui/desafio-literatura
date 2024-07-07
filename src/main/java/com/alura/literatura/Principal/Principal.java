package com.alura.literatura.Principal;

import java.util.ArrayList;
import java.util.Scanner;

import org.hibernate.mapping.List;

import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

public class Principal {

    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repository;
    private List<Autores> autores;

}
