package com.shiromadev.supermarket.item.shoppingcart;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class ProductCartList implements RandomAccess, Cloneable, java.io.Serializable, Iterable<ProductCart> {

	ProductCart[] products;
	private int size;
	private static final int DEFAULT_CAPACITY = 1;
	private static final ProductCart[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};

	public ProductCartList() {
		products = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
	}

	public ProductCartList(int initialCapacity){
		if (initialCapacity > 0) {
			products = new ProductCart[initialCapacity];
		} else if (initialCapacity == 0) {
			products = new ProductCart[DEFAULT_CAPACITY];
		} else {
			throw new IllegalArgumentException("Illegal Capacity: "+
				initialCapacity);
		}
	}

	private ProductCart[] grow(int minCapacity) {
		int oldCapacity = products.length;
		if (oldCapacity > 0 || products != DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
			int newCapacity = ++oldCapacity;
			return products = Arrays.copyOf(products, newCapacity);
		} else {
			return products = new ProductCart[Math.max(DEFAULT_CAPACITY, minCapacity)];
		}
	}

	private ProductCart[] grow() {
		return grow(size + 1);
	}

	private void add(ProductCart e, ProductCart[] elementData, int s) {
		if (s == elementData.length)
			elementData = grow();
		elementData[s] = e;
		size = s + 1;
	}

	public void add(ProductCart product){
		boolean search = false;
		if(!isEmpty()){
			for(ProductCart item : products){
				System.out.println(products.length);
				System.out.println(item);
				System.out.println("item: " + item.getProduct().getName());
				if(Objects.equals(item.getProduct().getName(), product.getProduct().getName())){
					item.setCountProduct(item.getCountProduct() + product.getCountProduct());
					search = true;
					break;
				}
			}
		}
		if(!search) {
			add(product, products, size);
		}
	}

	public boolean isEmpty(){
		if(products == DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) return true;
		return products[0] == null;
	}

	public ProductCart get(int index) {
		return products[index];
	}

	public void remove(int index) {
		Objects.checkIndex(index, size);
		fastRemove(index);
	}

	public void remove(ProductCart o) {
		System.out.println("До удаления: "+products.length);
		final ProductCart[] es = products;
		final int size = this.size;
		int i = 0;
		found: {
			if (o == null) {
				for (; i < size; i++)
					if (es[i] == null)
						break found;
			} else {
				for (; i < size; i++)
					if (o.equals(es[i]))
						break found;
			}
			return;
		}
		fastRemove(i);
		System.out.println("После удаления: "+products.length);
	}


	private void fastRemove(int i) {
		ProductCart[] productOld = products;
		--size;
		if(size == 0) products = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
		else products = new ProductCart[size];
		int skip = 0;
		for(int k = 0; k < productOld.length; k++){
			if(k == i) {
				skip = k + 1;
			}
			if(skip >= productOld.length){
				break;
			}
			System.out.println("k:"+k + "skip:"+skip);
			products[k] = productOld[skip];
			skip++;
		}
	}

	public int size() {
		return size;
	}

	@NotNull
	@Override
	public ProductCartList clone() {
		try {
			return (ProductCartList) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	@NonNull
	@NotNull
	@Override
	public Iterator<ProductCart> iterator() {
		return new Iterator<>() {
			private final ProductCart[] currentData = products;
			private int pos = 0;
			@Override
			public boolean hasNext() {
				return pos < currentData.length;
			}

			@Override
			public ProductCart next() {
				return currentData[pos++];
			}
			@Override
			public void remove() {
				ProductCartList.this.remove(pos++);
			}
		};
	}
}
