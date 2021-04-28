package code.flatura.edukpi.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the indicators database table.
 * 
 */
@Entity
@Table(name="indicators")
public class Indicator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

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

	@NotNull
	@Column(name="period")
	private Integer period;

	@ManyToMany
	@JoinTable(
			name = "indicators_to_roles",
			joinColumns = @JoinColumn(name = "indicator_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public Indicator() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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


	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}


	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}