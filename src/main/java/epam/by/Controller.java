package epam.by;

import epam.by.command.Command;
import epam.by.command.CommandFactory;
import epam.by.command.CommandResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestDispatcher(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestDispatcher(request, response);
    }

    private void requestDispatcher(HttpServletRequest request, HttpServletResponse response) {
        String stringCommand = request.getParameter("command");
        try {
            Command command = CommandFactory.create(stringCommand);
            CommandResult result = command.execute(request, response);
            if (result.isRedirect()) {
                response.sendRedirect(request.getContextPath() + result.getPage());
            } else {
                ServletContext servletContext = getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(result.getPage());
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
