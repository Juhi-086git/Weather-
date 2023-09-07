package com.mitkooo.weather.fragments;

import androidx.fragment.app.Fragment;

import com.mitkooo.weather.MainActivity;

//Base fragment, used for defining common methods for all fragments
public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();

        //hide back arrow in home fragment
        boolean showBack = true;
        if (this instanceof HomeFragment) {
            showBack = false;
        }
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(showBack);
    }

    //method for navigation to a new fragment
    protected void addFragment(Fragment fragment) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.addFragment(fragment);
        }
    }

    //set action bar title for each fragment
    protected void setActionBarTitle(int resourceID) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setActionBarTitle(resourceID);
        }
    }

    public void onBackPressed() {
        //used for back button press in children classes
    }
}
