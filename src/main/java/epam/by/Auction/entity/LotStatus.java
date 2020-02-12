package epam.by.Auction.entity;

public enum LotStatus {
    MODERATION("moderation"),
    ACTIVE("active"),
    NOT_ACTIVE("not_active"),
    SOLD("sold"),
    DELETED("deleted");

    private String status;

    public String getStatus() {
        return status;
    }

    LotStatus(String status) {
        this.status = status;
    }
}
