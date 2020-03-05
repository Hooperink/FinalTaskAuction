package epam.by.Auction.mapper;

import epam.by.Auction.dto.User;
import epam.by.Auction.dto.UserRole;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(User.ID);
        String login = resultSet.getString(User.LOGIN);
        String password = resultSet.getString(User.PASSWORD);
        boolean isActive = resultSet.getBoolean(User.IS_ACTIVE);
        BigDecimal money = resultSet.getBigDecimal(User.BALANCE);
        String role = resultSet.getString(User.ROLE).toUpperCase();
        UserRole userRole = UserRole.valueOf(role);
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setIsActive(isActive);
        user.setBalance(money);
        user.setRole(userRole);
        return user;
    }

    @Override
    public List<Object> getFieldsToSave(User item) {
        List<Object> objectsToSave = new ArrayList<>();
        objectsToSave.add(item.getLogin());
        objectsToSave.add(item.getPassword());
        objectsToSave.add(item.getIsActive());
        objectsToSave.add(item.getBalance());
        return objectsToSave;
    }
}
