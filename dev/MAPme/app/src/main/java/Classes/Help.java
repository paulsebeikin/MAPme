package Classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by g13s0714 on 2016/05/24.
 */
public class Help implements Parcelable {
    public String helpText;

    public Help (String help) {
      this.helpText = help;
    }

    public int describeContents() {return 0;}

    @Override
    public void writeToParcel (Parcel dest, int flags) {
      dest.writeString(helpText);
    }

    public Help (Parcel in) {
        helpText = in.readString();
    }

    public static final Parcelable.Creator<Help> CREATOR = new Parcelable.Creator<Help>() {
        public Help createFromParcel(Parcel in) {
            return new Help(in);
        }
        public Help [] newArray (int size) {
            return new Help[size];
        }
    };

}
