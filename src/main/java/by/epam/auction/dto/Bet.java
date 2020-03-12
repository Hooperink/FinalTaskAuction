package by.epam.auction.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Bet implements Serializable, Identifiable {

    public static final String TABLE = "lot_bet";
    public static final String ID = "id";
    public static final String LOT_ID = "lot_id";
    public static final String BUYER_ID = "buyer_id";
    public static final String BET = "bet";

    private Long id;
    private long buyerId;
    private long lotId;
    private BigDecimal bet;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }

    public BigDecimal getBet() {
        return bet;
    }

    public void setBet(BigDecimal bet) {
        this.bet = bet;
    }
}
