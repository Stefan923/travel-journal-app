package me.stefan923.traveljournal.ui.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.stefan923.traveljournal.R;
import me.stefan923.traveljournal.model.Trip;
import me.stefan923.traveljournal.model.Weather;
import me.stefan923.traveljournal.model.WeatherInfo;
import me.stefan923.traveljournal.retrofit.OnGetWeatherCallback;
import me.stefan923.traveljournal.retrofit.WeatherRepository;

public class ShowTripActivity extends AppCompatActivity {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip);

        Trip trip = (Trip) getIntent().getExtras().getSerializable("trip");
        if (trip == null) {
            finish();
            return;
        }

        setUpTripDetails(trip);
        loadWeather(trip);
        setUpBackButton();
    }

    private void setUpTripDetails(Trip trip) {
        ((EditText) findViewById(R.id.nameEditTextShow)).setText(trip.getName());
        ((EditText) findViewById(R.id.destinationEditTextShow)).setText(trip.getDestination());
        ((EditText) findViewById(R.id.startDateEditTextShow)).setText(dateFormat.format(trip.getStartDate()));
        ((EditText) findViewById(R.id.endDateEditTextShow)).setText(dateFormat.format(trip.getEndDate()));
        ((RatingBar) findViewById(R.id.tripRatingBarShow)).setRating(trip.getRating());
        ((TextView) findViewById(R.id.priceTextViewShow)).setText(trip.getPrice() + " EUR");
        SeekBar priceSeekBar = findViewById(R.id.priceSeekBarShow);
        priceSeekBar.setProgress(trip.getPrice());
        priceSeekBar.setOnTouchListener((view, event) -> true);

        switch (trip.getTripType()) {
            case CITY_BREAK:
                ((RadioButton) findViewById(R.id.typeRadioCityBreakShow)).setChecked(true);
                break;
            case SEA_SIDE:
                ((RadioButton) findViewById(R.id.typeRadioSeaSideShow)).setChecked(true);
                break;
            case MOUNTAINS:
                ((RadioButton) findViewById(R.id.typeRadioMountainsShow)).setChecked(true);
                break;
            default:
                break;
        }

        byte[] pictureByteArray = trip.getPicture();
        Bitmap pictureBitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.length);
        ((ImageView) findViewById(R.id.pictureImageViewShow)).setImageBitmap(pictureBitmap);


    }

    private void loadWeather(Trip trip) {
        WeatherRepository weatherRepository = WeatherRepository.getInstance();

        weatherRepository.getWeather(new OnGetWeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                List<WeatherInfo> weatherInfo = weather.getWeatherInfo();
                if (!weatherInfo.isEmpty()) {
                    ((TextView) findViewById(R.id.weatherTextViewShow))
                            .setText("Weather: " + weatherInfo.get(0).getDescription());
                } else {
                    onError();
                }
            }

            @Override
            public void onError() {
                ((TextView) findViewById(R.id.weatherTextViewShow))
                        .setText("Weather: couldn't load");
            }
        }, trip.getDestination());
    }

    private void setUpBackButton() {
        ((Button) findViewById(R.id.backButton)).setOnClickListener(view -> finish());
    }
}