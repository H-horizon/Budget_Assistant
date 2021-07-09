package android.h.horizon.budget_assistant.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;


import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "transaction_date";

    private static final String ARG_DATE = "date";
    private static final String TAG = "DialogFragment";
    private DatePicker mDatePicker;

    /**
     * @param date is the initial date the transaction occurred
     * @return fragment with the set arguments
     */
    public static DatePickerFragment newInstance(Date date) {
        Log.d(TAG, "newInstance(Date date) called");
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog(Bundle savedInstanceState) called");
        Date date = (Date) getArguments().getSerializable(ARG_DATE);//From host fragment(s)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        View view = getViewAfterSettingDateDialog(calendar);
        return getDateDialog(view);
    }

    private View getViewAfterSettingDateDialog(Calendar calendar) {
        Log.d(TAG, "getViewAfterSettingDateDialog(Calendar calendar) called");
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);
        return view;
    }

    private AlertDialog getDateDialog(View view) {
        Log.d(TAG, "getDateDialog(View view) called");
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);//Target Fragment must be set in host fragment before result is sent
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        Log.d(TAG, "sendResult(int resultCode, Date date) called");
        if (getTargetFragment() == null) {
            Log.d(TAG, "sendResult() : TargetFragment has not been set");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
