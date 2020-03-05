package epam.by.Auction.command;


import epam.by.Auction.exception.DaoException;
import epam.by.Auction.exception.PageNotFoundException;
import epam.by.Auction.exception.ServiceException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServiceException, IOException, ServletException, PageNotFoundException;
}
