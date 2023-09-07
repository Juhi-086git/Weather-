package com.mitkooo.weather.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mitkooo.weather.R;
import com.mitkooo.weather.adapters.DailyWeatherAdapter;
import com.mitkooo.weather.models.WeatherModel;
import com.mitkooo.weather.services.DataService;
import com.mitkooo.weather.services.LoadingDialogService;
import com.mitkooo.weather.services.Parser;
import com.mitkooo.weather.services.ServerCommunication;
import com.mitkooo.weather.listeners.RequestsListener;
import com.mitkooo.weather.utils.DateUtils;

import java.util.ArrayList;

//The home/main fragment of the application

public class HomeFragment extends BaseFragment implements RequestsListener {

    //define private properties, used in the fragment
    private TextView cityTextView;
    private TextView currentTemperatureTextView;
    private TextView minTempTodayTextView;
    private TextView maxTempTodayTextView;
    private ListView listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //display the settings icon
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflate the view and assign/prepare the view elements for work;
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        cityTextView = v.findViewById(R.id.cityTextView);
        currentTemperatureTextView = v.findViewById(R.id.currentTemperatureTextView);
        minTempTodayTextView = v.findViewById(R.id.minTempTodayTextView);
        maxTempTodayTextView = v.findViewById(R.id.maxTempTodayTextView);
        listView = v.findViewById(R.id.listView);

        return v;
    }

    //Add Settings button in the action bar
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(0, 0, 0, "").setIcon(R.drawable.settings);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    //Actionbar button listener
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Settings button pressed
        if (item.getItemId() == 0) {
            SettingsFragment f = new SettingsFragment();
            addFragment(f);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        //set title
        setActionBarTitle(R.string.home_title);

        //Set city
        cityTextView.setText(getActivity().getResources().getStringArray(R.array.cities_array)[DataService.getInstance().getCity()]);

        //bind the interface
        ServerCommunication.getInstance(getActivity()).setListener(HomeFragment.this);

        //execute the request
        LoadingDialogService.getInstance().show();
        ServerCommunication.getInstance(getActivity()).requestForecast();
    }

    //implementation of the RequestsListener interface. Called when the request is finished
    @Override
    public void weeklyRequestReceived(String jsonString) {
        LoadingDialogService.getInstance().dismiss();

        //Parse result
        ArrayList<WeatherModel> result = Parser.weeklyRequest(jsonString);

        if (result == null) {
            //if there are errors with the result, show alert dialog
            showAlertDialog();
        }

        //display the current weather
        currentTemperatureTextView.setText(result.get(0).getCurrentTempString());
        minTempTodayTextView.setText(result.get(0).getMinTempString());
        maxTempTodayTextView.setText(result.get(0).getMaxTempString());

        //load the weather forecast data in the listview adapter
        listView.setAdapter(new DailyWeatherAdapter(getActivity(), result));

        //for debug purposes
        for (WeatherModel weather : result) {
            Log.e("DEBUG", DateUtils.dateToString(weather.getDate()) + " temps are - " + "L:" + weather.getMinTemp() + " H:" + weather.getMaxTemp() + " isDay: " + weather.isDay());
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.home_information));
        builder.setMessage(getString(R.string.home_error));
        builder.setCancelable(true);

        builder.setNegativeButton(
                getString(R.string.home_close),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
