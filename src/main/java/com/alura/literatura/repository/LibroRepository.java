package com.alura.literatura.repository;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.literatura.model.Libro;


@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    Libro findByTituloIgnoreCase(String titulo);
    List<Libro> findByIdiomasContaining(String idioma);
}
