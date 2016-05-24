package Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import psyblaze.mapme.R;

/**
 * Created by g13s0714 on 2016/05/24.
 */

public class HelpArrayAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Help> values;

    public HelpArrayAdapter (Context context, ArrayList<Help> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.help_rowlayout, parent, false);
        TextView nameLabel = (TextView) rowView.findViewById(R.id.nameLabel);
        nameLabel.setText(values.get(position).helpText);

        return rowView;
    }

    public Help getHelp(int position) {
        if (position < values.size()) return values.get(position);
        return null;
    }

    public void updateContact (Help toUpdate) {
        int pos = Integer.parseInt(toUpdate.helpText);
        if (pos < values.size()) values.set(pos, toUpdate);
        notifyDataSetChanged();
    }
}
