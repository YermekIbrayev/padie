package com.iskhak.servicehelper.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.data.model.ServiceItem;

import java.util.ArrayList;
/*Test class*/
public class DBHelper extends SQLiteOpenHelper {

    final static String DBName = "ServiceDB";
    final static String SERVICE_GROUP_TABLE = "ServicesTbl";
    final static String MAIN_SELECTION_TABLE = "MainSelectionsTbl";
    final static String EXTRA_SELECTION_TABLE = "ExtraSelectionsTbl";
    final static int DBVersion = 1;
    final static String LOG_TAG = "DBLog";
    private static final String[] SERVICE_NAMES = {
            "Clean & Inspect Chimney",      //1
            "Clean Blinds or Shades",       //2
            "Clean Ducts & Vents",          //3
            "Clean Gutters & Downspouts",   //4
            "Clean Range & Hood",           //5
            "Clean Walls & Ceilings",       //6
            "Maid Service",                 //7
            "Post Construction Clean-up",   //8
            "Window Cleaning",              //9
            "Carpet & Upholstery Cleaning"  //10
    };

    private static final String[] MAIN_QUESTIONS = {
            "What services do you want?",                                           //1
            "What type of cleaning is needed?",                                     //2
            "What type of system needs cleaning?",                                  //3
            "Why do you need to have your gutters cleaned? Check all that apply.",  //4
            "What do you need cleaned? Check all that apply",                       //5
            "What type of cleaning is needed?",                                     //6
            "What type of cleaning is needed?",                                     //7
            "What type of construction work are you having done?",                  //8
            "How do you want them cleaned?",                                        //9
            ""                                                                      //10
    };

    private static final String[] EXTRA_QUESTIONS = {
            "How many chimney flues need cleaning?",                                                                                     //1
            "Which of following kinds of blinds need cleaning?",                                    //2
            "How many registers/vents need cleaning?",                                              //3
            "How many stories in your house?",                                                      //4
            "",                                                                                     //5
            "What wall and/or ceiling materials need cleaning? Check all that apply",               //6
            "",                                                                                     //7
            "Select the items in your home that need dusting and cleaning. Check all that apply",   //8
            "Do you have screens to be removed and cleaned also?",                                  //9
            ""                                                                                      //10
    };


    private static final String[][] MAIN_SELECTION_ITEMS =
            {
                    {"Chimney sweeping", "Chimney inspection", "Install chimney cap", "Waterproofing"},
                    {"Requires cleaning", "Stain removal", "Pet stains", "Dust", "Dirt/grime", "Mildew"},
                    {"Heating or cooling air ducts", "Both air ducts & dryer vent", "Dryer vent"},
                    {"Water isn’t draining from the downspouts", "Water overflows from gutter and drips from joints", "Gutter are clogged with leaves and needles",
                            "Gutter are clogged with gravel from roof", "As regular maintenance to keep them working properly"},
                    {"Hoods", "Grease traps and drains", "Kitchen equipment"},
                    {"General cleaning", "Move-out", "Heavy cleaning", "Completion of construction project"},
                    {"Recurring service", "One time cleaning", "Move-out", "Post construction"},
                    {"Major new construction (walls and structural elements moved and built)", "Remodel or addition (carpentry, drywall, cabinets, etc.)", "Painting or staining", "Reflooring or installing/refinishing wood floors", "Small projects"},
                    {"Both inside and outside", "Inside only", "Outside only"}
            };

    private static final String[][] EXTRA_SELECTION_ITEMS =
            {
                    {"1", "2", "3", "4"},
                    {"Horizontal vinyl/metal mini-blinds", "Horizontal wood/wood-like blinds",
                            "Vinyl/metal vertical blinds", "Fabric vertical blinds",
                            "Roller shades", "Woven wood shades"},
                    {"1", "2-5", "6-10", "11-15"},
                    {"One story", "Two stories", "Three stories"},
                    {},
                    {"Painted drywall", "Painted plaster", "Sprayed acoustic (popcorn) ceiling",
                            "Wallpaper", "Stained wood", "Painted wood", "Masonry/brick/stone"},
                    {},
                    {"Walls, ceilings, woodwork", "Carpeting", "Tile, vinyl or linoleum",
                            "Hardwood flooring", "Mirrors", "Light fixtures",
                            "Counters, cabinets and shelving", "Air ducts"},
                    {"Yes", "No"}
            };


    public DBHelper(Context context){
        super(context, DBName, null, DBVersion);
    }

    static class Test{
        void test(){
            String a = MAIN_QUESTIONS[1];
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        db.execSQL("CREATE TABLE " + SERVICE_GROUP_TABLE + "(" +
            "ID integer primary key," +
            "Name text, " +
            "MainQuestion text, " +
            "ExtraQuestion text )");

        db.execSQL("CREATE TABLE " + MAIN_SELECTION_TABLE + "(" +
                "ID integer primary key," +
                "PID integer," +
                "Name text )");

        db.execSQL("CREATE TABLE " + EXTRA_SELECTION_TABLE + "(" +
                "ID integer primary key," +
                "PID integer," +
                "Name text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        updateServiceNames();
        updateMainSelectionNames();
        updateExtraSelectionNames();
        db.setTransactionSuccessful();
        db.endTransaction();
        this.close();
    }


    private void updateServiceNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SERVICE_GROUP_TABLE, null, null);
        ContentValues cv = new ContentValues();
        for(int i = 0; i<SERVICE_NAMES.length;i++){
//            Log.d("DB", "REPLACE INTO "+ SERVICE_GROUP_TABLE +
//                    " (ID, Name, MainQuestion, ExtraQuestion)" +
//                    " VALUES ("+i+", '"+SERVICE_NAMES[i]+"', '"+MAIN_QUESTIONS[i]+"', '"+EXTRA_QUESTIONS[i]+"'); ");
            cv.put("ID", i);
            cv.put("Name",SERVICE_NAMES[i]);
            cv.put("MainQuestion",MAIN_QUESTIONS[i]);
            cv.put("ExtraQuestion",EXTRA_QUESTIONS[i]);
            db.insert(SERVICE_GROUP_TABLE,null,cv);
        }
    }

    private void updateMainSelectionNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MAIN_SELECTION_TABLE, null, null);
        ContentValues cv = new ContentValues();
        int currentID = 0;
        for(int i = 0; i< MAIN_SELECTION_ITEMS.length; i++){
            for(int j = 0; j< MAIN_SELECTION_ITEMS[i].length; j++){
//                db.execSQL("REPLACE INTO "+MAIN_SELECTION_TABLE+" (ID, PID,Name) VALUES ("+currentID+", "+i+", '"+ MAIN_SELECTION_ITEMS[i][j]+"');");
                cv.put("ID", currentID);
                cv.put("PID", i);
                cv.put("Name",MAIN_SELECTION_ITEMS[i][j]);
                db.insert(MAIN_SELECTION_TABLE,null,cv);
                currentID++;
            }
        }
    }

    private void updateExtraSelectionNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXTRA_SELECTION_TABLE, null, null);
        ContentValues cv = new ContentValues();
        int currentID = 0;
        for(int i = 0; i< EXTRA_SELECTION_ITEMS.length; i++){
            for(int j = 0; j< EXTRA_SELECTION_ITEMS[i].length; j++){
                //db.execSQL("REPLACE INTO "+EXTRA_SELECTION_TABLE+" (ID, PID,Name) VALUES ("+currentID+", "+i+", '"+ EXTRA_SELECTION_ITEMS[i][j]+"');");
                cv.put("ID", currentID);
                cv.put("PID", i);
                cv.put("Name",EXTRA_SELECTION_ITEMS[i][j]);
                db.insert(EXTRA_SELECTION_TABLE,null,cv);
                currentID++;
            }
        }
    }

    public ArrayList<ServiceGroup> getServiceGroupNamesFromDB(){
        ArrayList<ServiceGroup> _result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + SERVICE_GROUP_TABLE, null);
        if(c!=null){
            if(c.moveToFirst()){
                int idColumnIndex = c.getColumnIndex("ID");
                int nameColumnIndex = c.getColumnIndex("Name");
                int textMainQIndex = c.getColumnIndex("MainQuestion");
                int textExtraQIndex = c.getColumnIndex("ExtraQuestion");
                do{
                    ServiceGroup item = ServiceGroup.builder()
                            .setId(c.getInt(idColumnIndex))
                            .setName(c.getString(nameColumnIndex))
                            .setMainQuestion(c.getString(textMainQIndex))
                            .setExtraQuestion(c.getString(textExtraQIndex))
                            .build();
                            //new ServiceGroup(c.getInt(idColumnIndex),c.getString(nameColumnIndex), c.getString(textMainQIndex), c.getString(textExtraQIndex));
                    _result.add(item);
                } while (c.moveToNext());
            }
        }
        return  _result;
    }

/*    public ArrayList<ServiceGroup> getServiceGroupNames(){
        ArrayList<ServiceGroup> _result = DataHolder.getInstance().getServiceGroups();
        return _result;
    }
    public ArrayList<ServiceItem> getMainSelectionNames(){

    }*/

    public ArrayList<ServiceItem> getMainSelectionNamesFromDB(){
        ArrayList<ServiceItem> _result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + MAIN_SELECTION_TABLE + " where PID =" + DataHolder.getInstance().getMainSelectionPID(), null);
        if(c!=null){
            if(c.moveToFirst()){
                int idColumnIndex = c.getColumnIndex("ID");
                int nameColumnIndex = c.getColumnIndex("Name");
                do{
                    ServiceItem item = new ServiceItem(c.getInt(idColumnIndex), c.getString(nameColumnIndex));
                    _result.add(item);
                } while (c.moveToNext());
            }
        }
        return  _result;
    }

    public ArrayList<ServiceItem> getExtraSelectionNamesFromDB(){
        ArrayList<ServiceItem> _result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + EXTRA_SELECTION_TABLE + " where PID =" + DataHolder.getInstance().getMainSelectionPID(), null);
        if(c!=null){
            if(c.moveToFirst()){
                int idColumnIndex = c.getColumnIndex("ID");
                int nameColumnIndex = c.getColumnIndex("Name");
                do{
                    ServiceItem item = new ServiceItem(c.getInt(idColumnIndex), c.getString(nameColumnIndex));
                    _result.add(item);
                } while (c.moveToNext());
            }
        }
        return  _result;
    }
}
