package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class LoginServlet extends HttpServlet {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
