package code.flatura.edukpi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="departments")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NotNull
    @Column(name="id")
    private UUID id;

    @NotNull
    @Column(name="name")
    private String name;

    public Department(@NotNull String name) {
        this.name = name;
    }

    public Department() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
