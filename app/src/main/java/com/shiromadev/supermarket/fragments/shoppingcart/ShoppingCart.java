package com.shiromadev.supermarket.fragments.shoppingcart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.api.custom.view.shoppingcart.RemoveProduct;
import com.shiromadev.supermarket.item.Product;
import com.shiromadev.supermarket.item.shoppingcart.ProductCart;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShoppingCart extends Fragment {
	private static final ArrayList<TableRow> tableRows = new ArrayList<>();
	private static final ArrayList<TextView> columnsTextTitle = new ArrayList<>();
	private static final ArrayList<TextView> columnsTextPrice = new ArrayList<>();
	private static final ArrayList<TextView> columnsTextQuantity = new ArrayList<>();
	private static final ArrayList<TextView> columnsTextTotal = new ArrayList<>();
	private static final ArrayList<RemoveProduct> columnsItemRemove = new ArrayList<>();
	private TableLayout shoppingCartTable;
	TextView totalSum;

	private static FragmentManager fragmentManager;

	public ShoppingCart(FragmentManager fragmentManager) {
		ShoppingCart.fragmentManager = fragmentManager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("MissingInflatedId")
	@Override
	public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
		shoppingCartTable = root.findViewById(R.id.shopping_cart_table);
		totalSum = root.findViewById(R.id.total_sum);
		System.out.println("Продукты в корзине:");
		for (ProductCart item : MainActivity.getShoppingCart()) {
			System.out.println(item.getProduct().getName());
		}
		loadShoppingCartProducts();
		total();
		return root;
	}

	@SuppressLint("ResourceAsColor")
	private void loadShoppingCartProducts() {
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
		);

		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
			TableRow.LayoutParams.WRAP_CONTENT,
			100
		);

		for (int i = 0; i < MainActivity.getShoppingCart().size(); i++) {
			Product product = MainActivity.getShoppingCart().get(i).getProduct();
			int k = 0;
			tableRows.add(k, new TableRow(getContext()));
			//наименование товара
			columnsTextTitle.add(k, new TextView(getContext()));
			columnsTextTitle.get(k).setText(product.getName());
			columnsTextTitle.get(k).setTextSize(16);
			columnsTextTitle.get(k).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
			columnsTextTitle.get(k).setLayoutParams(params);
			tableRows.get(k).addView(columnsTextTitle.get(k), rowParams);

			//цена товара
			columnsTextPrice.add(k, new TextView(getContext()));
			columnsTextPrice.get(k).setText(String.valueOf(product.getPrice()));
			columnsTextPrice.get(k).setTextSize(16);
			columnsTextPrice.get(k).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
			columnsTextPrice.get(k).setLayoutParams(params);
			tableRows.get(k).addView(columnsTextPrice.get(k), rowParams);

			//количество одного товара в корзине
			columnsTextQuantity.add(k, new TextView(getContext()));
			columnsTextQuantity.get(k).setText(String.valueOf(MainActivity.getShoppingCart().get(i).getCountProduct()));
			columnsTextQuantity.get(k).setTextSize(16);
			columnsTextQuantity.get(k).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
			columnsTextQuantity.get(k).setLayoutParams(params);
			tableRows.get(k).addView(columnsTextQuantity.get(k), rowParams);

			//общая сумма всего количества товара
			columnsTextTotal.add(k, new TextView(getContext()));
			columnsTextTotal.get(k).setText(String.valueOf(MainActivity.getShoppingCart().get(i).totalSumProduct()));
			columnsTextTotal.get(k).setTextSize(16);
			columnsTextTotal.get(k).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
			columnsTextTotal.get(k).setLayoutParams(params);
			tableRows.get(k).addView(columnsTextTotal.get(k), rowParams);

			//кнопка удаления из корзины
			columnsItemRemove.add(k, new RemoveProduct(getContext(), MainActivity.getShoppingCart().get(i)));
			rowParams = new TableRow.LayoutParams(
				60,
				100
			);
			tableRows.get(k).addView(columnsItemRemove.get(k).getBt(), rowParams);

			shoppingCartTable.addView(tableRows.get(k));
		}
	}

	@SuppressLint("SetTextI18n")
	private void total(){
		int total = 0;
		for (ProductCart productCart: MainActivity.getShoppingCart()) {
			total += productCart.totalSumProduct();
		}
		totalSum.setText("К Оплате: " + total + "Руб");
	}

	public static void update() {
		fragmentManager.beginTransaction()
			.replace(MainActivity.getContainer().getId(), new ShoppingCart(fragmentManager))
			.commit();
	}
}