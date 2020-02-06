package epam.by.mapper;

import epam.by.entity.Lot;
import epam.by.entity.LotStatus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LotRowMapper implements RowMapper<Lot> {

    @Override
    public Lot map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(Lot.ID);
        String name = resultSet.getString(Lot.NAME);
        Timestamp endSellDate = resultSet.getTimestamp(Lot.END_DATE_SELL);
        Timestamp startSellDate = resultSet.getTimestamp(Lot.START_SALE_DATE);
        BigDecimal price = resultSet.getBigDecimal(Lot.PRICE);
        String status = resultSet.getString(Lot.STATUS).toUpperCase();
        LotStatus lotStatus = LotStatus.valueOf(status);
        long sellerId = resultSet.getLong(Lot.SELLER_ID);
        long buyerId = resultSet.getLong(Lot.BUYER_ID);
        String description = resultSet.getString(Lot.DESCRIPTION);

        Lot lot = new Lot();
        lot.setId(id);
        lot.setName(name);
        lot.setStartSellDate(startSellDate);
        lot.setEndSellDate(endSellDate);
        lot.setPrice(price);
        lot.setStatus(lotStatus);
        lot.setDescription(description);
        lot.setBuyerId(buyerId == 0 ? null : buyerId);
        lot.setSellerId(sellerId);
        return lot;
    }

    @Override
    public List<Object> getFieldsToSave(Lot item) {
        List<Object> objectsToSave = new ArrayList<>();
        objectsToSave.add(item.getId());
        objectsToSave.add(item.getName());
        objectsToSave.add(item.getStartSellDate());
        objectsToSave.add(item.getPrice());
        objectsToSave.add(item.getStatus() != null ? item.getStatus().getStatus() : null);
        objectsToSave.add(item.getSellerId());
        objectsToSave.add(item.getBuyerId());
        objectsToSave.add(item.getDescription());
        objectsToSave.add(item.getEndSellDate());
        return objectsToSave;
    }
}
