package code.flatura.edukpi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="id")
	private UUID id;

	@NotNull
	@Column(name="login")
	private String login;

	@Column(name = "password", nullable = false)
	// https://stackoverflow.com/a/12505165/548473
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@NotNull
	@Column(name="name")
	private String name;

	@NotNull
	@Column(name="surname")
	private String surname;

	@NotNull
	//bi-directional many-to-one association to Role
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Role> roles;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="position_id", nullable=false)
	private Position position;

	@NotNull
	@Column(name="created")
	private LocalDateTime created;

	@Column(name="last_seen")
	private LocalDateTime lastSeen;

	public User() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
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


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
}