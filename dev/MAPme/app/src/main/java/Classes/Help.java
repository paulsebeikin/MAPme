package Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Help implements Parcelable {
    public String helpText;     // the actual Help content
    public String menuString;   // the string value that describes what you might need help with

    public Help (String help, String menuString) {
      this.helpText = help;
      this.menuString = menuString;
    }

    public int describeContents() {return 0;}

    @Override
    public void writeToParcel (Parcel dest, int flags) {
      dest.writeString(helpText);
      dest.writeString(menuString);
    }

    public Help (Parcel in) {
        helpText = in.readString();
        menuString = in.readString();
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
