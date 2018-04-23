package ejb;

import models.Nutzer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class NutzerService {

    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;


    public void persist(Nutzer n) {
        em.persist(n);
    }

}
