package com.nagarro.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.training.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
}
