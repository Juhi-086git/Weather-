package com.mitkooo.weather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mitkooo.weather.R;
import com.mitkooo.weather.services.DataService;

public class SettingsFragment extends BaseFragment {

    private TextView versionTextView;
    private RadioGroup radioGroup;
    private RadioButton unitC;
    private RadioButton unitF;
    private Spinner citySpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        //inflate the view and assign/prepare the view elements for work;
        versionTextView = v.findViewById(R.id.version);
        radioGroup = v.findViewById(R.id.radioGroup);
        unitC = v.findViewById(R.id.unitC);
        unitF = v.findViewById(R.id.unitF);
        citySpinner = v.findViewById(R.id.citySpinner);

        return v;
    }

    //setup back key
    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();

        //set fragment title
        setActionBarTitle(R.string.settings_title);

        //show version
        try {
            versionTextView.setText(String.format("v%s", getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName));
        } catch (Exception e) {
            e.printStackTrace();
            versionTextView.setVisibility(View.GONE);
        }

        //fill radioGroup
        if (DataService.getInstance().isFahrenheit()) {
            unitF.setChecked(true);
        } else {
            unitC.setChecked(true);
        }

        //set listener to radioGroup click events
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);

                //set if Celsius or Fahrenheit is selected
                DataService.getInstance().setFahrenheit(checkedRadioButton.getTag().equals("f"));
            }
        });

        //setup dropdown menu for city selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        //set the last selected city
        citySpinner.setSelection(DataService.getInstance().getCity());

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //write the newly selected city
                DataService.getInstance().setCity(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //unused
            }
        });

    }
}
