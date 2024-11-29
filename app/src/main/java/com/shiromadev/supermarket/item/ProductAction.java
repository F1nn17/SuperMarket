package com.shiromadev.supermarket.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductAction {
	private Product product;
	private int newPrice;
}
