package com.shiromadev.supermarket.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.shiromadev.supermarket.item.Product;
import com.shiromadev.supermarket.sqlite.contoller.SqliteController;

import java.util.ArrayList;

public class SQLiteControllerHelper {
	private final SqliteController sqliteController;
	private SQLiteDatabase database;

	public SQLiteControllerHelper(Context context) {
		sqliteController = new SqliteController(context);
		database = sqliteController.getWritableDatabase();
	}

	public ArrayList<Product> getProductCategories(Product.CATEGORIES categories) {
		database = sqliteController.getReadableDatabase();
		return sqliteController.getProductCategories(database, categories);
	}

}