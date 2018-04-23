package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Sprache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nameSprache;

    @ManyToMany(mappedBy = "sprachenSet")
    private Set<Nutzer> nutzerSet;

    public String getNameSprache() {
        return nameSprache;
    }

    public void setNameSprache(String nameSprache) {
        this.nameSprache = nameSprache;
    }

}
