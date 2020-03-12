package by.epam.auction.mapper;

import by.epam.auction.dto.Bet;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BetRowMapper implements RowMapper<Bet> {
    @Override
    public Bet map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(Bet.ID);
        long buyerId = resultSet.getLong(Bet.BUYER_ID);
        long lotId = resultSet.getLong(Bet.LOT_ID);
        BigDecimal bet = resultSet.getBigDecimal(Bet.BET);

        Bet itemBet = new Bet();
        itemBet.setId(id);
        itemBet.setBuyerId(buyerId);
        itemBet.setLotId(lotId);
        itemBet.setBet(bet);
        return itemBet;
    }

    @Override
    public List<Object> getFieldsToSave(Bet bet) {
        List<Object> objectsToSave = new ArrayList<>();
        objectsToSave.add(bet.getLotId());
        objectsToSave.add(bet.getBuyerId());
        objectsToSave.add(bet.getBet());
        return objectsToSave;
    }
}
