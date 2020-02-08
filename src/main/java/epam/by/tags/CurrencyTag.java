package epam.by.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyTag extends TagSupport {
    private String locale;
    private BigDecimal currency;
    private BigDecimal currencyRate;

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
            } else if ("ru_RU".equalsIgnoreCase(locale)) {
                currencyRate = new BigDecimal("29.39");
            } else if (("be_BY.").equalsIgnoreCase(locale)) {
                currencyRate = new BigDecimal("1");
            }
            BigDecimal afterChange = currency.multiply(currencyRate);
            pageContext.getOut().write(String.valueOf(afterChange));
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

}
