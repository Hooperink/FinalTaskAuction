package epam.by.Auction.command;


import epam.by.Auction.exception.DaoException;
import epam.by.Auction.exception.ServiceException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServiceException;
}
