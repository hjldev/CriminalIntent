package com.baiyyy.criminalintent.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.baiyyy.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by huangjinlong on 2016/1/5.
 */
public class DatePickerFragment extends DialogFragment{

    public static final String DATA_DATE = "data_date";
    private Date mDate;

    private int years, months, days, hours, minutes;

    private CrimeFragment.Callbacks callbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (CrimeFragment.Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(DATA_DATE, date);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(DATA_DATE);
        // 通过Calendar类设置时间，然后分别得到int的年月日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        years = calendar.get(Calendar.YEAR);
        months = calendar.get(Calendar.MONTH);
        days = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        datePicker.init(years, months, days, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                years = year;
                months = monthOfYear;
                days = dayOfMonth;
                mDate = new GregorianCalendar(years, months, days, hours, minutes).getTime();
                // 更新里面的值，当设备旋转时可以保证值不变
                getArguments().putSerializable(DATA_DATE, mDate);
            }
        });
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        timePicker.setCurrentMinute(minutes);
        timePicker.setCurrentHour(hours);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hours = hourOfDay;
                minutes = minute;
                mDate = new GregorianCalendar(years, months, days, hours, minutes).getTime();
                // 更新里面的值，当设备旋转时可以保证值不变
                getArguments().putSerializable(DATA_DATE, mDate);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void sendResult(int resultOk) {
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(DATA_DATE, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
    }

}
