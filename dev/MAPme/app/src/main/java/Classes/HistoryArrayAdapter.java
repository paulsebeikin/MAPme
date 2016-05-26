package Classes;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.history_rowlayout, parent, false);

        TextView recordName = (TextView) rowView.findViewById(R.id.record_name);
        TextView recordDate = (TextView) rowView.findViewById(R.id.record_dt);
        TextView recordDesc = (TextView) rowView.findViewById(R.id.record_desc);
        TextView recordCountry = (TextView) rowView.findViewById(R.id.record_country);
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
        switch (imgList.length){
            case 1:
                numImg.setImageResource(R.drawable.ic_numimg1);
                break;
            case 2:
                numImg.setImageResource(R.drawable.ic_numimg2);
                break;
            case 3:
                numImg.setImageResource(R.drawable.ic_numimg3);
                break;
        }
        return rowView;
    }
}


