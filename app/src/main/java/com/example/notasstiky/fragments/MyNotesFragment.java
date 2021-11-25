package com.example.notasstiky.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.notasstiky.R;
import com.example.notasstiky.activities.AddNewNotes;

public class MyNotesFragment extends Fragment {
    ImageView addNote;
    public static final int REQUEST_CODE_ADD_NOTE = 1;

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);
    addNote = view.findViewById(R.id.add_notes);
    addNote.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(getContext(), AddNewNotes.class),REQUEST_CODE_ADD_NOTE);
        }
    });
        return view;
    }
}