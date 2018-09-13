package app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import app.entity.Book;
import app.entity.Ticket;
import app.entity.User;

public interface BookService {
	// UC3
	void create(Book book);

	// UC4
	void update(Book book);

	// UC5
	void delete(long bookId);

	// UC7
	List<Book> list();

	// UC6
	Book show(long bookId);
	
	// UC22
	List<User> viewBorrowers(long bookId);
	
	// UC22
	List<Ticket> viewTickets(long bookId);

	Page<Book> paginate(Pageable pageable);

}
