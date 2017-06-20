package com.lg.moneyregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-02-19.
 */
public class DataBaseHandler extends SQLiteOpenHelper implements dbInterface {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DB";

    // Table name
    private static final String TABLE_NAME1 = "moneyDataTable";
    private static final String TABLE_NAME2 = "personDataTable";


    // Table Columns names
    private static final String KEY_ID1 = "id";
    private static final String KEY_MONEY = "money";
    private static final String KEY_IS_INPUT = "isInput";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DATE = "date";
    private static final String KEY_INSTALLMENT_MONTH = "installMonth";

    private static final String KEY_ID2 = "id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_DEPOSIT_AMOUNT= "depositAmount";
    private static final String KEY_DEPOSIT_DATE= "depositDate";
    private static final String KEY_RECENT_DEPOSIT_DATE= "recentDepositDate";
    private static final String KEY_PHONE_NUM = "phoneNum";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHandler getInstance(Context context) {
        return new DataBaseHandler(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //DB를 새로 만든다.
        String CREATE_TABLE1 = "CREATE TABLE " + TABLE_NAME1 + "("
                + KEY_ID1 + " INTEGER PRIMARY KEY,"
                + KEY_MONEY + " INTEGER,"
                + KEY_IS_INPUT + " INTEGER,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_DATE + " LONG,"
                + KEY_INSTALLMENT_MONTH + " TEXT "+ ")";
        db.execSQL(CREATE_TABLE1);
        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + "("
                + KEY_ID2 + " INTEGER PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_BALANCE + " DOUBLE,"
                + KEY_DEPOSIT_AMOUNT + " DOUBLE,"
                + KEY_DEPOSIT_DATE + " TEXT,"
                + KEY_RECENT_DEPOSIT_DATE + " TEXT,"
                + KEY_PHONE_NUM + " TEXT "+ ")";
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DB를 지우고 다시 쓴다.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        this.onCreate(db);
    }

    //CRUDOperations Override Method

    @Override
    public void insertData(MoneyData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MONEY, data.getAmount());
        contentValues.put(KEY_IS_INPUT, data.isInput());
        contentValues.put(KEY_CATEGORY, data.getCategory());
        contentValues.put(KEY_DATE, data.getDate());
        contentValues.put(KEY_INSTALLMENT_MONTH, data.getInstallmentMonth());

        long returnResult = db.insert(TABLE_NAME1, null, contentValues);

        db.close();
    }
    @Override
    public void insertPersonData(PersonData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PASSWORD, data.getPW());
        contentValues.put(KEY_BALANCE, data.getBalance());
        contentValues.put(KEY_DEPOSIT_AMOUNT, data.getDepositAmount());
        contentValues.put(KEY_DEPOSIT_DATE, data.getDepositDate());
        contentValues.put(KEY_RECENT_DEPOSIT_DATE, data.getRecentDepositDate());
        contentValues.put(KEY_PHONE_NUM, data.getPhoneNum());

        long returnResult = db.insert(TABLE_NAME2, null, contentValues);

        db.close();
    }


    @Override
    public void updatePersonData(PersonData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, data.getPW());
        values.put(KEY_BALANCE, data.getBalance());
        values.put(KEY_DEPOSIT_AMOUNT, data.getDepositAmount());
        values.put(KEY_DEPOSIT_DATE, data.getDepositDate());
        values.put(KEY_RECENT_DEPOSIT_DATE, data.getRecentDepositDate());
        values.put(KEY_PHONE_NUM, data.getPhoneNum());
        String[] nums = { String.valueOf(data.getId()) };

        int i = db.update(TABLE_NAME2,
                values,
                KEY_ID2 + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }
    @Override
    public List<MoneyData> getDataList() {

        List<MoneyData> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME1;
        Cursor cursor = db.rawQuery(query, null);

        MoneyData data;
        if (cursor.moveToFirst()) {
            do {
                data = new MoneyData();
                data.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID1)));
                data.setAmount(cursor.getInt(cursor.getColumnIndex(KEY_MONEY)));
                data.setInput(cursor.getInt(cursor.getColumnIndex(KEY_IS_INPUT)));
                data.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                data.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                data.setInstallmentMonth(cursor.getInt(cursor.getColumnIndex(KEY_INSTALLMENT_MONTH)));

                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    @Override
    public List<MoneyData> getSpecificDataList(String date) {
        List<MoneyData> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME1 +" WHERE " +KEY_DATE +" like '%"+date+"%'";
        Cursor cursor = db.rawQuery(query, null);

        MoneyData data;
        if (cursor.moveToFirst()) {
            do {
                data = new MoneyData();
                data.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID1)));
                data.setAmount(cursor.getInt(cursor.getColumnIndex(KEY_MONEY)));
                data.setInput(cursor.getInt(cursor.getColumnIndex(KEY_IS_INPUT)));
                data.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                data.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                data.setInstallmentMonth(cursor.getInt(cursor.getColumnIndex(KEY_INSTALLMENT_MONTH)));

                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    @Override
    public PersonData getPersonData() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] culums = { KEY_ID2, KEY_PASSWORD, KEY_BALANCE, KEY_DEPOSIT_AMOUNT, KEY_DEPOSIT_DATE, KEY_RECENT_DEPOSIT_DATE, KEY_PHONE_NUM};
        Cursor cursor = db.query(TABLE_NAME2, culums, " id = ?", new String[] { String.valueOf(1) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        PersonData data = new PersonData();
        try {
            data.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID2)));
            data.setPW(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            data.setBalance(cursor.getDouble(cursor.getColumnIndex(KEY_BALANCE)));
            data.setDepositAmount(cursor.getDouble(cursor.getColumnIndex(KEY_DEPOSIT_AMOUNT)));
            data.setDepositDate(cursor.getString(cursor.getColumnIndex(KEY_DEPOSIT_DATE)));
            data.setRecentDepositDate(cursor.getString(cursor.getColumnIndex(KEY_RECENT_DEPOSIT_DATE)));
            data.setPhoneNum(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUM)));
        }catch (CursorIndexOutOfBoundsException boundsException){
            return null;
        }
        return data;
    }
}