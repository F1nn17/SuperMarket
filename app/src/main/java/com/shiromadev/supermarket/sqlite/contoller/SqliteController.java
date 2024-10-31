package com.shiromadev.supermarket.sqlite.contoller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.shiromadev.supermarket.item.Product;
import java.util.ArrayList;

public class SqliteController extends SQLiteOpenHelper {
	private static final String NAME_DB = "products.db";
	private static final int SCHEMA = 0;

	private static final String TABLE_NAME = "products";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_CATEGORIES = "categories";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PRICE = "price";
	private static final String COLUMN_ICON = "icon";

	public SqliteController(@Nullable Context context) {
		super(context, NAME_DB, null, SCHEMA);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("Create database table");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
			"( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CATEGORIES + " TEXT," +
			COLUMN_NAME + " TEXT," + COLUMN_PRICE + " INTEGER, " + COLUMN_ICON + " TEXT);");
		System.out.println("Create database table success!");
		fullingTable(db);
		System.out.println("Database table is filled!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	private void fullingTable(SQLiteDatabase db){
		ArrayList<Product> tables = new ArrayList<>();
		//milk
		tables.add(Product.builder().name("Milk").price(119).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		tables.add(Product.builder().name("Kefir").price(99).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		tables.add(Product.builder().name("Cottage cheese").price(249).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		tables.add(Product.builder().name("Yogurt").price(59).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		//meat
		tables.add(Product.builder().name("Beef").price(1199).categories(Product.CATEGORIES.MEAT_PRODUCTS).build());
		tables.add(Product.builder().name("Pork").price(1099).categories(Product.CATEGORIES.MEAT_PRODUCTS).build());
		tables.add(Product.builder().name("Chicken").price(699).categories(Product.CATEGORIES.MEAT_PRODUCTS).build());
		//drink
		tables.add(Product.builder().name("Water").price(49).categories(Product.CATEGORIES.DRINKS).build());
		tables.add(Product.builder().name("Coca-Cola").price(69).categories(Product.CATEGORIES.DRINKS).build());
		tables.add(Product.builder().name("Tea").price(39).categories(Product.CATEGORIES.DRINKS).build());
		tables.add(Product.builder().name("Coffee").price(99).categories(Product.CATEGORIES.DRINKS).build());
		//fruits
		tables.add(Product.builder().name("Apple").price(29).categories(Product.CATEGORIES.FRUITS).build());
		tables.add(Product.builder().name("Lemon").price(19).categories(Product.CATEGORIES.FRUITS).build());
		//vegetables
		tables.add(Product.builder().name("Potato").price(39).categories(Product.CATEGORIES.VEGETABLES).build());
		tables.add(Product.builder().name("Tomato").price(49).categories(Product.CATEGORIES.VEGETABLES).build());

		ContentValues values = new ContentValues(tables.size());
		for (Product item : tables) {
			values.put(COLUMN_CATEGORIES, item.getCategories().getCATEGORIES());
			values.put(COLUMN_NAME, item.getName());
			values.put(COLUMN_PRICE, item.getPrice());
			values.put(COLUMN_ICON, item.getIcon());
			db.insert(TABLE_NAME, null, values);
			values.clear();
		}
	}

	@SuppressLint({"Recycle", "Range"})
	public ArrayList<Product> getProductCategories(SQLiteDatabase db, Product.CATEGORIES categories){
		ArrayList<Product> tables = new ArrayList<>();
		Cursor cursor = db.rawQuery(
			"SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORIES + " = " + categories.getCATEGORIES(),
			null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String searchGroup = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIES));
			Product.CATEGORIES group = switch (searchGroup) {
				case "milk_products" -> Product.CATEGORIES.MILK_PRODUCTS;
				case "meat_products" -> Product.CATEGORIES.MEAT_PRODUCTS;
				case "fruits" -> Product.CATEGORIES.FRUITS;
				case "vegetables" -> Product.CATEGORIES.VEGETABLES;
				case "drinks" -> Product.CATEGORIES.DRINKS;
				default -> Product.CATEGORIES.DEFAULT;
			};
			tables.add(createItem(group,
				cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
				cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)),
				cursor.getString(cursor.getColumnIndex(COLUMN_ICON))));
			cursor.moveToNext();
		}
		db.close();
		return tables;
	}

	private Product createItem(Product.CATEGORIES categories, String name, int money, String icon) {
		return Product.builder()
			.categories(categories)
			.name(name)
			.price(money)
			.icon(icon)
			.build();
	}
}
