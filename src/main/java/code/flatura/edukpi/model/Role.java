package code.flatura.edukpi.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
public class Role implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="id")
	private UUID id;

	@NotNull
	@Column(name="name")
	private String name;

	public Role() {
	}

	public Role(@NotNull String name) {
		this.name = name;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}