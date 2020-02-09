package epam.by.Auction.utils;

import epam.by.Auction.entity.User;
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
