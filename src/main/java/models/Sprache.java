package models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({@NamedQuery(name = "findAllSprache", query = "SELECT sprache FROM Sprache sprache"),
@NamedQuery(name = "findBySpracheName", query = "SELECT sprache FROM Sprache sprache WHERE sprache.nameSprache = :vn")})
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
}
