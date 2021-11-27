package com.example.notasstiky.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.notasstiky.R;
import com.example.notasstiky.activities.AddNewNotes;
import com.example.notasstiky.adapters.MyNoteAdapter;
import com.example.notasstiky.database.MyNoteDatabase;
import com.example.notasstiky.entities.MyNoteEntities;

import java.util.ArrayList;
import java.util.List;

public class MyNotesFragment extends Fragment {

    ImageView addNote;
    public static final int REQUEST_CODE_ADD_NOTE = 1;

    private RecyclerView noteRec;
    private List<MyNoteEntities> noteEntitiesList;
    private MyNoteAdapter myNoteAdapter;

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

        noteRec = view.findViewById(R.id.reminder_rec);
        noteRec.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteEntitiesList = new ArrayList<>();
        myNoteAdapter = new MyNoteAdapter(noteEntitiesList);
        noteRec.setAdapter(myNoteAdapter);
        getAllNotes();
        return view;
    }

    private void getAllNotes() {

        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void,Void,List<MyNoteEntities>> {
            @Override
            protected List<MyNoteEntities> doInBackground(Void... voids){
                return MyNoteDatabase
                        .getMyNoteDatabase(getActivity().getApplicationContext())
                        .notesDao()
                        .getAllNotes();
            }
            @Override
            protected void onPostExecute(List<MyNoteEntities>myNoteEntities){
                super.onPostExecute(myNoteEntities);
                if (noteEntitiesList.size() == 0){
                    noteEntitiesList.addAll(myNoteEntities);
                    myNoteAdapter.notifyDataSetChanged();
                }else{
                    noteEntitiesList.add(0,myNoteEntities.get(0));
                    myNoteAdapter.notifyItemInserted(0);
                }
                noteRec.smoothScrollToPosition(0);
            }

        }
        new GetNoteTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getAllNotes();
        }
    }
}