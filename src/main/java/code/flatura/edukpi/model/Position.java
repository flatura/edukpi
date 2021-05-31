package code.flatura.edukpi.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;


/**
 * The persistent class for the roles database table.
 *
 */
@Entity
@Table(name="positions")
public class Position implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "indicators_positions",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "indicator_id"))
    private Set<Indicator> indicators;

    @OneToMany(mappedBy = "position")
    private Set<User> users;

    public Set<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<Indicator> indicators) {
        this.indicators = indicators;
    }

    public Position() {
    }

    public Position(String name, Set<Indicator> indicators) {
        this.name = name;
        this.indicators = indicators;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}