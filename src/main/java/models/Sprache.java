package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "findBySpracheID", query = "SELECT sprache FROM Sprache sprache WHERE sprache.id = :id")
public class Sprache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nameSprache;

    @ManyToMany(mappedBy = "sprachenSet")
    private Set<Nutzer> nutzerSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSprache() {
        return nameSprache;
    }

    public void setNameSprache(String nameSprache) {
        this.nameSprache = nameSprache;
    }

    public Set<Nutzer> getNutzerSet() {
        return nutzerSet;
    }

    public void setNutzerSet(Set<Nutzer> nutzerSet) {
        this.nutzerSet = nutzerSet;
    }

    @Override
    public String toString() {
        return getNameSprache();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprache sprache = (Sprache) o;
        return id == sprache.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
