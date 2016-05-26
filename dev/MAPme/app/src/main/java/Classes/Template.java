package Classes;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Created by Paul on 5/22/2016.
 */
public class Template {

    public String project, country, province, town, desc, environment, species, numObserved, natCul, growth, source;
    public Date dt;
    public Double[] location;
    public Double altitude;
    public Boolean flower, fruit;
    public String[] images;

    public Template(String project, String country, String province, String town, String desc, Date dt, Double[] location,
                    Double altitude, String[] images, Objects... params) {

    }

    public void Reset(){
        location = new Double[]{0.0,0.0};
        images = new String[3];
    }

    public Template() {
        location = new Double[]{0.0,0.0};
        altitude = 0.0;
        flower = false;
        fruit = false;
        dt = new Date();
        images = new String[3];
    }
}

