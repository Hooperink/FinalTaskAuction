package epam.by.Auction;

import epam.by.Auction.command.Command;
import epam.by.Auction.command.CommandFactory;
import epam.by.Auction.command.CommandResult;
import epam.by.Auction.connection.ConnectionFactory;
import epam.by.Auction.exception.DaoException;
import epam.by.Auction.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    public final static Logger logger = LogManager.getLogger(ConnectionFactory.class.getName());

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
        } catch (DaoException e) {
            logger.error("Error in DAO. " + e);
            //TODO: error page
        } catch (ServiceException e) {
            logger.error("Error in service. " + e);
        } catch (IOException e) {
            logger.error("IOException. " + e);
        } catch (ServletException e) {
            logger.error("Servlet error. " + e);
        }
    }
}
