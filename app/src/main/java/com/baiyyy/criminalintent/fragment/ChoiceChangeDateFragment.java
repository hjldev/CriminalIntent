package com.baiyyy.criminalintent.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyyy.criminalintent.R;

import java.util.Date;

/**
 * Created by huangjinlong on 2016/1/5.
 */
public class ChoiceChangeDateFragment extends DialogFragment{
    private static final String CHOICE_STYLE = "choice_style";
    private static final String CHOICE_DATE = "date";

    private Date mDate;



    public static ChoiceChangeDateFragment newInstance(Date date){
        Bundle args =  new Bundle();
        args.putSerializable(CHOICE_DATE, date);
        ChoiceChangeDateFragment choiceChangeDateFragment = new ChoiceChangeDateFragment();
        choiceChangeDateFragment.setArguments(args);
        return choiceChangeDateFragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(CHOICE_DATE);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(R.string.choice_date);
        tv1.setTextSize(20);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(R.string.choice_time);
        tv2.setTextSize(20);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mDate);
                datePickerFragment.setTargetFragment(ChoiceChangeDateFragment.this, CrimeFragment.DATE_RESULT);
                datePickerFragment.show(fm, CHOICE_STYLE);
            }
        });
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(tv1);
        linearLayout.addView(tv2);
        return new AlertDialog.Builder(getActivity())
                .setView(linearLayout)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CrimeFragment.DATE_RESULT){
                mDate = (Date) data.getSerializableExtra(DatePickerFragment.DATA_DATE);
                if (getTargetFragment() == null){
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(DatePickerFragment.DATA_DATE, mDate);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        }
    }

}
