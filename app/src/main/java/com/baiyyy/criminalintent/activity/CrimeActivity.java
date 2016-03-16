package com.baiyyy.criminalintent.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.baiyyy.criminalintent.R;
import com.baiyyy.criminalintent.SingleFragmentActivity;
import com.baiyyy.criminalintent.bean.Crime;
import com.baiyyy.criminalintent.fragment.CrimeFragment;
import com.baiyyy.criminalintent.fragment.CrimeListFragment;

import java.util.UUID;

/**
 * Created by huangjinlong on 2016/1/4.
 */
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID mId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(mId);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

}
