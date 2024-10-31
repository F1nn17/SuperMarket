package com.shiromadev.supermarket.item;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Product {
	private String name;
	private int price;
	private CATEGORIES categories;
	private String icon;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Product item)) return false;
		return this.name.equals(item.name);
	}

	@Getter
	public enum CATEGORIES {
		MILK_PRODUCTS("milk_products"),
		MEAT_PRODUCTS("meat_products"),
		FRUITS("fruits"),
		VEGETABLES("vegetables"),
		DRINKS("drinks"),
		DEFAULT("");
		private final String CATEGORIES;

		CATEGORIES(String categories) {
			CATEGORIES = categories;
		}

	}

}
