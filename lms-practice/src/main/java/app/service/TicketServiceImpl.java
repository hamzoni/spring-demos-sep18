package app.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Book;
import app.entity.Ticket;
import app.entity.User;
import app.exception.ExceptionDuplicateRecord;
import app.exception.ExceptionInvalidBusinessProcess;
import app.exception.ExceptionInvalidParam;
import app.exception.ExceptionOutOfBook;
import app.exception.ExceptionRecordNotFound;
import app.repository.BookRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BookRepository bookRepo;

	@Override
	@Transactional
	public synchronized void borrow(long bookId, long userId) {

		// check if items are available
		if (!bookRepo.existsById(bookId)) {
			throw new ExceptionRecordNotFound("Book #" + bookId);
		}

		if (!userRepo.existsById(userId)) {
			throw new ExceptionRecordNotFound("User #" + bookId);
		}

		// check if book is already booked by the user
		if (ticketRepo.existByCase(userId, bookId)) {
			throw new ExceptionDuplicateRecord("User has already borrowed this book");
		}

		// prepare data to insert new records
		Book book = bookRepo.findById(bookId).get();
		User user = userRepo.findById(userId).get();

		if (book.getStock() == 0)
			throw new ExceptionOutOfBook();

		book.setStock(book.getStock() - 1);
		bookRepo.save(book);

		// check if books are still available
		Ticket ticket = new Ticket();
		ticket.setBook(book);
		ticket.setBorrower(user);
		ticket.setAction(Ticket.REQUEST.BORROW);

		ticketRepo.save(ticket);
	}

	@Override
	public void lend(long libraryId, LocalDateTime expireDate) {

		// check if ticket is available
		if (!ticketRepo.existsById(libraryId))
			throw new ExceptionRecordNotFound();

		Ticket ticket = ticketRepo.findById(libraryId).get();

		// ticket must be in BORROW process
		if (ticket.getAction() != Ticket.REQUEST.BORROW) {
			throw new ExceptionInvalidBusinessProcess("User has already borrowed this book");
		}

		ticket.setAction(Ticket.REQUEST.NONE);

		// set expire date for book
		int borrowDays = 30;
		LocalDateTime dateToExpire = LocalDateTime.now().plusDays(borrowDays);
		ticket.setExpireDate(dateToExpire);
		ticketRepo.save(ticket);
	}

	@Override
	public void requestExtendBorrowDate(long libraryId) {
		// check if ticket is available
		if (!ticketRepo.existsById(libraryId))
			throw new ExceptionRecordNotFound();

		Ticket ticket = ticketRepo.findById(libraryId).get();

		// ticket must be in NONE process
		if (ticket.getAction() == Ticket.REQUEST.BORROW) {
			throw new ExceptionInvalidBusinessProcess("Book is in borrowing process");
		}
		if (ticket.getAction() == Ticket.REQUEST.EXTEND) {
			throw new ExceptionInvalidBusinessProcess("Book is already in process of extending");
		}

		ticket.setAction(Ticket.REQUEST.EXTEND);
		ticketRepo.save(ticket);

	}

	@Override
	public void extendBorrowDate(long libraryId, LocalDateTime expireDate) {
		// check if ticket is available
		if (!ticketRepo.existsById(libraryId))
			throw new ExceptionRecordNotFound();

		// check if expire date is valid
		if (expireDate.isBefore(LocalDateTime.now())) {
			throw new ExceptionInvalidParam("Expire date must be later than now");
		}

		Ticket ticket = ticketRepo.findById(libraryId).get();
		
		// ticket must be in EXTEND process
		if (ticket.getAction() != Ticket.REQUEST.EXTEND) {
			throw new ExceptionInvalidBusinessProcess("No extend request found");
		}

		ticket.setExpireDate(expireDate);
		ticket.setExtendDate(LocalDateTime.now());
		ticket.setAction(Ticket.REQUEST.NONE);

		ticketRepo.save(ticket);
	}

	@Override
	public List<Book> viewLendedBooks() {
		return ticketRepo.findBookByActivity(Ticket.REQUEST.NONE);
	}

	@Override
	public List<Book> viewRequestedBorrowBooks() {
		return ticketRepo.findBookByActivity(Ticket.REQUEST.BORROW);
	}

	@Override
	public List<Book> viewRequestedExtendBooks() {
		return ticketRepo.findBookByActivity(Ticket.REQUEST.EXTEND);
	}

	@Override
	public List<Ticket> viewLendTickets() {
		return ticketRepo.findTicketByActivity(Ticket.REQUEST.NONE);
	}

	@Override
	public List<Ticket> viewRequestedBorrowTickets() {
		return ticketRepo.findTicketByActivity(Ticket.REQUEST.BORROW);
	}

	@Override
	public List<Ticket> viewRequestedExtendTickets() {
		return ticketRepo.findTicketByActivity(Ticket.REQUEST.EXTEND);
	}

	@Override
	public List<Ticket> viewsExpiredTickets() {
		return ticketRepo.getExpiredTickets();
	}

	@Override
	public List<Ticket> viewsExpiredTickets(Long userId) {
		return ticketRepo.getExpiredTickets(userId);
	}

}
