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

	@ManyToOne()
    @JoinColumn(name="indicator_id", nullable = false)
	private Indicator indicator;

	@Column(name="points_suggested")
	private Integer pointsSuggested;

	@Column(name="points_approved")
	private Integer pointsApproved;

    @ManyToOne()
    @JoinColumn(name="user_id", nullable = false)
	private User user;

	@NonNull
    @ManyToOne()
    @JoinColumn(name="creator_id")
	private User creator;

	@NonNull
	@Column(name="created", updatable = false)
	private LocalDateTime created;

	@Column(name="modified")
	private LocalDateTime lastModified;

	@Column(name="approved")
	private LocalDateTime approved;

    @ManyToOne()
    @JoinColumn(name="signer_id")
	private User signer;

    public Fact() {
	}

	public Fact(Indicator indicator, Integer pointsSuggested, User user, User creator) {
		this.indicator = indicator;
		this.pointsSuggested = pointsSuggested;
		this.user = user;
		this.creator = creator;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getPointsSuggested() {
		return this.pointsSuggested;
	}

	public void setPointsSuggested(Integer pointsSuggested) {
		this.pointsSuggested = pointsSuggested;
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

	public Integer getPointsApproved() {
		return pointsApproved;
	}

	public void setPointsApproved(Integer pointsApproved) {
		this.pointsApproved = pointsApproved;
	}

	public LocalDateTime getApproved() {
		return approved;
	}

	public void setApproved(LocalDateTime approved) {
		this.approved = approved;
	}

	public User getSigner() {
		return signer;
	}

	public void setSigner(User signer) {
		this.signer = signer;
	}
}