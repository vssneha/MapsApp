package maps.example.com.mapsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sneha on 26-Jan-16.
 */
public class SplashScreen extends Activity
{
    public static boolean showAll = false;
    public static String offenseType;
    public Context ctx = null;
    public static ArrayList<Location> arrList = new ArrayList<Location>();
    public TextView title = null;
    public EditText limitText = null;
    public EditText offsetText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        title = (TextView)findViewById(R.id.title);
        limitText = (EditText)findViewById(R.id.limit);
        offsetText = (EditText)findViewById(R.id.offset);


        ctx = SplashScreen.this;

    }



    public void showBurglaryLocations(View view)
    {
        offenseType = AppConstansts.burglary;
        startActivity();
    }

    public void showTheftLocations(View view)
    {
        offenseType = AppConstansts.larcenyTheft;
        startActivity();
    }

    public void showNonCriminalLocations(View view)
    {
        offenseType = AppConstansts.nonCriminal;
        startActivity();
    }

    public void showOtherOffensesLocations(View view)
    {
        offenseType = AppConstansts.otherOffenses;
        startActivity();
    }

    public void showVandalismLocations(View view)
    {
        offenseType = AppConstansts.vandalism;
        startActivity();
    }

    public void showAssaultLocations(View view)
    {
        offenseType = AppConstansts.assault;
        startActivity();
    }

    public void showVehicleTheftLocations(View view)
    {
        offenseType = AppConstansts.vehicleTheft;
        startActivity();
    }

    public void showMissingPersonLocations(View view)
    {
        offenseType = AppConstansts.missingPerson;
        startActivity();
    }

    public void startActivity()
    {
        String limitStr = limitText.getText().toString();
        String offsetStr = offsetText.getText().toString();
        if(!(limitStr.trim().equals("") && offsetStr.trim().equals("")))
        {
            int limit = Integer.parseInt(limitStr);
            int offset = Integer.parseInt(offsetStr);
            new GetLocationsAsync((SplashScreen) ctx, limit, offset).execute();

        }

    }

    public void showAllLocations(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        showAll = checked;
    }


    public void changeTitle(View view)
    {

        LayoutInflater li = LayoutInflater.from(ctx);
        View promptsView = li.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if(!userInput.getText().toString().trim().equals(""))
                                {
                                    title.setText(userInput.getText());
                                }
                                else
                                {
                                     Toast.makeText(ctx, R.string.warning, Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

    }
}

