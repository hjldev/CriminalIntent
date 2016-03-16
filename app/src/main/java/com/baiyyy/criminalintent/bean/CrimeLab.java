package com.baiyyy.criminalintent.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by huangjinlong on 2016/1/4.
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrime;

    private CrimeLab(Context appContext){
        mAppContext = appContext;
        mCrime = new ArrayList<>();
        for (int i = 0; i < 100; i ++){
            Crime c = new Crime();
            c.setmTitle("Crime #" + i);
            c.setIsSolved(i%2 == 0);
            mCrime.add(c);
        }
    }

    public ArrayList<Crime> getmCrime() {
        return mCrime;
    }
    // 根据唯一标识符UUID得到Crime对象
    public Crime getmCrime(UUID id){
        for (Crime c : mCrime){
            if (c.getmId().equals(id)){
                return c;
            }
        }
        return null;
    }

    // 构造函数，由于是单例（跟应用的声明周期相同，且只允许创建一个实例，）作用与全局，包括Activity、Service等，使用getApplicationContext()，将不确定是否存在的Context替换成application context
    public static CrimeLab get(Context c){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public void add(Crime c){
        mCrime.add(c);
    }

    public void delete(Crime c){
        mCrime.remove(c);
    }
}
