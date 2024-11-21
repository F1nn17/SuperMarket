package com.shiromadev.supermarket.api.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.item.Product;
import com.shiromadev.supermarket.item.shoppingcart.ProductCart;
import lombok.Getter;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

@Getter
public class ProductView implements View.OnClickListener {

	private static final int gravity = Gravity.CENTER;
	private final LinearLayout layout;
	private final Product product;

	@SuppressLint("SetTextI18n")
	public ProductView(Context context, Product product) {
		this.product = product;
		layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f);
		params1.gravity = Gravity.FILL;
		layout.setLayoutParams(params1);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 250);
		params.gravity = gravity;
		ImageButton bt = new ImageButton(context);
		bt.setImageResource(R.mipmap.ic_icon_product_foreground);
		bt.setOnClickListener(this);
		layout.addView(bt, params);
		params = new LinearLayout.LayoutParams(250, 100);
		TextView tv = new TextView(context);
		tv.setText(product.getName() + "\n" + product.getPrice());
		tv.getAutoSizeTextAvailableSizes();
		tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
		layout.addView(tv, params);
	}

	@Override
	public void onClick(View v) {
		System.out.println("Товар добавлен в корзину");
		ProductCart productCart = ProductCart.builder()
			.name(product.getName())
			.price(product.getPrice())
			.categories(product.getCategories())
			.icon(product.getIcon())
			.countProduct(1)
			.build();
		MainActivity.getShoppingCart().add(productCart);
	}
}
