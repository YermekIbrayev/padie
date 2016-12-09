package com.iskhak.serviceprovider.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.SelectedItems;
import com.iskhak.serviceprovider.data.model.SelectedItemsAdd;
import com.iskhak.serviceprovider.data.model.SelectedItemsAddExtra;
import com.iskhak.serviceprovider.data.model.ServiceGroup;
import com.iskhak.serviceprovider.data.model.dbServiceItem;

import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;


public class Db {

    public Db() { }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public abstract static class ServiceGroups {

        public static final String TABLE_NAME = "ServiceGroups";
/*        public abstract Integer id();
        public abstract String name();
        public abstract String mainQuestion();
        public abstract String extraQuestion();
        public abstract List<dbServiceItem> mainSelections();
        public abstract List<dbServiceItem> extraSelections();*/

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MAIN_QUESTION = "mainQuestion";
        public static final String COLUMN_EXTRA_QUESTION = "extraQuestion";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID+ " INTEGER, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_MAIN_QUESTION + " TEXT, " +
                        COLUMN_EXTRA_QUESTION + " TEXT " +
                " ); ";

        public static ContentValues toContentValues(ServiceGroup serviceGroup) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, serviceGroup.id());
            if (serviceGroup.name()!= null) values.put(COLUMN_NAME, serviceGroup.name());
            if (serviceGroup.mainQuestion()!= null) values.put(COLUMN_MAIN_QUESTION, serviceGroup.mainQuestion());
            if (serviceGroup.extraQuestion()!= null) values.put(COLUMN_EXTRA_QUESTION, serviceGroup.extraQuestion());
            return values;
        }

        public static ServiceGroup parseCursor(Cursor cursor) {
            return ServiceGroup.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)))
                    .setMainQuestion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAIN_QUESTION)))
                    .setExtraQuestion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXTRA_QUESTION)))
                    .build();
        }
    }

    public abstract static class ServiceMainItems {

        public static final String TABLE_NAME = "ServiceMainItems";
/*      public abstract Integer id();
        @Nullable
        public abstract Integer pid();
        public abstract String name();*/

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PID = "pid";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID+ " INTEGER, " +
                        COLUMN_PID+ " INTEGER, " +
                        COLUMN_NAME + " TEXT " +
                        " ); ";

        public static ContentValues toContentValues(dbServiceItem serviceMainItem) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, serviceMainItem.id());
            values.put(COLUMN_PID, serviceMainItem.pid());
            if (serviceMainItem.name()!= null) values.put(COLUMN_NAME, serviceMainItem.name());
            return values;
        }

        public static dbServiceItem parseCursor(Cursor cursor) {
            return dbServiceItem.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setPid(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)))
                    .build();
        }
    }

    public abstract static class ServiceExtraItems {

        public static final String TABLE_NAME = "ServiceExtraItems";
/*      public abstract Integer id();
        @Nullable
        public abstract Integer pid();
        public abstract String name();*/

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PID = "pid";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID+ " INTEGER, " +
                        COLUMN_PID+ " INTEGER, " +
                        COLUMN_NAME + " TEXT " +
                        " ); ";

        public static ContentValues toContentValues(dbServiceItem serviceMainItem) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, serviceMainItem.id());
            values.put(COLUMN_PID, serviceMainItem.pid());
            if (serviceMainItem.name()!= null) values.put(COLUMN_NAME, serviceMainItem.name());
            return values;
        }

        public static dbServiceItem parseCursor(Cursor cursor) {
            return dbServiceItem.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setPid(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)))
                    .build();
        }
    }

    public abstract static class PackageModels {

        public static final String TABLE_NAME = "PackageModels";
/*      @Nullable
        public abstract Integer id();
        @Nullable
        public abstract Integer clientId();
        public abstract Date orderDate();
        public abstract String notes();
        public abstract String address();
        @Nullable public abstract Float price();*/

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CLIENT_ID = "ClientID";
        public static final String COLUMN_ORDER_DATE = "OrderDate";
        public static final String COLUMN_NOTES = "Notes";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_PRICE = "Price";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID+ " INTEGER, " +
                        COLUMN_CLIENT_ID+ " INTEGER, " +
                        COLUMN_ORDER_DATE + " TEXT, " +
                        COLUMN_NOTES + " TEXT, " +
                        COLUMN_ADDRESS + " TEXT, " +
                        COLUMN_PRICE + " REAL " +
                        " ); ";

        public static ContentValues toContentValues(PackageModel packageModel) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, packageModel.id());
            if (packageModel.clientId()!= null) values.put(COLUMN_CLIENT_ID, packageModel.clientId());
            if (packageModel.orderDate()!= null) values.put(COLUMN_ORDER_DATE, dateFormat.format(packageModel.orderDate()));
            if (packageModel.notes()!= null) values.put(COLUMN_NOTES, packageModel.notes());
            if (packageModel.address()!= null) values.put(COLUMN_ADDRESS, packageModel.address());
            if (packageModel.price()!= null) values.put(COLUMN_PRICE, packageModel.price());
            return values;
        }

        public static PackageModel parseCursor(Cursor cursor) {
            Date date = new Date();
            try {
                date = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE)));
            } catch (Exception e){
                Timber.w(e, "Data converting");
            }

            return PackageModel.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setClientId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ID)))
                    .setOrderDate(date)
                    .setNotes(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)))
                    .setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)))
                    .setPrice(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PRICE)))
                    .build();
        }
    }

    public abstract static class SelectedItemsTable{

        public static final String TABLE_NAME = "SelectedItems";
/*          public abstract Integer serviceID();*/

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SERVICE_ID = "ServiceID";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID+ " INTEGER PRIMARY KEY, " +
                        COLUMN_SERVICE_ID + " INTEGER " +
                        " ); ";

        public static ContentValues toContentValues(SelectedItems selectedItems) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SERVICE_ID, selectedItems.serviceID().id());
            return values;
        }

        public static SelectedItems parseCursor(Cursor cursor) {
            return null; /*SelectedItems.builder()
                    .setServiceID(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID)))
                    .build();*/
        }
    }

    public abstract static class SelectedItemsAddTable{

        public static final String TABLE_NAME = "SelectedItemsAdd";
/*      public abstract Integer pid();
        public abstract Integer additionalQID();*/

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PID = "Pid";
        public static final String COLUMN_ADD_QUESTION_ID = "additionalQID";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID+ " INTEGER PRIMARY KEY, " +
                        COLUMN_PID + " INTEGER, " +
                        COLUMN_ADD_QUESTION_ID + " INTEGER " +
                        " ); ";

        public static ContentValues toContentValues(SelectedItemsAdd selectedItemsAdd) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PID, selectedItemsAdd.pid());
            values.put(COLUMN_ADD_QUESTION_ID, selectedItemsAdd.additionalQID().id());
            return values;
        }

        public static SelectedItemsAdd parseCursor(Cursor cursor) {
            return null; /*SelectedItemsAdd.builder()
                    .setPid(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PID)))
                    .setAdditionalQID(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADD_QUESTION_ID)))
                    .build();*/
        }
    }

    public abstract static class SelectedItemsAddExtraTable{

        public static final String TABLE_NAME = "SelectedItemsAddExtra";
/*      public abstract Integer pid();
        public abstract Integer additionalQID();*/

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PID = "Pid";
        public static final String COLUMN_ADD_QUESTION_ID = "additionalQID";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID+ " INTEGER PRIMARY KEY, " +
                        COLUMN_PID + " INTEGER, " +
                        COLUMN_ADD_QUESTION_ID + " INTEGER " +
                        " ); ";

        public static ContentValues toContentValues(SelectedItemsAddExtra selectedItemsAdd) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PID, selectedItemsAdd.pid());
            values.put(COLUMN_ADD_QUESTION_ID, selectedItemsAdd.additionalQID().id());
            return values;
        }

        public static SelectedItemsAddExtra parseCursor(Cursor cursor) {
            return null;/*SelectedItemsAddExtra.builder()
                    .setPid(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PID)))
                    .setAdditionalQID(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADD_QUESTION_ID)))
                    .build();*/
        }
    }
}
