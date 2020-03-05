package epam.by.Auction.errors;

public enum ErrorCodeForMessage {
    ERROR_1("Not valid login or pass."),
    ERROR_2("You have already place bet."),
    ERROR_3("Your bet is lower than current"),
    ERROR_4("Not enough money"),
    ERROR_5("Your bet is lower than price"),
    ERROR_6("Nothing happened"),
    ERROR_7("Not bet is set"),
    ERROR_8("User already exists"),
    ERROR_9("Passwords are not equal");

    private String errorText;

    public String getErrorText() {
        return errorText;
    }

    ErrorCodeForMessage(String errorText) {
        this.errorText = errorText;
    }
}
