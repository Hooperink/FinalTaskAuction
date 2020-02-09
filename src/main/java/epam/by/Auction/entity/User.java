package epam.by.Auction.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class User implements Serializable, Identifiable {
    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String IS_ACTIVE = "is_active";
    public static final String BALANCE = "balance";
    public static final String ROLE = "role";
    private Long id;
    private String login;
    private String password;
    private boolean isActive;
    private BigDecimal balance;
    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }
}
