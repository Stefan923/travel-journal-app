package me.stefan923.traveljournal.ui.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.stefan923.traveljournal.R;
import me.stefan923.traveljournal.model.Trip;
import me.stefan923.traveljournal.model.TripType;

public class EditTripActivity extends AppCompatActivity {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;

    private final Calendar startDate = Calendar.getInstance();
    private final Calendar endDate = Calendar.getInstance();

    private EditText nameEditText;
    private EditText destinationEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;

    private ImageView pictureImageView;

    private SeekBar priceSeekBar;
    private TextView priceTextView;

    private RadioButton typeRadioCityBreak;
    private RadioButton typeRadioSeaSide;
    private RadioButton typeRadioMountains;

    private RatingBar tripRatingBar;

    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        loadViews();

        Intent intent = getIntent();
        if (intent.hasExtra("trip")) {
            trip = (Trip) intent.getExtras().getSerializable("trip");
            if (trip == null) {
                System.out.println("rip");
            }
            setUpTripDetails();
        } else {
            trip = new Trip();
        }

        setUpPicturePickers();
        setUpPriceSeekBar();
        setUpStartDatePicker();
        setUpEndDatePicker();
        setUpSaveButton();
    }

    private void loadViews() {
        nameEditText = findViewById(R.id.nameEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        priceSeekBar = findViewById(R.id.priceSeekBar);
        priceTextView = findViewById(R.id.priceTextView);
        tripRatingBar = findViewById(R.id.tripRatingBar);
        pictureImageView = findViewById(R.id.editTripPicture);
        typeRadioCityBreak = findViewById(R.id.typeRadioCityBreak);
        typeRadioSeaSide = findViewById(R.id.typeRadioSeaSide);
        typeRadioMountains = findViewById(R.id.typeRadioMountains);
    }

    private void setUpTripDetails() {
        nameEditText.setText(trip.getName());
        destinationEditText.setText(trip.getDestination());
        startDateEditText.setText(dateFormat.format(trip.getStartDate()));
        endDateEditText.setText(dateFormat.format(trip.getEndDate()));
        tripRatingBar.setRating(trip.getRating());
        priceSeekBar.setProgress(trip.getPrice());
        priceTextView.setText(trip.getPrice() + " EUR");

        switch (trip.getTripType()) {
            case CITY_BREAK:
                typeRadioCityBreak.setChecked(true);
                break;
            case SEA_SIDE:
                typeRadioSeaSide.setChecked(true);
                break;
            case MOUNTAINS:
                typeRadioMountains.setChecked(true);
                break;
            default:
                break;
        }

        byte[] pictureByteArray = trip.getPicture();
        Bitmap pictureBitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.length);
        pictureImageView.setImageBitmap(pictureBitmap);
    }

    private void setUpPicturePickers() {
        ImageButton galleryImageButton = findViewById(R.id.galleryImageButton);
        ImageButton cameraImageButton = findViewById(R.id.cameraImageButton);

        galleryImageButton.setOnClickListener(view -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , GALLERY_REQUEST_CODE);
        });

        cameraImageButton.setOnClickListener(view -> {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, CAMERA_REQUEST_CODE);
        });
    }

    private void setUpPriceSeekBar() {
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trip.setPrice(progress);
                priceTextView.setText(progress + " EUR");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void setUpStartDatePicker() {
        DatePickerDialog.OnDateSetListener startDatePicker = (view, year, monthOfYear, dayOfMonth) -> {
            this.startDate.set(Calendar.YEAR, year);
            this.startDate.set(Calendar.MONTH, monthOfYear);
            this.startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startDateEditText.setText(dateFormat.format(startDate.getTime()));
        };

        startDateEditText.setOnClickListener(view -> new DatePickerDialog(EditTripActivity.this,
                startDatePicker, this.startDate.get(Calendar.YEAR), this.startDate.get(Calendar.MONTH),
                this.startDate.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void setUpEndDatePicker() {
        DatePickerDialog.OnDateSetListener endDatePicker = (view, year, monthOfYear, dayOfMonth) -> {
            this.endDate.set(Calendar.YEAR, year);
            this.endDate.set(Calendar.MONTH, monthOfYear);
            this.endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDateEditText.setText(dateFormat.format(endDate.getTime()));
        };

        endDateEditText.setOnClickListener(view -> new DatePickerDialog(EditTripActivity.this,
                endDatePicker, this.endDate.get(Calendar.YEAR), this.endDate.get(Calendar.MONTH),
                this.endDate.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void setUpSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> {
            RadioGroup typeRadioGroup = findViewById(R.id.typeRadioGroup);

            Bitmap bitmap = ((BitmapDrawable) pictureImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] pictureByteArray = outputStream.toByteArray();

            switch (typeRadioGroup.getCheckedRadioButtonId()) {
                case R.id.typeRadioCityBreak:
                    trip.setTripType(TripType.CITY_BREAK);
                    break;
                case R.id.typeRadioSeaSide:
                    trip.setTripType(TripType.SEA_SIDE);
                    break;
                case R.id.typeRadioMountains:
                    trip.setTripType(TripType.MOUNTAINS);
                    break;
                default:
                    break;
            }

            trip.setName(nameEditText.getText().toString());
            trip.setDestination(destinationEditText.getText().toString());
            trip.setRating(tripRatingBar.getRating());
            trip.setStartDate(startDate.getTimeInMillis());
            trip.setEndDate(endDate.getTimeInMillis());
            trip.setPicture(pictureByteArray);

            Intent replyIntent = new Intent();
            replyIntent.putExtra("trip", trip);
            setResult(RESULT_OK, replyIntent);

            finish();
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    pictureImageView.setImageURI(selectedImage);
                }
                break;
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    pictureImageView.setImageBitmap(imageBitmap);
                }
                break;
            default:
                break;
        }
    }

}