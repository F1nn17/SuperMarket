package com.shiromadev.supermarket.item.shoppingcart;

import com.shiromadev.supermarket.item.Product;

import java.util.*;

public class ProductCartList<T extends Product>extends AbstractList<T>
	implements List<T>, RandomAccess, java.io.Serializable {

	private Object[] products;
	private int size;

	public ProductCartList() {
		products = new Object[1];
		size = 0;
	}

	public ProductCartList(int initialCapacity){
		if (initialCapacity > 0) {
			products = new ProductCart[initialCapacity];
		} else if (initialCapacity == 0) {
			products = new ProductCart[1];
		} else {
			throw new IllegalArgumentException("Illegal Capacity: "+
				initialCapacity);
		}
	}

	// Добавление элемента в список
	public boolean add(T product) {
		boolean search = false;
		if(!isEmpty()){
			for (Object o : products) {
				ProductCart item = (ProductCart) o;
				System.out.println(products.length);
				System.out.println(item);
				System.out.println("item: " + item.getName());
				if (Objects.equals(item.getName(), product.getName())) {
					item.setCountProduct(item.getCountProduct() + 1);
					search = true;
					break;
				}
			}
		}
		if(!search) {
			if (size == products.length) {
				resize();
			}
			products[size++] = product;
		}

		return false;
	}

	// Увеличение размера массива
	private void resize() {
		Object[] newArray = new Object[products.length+1];
		System.arraycopy(products, 0, newArray, 0, products.length);
		products = newArray;
	}


	public boolean isEmpty(){
		if(size == 0) return true;
		return products[0] == null;
	}

	// Получение элемента по индексу
	public T get(int index) {
		Objects.checkIndex(index, size);
		return elementData(index);
	}

	@SuppressWarnings("unchecked")
	T elementData(int index) {
		return (T) products[index];
	}

	public T remove(int index) {
		Objects.checkIndex(index, size);
		fastRemove(index);
		return null;
	}

	public void remove(T o) {
		System.out.println("До удаления: "+products.length);
		final Object[] es = products;
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
		Object[] productOld = products;
		--size;
		if(size == 0) products = new Object[1];
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
}
