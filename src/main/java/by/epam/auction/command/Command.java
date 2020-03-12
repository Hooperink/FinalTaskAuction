package by.epam.auction.command;


import by.epam.auction.exception.DaoException;
import by.epam.auction.exception.PageNotFoundException;
import by.epam.auction.exception.ServiceException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServiceException, IOException, ServletException, PageNotFoundException;
}
