package com.baiyyy.criminalintent.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baiyyy.criminalintent.R;
import com.baiyyy.criminalintent.activity.CrimeCameraActivity;
import com.baiyyy.criminalintent.bean.Crime;
import com.baiyyy.criminalintent.bean.CrimeLab;

import java.util.Date;
import java.util.UUID;

/**
 * Created by huangjinlong on 2016/1/4.
 */
public class CrimeFragment extends Fragment {

    public static final String EXTRA_CRIME_ID = "crime_id";
    public static final String DIALOG_DATE = "date";
    public static final int DATE_RESULT = 0;

    private Crime crime;
    private EditText crime_title;
    private Button date_btn;
    private CheckBox isSolved_cb;
    private ImageButton head_iv;

    private Callbacks callbacks;
    public interface Callbacks{
        void itemUpdate(Crime crime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    public static CrimeFragment newInstance(UUID mId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CRIME_ID, mId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        getActivity().setTitle(R.string.title);
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getmCrime(crimeId);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        crime_title = (EditText) view.findViewById(R.id.crime_title);
        crime_title.setText(crime.getmTitle());
        crime_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setmTitle(s.toString());
                callbacks.itemUpdate(crime);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        date_btn = (Button) view.findViewById(R.id.date_btn);
        updateButton();
//        date_btn.setText(DateFormat.getLongDateFormat(getActivity()).format(crime.getmDate()).toString());
//        date_btn.setEnabled(false);
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(crime.getmDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this, DATE_RESULT);
                datePickerFragment.show(fm, DIALOG_DATE);
            }
        });
        isSolved_cb = (CheckBox) view.findViewById(R.id.isSolved_cb);
        isSolved_cb.setChecked(crime.isSolved());
        isSolved_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setIsSolved(isChecked);
                callbacks.itemUpdate(crime);
            }
        });
        head_iv = (ImageButton) view.findViewById(R.id.head_iv);

        head_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivity(intent);
            }
        });
        // 检测设备是否有摄像头
        PackageManager pm = getActivity().getPackageManager();
        boolean hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
                pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||
                Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD ||
                Camera.getNumberOfCameras() > 0;
        if (!hasCamera) {
            head_iv.setEnabled(false);
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == DATE_RESULT) {
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.DATA_DATE);
                crime.setmDate(date);
                updateButton();
                callbacks.itemUpdate(crime);
            }
        }
    }

    private void updateButton() {
        date_btn.setText(DateFormat.format("yyyy,MM dd,EE,HH:mm", crime.getmDate()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
