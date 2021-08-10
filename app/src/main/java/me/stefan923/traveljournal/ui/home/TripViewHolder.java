package me.stefan923.traveljournal.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.stefan923.traveljournal.R;

public abstract class TripViewHolder extends RecyclerView.ViewHolder {

    final private LinearLayout rootView;

    final private TextView textViewName;
    final private TextView textViewPrice;
    final private TextView textViewDestination;

    final private RatingBar ratingBarRating;

    final private ImageView imageViewPicture;

    final private ToggleButton buttonAddToFavorite;

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);

        this.rootView = itemView.findViewById(R.id.rootView);
        this.textViewName = itemView.findViewById(R.id.tripName);
        this.textViewPrice = itemView.findViewById(R.id.tripPrice);
        this.textViewDestination = itemView.findViewById(R.id.tripDestination);
        this.ratingBarRating = itemView.findViewById(R.id.tripRating);
        this.imageViewPicture = itemView.findViewById(R.id.tripPicture);
        this.buttonAddToFavorite = itemView.findViewById(R.id.tripAddToFavorite);
    }

    public LinearLayout getRootView() {
        return rootView;
    }

    public TextView getTextViewName() {
        return textViewName;
    }

    public TextView getTextViewPrice() {
        return textViewPrice;
    }

    public RatingBar getRatingBarRating() {
        return ratingBarRating;
    }

    public TextView getTextViewDestination() {
        return textViewDestination;
    }

    public ImageView getImageViewPicture() {
        return imageViewPicture;
    }

    public ToggleButton getButtonAddToFavorite() {
        return buttonAddToFavorite;
    }
}
