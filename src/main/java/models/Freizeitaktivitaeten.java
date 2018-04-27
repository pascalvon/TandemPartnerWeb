package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Freizeitaktivitaeten {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(nullable = false)
    private String nameAktivitaet;

    @ManyToMany(mappedBy = "freizeitaktivitaetenSet")
    private Set<Nutzer> nutzerSet;

    public String getNameAktivitaet() {
        return nameAktivitaet;
    }

    public void setNameAktivitaet(String nameAktivitaet) {
        this.nameAktivitaet = nameAktivitaet;
    }

}
