package com.example.notasstiky.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notasstiky.R;
import com.example.notasstiky.entities.MyNoteEntities;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MyNoteAdapter extends  RecyclerView.Adapter<MyNoteAdapter.ViewHolder>{
   List<MyNoteEntities> noteEntitiesList;

    public MyNoteAdapter(List<MyNoteEntities> noteEntities) {
        this.noteEntitiesList = noteEntities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setNote(noteEntitiesList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteEntitiesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
 private TextView title,textNote,dateTime;
 private LinearLayout linearLayout;
 RoundedImageView roundedImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            textNote = itemView.findViewById(R.id.textNote);
            dateTime = itemView.findViewById(R.id.textDateTime);
            linearLayout = itemView.findViewById(R.id.layoutNote);
            roundedImageView = itemView.findViewById(R.id.imageNote_item);
        }

        public void setNote(MyNoteEntities noteEntities) {
            title.setText(noteEntities.getTitle());
            textNote.setText(noteEntities.getNoteText());
            dateTime.setText(noteEntities.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) linearLayout.getBackground();
            if (noteEntities.getColor() != null){
                gradientDrawable.setColor(Color.parseColor(noteEntities.getColor()));

            }
            else {
                gradientDrawable.setColor(Color.parseColor("#FF937B"));
            }

        }
    }
}
