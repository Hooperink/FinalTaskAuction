package epam.by.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletSecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String command = request.getParameter("command");
        HttpSession session = request.getSession();
        boolean isActive = false;
        if (session != null && session.getAttribute("isActive") != null) {
            isActive = (boolean) session.getAttribute("isActive");
        }
        boolean loggedIn = session != null && session.getAttribute("role") != null;
        if ((loggedIn && isActive) || (command != null && command.equals("showLoginPage") || (command != null && command.equals("login")))) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("controller?command=showLoginPage");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
