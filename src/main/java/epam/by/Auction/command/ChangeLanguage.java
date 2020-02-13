package epam.by.Auction.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLanguage implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter("locale");
        HttpSession session = request.getSession();
        session.setAttribute("lang", language);
        return CommandResult.redirect("?command=showMainPage");
    }
}
