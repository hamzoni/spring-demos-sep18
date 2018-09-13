package app.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.entity.Book;
import app.entity.Ticket;
import app.entity.User;
import app.exception.ExceptionDuplicateRecord;
import app.exception.ExceptionRecordNotFound;
import app.repository.BookRepository;
import app.repository.UserRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	UserRepository userRepo;

	public void create(Book book) {

		// check if book's name is duplicated
		try {
			bookRepo.save(book);
		} catch (ConstraintViolationException e) {
			throw new ExceptionDuplicateRecord();
		}

	}

	@Override
	public void update(Book book) {
		bookRepo.save(book);
	}

	@Override
	public void delete(long bookId) {

		if (!bookRepo.existsById(bookId))
			throw new ExceptionRecordNotFound();

		Book book = bookRepo.findById(bookId).get();
		bookRepo.delete(book);

	}

	@Override
	public List<Book> list() {
		return bookRepo.findAll();
	}

	@Override
	public Book show(long bookId) {
		return bookRepo.findById(bookId).get();
	}

	@Override
	public List<User> viewBorrowers(long bookId) {
		// check if book is available
		if (!bookRepo.existsById(bookId))
			throw new ExceptionRecordNotFound();
		
		Book book = bookRepo.findById(bookId).get();
		return book.getBorrowers();
	}

	@Override
	public List<Ticket> viewTickets(long bookId) {
		// check if book is available
		if (!bookRepo.existsById(bookId))
			throw new ExceptionRecordNotFound();
		
		Book book = bookRepo.findById(bookId).get();
		return book.getTickets();
	}

	@Override
	public Page<Book> paginate(Pageable pageable) {
		return bookRepo.findAll(pageable);
	}
}
