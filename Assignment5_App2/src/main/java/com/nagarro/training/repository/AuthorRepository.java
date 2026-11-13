package com.nagarro.training.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.training.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	public Optional<Author> findByName(String name);
	
}
