package com.shiromadev.supermarket.api.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.item.Product;
import com.shiromadev.supermarket.item.shoppingcart.ProductCart;
import lombok.Getter;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

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
			.product(product)
			.countProduct(1)
			.build();
		MainActivity.getShoppingCart().add(productCart);
	}
}
