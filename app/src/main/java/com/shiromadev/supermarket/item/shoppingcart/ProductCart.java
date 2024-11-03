package com.shiromadev.supermarket.item.shoppingcart;

import com.shiromadev.supermarket.item.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCart {
	private Product product;
	private int countProduct;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProductCart item)) return false;
		return this.product.equals(item.product);
	}

	public int totalSumProduct(){
		return product.getPrice() * countProduct;
	}
}