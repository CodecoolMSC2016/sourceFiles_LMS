package profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by imre_meszesan on 10.04.17.
 */

public class LogoutHandler extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie: cookies){
                cookie.setMaxAge(0);
            }
        }
        if(session != null){
            session.invalidate();
            session.setMaxInactiveInterval(1);

            response.sendRedirect("./login.jsp");
        }
    }
}
