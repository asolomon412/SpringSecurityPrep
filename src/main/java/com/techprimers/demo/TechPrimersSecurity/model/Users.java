package com.techprimers.demo.TechPrimersSecurity.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "active")
	private int active;
	/*
	 * Cascading consists in propagating the Parent entity state transition to one
	 * or more Child entities, and it can be used for both unidirectional and
	 * bidirectional associations.
	 * 
	 * Fetch: Sometimes you have two entities and there's a relationship between
	 * them. For example, you might have an entity called University and another
	 * entity called Student.
	 * 
	 * The University entity might have some basic properties such as id, name,
	 * address, etc. as well as a property called students
	 * 
	 * Now when you load a University from the database, JPA loads its id, name, and
	 * address fields for you. But you have two options for students: to load it
	 * together with the rest of the fields (i.e. eagerly) or to load it on-demand
	 * (i.e. lazily) when you call the university's getStudents() method.
	 * 
	 * When a university has many students it is not efficient to load all of its
	 * students with it when they are not needed. So in suchlike cases, you can
	 * declare that you want students to be loaded when they are actually needed.
	 * This is called lazy loading.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	/*
	 * A collection that contains no duplicate elements. More formally, sets contain
	 * no pair of elements e1 and e2 such that e1.equals(e2), and at most one null
	 * element. As implied by its name, this interface models the mathematical set
	 * abstraction.
	 * 
	 */
	private Set<Role> roles;

	public Users() {
	}

	// this is a constructor used to make a copy from the child class of Users (CustomUserDetails)
	public Users(Users users) {
		this.active = users.getActive();
		this.email = users.getEmail();
		this.roles = users.getRoles();
		this.name = users.getName();
		this.lastName = users.getLastName();
		this.id = users.getId();
		this.password = users.getPassword();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
