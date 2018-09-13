package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import app.api.ApiVersion;
import app.entity.Book;
import app.entity.Ticket;
import app.entity.User;
import app.service.UserService;
import app.util.Notification;
import app.util.View;

@RestController
@RequestMapping("users")
@ApiVersion("1")
public class UserController {

	@Autowired
	private UserService userService;
	
	@JsonView(View.Book.class)
	@GetMapping("/{userId}/books")
	public List<Book> viewBooks(@PathVariable long userId) {
		return userService.viewBorrowedBooks(userId);
	}
	
	@JsonView(View.TicketUser.class)
	@GetMapping("/{userId}/tickets")
	public List<Ticket> viewTickets(@PathVariable long userId) {
		return userService.viewBorrowTickets(userId);
	}

	@PostMapping
	public Notification createUser(@RequestBody User user) {
		userService.create(user);
		return new Notification(true, "Saved");
	}

	@PutMapping
	public Notification updateUser(@RequestBody User user) {
		userService.update(user);
		return new Notification(true, "Saved");
	}
	
	@DeleteMapping("{id}")
	public Notification deleteUser(@PathVariable("id") int userId) {
		userService.delete(userId);
		return new Notification(true, "Done");
	}
	
	@GetMapping("{id}")
	public User showUserInfo(@PathVariable("id") int userId) {
		return userService.show(userId);
	}
	
	@GetMapping
	@JsonView(View.User.class)
	public List<User> listUsers() {
		return userService.list();
	}
	
}
