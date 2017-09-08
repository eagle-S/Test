package com.skyworthauto.navi.fragment.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.LatLonPoint;

public class SearchParams implements Parcelable {
    public int mSearchType;
    public String mSearchName;
    public String mKeyword;
    public String mCity;
    public int mMaxCount;
    public int mRange;
    public int mCurPage;
    public int mTotalPage;
    public LatLonPoint mMyPoint;
    public String mCtgr;

    public String getSearchName() {
        return mSearchName;
    }

    public String getKeyword() {
        return mKeyword;
    }

    public LatLonPoint getMyPoint() {
        return mMyPoint;
    }

    public String getCtgr() {
        return mCtgr;
    }

    public String getCity() {
        return mCity;
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public int getRange() {
        return mRange;
    }

    public int getCurPage() {
        return mCurPage;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }

    public void setMyPoint(LatLonPoint myPoint) {
        mMyPoint = myPoint;
    }

    public void setCtgr(String ctgr) {
        mCtgr = ctgr;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
    }

    public void setRange(int range) {
        mRange = range;
    }

    public void setCurPage(int curPage) {
        mCurPage = curPage;
    }

    public void setTotalPage(int totalPage) {
        mTotalPage = totalPage;
    }

    public void setSearchType(int type) {
        mSearchType = type;
    }

    public int getSearchType() {
        return mSearchType;
    }

    public void setSearchName(String name) {
        mSearchName = name;
    }

    protected SearchParams(Parcel in) {
        mSearchType = in.readInt();
        mSearchName = in.readString();
        mKeyword = in.readString();
        mCity = in.readString();
        mMaxCount = in.readInt();
        mRange = in.readInt();
        mCurPage = in.readInt();
        mTotalPage = in.readInt();
        mMyPoint = in.readParcelable(LatLonPoint.class.getClassLoader());
        mCtgr = in.readString();
    }

    public static final Creator<SearchParams> CREATOR = new Creator<SearchParams>() {
        @Override
        public SearchParams createFromParcel(Parcel in) {
            return new SearchParams(in);
        }

        @Override
        public SearchParams[] newArray(int size) {
            return new SearchParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mSearchType);
        dest.writeString(mSearchName);
        dest.writeString(mKeyword);
        dest.writeString(mCity);
        dest.writeInt(mMaxCount);
        dest.writeInt(mRange);
        dest.writeInt(mCurPage);
        dest.writeInt(mTotalPage);
        dest.writeParcelable(mMyPoint, flags);
        dest.writeString(mCtgr);
    }
}
