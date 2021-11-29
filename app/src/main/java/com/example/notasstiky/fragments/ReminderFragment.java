package com.example.notasstiky.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notasstiky.R;
import com.example.notasstiky.activities.AddNewReminderActivity;
import com.example.notasstiky.adapters.MyReminderAdapter;
import com.example.notasstiky.database.MyNoteDatabase;
import com.example.notasstiky.entities.MyReminderEntities;
import com.example.notasstiky.listeners.MyReminderListeners;

import java.util.ArrayList;
import java.util.List;

public class ReminderFragment extends Fragment implements MyReminderListeners {
    ImageView addReminder;

    public static final int REQUEST_CODE_ADD_REMINDER = 1;
    public static final int UPDATE_REMINDER = 2;
    public static final int SHOW_REMINDER = 3;
    private int clickedPosition = -1;


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
            startActivityForResult(new Intent(getContext(), AddNewReminderActivity.class),REQUEST_CODE_ADD_REMINDER);
        }
    });
        noteRec = view.findViewById(R.id.reminder_rec);
        noteRec.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteEntitiesList = new ArrayList<>();
        myNoteAdapter = new MyReminderAdapter(noteEntitiesList,this);
        noteRec.setAdapter(myNoteAdapter);
        
        getAllReminder(SHOW_REMINDER,false);

        return view;
    }

    private void getAllReminder(final int requestCode,final boolean isReminderDeleted) {

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
                if (requestCode == SHOW_REMINDER){
                    noteEntitiesList.addAll(myReminderEntities);
                    myNoteAdapter.notifyDataSetChanged();
                }else if(requestCode == REQUEST_CODE_ADD_REMINDER){
                    noteEntitiesList.add(0,myReminderEntities.get(0));
                    myNoteAdapter.notifyItemInserted(0);
                    noteRec.smoothScrollToPosition(0);
                }else if (requestCode == UPDATE_REMINDER){
                    noteEntitiesList.remove(clickedPosition);
                    if (isReminderDeleted){
                        myNoteAdapter.notifyItemRemoved(clickedPosition);
                    }else{
                        noteEntitiesList.add(clickedPosition,myReminderEntities.get(clickedPosition));
                        myNoteAdapter.notifyItemChanged(clickedPosition);
                    }
                }
            }
            }
            new GetAllReminder().execute();
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_REMINDER && resultCode == RESULT_OK){
            getAllReminder(REQUEST_CODE_ADD_REMINDER,false);
        }else if(requestCode == UPDATE_REMINDER && resultCode == RESULT_OK){
            if (data != null){
                getAllReminder(UPDATE_REMINDER,data.getBooleanExtra("isReminderDeleted",false));
            }
        }
    }

    ////////////////////recycler click
    public void myReminderClick(MyReminderEntities myReminderEntities, int position) {

        clickedPosition = position;
        Intent intent = new Intent(getContext().getApplicationContext(),AddNewReminderActivity.class);
        intent.putExtra("updateOrView",true);
        intent.putExtra("myNotes",myReminderEntities);
        startActivityForResult(intent,UPDATE_REMINDER);
    }

    }
