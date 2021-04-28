package code.flatura.edukpi.model;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * The persistent class for the facts database table.
 * 
 */
@Entity
@Table(name="facts")
public class Fact implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", unique = true, updatable = false)
	private UUID id;

	@NonNull
	@Column(name="indicator_id")
	private Indicator indicator;

	@Column(name="points")
	private Integer points;

	@NonNull
	@Column(name="user_id")
	private User user;

	@NonNull
	@Column(name="author_id", updatable = false)
	private User creator;

	@NonNull
	@Column(name="created", updatable = false)
	private LocalDateTime created;

	@Column(name="modified")
	private LocalDateTime lastModified;

	public Fact() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	//bi-directional many-to-one association to Indicator
	@ManyToOne(fetch=FetchType.LAZY)
	public Indicator getIndicator() {
		return this.indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}
}