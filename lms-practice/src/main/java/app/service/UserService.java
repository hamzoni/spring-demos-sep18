package app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import app.entity.Book;
import app.entity.Ticket;
import app.entity.User;

/*
 * Use cases: 18, 16, 17, 15, 19, 13, 24
 */

public interface UserService extends UserDetailsService {
	// UC18
	void create(User user);

	// UC16
	void update(User user);

	// UC17
	void delete(long userId);

	// UC15
	List<User> list();

	// UC19
	User show(long userId);

	// UC13
	List<Book> viewBorrowedBooks(long borrowerId);

	// UC24
	List<Ticket> viewBorrowTickets(long borrowerId);
	
}
