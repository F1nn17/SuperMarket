package com.shiromadev.supermarket.item.shoppingcart;

import com.shiromadev.supermarket.item.Product;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ProductCart extends Product {
	private int countProduct;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProductCart item)) return false;
		return this.getName().equals(item.getName());
	}

	public int totalSumProduct(){
		return getPrice() * countProduct;
	}
}