package com.alura.literatura.Principal;

import java.util.Scanner;
import org.antlr.v4.runtime.InputMismatchException;

import java.util.List;
import java.util.*;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Datos;
import com.alura.literatura.model.DatosAutor;
import com.alura.literatura.model.DatosLibro;
import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;


public class Principal {

    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraELMenu(){

        var opcion = -10;
        try {
            
            while (opcion != 0) {
                var menu = """
                        1) - Buscar Libro por titulo.
                        2) - Listar libros Registrados.
                        3) - Listar autores resgistrados.
                        4) - Listar autores vivos en un determinado año.
                        5) - Listar libros por idioma.

                        0) - Salir
                        """;
                        System.out.println(menu);
                        opcion = input.nextInt();
                        input.nextLine();

                        switch (opcion) {
                            case 1:
                                buscarLibroPorTitulo();
                                break;
                            case 2:
                                listaDeLibrosRegistrados();
                                break;
                            case 3:
                                listaDeAutoresRegistrados();
                                break;
                            case 4:
                                listaDeAutoresVivosEnDeterminadoAno();
                                break;
                            case 5:
                                listaDeLibrosPorIdioma();
                                break;
                            case 0:
                                System.out.println("Saliendo de la aplicacion");
                            default:
                            System.out.println("Opcion invalida");
                                break;
                        }
            }

        } catch (InputMismatchException e) {
            System.out.println("ERROR! Ingrese un valor valido para la lista mostrada.");
            input.nextLine();//Limpiar el bufer de entrada
        }
    }

    
    private Datos buscarDatosLibros(){
        
        System.out.println("¡Que libro quiere buscar!");
        var libro = input.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+"?search="+libro.replace(" ","+"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        return datos;    
    }

    private Libro agregarLibroBD(DatosLibro datosLibro, Autor autor){
        Libro libro = new Libro(datosLibro, autor);
        return libroRepository.save(libro);
    }

    private void buscarLibroPorTitulo(){
        Datos datos = buscarDatosLibros();

        if (!datos.resultados().isEmpty()) {
            DatosLibro datosLibros = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibros.autor().get(0);
            Libro libroBuscado = libroRepository.findByTituloIgnoreCase(datosLibros.titulo());

            if (libroBuscado != null) {
                System.out.println(libroBuscado);
                System.out.println("Este Libro ya esta existente en la base de datos");

            } else {
                Autor autorBuscado = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());

                if (autorBuscado == null) {
                    Autor autor = new Autor(datosAutor);
                    autor.setNombre(datosAutor.nombre());
                    autorRepository.save(autor);
                    Libro libro = agregarLibroBD(datosLibros, autor);
                    System.out.println(libro);
                } else {
                    Libro libro = agregarLibroBD(datosLibros, autorBuscado);
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("Este libro no se encuentra, busque con otro");
        }
    }


    private void listaDeLibrosRegistrados() {
        libros = libroRepository.findAll();
        if (!libros.isEmpty()) {
            libros.stream().forEach(System.out::println);
        } else {
            System.out.println("No hay ningún Libro registrado.");
        }
    }

    private void listaDeAutoresRegistrados() {
        autores = autorRepository.findAll();
        if (!autores.isEmpty()) {
            autores.stream().forEach(System.out::println);
        } else {
            System.out.println("No hay ningún Autor registrado");
        }
    }

    private void listaDeAutoresVivosEnDeterminadoAno() {
        System.out.println("Ingrese el año en el que quiere saber los autores vivos: ");
        String fecha = input.nextLine();
        try {
            List<Autor> autoresVivosEnCiertaFecha = autorRepository.autorVivoEnDeterminadoAno(fecha);
            if (!autoresVivosEnCiertaFecha.isEmpty()) {
                autoresVivosEnCiertaFecha.stream().forEach(System.out::println);
            } else {
                System.out.println("No existen Autores vivos en esos años.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listaDeLibrosPorIdioma() {
        System.out.println("""
                1) Español (ES)
                2) Inglés (EN)
                3) Francés (FR)
                4) Portugués (PT)
                                
                5) Regresar al menú principal
                                
                Por favor, ingrese el número de opción para elegir el idioma de los libros a consultar:
                """);
        int opcion;
        opcion = input.nextInt();
        input.nextLine();
        switch (opcion) {
            case 1:
                libros = libroRepository.findByIdiomasContaining("es");
                if (!libros.isEmpty()) {
                    libros.stream().forEach(System.out::println);
                } else {
                    System.out.println("No hay ningún libro registrado en Español.");
                }
                break;
            case 2:
                libros = libroRepository.findByIdiomasContaining("en");
                if (!libros.isEmpty()) {
                    libros.stream().forEach(System.out::println);
                } else {
                    System.out.println("No hay ningún libro registrado en Inglés.");
                }
                break;
            case 3:
                libros = libroRepository.findByIdiomasContaining("fr");
                if (!libros.isEmpty()) {
                    libros.stream().forEach(System.out::println);
                } else {
                    System.out.println("No hay ningún libro registrado en Francés.");
                }
                break;
            case 4:
                libros = libroRepository.findByIdiomasContaining("pt");
                if (!libros.isEmpty()) {
                    libros.stream().forEach(System.out::println);
                } else {
                    System.out.println("No hay ningún libro registrado en Portugués.");
                }
                break;
            case 5:
                muestraELMenu();
                break;
            default:
                System.out.println("La opción seleccionada no es válida.");
        }
    }


}
