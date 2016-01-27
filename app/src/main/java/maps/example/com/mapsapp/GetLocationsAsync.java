package maps.example.com.mapsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
* Created by Sneha on 27-Jan-16.
*/
class GetLocationsAsync extends AsyncTask
{

   Context ctxt = null;
   ProgressDialog progress = null;

   int limit = 0;
   int offset = 0;

   public GetLocationsAsync(SplashScreen ct, int lmt, int offst)
   {
       ctxt = ct;
       limit = lmt;
       offset = offst;
       progress = new ProgressDialog(ct);
       progress.setTitle(R.string.loading);
   }


   @Override
   protected void onPreExecute()
   {
       super.onPreExecute();
       progress.show();
   }

   @Override
   protected Object doInBackground(Object[] params) {

       InputStream is = null;
       int length = 500;
       try
       {
           URL url = new URL("https://data.sfgov.org/resource/ritf-b9ki.json?$limit="+limit+"&$offset="+offset+"");
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setReadTimeout(10000 /* milliseconds */);
           conn.setConnectTimeout(15000 /* milliseconds */);
           conn.setRequestMethod("GET");
           conn.setDoInput(true);
           conn.connect();
           int response = conn.getResponseCode();
           is = conn.getInputStream();

           String contentAsString = convertInputStreamToString(is, length);
           return contentAsString;
       }
       catch(Exception ex)
       {
           Log.e("EXCEPTION", ex.getLocalizedMessage());
       }

       return null;
   }

   @Override
   protected void onPostExecute(Object o) {
       super.onPostExecute(o);
       try
       {
           progress.cancel();
           showMap(o.toString(), ctxt);
       }
       catch (Exception ex)
       {
           Log.e("EXCEPTION", ex.getLocalizedMessage());
       }

   }

    private void showMap(String obj, Context ct)
    {
        try
       {
           Gson gson = new Gson();

           Category[] events = gson.fromJson(obj, Category[].class);
           SplashScreen sc = new SplashScreen();
           for(Category cat : events)
           {
               String offenceType = cat.category;
               String latitude = cat.y;
               String longitude = cat.x;
               String district = cat.pddistrict;


               sc.arrList.add(new Location(offenceType, latitude, longitude, district));
           }

           Intent intent = new Intent(ct, MapsActivity.class);
           intent.putExtra("limit", limit);
           intent.putExtra("offset", offset);
           intent.putParcelableArrayListExtra("Data", sc.arrList);
           ct.startActivity(intent);

       }
       catch (Exception ex)
       {
           Log.e("EXCEPTION", ex.getLocalizedMessage());
       }
    }

   public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {

       java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
       return s.hasNext() ? s.next() : "";
   }
}
