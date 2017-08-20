package com.belatrix.pickmeup.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by gzavaleta on 02/09/16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    DateSelected dateSelected;
    static final int ONE_MONTH_LIMIT_TIME_ON_FUTURE = 30;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            dateSelected = (DateSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);

        // Limit time to 1 month on future
        c.add(Calendar.DATE, ONE_MONTH_LIMIT_TIME_ON_FUTURE);
        datePicker.getDatePicker().setMaxDate(c.getTimeInMillis());

        return datePicker;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        dateSelected.getDate(year, month, day);
    }

    public interface DateSelected {

        void getDate(int year, int month, int day);
    }
}