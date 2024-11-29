package com.shiromadev.supermarket.fragments.stockroulette;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager2.widget.ViewPager2;
import com.shiromadev.supermarket.MainActivity;
import com.shiromadev.supermarket.R;
import com.shiromadev.supermarket.api.adapter.SliderAdapter;
import com.shiromadev.supermarket.item.Product;
import com.shiromadev.supermarket.item.ProductAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class StockRoulette extends Fragment {
	private ViewPager2 viewPager;
	private ViewPager2 viewPagerLeft;
	private ViewPager2 viewPagerRight;
	private SliderAdapter sliderAdapter;
	private final Handler handler = new Handler(Looper.getMainLooper());
	private int currentPosition = 0; // Текущая позиция слайда
	private int scrollDuration = 5000; // Общая продолжительность прокрутки (5 секунд)
	private final int interval = 1000; // Интервал между прокрутками (1 секунда)
	private List<SliderAdapter.SlideItem> slideItems  = new ArrayList<>();
	private static final String PREFS_NAME = "TimerPrefs";
	private static final String KEY_BUTTON_TIMESTAMP = "button_timestamp";
	private Button startButton;
	private TextView timerText;
	private long timeLeftInMillis;
	private long hours = 24;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_stock_roulette, container, false);
		viewPager = root.findViewById(R.id.viewPagerCentral);
		viewPagerLeft = root.findViewById(R.id.viewPagerLeft);
		viewPagerRight = root.findViewById(R.id.viewPagerRight);
		startButton = root.findViewById(R.id.startButton);
		timerText = root.findViewById(R.id.timerText);
		startButton.setOnClickListener(v -> startAutoScroll());
		ArrayList<Product> products = MainActivity.getSqlHelper().getAllProducts();
		slideItems = new ArrayList<>();
		checkButtonTimestamp();
		Random random = new Random();
		for (Product item : products) {
			int randInt = random.nextInt(75 - 5) + 5;
			@SuppressLint("DefaultLocale") String text = String.format("Скидка %d%%\n%s", randInt, item.getName());
			slideItems.add(new SliderAdapter.SlideItem(text, item, randInt));
		}
		// Установите адаптер
		sliderAdapter = new SliderAdapter(slideItems);
		viewPager.setAdapter(sliderAdapter);
		viewPagerLeft.setAdapter(sliderAdapter);
		viewPagerRight.setAdapter(sliderAdapter);
		viewPager.setUserInputEnabled(false);
		int max = slideItems.size();
		int randCard = random.nextInt(max - 1) + 1;
		viewPager.setCurrentItem(randCard, false);
		viewPagerLeft.setCurrentItem(randCard-1, false);
		viewPagerRight.setCurrentItem(randCard+1, false);
		scrollDuration = random.nextInt(7000-5000)+5000;

		// Добавьте анимацию для перехода между слайдами
		viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
			@Override
			public void transformPage(@NotNull View page, float position) {
				page.setAlpha(0f);
				page.setVisibility(View.VISIBLE);
				page.setTranslationX(page.getWidth() * -position);
				page.setAlpha(1 - Math.abs(position));
			}
		});

		return root;
	}

	@SuppressLint("SetTextI18n")
	private void startAutoScroll() {
		timerText.setVisibility(View.VISIBLE);
		MainActivity.setAction(true);
		// Обновляем текст на таймере
		// Зафиксировать текущее время в миллисекундах
		long currentTimeMillis = System.currentTimeMillis();
		// Сохраняем время нажатия кнопки в SharedPreferences
		saveButtonTimestamp(currentTimeMillis);
		checkButtonTimestamp();
		// Показать сообщение о том, что время зафиксировано
		// Скрываем кнопку после нажатия
		startButton.setVisibility(View.INVISIBLE);
		// Начальная позиция слайда
		currentPosition = viewPager.getCurrentItem();
		// Запуск прокрутки
		startSliding();
		// Останавливаем прокрутку через 5 секунд
		handler.postDelayed(() -> {
			// Прекращаем прокрутку после 5 секунд
			handler.removeCallbacksAndMessages(null);
			System.out.println("Прокрутка завершена");
			System.out.println("акция применена след: " + slideItems.get(currentPosition).getText());
			applyAction(slideItems.get(currentPosition).getProduct(), slideItems.get(currentPosition).getNewPrice());
		}, scrollDuration);
	}

	private void applyAction(Product product, int newPrice) {
		MainActivity.setProductAction(ProductAction.builder()
				.product(product)
				.newPrice(newPrice)
			.build());
	}

	private void startSliding() {
		// Прокручиваем слайды каждые 1 секунду
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Вычисляем следующую позицию слайда
				currentPosition = (currentPosition + 1) % sliderAdapter.getItemCount();
				viewPager.setCurrentItem(currentPosition, false);
				viewPagerLeft.setCurrentItem(currentPosition-1, false);
				viewPagerRight.setCurrentItem(currentPosition+1, false);
				// Повторяем прокрутку через интервал (1 секунда)
				handler.postDelayed(this, interval);
			}
		}, interval); // Начнем через 1 секунду
	}

	// Метод для сохранения метки времени (время нажатия кнопки)
	private void saveButtonTimestamp(long timestamp) {
		SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(KEY_BUTTON_TIMESTAMP, timestamp);
		editor.apply();
	}

	// Метод для проверки, прошло ли 12 часа с момента нажатия
	@SuppressLint("DefaultLocale")
	private void checkButtonTimestamp() {
		// Получаем сохранённую метку времени
		SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		long buttonTimestamp = preferences.getLong(KEY_BUTTON_TIMESTAMP, 0);

		// Проверяем, прошло ли 12 часа
		if (buttonTimestamp != 0) {
			long currentTimeMillis = System.currentTimeMillis();
			long timeDifference = currentTimeMillis - buttonTimestamp;

			// Если прошло больше 12 часов, показываем кнопку
			if (timeDifference >= hours * 60 * 60 * 1000) {
				// Показываем кнопку снова
				startButton.setVisibility(View.VISIBLE);
				timerText.setVisibility(View.INVISIBLE);
				timerText.setText(""); // Очищаем текст
				System.out.println("12 часа прошли. Кнопка снова доступна.");
			} else {
				// Если не прошло 12 часа, показываем оставшееся время
				long remainingTimeMillis = (hours * 60 * 60 * 1000) - timeDifference;
				timeLeftInMillis = remainingTimeMillis;
				updateTimerDisplay(remainingTimeMillis);

				// Скрываем кнопку
				startButton.setVisibility(View.INVISIBLE);
				timerText.setVisibility(View.VISIBLE);

				// Начинаем отсчёт и обновление текста
				startCountDown();
			}
		} else {
			// Если нет сохранённого времени, показываем кнопку
			startButton.setVisibility(View.VISIBLE);
			timerText.setVisibility(View.INVISIBLE);
		}
	}


	// Метод для отображения оставшегося времени в TextView
	private void updateTimerDisplay(long millisUntilFinished) {
		int hours = (int) (millisUntilFinished / 1000) / 3600;
		int minutes = (int) ((millisUntilFinished / 1000) % 3600) / 60;
		int seconds = (int) (millisUntilFinished / 1000) % 60;

		String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
		timerText.setText(timeLeft);
	}

	// Метод для запуска отсчёта времени
	private void startCountDown() {
		new CountDownTimer(timeLeftInMillis, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				// Обновляем текст с оставшимся временем каждую секунду
				updateTimerDisplay(millisUntilFinished);
			}

			@Override
			public void onFinish() {
				// Когда таймер закончится, показываем кнопку и скрываем таймер
				startButton.setVisibility(View.VISIBLE);
				timerText.setVisibility(View.INVISIBLE);
				timerText.setText(""); // Очищаем текст
			}
		}.start();
	}

}