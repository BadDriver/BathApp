package com.example.bathreserve.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bathreserve.R;

import java.util.List;

public class TimeReservationListRecylerViewAdapter extends RecyclerView.Adapter<TimeReservationListRecylerViewAdapter.ViewHolder> {
    private List<String> hours;
    private List<String> minutes;
    private LayoutInflater mInflater;
    private TimeReservationListRecylerViewAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public TimeReservationListRecylerViewAdapter(Context context, List<String> hours, List<String> minutes) {
        this.mInflater = LayoutInflater.from(context);
        this.hours = hours;
        this.minutes = minutes;
    }

    // inflates the row layout from xml when needed
    @Override
    public TimeReservationListRecylerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_time_reservation_list, parent, false);
        return new TimeReservationListRecylerViewAdapter.ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(TimeReservationListRecylerViewAdapter.ViewHolder holder, int position) {
        String hour = hours.get(position);
        holder.textViewHour.setText(hour);
        String minute = minutes.get(position);
        holder.textViewMinute.setText(minute);
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return hours.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewHour;
        TextView textViewMinute;
        Button buttonReserve;

        ViewHolder(View itemView) {
            super(itemView);
            textViewHour = itemView.findViewById(R.id.textViewReservationTimeHour);
            textViewMinute = itemView.findViewById(R.id.textViewReservationTimeMinute);
            buttonReserve = itemView.findViewById(R.id.buttonReserveTime);
            itemView.setOnClickListener(this);
            buttonReserve.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getHour(int id) {
        return hours.get(id);
    }
    public String getMinute(int id) {
        return minutes.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(TimeReservationListRecylerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
