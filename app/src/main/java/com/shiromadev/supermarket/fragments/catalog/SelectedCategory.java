package com.shiromadev.supermarket.fragments.catalog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.GridLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.item.Product;

import java.util.ArrayList;

public class SelectedCategory extends Fragment {


	private ArrayList<Product> products;
	private GridLayout gridProduct;
	private View root;
	public SelectedCategory(String flag){
		switch (flag){
			case "FLAG_MILK":
			//	products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.MILK_PRODUCTS);
				break;
			case "FLAG_MEAT":
			//	products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.MEAT_PRODUCTS);
				break;
			case "FLAG_DRINKS":
			//	products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.DRINKS);
				break;
			case "FLAG_FRUITS":
			//	products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.FRUITS);
				break;
			case "FLAG_VEGETABLES":
			//	products = MainActivity.getSqlHelper().getProductCategories(Product.CATEGORIES.VEGETABLES);
				break;
		}
	}

	@SuppressLint("SetTextI18n")
	private void loadViewProducts(){
		int c = 0, r = 0, id = 0;
	//	for (Product item: products) {

//			Button btn = new Button(getContext());
//			btn.setId(id);
//			final int id_ = btn.getId();
//			btn.setText(item.getName() + ":" + item.getPrice());
//			btn.setBackgroundColor(Color.rgb(70, 80, 90));
//			btn.setWidth(250);
//			btn.setHeight(250);
//			gridProduct.addView(btn);
//			btn = ((Button) root.findViewById(id_));
//			btn.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View view) {
//					Toast.makeText(view.getContext(),
//							"Button clicked index = " + id_, Toast.LENGTH_SHORT)
//						.show();
//				}
//			});
	//	}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_selected_category, container, false);
		gridProduct =root.findViewById(R.id.grid_product);
		loadViewProducts();
		return root;
	}
}