package app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import app.api.ApiVersion;
import app.entity.Book;
import app.entity.Ticket;
import app.exception.ExceptionMalformParam;
import app.service.TicketService;
import app.util.DateUtil;
import app.util.Notification;
import app.util.View;

@RestController
@RequestMapping("tickets")
@ApiVersion("1")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@JsonView(View.TicketDetail.class)
	@GetMapping(value = {"list/expired", "list/expired/{id}"})
	public List<Ticket> showExpiredTickets(@PathVariable Optional<Long> id) {
		if (id.isPresent()) {
			 return ticketService.viewsExpiredTickets(id.get());
		}
		return ticketService.viewsExpiredTickets();
	}

	@JsonView(View.Ticket.class)
	@GetMapping("list/books/{type}")
	public ResponseEntity<List<Book>> listExtendBooks(@PathVariable String type) {
		
		List<Book> tickets = null;
		
		if (type.equals("extends")) {
			tickets = ticketService.viewRequestedExtendBooks();
		} else if (type.equals("lends")) {
			tickets = ticketService.viewLendedBooks();
		}
		
		if (tickets != null) return new ResponseEntity<>(tickets, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	

	@PutMapping("request/borrow")
	public Notification borrowBook(@RequestBody Map<String, Object> requests) throws Exception {
		if (!requests.containsKey("bookId") || !requests.containsKey("userId")) {
			throw new ExceptionMalformParam("bookId and userId required");
		}

		long bookId = ((Integer) requests.get("bookId")).longValue();
		long userId = ((Integer) requests.get("userId")).longValue();

		ticketService.borrow(bookId, userId);
		return new Notification(true, "Done");
	}

	@PutMapping("lend")
	public Notification lendBook(@RequestBody Map<String, Object> requests) {
		if (!requests.containsKey("libraryId") || !requests.containsKey("expireDate")) {
			throw new ExceptionMalformParam("libraryId and expireDate required");
		}

		long libraryId = ((Integer) requests.get("libraryId")).longValue();
		long timestamp = (long) requests.get("expireDate");

		LocalDateTime expireDate = DateUtil.miliToDate(timestamp);

		ticketService.lend(libraryId, expireDate);
		return new Notification(true, "Done");
	}

	@PutMapping("{ticketId}/request/extend")
	public Notification requestExtendBorrowDate(@PathVariable("ticketId") long id) {
		ticketService.requestExtendBorrowDate(id);

		return new Notification(true, "Request extend ticket successfully");
	}

	@PutMapping("{ticketId}/extend")
	public Notification extendBorrowDate(@PathVariable("ticketId") long id,
			@RequestBody Map<String, Object> requests) {
		if (!requests.containsKey("expireDate")) {
			throw new ExceptionMalformParam("expireDate required");
		}

		long timestamp = (long) requests.get("expireDate");
		LocalDateTime expireDate = DateUtil.miliToDate(timestamp);

		ticketService.extendBorrowDate(id, expireDate);

		return new Notification(true, "Extend ticket successfully");
	}

	@JsonView(View.Ticket.class)
	@GetMapping("lend")
	public List<Ticket> listLendTickets() {
		return ticketService.viewLendTickets();
	}

	@JsonView(View.Ticket.class)
	@GetMapping("borrow")
	public List<Ticket> listRequestBorrowTickets() {
		return ticketService.viewRequestedBorrowTickets();
	}

	@JsonView(View.Ticket.class)
	@GetMapping("extend")
	public List<Ticket> listRequestExtendTickets() {
		return ticketService.viewRequestedExtendTickets();
	}

}
