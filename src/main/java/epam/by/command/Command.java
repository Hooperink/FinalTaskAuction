package epam.by.command;


import epam.by.exception.DaoException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException, epam.by.exception.ServiceException;
}
