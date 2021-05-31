package code.flatura.edukpi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="indicator_categories")
public class IndicatorCategory implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NotNull
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    private String name;

    public IndicatorCategory() {
    }

    public IndicatorCategory(String name) {
        this.name = name;
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
