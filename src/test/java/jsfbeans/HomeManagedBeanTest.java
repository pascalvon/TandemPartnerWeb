package jsfbeans;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.rmi.runtime.Log;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HomeManagedBeanTest {

    // TODO Joe: 19.06.2018 funkt nicht, waren erste Ueberlegungen
    LoginManagedBean loginManagedBean;
    @BeforeEach
    void setUp() {
        System.out.println("Test");
        loginManagedBean = mock(LoginManagedBean.class);
        loginManagedBean.getNutzer().setMail("test@web.de");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void showSpracheName() {
        assertEquals("test@web.de", loginManagedBean.getMail());
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