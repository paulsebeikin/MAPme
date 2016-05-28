package Classes;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import psyblaze.mapme.R;

public class HistoryArrayAdapter extends ArrayAdapter {

    Context context;
    List<Record> values;

    public HistoryArrayAdapter (Context context, List<Record> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public Record getRecord(int position) {
        if (position < values.size()) return values.get(position);
        return null;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.history_rowlayout, parent, false);



        SwipeLayout swipeLayout =  (SwipeLayout) rowView.findViewById(R.id.swipe_row);

        //set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, rowView.findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        TextView recordName = (TextView) rowView.findViewById(R.id.record_name);
        TextView recordDate = (TextView) rowView.findViewById(R.id.record_dt);
        TextView recordDesc = (TextView) rowView.findViewById(R.id.record_desc);
        TextView recordCountry = (TextView) rowView.findViewById(R.id.record_country);
        ImageView sync = (ImageView) rowView.findViewById(R.id.synced);
        ImageView numImg = (ImageView) rowView.findViewById(R.id.img_num);

        Record curr = values.get(position);

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
        return rowView;
    }
}