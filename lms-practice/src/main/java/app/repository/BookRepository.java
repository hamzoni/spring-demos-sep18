package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.entity.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
	
}