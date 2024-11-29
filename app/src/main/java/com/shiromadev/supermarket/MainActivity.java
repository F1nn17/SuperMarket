package com.shiromadev.supermarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.Gson;
import com.shiromadev.supermarket.fragments.barcode.Barcode;
import com.shiromadev.supermarket.fragments.catalog.Catalog;
import com.shiromadev.supermarket.fragments.shoppingcart.ShoppingCart;
import com.shiromadev.supermarket.fragments.stockroulette.StockRoulette;
import com.shiromadev.supermarket.item.Product;
import com.shiromadev.supermarket.item.ProductAction;
import com.shiromadev.supermarket.item.shoppingcart.ProductCart;
import com.shiromadev.supermarket.item.shoppingcart.ProductCartList;
import com.shiromadev.supermarket.sqlite.helper.SQLiteControllerHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
	@SuppressLint("StaticFieldLeak")
	@Getter
	private static FrameLayout container;
	@SuppressLint("StaticFieldLeak")
	private static TextView countProducts;
	FragmentManager fragmentManager;

	@Getter
	private static ProductCartList<ProductCart> shoppingCart = new ProductCartList<>();

	@Getter
	private static SQLiteControllerHelper sqlHelper;

	@Getter
	private static ProductAction productAction;
	@Setter
	private static boolean isAction = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			sqlHelper = new SQLiteControllerHelper(this);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		container = (FrameLayout) findViewById(R.id.child_fragment);
		countProducts = (TextView) findViewById(R.id.countProducts);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		fragmentManager = getSupportFragmentManager();
		if (savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			Catalog catalog = new Catalog();
			fragmentTransaction.add(container.getId(), catalog);
			fragmentTransaction.commit();
		}
	}

	private void switchFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
	}


	public static void updateCount() {
		countProducts.setText(String.valueOf(shoppingCart.getCountProducts()));
	}

	public void onClick(View view) {
		int checkId = view.getId();
		if (checkId == R.id.catalog_button) {
			switchFragment(new Catalog());
		}
		if (checkId == R.id.barcode) {
			switchFragment(new Barcode());
		}
		if (checkId == R.id.stock_roulette) {
			switchFragment(new StockRoulette());
		}
		if (checkId == R.id.shopping_cart) {
			updateCount();
			appleAction();
			switchFragment(new ShoppingCart(getSupportFragmentManager()));
		}
	}

	public static void setProductAction(ProductAction action) {
		productAction = action;
		appleAction();
	}

	private void saveAction() {
		if (productAction != null) {
			SharedPreferences prefs = getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			Gson gson = new Gson();
			String json = gson.toJson(productAction);
			editor.putString("productAction", json);
			editor.apply();
		}
	}

	private void loadAction() {
		SharedPreferences prefs = getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
		String json = prefs.getString("productAction", "");
		Gson gson = new Gson();
		if (!json.isEmpty()) {
			productAction = gson.fromJson(json, ProductAction.class);
			appleAction();
		}
	}

	private static void appleAction() {
		if(productAction != null) {
			for (ProductCart item : shoppingCart) {
				if (item.equals(productAction.getProduct()) && isAction) {
					int price = item.getPrice();
					int percentage = MainActivity.getProductAction().getNewPrice();
					int newPrice = price - (price * percentage / 100);
					item.setPrice(newPrice);
					isAction = false;
				}
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("Load carts");
		shoppingCart = sqlHelper.loadShoppingCart();
		updateCount();
		loadAction();
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("Save carts");
		sqlHelper.saveShoppingCart(shoppingCart);
		updateCount();
		saveAction();
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("Save carts");
		sqlHelper.saveShoppingCart(shoppingCart);
		updateCount();
		saveAction();
	}
}
