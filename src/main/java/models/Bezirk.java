package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({@NamedQuery(name = "findAllBezirke", query = "select bezirk from Bezirk bezirk where bezirk.bezirkName != null "),
              @NamedQuery(name = "findBezirkByID", query = "SELECT bezirk FROM Bezirk bezirk WHERE bezirk.id = :vn")})
public class Bezirk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @OneToMany(mappedBy = "bezirk", cascade = CascadeType.ALL)
    private Set<Nutzer> nutzerSet;

    @Column(nullable = false)
    private String bezirkName;

    public Bezirk(){
        super();
        nutzerSet = new HashSet<>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBezirkName() {
        return bezirkName;
    }

    public void setBezirkName(String bezirkName) {
        this.bezirkName = bezirkName;
    }

    @Override
    public String toString() {
        return bezirkName;
    }
}
