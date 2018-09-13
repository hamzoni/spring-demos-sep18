package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.entity.Book;
import app.entity.Ticket;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long>, TicketRepositoryCustomized {
	@Query("FROM Ticket t WHERE t.action = :action")
	public List<Ticket> findTicketByActivity(@Param("action") int action);

	@Query(value = "FROM Book b WHERE EXISTS (FROM Ticket t"
			+ " WHERE t.action = :action AND t.book.id = b.id) ")
	public List<Book> findBookByActivity(@Param("action") int action);

	@Query(value = "SELECT * FROM ticket WHERE borrower_id = :userId AND book_id = :bookId)", nativeQuery = true)
	public Ticket findByCase(@Param("userId") long userId, @Param("bookId") long bookId);

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END "
			+ "FROM Ticket c WHERE borrower_id = :userId AND book_id = :bookId")
	public boolean existByCase(@Param("userId") long userId, @Param("bookId") long bookId);
}
