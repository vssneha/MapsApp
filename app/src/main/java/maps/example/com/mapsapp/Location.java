package maps.example.com.mapsapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sneha on 26-Jan-16.
 */
public class Location implements Parcelable
{
    String latitude;
    String longitude;
    String offenseType;
    String district;

    public Location(String offType, String lat, String longtude, String dstrict)
    {
        offenseType= offType;
        latitude = lat;
        longitude = longtude;
        district = dstrict;
    }


    private Location(Parcel in) {

        offenseType = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        district = in.readString();

    }

    public void writeToParcel(Parcel out, int flags) {

        out.writeString(offenseType);
        out.writeString(latitude);
        out.writeString(longitude);
        out.writeString(district);

    }


    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}
