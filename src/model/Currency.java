package model;

import controller.*;

/**
 *
 * @author 
 */

public class Currency {
    private final double conversion;

    public Currency(CurrencyExchangeAPI currencyExchangeAPI) {
        this.conversion = currencyExchangeAPI.conversion_result();
    }

    public double getConversion() {
        return conversion;
    }

}



