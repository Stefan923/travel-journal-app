package me.stefan923.traveljournal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.stefan923.traveljournal.R;
import me.stefan923.traveljournal.databinding.FragmentHomeBinding;
import me.stefan923.traveljournal.model.Trip;
import me.stefan923.traveljournal.ui.trip.EditTripActivity;

public class HomeFragment extends Fragment {

    public static final int EDIT_TRIP_REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;

    private FragmentHomeBinding binding;

    private HomeViewModel homeViewModel;

    private RecyclerView tripsRecyclerView;
    private TripAdapter tripAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.fab).setOnClickListener(fabView -> {
            Intent addTripIntent = new Intent(getContext(), EditTripActivity.class);
            startActivityForResult(addTripIntent, EDIT_TRIP_REQUEST_CODE);
        });
        tripsRecyclerView = view.findViewById(R.id.trip_recycler_view);
        setLayoutManager();
        setAdapter();

        homeViewModel.getTrips().observe(getViewLifecycleOwner(), trips -> tripAdapter.setTrips(trips));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        tripsRecyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter() {
        tripAdapter = new TripAdapter(getContext());
        tripsRecyclerView.setAdapter(tripAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TRIP_REQUEST_CODE && resultCode == RESULT_OK) {
            Trip trip = (Trip) data.getSerializableExtra("trip");
            homeViewModel.addTrip(trip);
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.toast_text_could_not_save_trip,
                    Toast.LENGTH_LONG).show();
        }
    }

}