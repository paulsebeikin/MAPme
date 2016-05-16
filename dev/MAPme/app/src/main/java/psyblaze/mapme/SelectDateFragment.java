package psyblaze.mapme;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.widget.DatePicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog.OnDateSetListener externalListener;

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener){
        this.externalListener = listener;
    }

   public void onDateSet(DatePicker view, int year, int month, int day){
       if(externalListener != null)
           externalListener.onDateSet(view, year, month+1, day);
   }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


}
