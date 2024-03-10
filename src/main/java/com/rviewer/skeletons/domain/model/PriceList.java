package com.rviewer.skeletons.domain.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceList {

    private static final Logger logger = LoggerFactory.getLogger(PriceList.class);

    // Prices for the products per liter
    public static final double PRICE_BEER_A = 12.25;

    static {
        logger.warn("PriceList is a utility class and should not be instantiated.");
    }

    private PriceList() {
        logger.error("Attempt to instantiate PriceList, which is a utility class.");
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}
