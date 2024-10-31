package com.shiromadev.supermarket.fragments.catalog;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shiromadev.supermarket.R;

public class CatalogMenu extends Fragment implements View.OnClickListener {

	private static final String FLAG_MILK = "FLAG_MILK";
	private static final String FLAG_MEAT = "FLAG_MEAT";
	private static final String FLAG_DRINKS = "FLAG_DRINKS";
	private static final String FLAG_FRUITS = "FLAG_FRUITS";
	private static final String FLAG_VEGETABLES = "FLAG_VEGETABLES";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_catalog_menu, container, false);

		ImageButton milkProduct = (ImageButton) root.findViewById(R.id.milk_product);
		ImageButton meatProduct = (ImageButton) root.findViewById(R.id.meat_products);
		ImageButton fruits = (ImageButton) root.findViewById(R.id.fruits);
		ImageButton drinks = (ImageButton) root.findViewById(R.id.drinks);
		ImageButton vegetables = (ImageButton) root.findViewById(R.id.vegetables);
		milkProduct.setOnClickListener(this);
		meatProduct.setOnClickListener(this);
		fruits.setOnClickListener(this);
		drinks.setOnClickListener(this);
		vegetables.setOnClickListener(this);

		return root;
	}

	private void switchCatalog(String flag){
		Catalog.setFlagCategory(flag);
	}

	@Override
	public void onClick(View view) {
		int checkId = view.getId();
		if (checkId == R.id.milk_product) {
			switchCatalog(FLAG_MILK);
		}
		if (checkId == R.id.meat_products) {
			switchCatalog(FLAG_MEAT);
		}
		if (checkId == R.id.fruits) {
			switchCatalog(FLAG_FRUITS);
		}
		if (checkId == R.id.drinks) {
			switchCatalog(FLAG_DRINKS);
		}
		if (checkId == R.id.vegetables) {
			switchCatalog(FLAG_VEGETABLES);
		}
	}
}
