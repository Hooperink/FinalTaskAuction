package by.epam.auction;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandFactory;
import by.epam.auction.command.CommandResult;
import by.epam.auction.exception.ServiceException;
import by.epam.auction.connection.ConnectionFactory;
import by.epam.auction.exception.DaoException;
import by.epam.auction.exception.PageNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig(fileSizeThreshold=1024*1024*2,
maxFileSize=1024*1024*10,
maxRequestSize=1024*1024*50)
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
        } catch (ServiceException e) {
            logger.error("Error in service. " + e);
        } catch (IOException e) {
            logger.error("IOException. " + e);
        } catch (ServletException e) {
            logger.error("Servlet error. " + e);
        } catch (PageNotFoundException e) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException ex) {
                logger.error("IOException. " + e);
            }
        }
    }
}
