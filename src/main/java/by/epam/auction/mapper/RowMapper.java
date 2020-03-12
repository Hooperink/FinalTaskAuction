package by.epam.auction.mapper;

import by.epam.auction.dto.Bet;
import by.epam.auction.dto.Identifiable;
import by.epam.auction.dto.Lot;
import by.epam.auction.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapper<T extends Identifiable> {

    T map (ResultSet resultSet) throws SQLException;
    List<Object> getFieldsToSave(T item);

    static RowMapper<? extends Identifiable> create(String table) {
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
