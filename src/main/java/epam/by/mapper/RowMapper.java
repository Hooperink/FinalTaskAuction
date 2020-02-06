package epam.by.mapper;

import epam.by.entity.Bet;
import epam.by.entity.Identifiable;
import epam.by.entity.Lot;
import epam.by.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapper<T extends Identifiable> {

    T map (ResultSet resultSet) throws SQLException;
    List<Object> getFieldsToSave(T item);

    public static RowMapper<? extends Identifiable> create(String table) {
        switch (table) {
            case User.TABLE:
                return new UserRowMapper();
            case Lot.TABLE:
                return new LotRowMapper();
            case Bet.TABLE:
                return new BetRowMapper();
            default:
                throw new IllegalArgumentException("Unknown table: " + table);
        }
    }
}
