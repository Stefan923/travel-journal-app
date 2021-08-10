package me.stefan923.traveljournal.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

import me.stefan923.traveljournal.converter.TripTypeConverter;

@Entity(tableName = "trips")
public class Trip implements Serializable {

    private static final long serialVersionUID = -7061705905690151489L;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "destination")
    private String destination;

    @TypeConverters(TripTypeConverter.class)
    @ColumnInfo(name = "trip_type")
    private TripType tripType;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "rating")
    private float rating;

    @ColumnInfo(name = "start_date")
    private long startDate;

    @ColumnInfo(name = "end_date")
    private long endDate;

    @ColumnInfo(name = "picture", typeAffinity = ColumnInfo.BLOB)
    private byte[] picture;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    public Trip() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
