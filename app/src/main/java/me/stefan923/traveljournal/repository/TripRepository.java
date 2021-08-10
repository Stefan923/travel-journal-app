package me.stefan923.traveljournal.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.stefan923.traveljournal.model.Trip;

public class TripRepository {

    private final TripDao tripDao;

    private final LiveData<List<Trip>> trips;

    public TripRepository(Application application) {
        TripRoomDatabase database = TripRoomDatabase.getDatabase(application);

        this.tripDao = database.getTripDao();
        this.trips = tripDao.getTrips();
    }

    public LiveData<List<Trip>> getTrips() {
        return trips;
    }

    public void insertTrip(Trip trip) {
        TripRoomDatabase.getWriteExecutor().execute(() -> tripDao.insert(trip));
    }

}
