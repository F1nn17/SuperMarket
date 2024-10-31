package com.shiromadev.supermarket;

import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.shiromadev.supermarket.fragments.catalog.Catalog;
import com.shiromadev.supermarket.sqlite.helper.SQLiteControllerHelper;
import lombok.Getter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
	FrameLayout container;
	FragmentManager fragmentManager;
	@Getter
	private static SQLiteControllerHelper sqlHelper;

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
		setContentView(R.layout.activity_main);
		container = (FrameLayout) findViewById(R.id.child_fragment);
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

	private void switchFragment(Fragment fragment){
		getSupportFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
	}

	public void onClick(View view) {
		int checkId = view.getId();
		if (checkId == R.id.catalog_button) {
			switchFragment(new Catalog());
		}
		if (checkId == R.id.barcode) {
			//switchFragment(new Catalog());
		}
		if (checkId == R.id.stock_roulette) {
			//switchFragment(new Catalog());
		}
		if (checkId == R.id.shopping_cart) {
			//switchFragment(new Catalog());
		}
	}
}