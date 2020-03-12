package by.epam.auction.tags;

import by.epam.auction.connection.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyTag extends TagSupport {

    public final static Logger logger = LogManager.getLogger(ConnectionFactory.class.getName());

    private String locale;
    private BigDecimal currency;

    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            BigDecimal currencyRate;
            String currencyTag;
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
            BigDecimal afterChange = currency.multiply(currencyRate).setScale(2, RoundingMode.DOWN);
            pageContext.getOut().write(afterChange + " " + currencyTag);
            return SKIP_BODY;
        } catch (IOException e) {
            logger.error("Write error.", e);
            throw new JspException(e);
        }
    }
}