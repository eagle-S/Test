package com.skyworthauto.navi.base2;


public class TypedData implements IType {

    private int mType;
    private Object mData;

    public TypedData(int type, Object data) {
        mType = type;
        mData = data;
    }

    @Override
    public int getType(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public Object getData() {
        return mData;
    }
}
