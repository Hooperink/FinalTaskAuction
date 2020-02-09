package epam.by.Auction.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Lot implements Serializable, Identifiable {
    public static final String TABLE = "lot";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String START_SALE_DATE = "start_sell_date";
    public static final String END_DATE_SELL = "end_date_sell";
    public static final String PRICE = "price";
    public static final String STATUS = "status";
    public static final String DESCRIPTION = "description";
    public static final String SELLER_ID = "seller_id";
    public static final String BUYER_ID = "buyer_id";

    private Long id;
    private Long sellerId;
    private Long buyerId;
    private String name;
    private Timestamp startSellDate;
    private Timestamp endSellDate;
    private BigDecimal price;
    private LotStatus status;
    private String description;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Timestamp getStartSellDate() {
        return startSellDate;
    }

    public void setStartSellDate(Timestamp startSellDay) {
        this.startSellDate = startSellDay;
    }

    public Timestamp getEndSellDate() {
        return endSellDate;
    }

    public void setEndSellDate(Timestamp endSellDay) {
        this.endSellDate = endSellDay;
    }

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
