package com.jackzheng.jizhang.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.jackzheng.jizhang.sqlHelper.TypeDb;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2017/12/31.
 */

public class TypeData {

    private int typeId;
    private int recordId;
    private int unitPrice;
    private String typeName ;
    public static final int RECORD_TYPE_INCOME = 1;
    public static final int RECORD_TYPE_OUT = 2;
    public static final int RECORD_TYPE_STOCK_OUT = 3;
    public static final int RECORD_TYPE_STOCK_IN = 4;
    public TypeData(int typeId, int recordId, int unitPrice, String typeName) {
        this.typeId = typeId;
        this.recordId = recordId;
        this.unitPrice = unitPrice;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public ContentValues toContentValues(){
        ContentValues value = new ContentValues();
        value.put(TypeDb.KEY_RECORDTYPE,recordId);
        value.put(TypeDb.KEY_TYPEID,typeId);
        value.put(TypeDb.KEY_TYPENAME,typeName);
        value.put(TypeDb.KEY_UNITPRICE,unitPrice);
        return value;
    }
    public static ArrayList<TypeData> cursorToArray(Cursor cursor){
        ArrayList<TypeData> list = new ArrayList<TypeData>();
        cursor.moveToFirst();
        int recordType = -1;
        int typeId = -1;
        int unitPrice = -1;
        String typeName = null;
        while (!cursor.isAfterLast()){
            recordType = cursor.getInt(cursor.getColumnIndex(TypeDb.KEY_RECORDTYPE));
            typeId = cursor.getInt(cursor.getColumnIndex(TypeDb.KEY_TYPEID));
            typeName = cursor.getString(cursor.getColumnIndex(TypeDb.KEY_TYPENAME));
            unitPrice = cursor.getInt(cursor.getColumnIndex(TypeDb.KEY_UNITPRICE));
            TypeData data = new TypeData(typeId,recordType,unitPrice,typeName);
            list.add(data);
        }
        return list;
    }

}
