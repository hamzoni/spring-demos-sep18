package app.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import app.entity.Ticket;
import app.entity.User;
import app.util.DataUtil;

public class TicketRepositoryImpl implements TicketRepositoryCustomized {
	
	@Autowired
	UserRepository userRepo;

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<Ticket> getExpiredTickets() {
		
		LocalDateTime now = LocalDateTime.now();
		
		String query = "FROM Ticket e WHERE e.expireDate < :now";
		
		List<Ticket> tickets = DataUtil.castList(Ticket.class, em
			.createQuery(query)
			.setParameter("now", now)
			.getResultList());
		
		return tickets;
	}

	@Override
	public List<Ticket> getExpiredTickets(long userId) {
		
		Optional<User> user = userRepo.findById(userId);
		
		if (!user.isPresent()) return new ArrayList<Ticket>();
		
		LocalDateTime now = LocalDateTime.now();
		
		String query = "FROM Ticket e WHERE e.expireDate < :now AND e.borrower = :user";
		
		List<Ticket> tickets = DataUtil.castList(Ticket.class, em
			.createQuery(query)
			.setParameter("now", now)
			.setParameter("user", user.get())
			.getResultList());
		
		return tickets;
		
	}

}
