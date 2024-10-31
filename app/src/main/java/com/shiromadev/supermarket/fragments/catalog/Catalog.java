package com.shiromadev.supermarket.fragments.catalog;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.shiromadev.supermarket.R;
import org.jetbrains.annotations.NotNull;

public class Catalog extends Fragment {

	private static String flagCategory;
	private static FragmentManager manager;
	private static TextView headerTitle;
	private static View root;

	public static void setFlagCategory(String flagCategory) {
		Catalog.flagCategory = flagCategory;
		switch (flagCategory){
			case "FLAG_MILK":
				headerTitle.setText(root.getResources().getString(R.string.milk_product));
				break;
			case "FLAG_MEAT":
				headerTitle.setText(root.getResources().getString(R.string.meat_products));
				break;
			case "FLAG_DRINKS":
				headerTitle.setText(root.getResources().getString(R.string.drinks));
				break;
			case "FLAG_FRUITS":
				headerTitle.setText(root.getResources().getString(R.string.fruits));
				break;
			case "FLAG_VEGETABLES":
				headerTitle.setText(root.getResources().getString(R.string.vegetables));
				break;
		}
		openCategory();
	}

	private static void openCategory (){
		manager.beginTransaction().replace(R.id.child_fragment_catalogs, new SelectedCategory(flagCategory)).commit();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
			CatalogMenu catalog = new CatalogMenu();
			fragmentTransaction.add(R.id.child_fragment_catalogs, catalog);
			fragmentTransaction.commit();
		}
	}

	@Override
	public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_catalog, container, false);
		manager = getChildFragmentManager();
		headerTitle = root.findViewById(R.id.header_text);
		return root;
	}
}