package com.shiromadev.supermarket.sqlite.contoller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.shiromadev.supermarket.item.Product;
import com.shiromadev.supermarket.item.shoppingcart.ProductCart;
import com.shiromadev.supermarket.item.shoppingcart.ProductCartList;

import java.util.ArrayList;
import java.util.Arrays;

public class SqliteController extends SQLiteOpenHelper {
	private static final String NAME_DB = "products.db";
	private static final int SCHEMA = 11;

	private static final String TABLE_NAME = "products";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_CATEGORIES = "categories";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PRICE = "price";
	private static final String COLUMN_ICON = "icon";

	private static final String TABLE_NAME_CART = "cart";
	private static final String COLUMN_CART_ID = "cart_id";
	private static final String COLUMN_PRODUCT_ID = "product_id";
	private static final String COLUMN_COUNT = "count";

	public SqliteController(@Nullable Context context) {
		super(context, NAME_DB, null, SCHEMA);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("Create database table");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
			" ( "
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_CATEGORIES + " TEXT,"
			+ COLUMN_NAME + " TEXT, "
			+ COLUMN_PRICE + " INTEGER, "
			+ COLUMN_ICON + " TEXT "
			+ ");"
		);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CART +
			" ( "
			+ COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_PRODUCT_ID + " INTEGER NOT NULL,"
			+ COLUMN_COUNT + " INTEGER NOT NULL "
			+ ");"
		);
		System.out.println("Create database table success!");
		fullingTable(db);
		System.out.println("Database table is filled!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public void fullingTable(SQLiteDatabase db) {
		System.out.println("Заполнение таблицы!");
		ArrayList<Product> tables = new ArrayList<>();
		//milk
		tables.add(Product.builder().name("Молоко").price(119).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		tables.add(Product.builder().name("Кефир").price(99).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		tables.add(Product.builder().name("Сыр").price(249).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		tables.add(Product.builder().name("Йогурт").price(59).categories(Product.CATEGORIES.MILK_PRODUCTS).build());
		//meat
		tables.add(Product.builder().name("Говядина").price(1199).categories(Product.CATEGORIES.MEAT_PRODUCTS).build());
		tables.add(Product.builder().name("Свинина").price(1099).categories(Product.CATEGORIES.MEAT_PRODUCTS).build());
		tables.add(Product.builder().name("Курица").price(699).categories(Product.CATEGORIES.MEAT_PRODUCTS).build());
		//drink
		tables.add(Product.builder().name("Вода").price(49).categories(Product.CATEGORIES.DRINKS).build());
		tables.add(Product.builder().name("Кола-Китайская").price(69).categories(Product.CATEGORIES.DRINKS).build());
		tables.add(Product.builder().name("Черный чай").price(39).categories(Product.CATEGORIES.DRINKS).build());
		tables.add(Product.builder().name("КОФЕ").price(99).categories(Product.CATEGORIES.DRINKS).build());
		//fruits
		tables.add(Product.builder().name("Яблоко").price(29).categories(Product.CATEGORIES.FRUITS).build());
		tables.add(Product.builder().name("Лимон").price(19).categories(Product.CATEGORIES.FRUITS).build());
		//vegetables
		tables.add(Product.builder().name("Картофель").price(39).categories(Product.CATEGORIES.VEGETABLES).build());
		tables.add(Product.builder().name("Томат").price(49).categories(Product.CATEGORIES.VEGETABLES).build());

		ContentValues values = new ContentValues(tables.size());
		int id = 1;
		for (Product item : tables) {
			values.put(COLUMN_ID, id++);
			values.put(COLUMN_CATEGORIES, item.getCategories().getCATEGORIES());
			values.put(COLUMN_NAME, item.getName());
			values.put(COLUMN_PRICE, item.getPrice());
			values.put(COLUMN_ICON, item.getIcon());
			db.insert(TABLE_NAME, null, values);
			values.clear();
		}
	}

	@SuppressLint({"Recycle", "Range"})
	public ArrayList<Product> getProductCategories(SQLiteDatabase db, Product.CATEGORIES categories) {
		ArrayList<Product> tables = new ArrayList<>();
		System.out.println("Вызов метода!");
		System.out.println("Получаем продукты из нужной категории: " + categories.getCATEGORIES());
		Cursor cursor = db.rawQuery(
			"SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORIES + " = '" + categories.getCATEGORIES() + "'",
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
			System.out.println("item id: " + cursor.getColumnIndex(COLUMN_ID));
			tables.add(createItem(
				cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
				group,
				cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
				cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)),
				cursor.getString(cursor.getColumnIndex(COLUMN_ICON))));
			cursor.moveToNext();
		}
		System.out.println("Продукты получены!");
		db.close();
		return tables;
	}

	@SuppressLint({"Recycle", "Range"})
	public ArrayList<Product> getAll(SQLiteDatabase db) {
		ArrayList<Product> tables = new ArrayList<>();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
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
			tables.add(createItem(
				cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
				group,
				cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
				cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)),
				cursor.getString(cursor.getColumnIndex(COLUMN_ICON))));
			cursor.moveToNext();
		}
		db.close();
		return tables;
	}

	public void saveShoppingCart(SQLiteDatabase db, ProductCartList<ProductCart> cart) {
		try (db) {
			if (!cart.isEmpty()) {
				db.execSQL("DELETE FROM " + TABLE_NAME_CART);
				ContentValues values = new ContentValues(cart.size());
				for (ProductCart item : cart) {
					values.put(COLUMN_PRODUCT_ID, item.getId());
					values.put(COLUMN_COUNT, item.getCountProduct());
					db.insert(TABLE_NAME_CART, null, values);
					values.clear();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
	}

	@SuppressLint("Range")
	public ProductCartList<ProductCart> loadShoppingCart(SQLiteDatabase db) {
		System.out.println("load shopping cart in db");
		ProductCartList<ProductCart> products = new ProductCartList<>();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CART, null);
		System.out.println("carts size: " + cursor.getCount());
		if (cursor.getCount() == 0 ) return new ProductCartList<>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
			System.out.println("id: " + id);
			int count = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT));
			products.add(createItem(getProduct(db, id), count));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return products;
	}

	@SuppressLint({"Recycle", "Range"})
	private Product getProduct(SQLiteDatabase db, int id) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + "'" + id + "'", null);
		Product product;
		cursor.moveToFirst();
		String searchGroup = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIES));
		Product.CATEGORIES group = switch (searchGroup) {
			case "milk_products" -> Product.CATEGORIES.MILK_PRODUCTS;
			case "meat_products" -> Product.CATEGORIES.MEAT_PRODUCTS;
			case "fruits" -> Product.CATEGORIES.FRUITS;
			case "vegetables" -> Product.CATEGORIES.VEGETABLES;
			case "drinks" -> Product.CATEGORIES.DRINKS;
			default -> Product.CATEGORIES.DEFAULT;
		};
		product = createItem(
			cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
			group,
			cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
			cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)),
			cursor.getString(cursor.getColumnIndex(COLUMN_ICON)));
		return product;
	}

	private Product createItem(int id,Product.CATEGORIES categories, String name, int money, String icon) {
		return Product.builder()
			.categories(categories)
			.id(id)
			.name(name)
			.price(money)
			.icon(icon)
			.build();
	}


	private ProductCart createItem(Product p, int count) {
		return ProductCart.builder()
			.name(p.getName())
			.price(p.getPrice())
			.categories(p.getCategories())
			.icon(p.getIcon())
			.countProduct(count)
			.build();
	}
}
