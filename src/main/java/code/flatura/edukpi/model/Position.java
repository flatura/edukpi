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
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    private String name;

    public Position() {
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