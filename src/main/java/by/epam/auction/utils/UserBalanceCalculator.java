package by.epam.auction.utils;

import by.epam.auction.dto.User;
import java.math.BigDecimal;

public class UserBalanceCalculator {

    public User addToBalance(User user, BigDecimal amountToAdd) {
        BigDecimal oldBalanceOfOldUser = user.getBalance();
        BigDecimal newBalanceOfOldUser = oldBalanceOfOldUser.add(amountToAdd);
        user.setBalance(newBalanceOfOldUser);
        return user;
    }

    public User subtractFromBalance(User user, BigDecimal amountToSubtract) {
        BigDecimal oldBalanceOfOldUser = user.getBalance();
        BigDecimal newBalanceOfOldUser = oldBalanceOfOldUser.subtract(amountToSubtract);
        user.setBalance(newBalanceOfOldUser);
        return user;
    }
}
