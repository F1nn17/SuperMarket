package com.shiromadev.supermarket.api.custom.view.shoppingcart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.fragments.shoppingcart.ShoppingCart;
import com.shiromadev.supermarket.item.shoppingcart.ProductCart;
import lombok.Getter;

@Getter
public class RemoveProduct implements View.OnClickListener {
	private final Button bt;
	private final ProductCart product;
	private final Context context;

	@SuppressLint({"SetTextI18n", "ResourceAsColor"})
	public RemoveProduct(Context context, ProductCart product){
		this.product = product;
		this.context = context;
		bt = new Button(context);
        bt.setText("x");
		bt.setTextSize(12);
		bt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		bt.setTextColor(Color.RED);
		bt.setBackgroundColor(Color.TRANSPARENT);
		bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// Создаем и показываем AlertDialog
		new AlertDialog.Builder(context)
				.setTitle("Подтверждение удаления") // Заголовок
				.setMessage("Вы уверены, что хотите удалить этот товар?") // Сообщение
				.setPositiveButton("Да", (dialog, which) -> {
					// Удаляем продукт при подтверждении
					MainActivity.getShoppingCart().remove(product);
					ShoppingCart.update();
					MainActivity.updateCount();
				})
				.setNegativeButton("Нет", (dialog, which) -> {
					// Просто закрываем диалог, ничего не делаем
					dialog.dismiss();
				})
				.show();
	}
}
