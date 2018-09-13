package app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import app.util.View;

@Entity
public class Role {
	
	public static final int ADMIN = 0;
	public static final int LIBRARIAN = 1;
	public static final int BORROWER = 2;
	
	public static final String[] TITLE = new String[] {
		"ADMIN", "LIBRARIAN", "BORROWER"
	};

	@JsonView(View.Role.class)
	@Id
	private int id;

	@JsonView(View.Role.class)
	@Column(unique = true)
	private String name;
	
	public Role() {
		super();
	}

	public Role(int id) {
		super();
		this.id = id;
		this.name = TITLE[id];
	}

	public int getId() {
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

}
