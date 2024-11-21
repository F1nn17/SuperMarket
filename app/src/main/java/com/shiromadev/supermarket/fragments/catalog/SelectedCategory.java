package com.shiromadev.supermarket.fragments.catalog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.api.custom.view.ProductView;
import com.shiromadev.supermarket.item.Product;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SelectedCategory extends Fragment {
	private ArrayList<Product> products;
	private GridLayout gridProduct;

	public SelectedCategory(String flag){
		products = new ArrayList<>();
		switch (flag){
			case "FLAG_MILK":
				products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.MILK_PRODUCTS);
				break;
			case "FLAG_MEAT":
				products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.MEAT_PRODUCTS);
				break;
			case "FLAG_DRINKS":
				products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.DRINKS);
				break;
			case "FLAG_FRUITS":
				products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.FRUITS);
				break;
			case "FLAG_VEGETABLES":
				products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.VEGETABLES);
				break;
		}
	}

	@SuppressLint("SetTextI18n")
	private void loadViewProducts(){
		gridProduct.setRowCount(products.size()/2+2);
		int i = 0, k = 0;
		GridLayout.LayoutParams params;
		for (Product item: products) {
			params = new GridLayout.LayoutParams();
			params.rowSpec = GridLayout.spec(i);
			params.columnSpec = GridLayout.spec(k);
			params.setGravity(Gravity.CENTER);
			params.setMarginStart(125);
			gridProduct.addView(new ProductView(getContext(), item).getLayout(), params);
			k++;
			if (k > 1){
				i++;
				k = 0;
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_selected_category, container, false);
		gridProduct = root.findViewById(R.id.grid_product);
		loadViewProducts();
		return root;
	}
}