package com.mitkooo.weather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mitkooo.weather.R;
import com.mitkooo.weather.models.WeatherModel;
import com.mitkooo.weather.services.DataService;

import java.util.ArrayList;

public class DailyWeatherAdapter extends ArrayAdapter<WeatherModel> {

    public DailyWeatherAdapter(Context context, ArrayList<WeatherModel> items) {
        super(context, R.layout.row_daily_weather, items);
        this.items = items;
    }

    private ArrayList<WeatherModel> items;

    //describe the elements of the row
    private static class ViewHolder {
        TextView dateTextView;
        TextView minTempTextView;
        TextView maxTempTextView;
        TextView conditionTextView;
    }

    //generate the view for each row - use/reuse the cells for all visible items
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        //if the row's view is not created, create it
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.row_daily_weather, parent, false);

            //bind row elements
            holder = new ViewHolder();
            holder.dateTextView = row.findViewById(R.id.dateTextView);
            holder.minTempTextView = row.findViewById(R.id.minTempTextView);
            holder.maxTempTextView = row.findViewById(R.id.maxTempTextView);
            holder.conditionTextView = row.findViewById(R.id.conditionTextView);

            row.setTag(holder);
        }

        //row's view exists - reuse it for new object from the items ArrayList

        holder = (ViewHolder) row.getTag();

        //populate row with data
        WeatherModel weatherModel = items.get(position);

        holder.dateTextView.setText(weatherModel.getDateToString());
        holder.minTempTextView.setText(weatherModel.getMinTempString());
        holder.maxTempTextView.setText(weatherModel.getMaxTempString());

        //set weather status
        if (DataService.getInstance().isFahrenheit()) {
            if (weatherModel.getMinTemp() <= 32) {
                holder.conditionTextView.setText(getContext().getText(R.string.home_status_cold));
            } else if (weatherModel.getMaxTemp() > 77) {
                holder.conditionTextView.setText(getContext().getText(R.string.home_status_hot));
            } else {
                holder.conditionTextView.setText(getContext().getText(R.string.home_status_normal));
            }
        } else {
            if (weatherModel.getMinTemp() <= 0) {
                holder.conditionTextView.setText(getContext().getText(R.string.home_status_cold));
            } else if (weatherModel.getMaxTemp() > 25) {
                holder.conditionTextView.setText(getContext().getText(R.string.home_status_hot));
            } else {
                holder.conditionTextView.setText(getContext().getText(R.string.home_status_normal));
            }
        }

        return row;

    }
}
