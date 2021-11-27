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
import com.example.notasstiky.activities.AddNewReminderActivity;
import com.example.notasstiky.adapters.MyNoteAdapter;
import com.example.notasstiky.adapters.MyReminderAdapter;
import com.example.notasstiky.database.MyNoteDatabase;
import com.example.notasstiky.entities.MyReminderEntities;

import java.util.ArrayList;
import java.util.List;

public class ReminderFragment extends Fragment {
ImageView addReminder;

    private RecyclerView noteRec;
    private List<MyReminderEntities> noteEntitiesList;
    private MyReminderAdapter myNoteAdapter;
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
        noteRec = view.findViewById(R.id.reminder_rec);
        noteRec.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteEntitiesList = new ArrayList<>();
        myNoteAdapter = new MyReminderAdapter(noteEntitiesList);
        noteRec.setAdapter(myNoteAdapter);
        
        getAllReminder();

        return view;
    }

    private void getAllReminder() {
        @SuppressLint("StaticFieldLeak")
        class GetAllReminder extends AsyncTask<Void, Void,List<MyReminderEntities>>{

            @Override
            protected List<MyReminderEntities> doInBackground(Void... voids) {
                  return MyNoteDatabase
                        .getMyNoteDatabase(getActivity().getApplicationContext())
                        .notesDao()
                        .getAllReminder();
            }

            @Override
            protected void onPostExecute(List<MyReminderEntities> myReminderEntities) {
                super.onPostExecute(myReminderEntities);
                if (noteEntitiesList.size() == 0){
                    noteEntitiesList.addAll(myReminderEntities);
                    myNoteAdapter.notifyDataSetChanged();
                }else{
                    noteEntitiesList.add(0,myReminderEntities.get(0));
                    myNoteAdapter.notifyItemInserted(0);
                }
                noteRec.smoothScrollToPosition(0);
            }
            }
            new GetAllReminder().execute();
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK){
            getAllReminder();
        }
    }

    }
