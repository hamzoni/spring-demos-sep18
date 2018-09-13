package app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import app.util.View;

@Entity
@Component
public class User {

	@JsonView(View.User.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonView(View.User.class)
	@Column(unique = true)
	private String username;

	@JsonView(View.User.class)
	@Column(unique = true)
	private String email;

	@JsonIgnore
	private String password;

	@JsonView(View.UserDetail.class)
	@OneToMany(targetEntity = Ticket.class)
	@JoinColumn(name = "borrower_id")
	private List<Ticket> tickets; // borrowed books

	@JsonView(View.User.class)
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Role> roles;

	public User() {
		super();
	}

	public User(String username, String email, String password, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	public List<Book> getBooks() {
		List<Book> books = new ArrayList<Book>();
		for (Ticket ticket : tickets) {
			books.add(ticket.getBook());
		}
		return books;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
