package epam.by.Auction.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyTag extends TagSupport {
    private String locale;
    private BigDecimal currency;
    private BigDecimal currencyRate;
    private String currencyTag;
    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if ("en_US".equalsIgnoreCase(locale)) {
                currencyRate = new BigDecimal("0.46");
                currencyTag = "US";
            } else if ("ru_RU".equalsIgnoreCase(locale)) {
                currencyRate = new BigDecimal("29.39");
                currencyTag = "RUB";
            } else {
                currencyRate = new BigDecimal("1");
                currencyTag = "BYN";
            }
            BigDecimal afterChange = currency.multiply(currencyRate);
            pageContext.getOut().write(String.valueOf(afterChange) + currencyTag);
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }
}