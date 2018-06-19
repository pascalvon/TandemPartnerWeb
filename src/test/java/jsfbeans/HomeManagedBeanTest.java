package jsfbeans;

import dao.DAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
class HomeManagedBeanTest {

    //19.06.18 Arne: Mock erstellt Objekte, die aus anderen Klassen kommen
    //19.06.18 Arne: In ihren eigenen Testklassen sollten sie getestet worden sein
    //19.06.18 Arne: darum ist es zu vernachlässigen, sie hier erneut zu testen.
    @Mock
    DAO dao = new DAO();

    @BeforeAll
    void DbConnection(){

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void showSpracheNameTest() {
    }

    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(spracheID).getNameSprache();
    }



    @Test
    void acceptMatchanfrage() {
    }

    @Test
    void refuseMatchanfrage() {
    }

    @Test
    void getNutzer() {
    }

    @Test
    void getMatchanfragenModelArrayList() {
    }
}