package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dto.UserDetailsDto;
import app.entity.Book;
import app.entity.Ticket;
import app.entity.User;
import app.exception.ExceptionRecordNotFound;
import app.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	public void create(User user) {
		userRepo.save(user);
	}

	@Override
	public void update(User user) {
		userRepo.save(user);
	}

	@Override
	public void delete(long userId) {

		if (!userRepo.existsById(userId))
			throw new ExceptionRecordNotFound();

		User user = userRepo.findById(userId).get();
		userRepo.delete(user);

	}

	@Override
	public List<User> list() {
		return userRepo.findAll();
	}

	@Override
	public User show(long userId) {
		return userRepo.findById(userId).get();
	}

	@Override
	public List<Book> viewBorrowedBooks(long borrowerId) {

		// check if user is available
		if (!userRepo.existsById(borrowerId))
			throw new ExceptionRecordNotFound();

		User borrower = userRepo.findById(borrowerId).get();
		return borrower.getBooks();
	}

	@Override
	public List<Ticket> viewBorrowTickets(long borrowerId) {
		// check if user is available
		if (!userRepo.existsById(borrowerId))
			throw new ExceptionRecordNotFound();

		User borrower = userRepo.findById(borrowerId).get();
		return borrower.getTickets();
	}

	////// This method is used for authentication 2.0 process
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		return new UserDetailsDto(user);
	}

}
