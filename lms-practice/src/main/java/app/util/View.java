package app.util;

public class View {
	public interface Role {}
	public interface User extends Role {}
	public interface UserDetail extends User {}
	
	public interface Ticket {}
	public interface TicketUser extends Ticket {}
	public interface TicketBook extends Ticket {}
	public interface TicketDetail extends TicketUser, TicketBook, Book, User {}
	
	public interface BookDetail extends Book {}
	public interface Book {}
}
