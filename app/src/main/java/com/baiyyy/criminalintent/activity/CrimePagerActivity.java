package com.baiyyy.criminalintent.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.baiyyy.criminalintent.R;
import com.baiyyy.criminalintent.bean.Crime;
import com.baiyyy.criminalintent.bean.CrimeLab;
import com.baiyyy.criminalintent.fragment.CrimeFragment;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by huangjinlong on 2016/1/5.
 */
public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks{

    // 直接以代码的方式创建ViewPager
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPage);
        setContentView(mViewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_menu_add);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mCrimes = CrimeLab.get(this).getmCrime();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getmId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        UUID uuid = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i=0; i<mCrimes.size(); i++){
            if (mCrimes.get(i).getmId().equals(uuid)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void itemUpdate(Crime crime) {

    }
}
