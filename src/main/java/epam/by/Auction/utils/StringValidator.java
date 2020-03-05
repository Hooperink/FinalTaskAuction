package epam.by.Auction.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator {
    public boolean validateName (String name) {
        name = name.trim();
        if(name.equals(""))
            return false;
        name = name.replaceAll("\\s+", " ");
        return name.length() >= 5;
    }

    public boolean validateText (String text) {
        text = text.trim();
        if(text.equals(""))
            return false;
        text = text.replaceAll("\\s+", " ");
        return text.length() >= 5 && text.length() < 250;
    }

    public boolean validateLogin (String login) {
        Pattern pattern = Pattern.compile("^[\\w.-]{0,19}[0-9a-zA-Z]$");
        Matcher matcher = pattern.matcher(login);
        return matcher.find();
    }
}
