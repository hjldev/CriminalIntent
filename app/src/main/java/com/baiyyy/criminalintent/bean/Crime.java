package com.baiyyy.criminalintent.bean;

import java.util.Date;
import java.util.UUID;

/**
 * Created by huangjinlong on 2016/1/4.
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean isSolved;

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public Crime() {
        // 生成唯一标识符
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
