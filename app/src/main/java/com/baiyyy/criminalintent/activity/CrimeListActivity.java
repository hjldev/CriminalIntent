package com.baiyyy.criminalintent.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.baiyyy.criminalintent.R;
import com.baiyyy.criminalintent.SingleFragmentActivity;
import com.baiyyy.criminalintent.bean.Crime;
import com.baiyyy.criminalintent.fragment.CrimeFragment;
import com.baiyyy.criminalintent.fragment.CrimeListFragment;

/**
 * Created by huangjinlong on 2016/1/4.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    // Fragment与Activity的完全解耦，将事件处理全部交由Activity
    @Override
    public void onItemSelect(Crime crime) {
        // 如果没有布局，将直接进入
        if (findViewById(R.id.detailFragmentContainer) == null) {
            Intent intent = new Intent(this, CrimePagerActivity.class);
            intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
            startActivity(intent);
        } else{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            // 用来判断此前有没加入过Fragment，通过id
            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = CrimeFragment.newInstance(crime.getmId());
            if (oldDetail != null){
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    @Override
    public void itemUpdate(Crime crime) {
        FragmentManager fm = getSupportFragmentManager();
        CrimeListFragment fragment = (CrimeListFragment) fm.findFragmentById(R.id.fragmentContainer);
        fragment.updateUI();
    }
}
