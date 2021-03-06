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
import com.daimajia.swipe.adapters.ArraySwipeAdapter;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import psyblaze.mapme.R;

public class HistoryArrayAdapter extends BaseSwipeAdapter {

    Context context;
    List<Record> values;

    public HistoryArrayAdapter (Context context, List<Record> values) {
        this.context = context;
        this.values = values;
    }

    public Record getRecord(int position) {
        if (position < values.size()) return values.get(position);
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_row;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_rowlayout, null);

        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = inflater.inflate(R.layout.history_rowlayout, parent, false);

        SwipeLayout swipeLayout =  (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

        //set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, v.findViewById(R.id.bottom_wrapper));

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

        v.findViewById(R.id.delRow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fillValues(position, v);
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
    }
}