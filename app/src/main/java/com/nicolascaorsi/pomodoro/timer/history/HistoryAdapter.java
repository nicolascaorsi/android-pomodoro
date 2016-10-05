package com.nicolascaorsi.pomodoro.timer.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicolascaorsi.pomodoro.R;
import com.nicolascaorsi.pomodoro.data.Pomodoro;
import com.nicolascaorsi.pomodoro.utils.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by nicolas on 10/2/16.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Pomodoro> mItems;
    private Context mContext;
    private static final int HEADER_VIEW= 1;
    private static final int ITEM_VIEW = 2;
    private static final SimpleDateFormat mDateFormatter = new SimpleDateFormat("HH:mm");

    public HistoryAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case HEADER_VIEW:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_header_item, parent, false));
            case ITEM_VIEW:
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pomodoro cPomodoro = mItems.get(position);

        if(getItemViewType(position) == HEADER_VIEW){
            ((HeaderViewHolder)holder).title.setText(DateFormatUtils.getFormattedDate(mContext, mItems.get(position).getDate().getTime()));
        }


        if(DateUtils.isToday(cPomodoro.getDate().getTime())){
            holder.date.setText(DateUtils.getRelativeTimeSpanString(cPomodoro.getDate().getTime()));
        }else {
            holder.date.setText(mDateFormatter.format(cPomodoro.getDate().getTime()));
        }

        holder.duration.setText(DateFormatUtils.getDateInMinutes(cPomodoro.getDuration()));
        holder.status.setText(cPomodoro.isCompleted()? R.string.finished : R.string.stopped);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || getHeaderId(position) != getHeaderId(position - 1))
            return HEADER_VIEW;
        return ITEM_VIEW;
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setItems(List<Pomodoro> items) {
        mItems = items;
    }

    public void addItems(Pomodoro item) {
        mItems.add(0, item);
    }

    private long getHeaderId(int position) {
//        Log.d("header", DateFormatUtils.getFormattedDate(mContext, mItems.get(position).getDate().getTime()) + mItems.get(position).getDate().toString());
        return DateFormatUtils.getFormattedDate(mContext, mItems.get(position).getDate().getTime()).hashCode();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView date;
        public final TextView duration;
        public final TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            duration = (TextView) itemView.findViewById(R.id.duration);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }

    protected class HeaderViewHolder extends ViewHolder {

        public final TextView title;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
