package code.flatura.edukpi.model;

import java.io.Serializable;
import java.util.Set;
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
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="id")
	private UUID id;

	@NotNull
	@Column(name="name")
	private String name;

	@ManyToMany
	@JoinTable(
			name = "indicators_to_roles",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "indicator_id"))
	private Set<Indicator> indicators;

	public Role() {
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

	public Set<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(Set<Indicator> indicators) {
		this.indicators = indicators;
	}
}