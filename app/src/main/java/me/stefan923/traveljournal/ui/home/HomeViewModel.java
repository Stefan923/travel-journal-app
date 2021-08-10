package me.stefan923.traveljournal.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.stefan923.traveljournal.model.Trip;
import me.stefan923.traveljournal.repository.TripRepository;

public class HomeViewModel extends AndroidViewModel {

    private final TripRepository tripRepository;
    private final LiveData<List<Trip>> trips;

    public HomeViewModel(Application application) {
        super(application);

        this.tripRepository = new TripRepository(application);
        this.trips = tripRepository.getTrips();
    }

    public LiveData<List<Trip>> getTrips() {
        return trips;
    }

    public void addTrip(Trip trip) {
        tripRepository.insertTrip(trip);
    }

}