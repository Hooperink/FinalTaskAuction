package epam.by.Auction.utils;

import epam.by.Auction.dto.User;
import org.junit.Test;;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserBalanceCalculatorTest {

    private User userToCompare = new User();
    private User userToCompareWith = new User();
    private UserBalanceCalculator userBalanceCalculator = new UserBalanceCalculator();

    @Test
    public void addToBalanceShouldReturnTrueWhenAddAsShould() {
        userToCompare.setBalance(new BigDecimal(0));
        userToCompareWith.setBalance(new BigDecimal(1));
        userToCompare = userBalanceCalculator.addToBalance(userToCompare, new BigDecimal(1));
        assertEquals(userToCompare, userToCompareWith);
    }

    @Test
    public void subtractFromBalanceShouldReturnTrueWhenSubtractAsShould() {
        userToCompare.setBalance(new BigDecimal(0));
        userToCompareWith.setBalance(new BigDecimal(-1));
        userToCompare = userBalanceCalculator.subtractFromBalance(userToCompare, new BigDecimal(1));
        assertEquals(userToCompare, userToCompareWith);
    }
}