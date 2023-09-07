package com.mitkooo.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitkooo.weather.fragments.BaseFragment;
import com.mitkooo.weather.fragments.HomeFragment;
import com.mitkooo.weather.services.DataService;
import com.mitkooo.weather.services.LoadingDialogService;


//This is the entry point of the application
public class MainActivity extends AppCompatActivity {

    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create custom view for the actionbar title. This is used for setting the title for each fragment

        //inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout actionBarTitle = (RelativeLayout) inflater.inflate(R.layout.actionbar_title, null, false);
        titleTextView = actionBarTitle.findViewById(R.id.titleTextView);

        //remove the default title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set the custom layout
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarTitle);

        //setup loading dialog
        LoadingDialogService.getInstance().createLoadingDialog(this);

        DataService.getInstance().setContext(this);

        //open the first/main fragment
        HomeFragment f = new HomeFragment();
        addFragment(f);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (currentFragment != null && currentFragment instanceof BaseFragment) {
            ((BaseFragment) currentFragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //check if back button is pressed
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //public method for adding new fragments to the backstack
    public void addFragment(Fragment fragment) {
        //hide keyboard

        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragment, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }


    //method for setting the title for each fragment
    public void setActionBarTitle(int resourceID) {
        titleTextView.setText(resourceID);
    }
}
