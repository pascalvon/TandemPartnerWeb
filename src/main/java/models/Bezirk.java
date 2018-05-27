package models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "findBezirkByID", query = "SELECT bezirk FROM Bezirk bezirk WHERE bezirk.id = :id")
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
