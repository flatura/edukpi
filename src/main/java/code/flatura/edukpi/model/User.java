package code.flatura.edukpi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User extends AbstractEntity {

	@NotNull
	@Column(name="login")
	private String login;

	@Column(name = "password", nullable = false)
	// https://stackoverflow.com/a/12505165/548473
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

    @NotNull
    @Column(name="email")
    private String email;

	@NotNull
	@Column(name="surname")
	private String surname;

	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Role> roles;

	@ManyToOne()
	@JoinColumn(name="position_id")
	private Position position;

	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Department> departments;

	@NotNull
	@Column(name="created")
	private LocalDateTime created;

	@Column(name="last_seen")
	private LocalDateTime lastSeen;

	@OneToMany(mappedBy="user")
	private Set<Fact> factsUser;

	@OneToMany(mappedBy="creator")
	private Set<Fact> factsCreator;

	@OneToMany(mappedBy="signer")
	private Set<Fact> factsSigner;

	public User() {
	}

	public User(@NotNull String login, String password, @NotNull String email, @NotNull String name, @NotNull String surname, @NotNull Set<Role> roles, @NotNull Position position, Set<Department> departments ) {
		super(name);
		this.login = login;
		this.password = password;
		this.email = email;
		this.surname = surname;
		this.roles = roles;
		this.position = position;
		this.departments = departments;
	}

	@Column(name="last_seen")
	public LocalDateTime getLastSeen() {
		return this.lastSeen;
	}

	public void setLastSeen(LocalDateTime lastSeen) {
		this.lastSeen = lastSeen;
	}


	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public Set<Fact> getFactsUser() {
		return factsUser;
	}

	public void setFactsUser(Set<Fact> factsUser) {
		this.factsUser = factsUser;
	}

	public Set<Fact> getFactsCreator() {
		return factsCreator;
	}

	public void setFactsCreator(Set<Fact> factsCreator) {
		this.factsCreator = factsCreator;
	}

	public Set<Fact> getFactsSigner() {
		return factsSigner;
	}

	public void setFactsSigner(Set<Fact> factsSigner) {
		this.factsSigner = factsSigner;
	}
}