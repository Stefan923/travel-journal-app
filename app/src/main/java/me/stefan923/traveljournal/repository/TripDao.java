package me.stefan923.traveljournal.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import me.stefan923.traveljournal.model.Trip;

@Dao
public interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trip trip);

    @Query("SELECT * FROM trips")
    LiveData<List<Trip>> getTrips();

}
