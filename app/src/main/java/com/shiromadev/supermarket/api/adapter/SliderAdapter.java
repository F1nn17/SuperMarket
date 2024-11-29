package com.shiromadev.supermarket.api.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.item.Product;
import lombok.NonNull;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SlideViewHolder> {

	private List<SlideItem> slideItems;

	public SliderAdapter(List<SlideItem> slideItems) {
		this.slideItems = slideItems;
	}

	@NonNull
	@Override
	public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_slide, parent, false);
		return new SlideViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
		SlideItem item = slideItems.get(position);
		holder.bind(item);
	}

	@Override
	public int getItemCount() {
		return slideItems.size();
	}

	public static class SlideItem {
		private String text;
		private Product product;
		private int newPrice;

		public SlideItem(String text) {
			this.text = text;
		}

		public SlideItem(String text, Product product){
			this.text = text;
			this.product = product;
		}
		public SlideItem(String text, Product product, int newPrice){
			this.text = text;
			this.product = product;
			this.newPrice = newPrice;
		}

		public String getText() {
			return text;
		}

		public Product getProduct() {
			return product;
		}

		public int getNewPrice() {
			return newPrice;
		}
	}

	public static class SlideViewHolder extends RecyclerView.ViewHolder {
		private TextView textView;

		public SlideViewHolder(View itemView) {
			super(itemView);
			textView = itemView.findViewById(R.id.discountText); // ID элемента в разметке
		}

		public void bind(SlideItem item) {
			textView.setText(item.getText());
		}
	}
}


