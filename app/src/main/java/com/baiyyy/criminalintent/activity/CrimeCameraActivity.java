package com.baiyyy.criminalintent.activity;

import android.support.v4.app.Fragment;

import com.baiyyy.criminalintent.R;
import com.baiyyy.criminalintent.SingleFragmentActivity;
import com.baiyyy.criminalintent.fragment.CrimeCameraFragment;

/**
 * Created by huangjinlong on 2016/1/7.
 */
public class CrimeCameraActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}
