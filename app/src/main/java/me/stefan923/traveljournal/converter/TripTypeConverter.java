package me.stefan923.traveljournal.converter;

import androidx.room.TypeConverter;

import me.stefan923.traveljournal.model.TripType;

public class TripTypeConverter {

    @TypeConverter
    public static TripType fromInteger(int value) {
        TripType[] types = TripType.values();
        return (value < 0 || value >= types.length) ? null : types[value];
    }

    @TypeConverter
    public static int tripTypeToInteger(TripType tripType) {
        return tripType == null ? -1 : tripType.ordinal();
    }

}
