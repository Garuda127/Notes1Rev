package com.example.notasstiky.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notasstiky.R;
import com.example.notasstiky.entities.MyReminderEntities;
import com.example.notasstiky.listeners.MyReminderListeners;

import java.util.List;

public class MyReminderAdapter extends RecyclerView.Adapter<MyReminderAdapter.ViewHolder> {

   List<MyReminderEntities> reminderEntities;
    MyReminderListeners myReminderListeners;

    public MyReminderAdapter(List<MyReminderEntities> reminderEntities, MyReminderListeners myReminderListeners) {
        this.reminderEntities = reminderEntities;
        this.myReminderListeners = myReminderListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.setReminder(reminderEntities.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        myReminderListeners.myReminderClick(reminderEntities.get(position),position);
     }
});
    }

    @Override
    public int getItemCount() {
        return reminderEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,dateTime,complete;
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complete = itemView.findViewById(R.id.reminder_complete);
            title = itemView.findViewById(R.id.reminder_Heading);
            dateTime = itemView.findViewById(R.id.date_reminder);
            view = itemView.findViewById(R.id.view_rem);
        }

        public void setReminder(MyReminderEntities myReminderEntities) {
            //obtiene los datos y los inserta a los text
            title.setText(myReminderEntities.getTitle());
            dateTime.setText(myReminderEntities.getDateTimeComplete());
            if(myReminderEntities.isTerminado()== true){
                complete.setText(R.string.Completado);
            }else {
                complete.setText(R.string.Pendiente);
            }

            //obtiene color de las notas
            GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
            if (myReminderEntities.getColor() != null){
                gradientDrawable.setColor(Color.parseColor(myReminderEntities.getColor()));

            }
            else {
                gradientDrawable.setColor(Color.parseColor("#FFFB7B"));
            }
        }
    }
}
