package com.alura.literatura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.literatura.model.Autores;

public interface LibroRepository extends JpaRepository<Autores,Long> {

}
