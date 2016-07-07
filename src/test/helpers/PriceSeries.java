package test.helpers;

import java.util.ArrayList;
import java.util.List;

import eu.verdelhan.ta4j.Decimal;

public class PriceSeries {
	private final PriceType type;
	private final List<Decimal> prices;

	public PriceSeries(PriceType priceType, int prices[]) {
		this.type = priceType;
		this.prices = new ArrayList<>();

		for (int price : prices) {
			this.prices.add(Decimal.valueOf(price));
		}
	}

	public PriceType getType() {
		return type;
	}

	public List<Decimal> getPrices() {
		return prices;
	}
}
