package FantasyBasketball.interceptors;

import FantasyBasketball.models.Client;
import FantasyBasketball.services.clientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class FantasyBasketballInterceptor implements HandlerInterceptor {

    @Autowired
    clientService clientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = request.getHeader("token");
        boolean registerPage = request.getRequestURI().equals("/register");
        if (token == null && !registerPage) {
            response.setStatus(401);
            response.getWriter().write("Token not found, please provide token to use Fantasy Basketball application. "
                    + "\n\tToken should be provided as 'token' under headers."
                    + "\n\tIf you don't have an account, you can register on the '/register' route.");
            return false;
        } else if (registerPage) {
            return true;
        }
        System.out.println("TOKEN: " + token);
        List<Client> clientCheck = clientService.getClientByGoogleId(token);
        if (clientCheck.size() != 1) {
            response.setStatus(401);
            response.getWriter().write("Invalid token provided. Please try again.");
            return false;
        }

        HttpSession session = request.getSession();
        session.setAttribute("client", clientCheck.get(0));

        return true;
    }
}
