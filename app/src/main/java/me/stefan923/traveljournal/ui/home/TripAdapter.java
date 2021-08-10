package me.stefan923.traveljournal.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.stefan923.traveljournal.R;
import me.stefan923.traveljournal.model.Trip;
import me.stefan923.traveljournal.ui.trip.EditTripActivity;
import me.stefan923.traveljournal.ui.trip.ShowTripActivity;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.InnerTripViewHolder> {

    private final LayoutInflater layoutInflater;

    private List<Trip> trips;

    public TripAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public InnerTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.trip_item, parent, false);
        return new InnerTripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerTripViewHolder holder, int position) {
        if(position % 2 == 1) {
            holder.getRootView().setBackgroundResource(R.color.light_gray);
        }

        Trip currentTrip = trips.get(position);
        holder.getTextViewName().setText(currentTrip.getName());
        holder.getTextViewPrice().setText("Price: " + currentTrip.getPrice() + " EUR");
        holder.getTextViewDestination().setText("Destination: " + currentTrip.getDestination());
        holder.getRatingBarRating().setRating(currentTrip.getRating());

        byte[] currentTripPicture = currentTrip.getPicture();
        holder.getImageViewPicture().setImageBitmap(BitmapFactory.decodeByteArray(currentTripPicture,
                0, currentTripPicture.length));

        ToggleButton toggleButton = holder.getButtonAddToFavorite();
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_favorite_border));
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_favorite));
            } else {
                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_favorite_border));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips == null ? 0 : trips.size();
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    class InnerTripViewHolder extends TripViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public InnerTripViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent showTripIntent = new Intent(view.getContext(), ShowTripActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("trip", trips.get(getAdapterPosition()));
            showTripIntent.putExtras(bundle);
            view.getContext().startActivity(showTripIntent);
        }

        @Override
        public boolean onLongClick(View view) {
            Intent editTripIntent = new Intent(view.getContext(), EditTripActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("trip", trips.get(getAdapterPosition()));
            editTripIntent.putExtras(bundle);

            FragmentManager.findFragment(view).startActivityForResult(editTripIntent, HomeFragment.EDIT_TRIP_REQUEST_CODE);

            return true;
        }
    }

}
