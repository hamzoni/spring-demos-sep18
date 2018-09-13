package app.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonView;

import app.util.View;

/*
 * This class contains the relationship between Book and User
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "borrower_id", "book_id" }) })
public class Ticket {

	public static class REQUEST {
		public static int NONE = 0;
		public static int EXTEND = 1; // request extend borrow date
		public static int BORROW = 2; // request borrow book
	}
	
	@JsonView(View.Ticket.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonView(View.TicketUser.class)
	@ManyToOne
	private Book book;

	@JsonView(View.TicketBook.class)
	@ManyToOne
	private User borrower;

	@JsonView(View.Ticket.class)
	private LocalDateTime borrowDate = LocalDateTime.now();

	@JsonView(View.Ticket.class)
	private LocalDateTime expireDate;

	@JsonView(View.Ticket.class)
	private LocalDateTime extendDate; // last borrow date

	@JsonView(View.Ticket.class)
	private int action = Ticket.REQUEST.NONE;

	public Ticket() {
		super();
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getBorrower() {
		return borrower;
	}

	public void setBorrower(User borrower) {
		this.borrower = borrower;
	}

	public LocalDateTime getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDateTime borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDateTime getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDateTime expireDate) {
		this.expireDate = expireDate;
	}

	public LocalDateTime getExtendDate() {
		return extendDate;
	}

	public void setExtendDate(LocalDateTime extendDate) {
		this.extendDate = extendDate;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
