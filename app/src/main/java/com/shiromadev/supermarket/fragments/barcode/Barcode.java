package com.shiromadev.supermarket.fragments.barcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.item.shoppingcart.ProductCart;

public class Barcode extends Fragment implements View.OnClickListener{
	private ImageView qrCode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_barcode, container, false);
		qrCode = root.findViewById(R.id.barcode_view);
		Button generateQRBtn = root.findViewById(R.id.generateQRBtn);
		generateQRBtn.setOnClickListener(this);
		StringBuilder textBarcode = new StringBuilder();
		for(ProductCart product: MainActivity.getShoppingCart()){
			textBarcode.append(product.getName()).append(":").append(product.getCountProduct()).append("\n");
		}
		generateQRCode(String.valueOf(textBarcode));
		return root;
	}

	private void generateQRCode(String text)
	{
		qrCode.setImageBitmap(null);
		BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
		try {
			Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
			qrCode.setImageBitmap(bitmap);
		}
		catch (WriterException e) {
			System.out.println(e.getMessage());
		}
	}


	@Override
	public void onClick(View v) {

	}
}