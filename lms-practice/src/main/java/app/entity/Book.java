package app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import app.util.View;

@Entity
public class Book {
	
	@JsonView(View.Book.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonView(View.Book.class)
	@Column(unique=true)
	private String name;
	
	@JsonView(View.Book.class)
	private int stock;
	
	@JsonView(View.BookDetail.class)
	@OneToMany(targetEntity = Ticket.class)
	@JoinColumn(name = "book_id")
	private List<Ticket> tickets;

	public Book() {
		super();
	}

	public List<User> getBorrowers() {
		List<User> borrowers = new ArrayList<User>();
		for (Ticket ticket : tickets) {
			borrowers.add(ticket.getBorrower());
		}
		return borrowers;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> borrowers) {
		this.tickets = borrowers;
	}

}
