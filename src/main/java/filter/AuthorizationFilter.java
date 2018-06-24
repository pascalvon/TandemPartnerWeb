package filter;

import jsfbeans.LoginManagedBean;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Der {@code AuthorizationFilter} hindert nicht-angemeldete Nutzer an dem Zugriff, &uuml;ber die URL, auf
 * xhtml-Seiten zu kommen, welche normalerweise einen angemeledeten Nutzer ben&ouml;tigen.
 */
@WebFilter("/nutzer/*")
public class AuthorizationFilter implements Filter {

    /**
     * &Uuml;berpr&uuml;ft die {@code HttpSession} nach dem Attribut "nutzer".
     * Falls dieses Attribut existiert, werden die "gesicherten" URL's, welche sich unter /webapp/nutzerbefinden,
     * freigegeben und der angemeldete Nutzer hat Zugriff auf sie.
     *
     * @param   req {@code ServletRequest}-Objekt, welches die Client-Anfrage-Informationen enth&auml;lt.
     * @param   res {@code ServletResponse}-Objekt, welches genutzt wird, um dem Client eine Antwort senden zu
     *                                     k&ouml;nnen.
     * @param   chain {@code FilterChain}-Objekt, welches den Filter darstellt.
     * @throws  ServletException wenn das Servlet nicht richtig ausgef&uuml;rt werden kann.
     * @throws  IOException wenn I/O-Operationen fehlgeschlagen sind oder unterbrochen wurden.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if ((session != null) && (session.getAttribute("nutzer") != null)) {
            chain.doFilter(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/login.xhtml");
        }
    }

    /**
     * Das Interface {@code Filter} gibt vor, diese Methode zu &uuml;berschreiben.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Das Interface {@code Filter} gibt vor, diese Methode zu &uuml;berschreiben.
     */
    @Override
    public void destroy() {

    }
}