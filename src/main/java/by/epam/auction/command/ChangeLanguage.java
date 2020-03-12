package by.epam.auction.command;

import by.epam.auction.constants.ConstantForCommands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLanguage implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(ConstantForCommands.LOCALE);
        HttpSession session = request.getSession();
        session.setAttribute(ConstantForCommands.LANG, language);
        return CommandResult.redirect("?command=showMainPage");
    }
}
