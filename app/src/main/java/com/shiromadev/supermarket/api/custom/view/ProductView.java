package com.shiromadev.supermarket.api.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.item.Product;
import lombok.Getter;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

@Getter
public class ProductView {

	private static final int gravity = Gravity.CENTER;
	private final LinearLayout layout;

	@SuppressLint("SetTextI18n")
	public ProductView(Context context, Product product) {
		// создание LinearLayout
		layout = new LinearLayout(context);
		// установим вертикальную ориентацию
		layout.setOrientation(LinearLayout.VERTICAL);
		// создаем LayoutParams
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 250);
		params.gravity = gravity;
		ImageButton bt = new ImageButton(context);
		bt.setImageResource(R.mipmap.ic_icon_product_foreground);
		layout.addView(bt, params);
		params = new LinearLayout.LayoutParams(250, 100);
		TextView tv = new TextView(context);
		tv.setText(product.getName() + "\n" + product.getPrice());
		tv.getAutoSizeTextAvailableSizes();
		tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
		layout.addView(tv, params);
	}
}
