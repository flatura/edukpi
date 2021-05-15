package code.flatura.edukpi.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the indicators database table.
 * 
 */
@Entity
@Table(name="indicators")
public class Indicator implements Serializable {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@NotNull
	@Column(name="name")
	private String name;

	@Column(name="description")
	private String description;

	@NotNull
	@Column(name="base")
	private Integer base;

	@NotNull
	@Column(name="max")
	private Integer max;

	public Indicator() {
	}

	public Indicator(@NotNull String name, String description, @NotNull Integer base, @NotNull Integer max) {
		this.name = name;
		this.description = description;
		this.base = base;
		this.max = max;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}


	public Integer getBase() {
		return this.base;
	}

	public void setBase(Integer base) {
		this.base = base;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getMax() {
		return this.max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}