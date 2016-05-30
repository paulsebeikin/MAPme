package Classes;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import psyblaze.mapme.HistoryActivity;
import psyblaze.mapme.HistoryDetailActivity;
import psyblaze.mapme.R;


public class ListViewAdapter extends BaseSwipeAdapter{

    private Context mContext;
    public List<Record> values;
    public static int OPENED;

    public ListViewAdapter(Context mContext, List<Record> values) {
        this.mContext = mContext;
        this.values = values;
    }

    public Record getRecord(int position) {
        try {
            return values.get(position);
        }
        catch (IndexOutOfBoundsException ex) { return null; }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_row;
    }

    @Override
    public View generateView(final int position, final ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.history_rowlayout, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        final View swipe_row = v.findViewById(R.id.swipe_row);
        ImageView delRow = (ImageView)v.findViewById(R.id.delRow);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                OPENED = position;
                super.onOpen(layout);
                swipe_row.setClickable(false);
            }
            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
                swipe_row.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_row.setClickable(true);
                    }
                } , 100);
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                swipe_row.setClickable(false);
                super.onStartOpen(layout);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                swipe_row.setClickable(false);
                super.onStartClose(layout);
            }
        });
        swipe_row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(mContext, HistoryDetailActivity.class);
                detailsIntent.putExtra("id", getRecord(position).getId());
                mContext.startActivity(detailsIntent);
            }
        });
        delRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record delete = values.get(position);
                values.get(position).setDeleted(true);
                values.remove(position);
                HistoryActivity.deleteRecord(delete, mContext);
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView recordName = (TextView) convertView.findViewById(R.id.record_name);
        TextView recordDate = (TextView) convertView.findViewById(R.id.record_dt);
        TextView recordDesc = (TextView) convertView.findViewById(R.id.record_desc);
        TextView recordCountry = (TextView) convertView.findViewById(R.id.record_country);
        ImageView sync = (ImageView) convertView.findViewById(R.id.synced);
        ImageView numImg = (ImageView) convertView.findViewById(R.id.img_num);

        Record curr = getItem(position);

        recordName.setText(curr.getProject());
        int day = curr.getDay();
        int month = curr.getMonth();
        int year = curr.getYear();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = sdf.parse(day + "/" + month + "/" + year);
        }
        catch (ParseException ex){
            date = null;
        }
        recordDate.setText(date.toString());
        recordDesc.setText(curr.getDesc());
        recordCountry.setText(curr.getCountry());
        String[] imgList = curr.getUrl().split(";");
        int count = 0;
        for (String str : imgList) if(!str.equals("null")) count++;
        switch (count){
            case 1:
                numImg.setImageResource(R.drawable.ic_num_img1);
                break;
            case 2:
                numImg.setImageResource(R.drawable.ic_num_img2);
                break;
            case 3:
                numImg.setImageResource(R.drawable.ic_num_img3);
                break;
        }
        int synced = curr.isUploaded() ? 1 : 0;
        switch(synced){
            case 0:
                sync.setImageResource((R.drawable.ic_sync_local));
                break;
            case 1:
                sync.setImageResource((R.drawable.ic_sync_remote));
                break;
        }
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Record getItem(int position) {
        if (position > getCount()) return null;
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}