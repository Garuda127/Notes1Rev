package com.example.notasstiky.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.notasstiky.R;
import com.example.notasstiky.activities.AddNewReminderActivity;

public class ReminderFragment extends Fragment {
ImageView addReminder;
    public ReminderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reminder, container, false);
    addReminder=view.findViewById(R.id.add_reminder);
    addReminder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(getContext(), AddNewReminderActivity.class),1);
        }
    });
        return view;
    }
}