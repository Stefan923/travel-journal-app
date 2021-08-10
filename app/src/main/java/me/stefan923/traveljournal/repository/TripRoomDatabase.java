package me.stefan923.traveljournal.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.stefan923.traveljournal.model.Trip;

@Database(entities = {Trip.class}, version = 1, exportSchema = false)
public abstract class TripRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "trips_database";
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService WRITE_EXECUTOR =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile TripRoomDatabase instance;

    public abstract TripDao getTripDao();

    public static TripRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (TripRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            TripRoomDatabase.class, DATABASE_NAME).build();
                }
            }
        }

        return instance;
    }

    public static ExecutorService getWriteExecutor() {
        return WRITE_EXECUTOR;
    }
}
