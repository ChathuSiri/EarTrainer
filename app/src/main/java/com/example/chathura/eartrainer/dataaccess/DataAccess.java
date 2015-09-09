package com.example.chathura.eartrainer.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chathura.eartrainer.logic.User;
import com.example.chathura.eartrainer.logic.sound;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


public class DataAccess extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION =13;
    private static final String DATABASE_NAME = "login.db";
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "userId";
    private static final String COLUMN_UNAME = "username";
    private static final String COLUMN_PASS = "password";
    //private static final String COLUMN_SCORE = "score";
    private static final String TABLE_NAME_CHORDS = "chords";
    private static final String TABLE_NAME_NOTES = "notes";
    private static final String TABLE_NAME_SCALES = "scales";

    public String username;
    private int level;

    public int levelnote;
    public int marknote;
    public int levelchord;
    public int markchord;
    public int levelscale;
    public int markscale;

    SQLiteDatabase database;
    private static final String TABLE_CREATE = "CREATE TABLE user (userId integer not null PRIMARY KEY, "+
            "username text not null, password text not null, levelnotes integer not null, marknotes integer not null, levelchords integer not null, markchords integer not null, levelscales integer not null, markscales integer not null)";

    private static final String TABLE_CHORDS_CREATE = "CREATE TABLE chords (name text not null, "+
            "file text not null)";

    private static final String TABLE_NOTES_CREATE = "CREATE TABLE notes (name text not null, "+
            "file text not null)";

    private static final String TABLE_NOTES_MARKS_CREATE = "CREATE TABLE notemarks (num int not null PRIMARY KEY, "+
            "level integer not null, marks integer not null)";

    private static final String TABLE_CHORDS_MARKS_CREATE = "CREATE TABLE chordmarks (num int not null PRIMARY KEY, "+
            "level integer not null, marks integer not null)";

    private static final String TABLE_SCALES_MARKS_CREATE = "CREATE TABLE scalemarks (num int not null PRIMARY KEY, "+
            "level integer not null, marks integer not null)";

    private static final String TABLE_SCALES_CREATE = "CREATE TABLE scales (name text not null, "+
            "file text not null)";

    public DataAccess(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        username = "default";
        level = 1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //creates table at the begining
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CHORDS_CREATE);
        db.execSQL(TABLE_NOTES_CREATE);
        db.execSQL(TABLE_SCALES_CREATE);
        db.execSQL(TABLE_NOTES_MARKS_CREATE);
        db.execSQL(TABLE_CHORDS_MARKS_CREATE);
        db.execSQL(TABLE_SCALES_MARKS_CREATE);

        this.database = db;
        insertChords(db);
        insertNotes(db);
        insertScales(db);
    }
    //this method will search for the password for a specific username
    public String searchPassword(String username){
        database = this.getReadableDatabase();
        String query = "SELECT username,password FROM "+TABLE_NAME;
        Cursor cursor = database.rawQuery(query,null);
        String user, pass;
        pass = "Not Found";
        if(cursor.moveToFirst()){

            do{
                user = cursor.getString(0);
                if(user.equals(username)){
                    pass = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        database.close();
        return pass;

    }
    //nethod is used for insert new user info
    public void insertUser(User user){
        int defaultScore =0;
        int defaultLevel =2;
        database= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT * FROM user";
        Cursor cursor = database.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_UNAME, user.getUserName());
        values.put(COLUMN_PASS, user.getPassword());
        values.put("levelnotes", defaultLevel);
        values.put("marknotes", defaultScore);
        values.put("levelchords", defaultLevel);
        values.put("markchords", defaultScore);
        values.put("levelscales", defaultLevel);
        values.put("markscales", defaultScore);
        database.insert(TABLE_NAME, null, values);
        database.close();
    }
    //this method returns a list of whole users
    public List<User> getUsers(){
        database = this.getReadableDatabase();
        String query = "SELECT username,password FROM "+TABLE_NAME;
        Cursor cursor = database.rawQuery(query,null);
        String user, pass;
        pass = "Not Found";
        List<User> userlist =new ArrayList<>();
        if(cursor.moveToFirst()){

            do{

                user = cursor.getString(0);
                pass = cursor.getString(1);
                userlist.add(new User(user,pass));
            }
            while(cursor.moveToNext());
        }
        database.close();
        return userlist;

    }

    //int this method latest marks and levels will be taken out from database and saved in varables
    public void getUserDetails(){
        //String user = "default";
        String query1 = "SELECT * FROM notemarks";
        String query2 = "SELECT * FROM chordmarks";
        String query3 = "SELECT * FROM scalemarks";
        database= this.getWritableDatabase();

        Cursor cursor = database.rawQuery(query1, null);
        int count = cursor.getCount();
        if(count==0){                                   //if table is empty add the first entry
            ContentValues values = new ContentValues();
            values.put("num", 0);
            values.put("level", 1);
            values.put("marks", 0);
            database.insert("notemarks", null, values);
        }
        cursor = database.rawQuery(query1, null);
        count = cursor.getCount();
        if(cursor.moveToFirst()){
            do {
                if(cursor.getInt(0)==(count-1)){        //then take the last entry information
                    levelnote = cursor.getInt(1);
                    marknote = cursor.getInt(2);
                }
            } while (cursor.moveToNext());

        }

        cursor = database.rawQuery(query2, null);
        count = cursor.getCount();
        if(count==0){                                   //if table is empty add the first entry
            ContentValues values = new ContentValues();
            values.put("num", 0);
            values.put("level", 1);
            values.put("marks", 0);
            database.insert("chordmarks", null, values);
        }
        cursor = database.rawQuery(query2, null);
        count = cursor.getCount();
        if(cursor.moveToFirst()){
            do {
                if(cursor.getInt(0)==(count-1)){        //then take the last entry information
                    levelchord = cursor.getInt(1);
                    markchord = cursor.getInt(2);
                }
            } while (cursor.moveToNext());

        }

        cursor = database.rawQuery(query3, null);
        count = cursor.getCount();
        if(count==0){                                   //if table is empty add the first entry
            ContentValues values = new ContentValues();
            values.put("num", 0);
            values.put("level", 1);
            values.put("marks", 0);
            database.insert("scalemarks", null, values);
        }
        cursor = database.rawQuery(query3, null);
        count = cursor.getCount();
        if(cursor.moveToFirst()){
            do {
                if(cursor.getInt(0)==(count-1)){        //then take the last entry information
                    levelscale = cursor.getInt(1);
                    markscale = cursor.getInt(2);
                }
            } while (cursor.moveToNext());

        }


    }

    public void setMarks(String exercise,int marks,int level){//will set marks and level in given exercise
        database = this.getReadableDatabase();
        getUserDetails();
        String query1 = "SELECT * FROM notemarks";
        String query2 = "SELECT * FROM chordmarks";
        String query3 = "SELECT * FROM scalemarks";

        //first counts of entries of the table will be taken
        Cursor cursor = database.rawQuery(query1, null);
        int countnotes = cursor.getCount();
        cursor = database.rawQuery(query2, null);
        int countchords = cursor.getCount();
        cursor = database.rawQuery(query3, null);
        int countscales = cursor.getCount();

        ContentValues data=new ContentValues();
        if(exercise.equals("notes")){                       //if exercise name is notes this segment will be executed
            data.put("marks", marks);
            data.put("level", level);
            data.put("num", countnotes);
            database.insert("notemarks", null, data);

        }
        else if(exercise.equals("chords")){                       //if exercise name is notes this segment will be executed
            data.put("marks", marks);
            data.put("level", level);
            data.put("num", countchords);
            database.insert("chordmarks", null, data);
        }
        else if(exercise.equals("scales")){                       //if exercise name is notes this segment will be executed
            data.put("marks", marks);
            data.put("level", level);
            data.put("num", countscales);
            database.insert("scalemarks", null, data);
        }

    }

    public ArrayList<BarEntry> getDataSet(String exercise){//data set will be returned for charting given exercise
        String query1 = "SELECT * FROM notemarks";
        String query2 = "SELECT * FROM chordmarks";
        String query3 = "SELECT * FROM scalemarks";
        database= this.getWritableDatabase();
        Cursor cursor;
        if(exercise.equals("notes")){                   //data will be quaried according to the exrecise type
            cursor  = database.rawQuery(query1, null);
        }
        else if(exercise.equals("chords")){
            cursor  = database.rawQuery(query2, null);
        }
        else
            cursor  = database.rawQuery(query3, null);

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        if (cursor.moveToFirst()) {                     //then table entries will be added to an arraylist
            do {
                sound chord = new sound();
                chord.setName(cursor.getString(0));
                chord.setFile(cursor.getString(1));
                BarEntry v1e1 = new BarEntry(cursor.getInt(2), cursor.getInt(0)); // Jan
                valueSet1.add(v1e1);
            } while (cursor.moveToNext());
        }

       return valueSet1;

    }

    //This method inserts chords to db in initial phase
    private void insertChords(SQLiteDatabase db){
        ArrayList<sound> chords = new ArrayList<>();

        chords.add(new sound("A maj","chord_amaj"));
        chords.add(new sound("D maj", "chord_dmaj"));
        chords.add(new sound("C maj","chord_cmaj"));
        chords.add(new sound("E maj","chord_emaj"));
        chords.add(new sound("G maj","chord_gmaj"));
        chords.add(new sound("E min","chord_emin"));
        chords.add(new sound("D min", "chord_dmin"));

        chords.add(new sound("B min","chord_bmin"));
        chords.add(new sound("F# min", "chord_fsmin"));
        chords.add(new sound("A# maj","chord_asmaj"));
        chords.add(new sound("F maj", "chord_fmaj"));
        chords.add(new sound("B maj","chord_bmaj"));
        chords.add(new sound("A min","chord_amin"));

        chords.add(new sound("Db maj","chord_dfmaj"));
        chords.add(new sound("Eb maj", "chord_efmaj"));
        chords.add(new sound("F# maj", "chord_fsmaj"));
        chords.add(new sound("G# maj","chord_gsmaj"));
        chords.add(new sound("A# min","chord_asmin"));
        chords.add(new sound("C min","chord_cmin"));
        chords.add(new sound("Db min","chord_dfmin"));
        chords.add(new sound("Eb min", "chord_efmin"));
        chords.add(new sound("F min", "chord_fmin"));
        chords.add(new sound("G min","chord_gmin"));
        chords.add(new sound("G# min","chord_gsmin"));


        for(sound sound:chords){
            ContentValues values = new ContentValues();
            values.put("name", sound.getName());
            values.put("file", sound.getFile());

            // Inserting Row
            db.insert(TABLE_NAME_CHORDS, null, values);
            //db.close(); // Closing database connection
        }


    }
    //this method returns a list of chords in the database
    public List<sound> getChords(String username){
        List<sound> chordList = new ArrayList<sound>();
        // Select All Query
        getUserDetails();
        if(markchord>11 && levelchord<3)
            levelchord++;

        String selectQuery = "SELECT * FROM chords";

        database= this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sound chord = new sound();
                chord.setName(cursor.getString(0));
                chord.setFile(cursor.getString(1));

                chordList.add(chord);
            } while (cursor.moveToNext());
        }

        // return chord list    list will be different according to level
        if(levelchord ==1) {
            List<sound> list = new ArrayList<>();
            list = chordList.subList(0,7);
            return list;
        }else if(levelchord ==2){
            List<sound> list = new ArrayList<>();
            list = chordList.subList(0,13);
            return list;
        }
        else{

            return chordList;}
    }


    //This method inserts notes to db in initial phase
    private void insertNotes(SQLiteDatabase db){
        ArrayList<sound> Notes = new ArrayList<>();
        Notes.add(new sound("A normal","noteanguitar"));
        Notes.add(new sound("B normal","notebnguitar"));
        Notes.add(new sound("C normal","notecnguitar"));
        Notes.add(new sound("D normal", "notednguitar"));
        Notes.add(new sound("E normal", "noteenguitar"));
        Notes.add(new sound("F normal", "notefnguitar"));
        Notes.add(new sound("G normal", "notegnguitar"));
        Notes.add(new sound("F sharp", "notefsguitar"));

        Notes.add(new sound("A flat","noteafguitar"));
        Notes.add(new sound("B flat","notebfguitar"));
        Notes.add(new sound("C sharp", "notecsguitar"));
        Notes.add(new sound("E flat","noteefguitar"));



        for(sound sound:Notes){
            ContentValues values = new ContentValues();
            values.put("name", sound.getName());
            values.put("file", sound.getFile());

            // Inserting Row
            db.insert(TABLE_NAME_NOTES, null, values);
            //db.close(); // Closing database connection
        }

    }

    //this method returns the list of notes in the database
    public List<sound> getNotes(String username){List<sound> NoteList = new ArrayList<sound>();
        // Select All Query
        String selectQuery = "SELECT * FROM notes";
        getUserDetails();
        if(marknote>11 && levelnote<3)
            levelnote++;
        database= this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sound chord = new sound();
                chord.setName(cursor.getString(0));
                chord.setFile(cursor.getString(1));

                NoteList.add(chord);
            } while (cursor.moveToNext());
        }

        // return notes list         list will be different according to level
        if(levelnote ==1) {
            List<sound> list = new ArrayList<>();
            list = NoteList.subList(0,7);
            return list;
        }else if(levelnote ==2){

            return NoteList;
        }

        else return NoteList;
    }

    //This method inserts scales to db in initial phase
    private void insertScales(SQLiteDatabase db){
        ArrayList<sound> Scales = new ArrayList<>();


        Scales.add(new sound("A maj","scaleanmajor"));
        Scales.add(new sound("B maj","scalebnmajor"));
        Scales.add(new sound("C maj","scalecnmajor"));
        Scales.add(new sound("D maj", "scalednmajor"));
        Scales.add(new sound("E maj","scaleenmajor"));
        Scales.add(new sound("F maj", "scalefnmajor"));
        Scales.add(new sound("G maj","scalegnmajor"));

        Scales.add(new sound("Ab maj","scaleafmajor"));
        Scales.add(new sound("Bb maj","scalebfmajor"));
        Scales.add(new sound("C# maj","scalecsmajor"));
        Scales.add(new sound("F# maj", "scalefsmajor"));
        Scales.add(new sound("Eb maj", "scaleefmajor"));


        Scales.add(new sound("Ab min","scaleafminor"));
        Scales.add(new sound("A min","scaleanminor"));
        Scales.add(new sound("Bb min","scalebfminor"));
        Scales.add(new sound("B min","scalebnminor"));
        Scales.add(new sound("C min","scalecnminor"));
        Scales.add(new sound("C# min","scalecsminor"));
        Scales.add(new sound("D min", "scalednminor"));
        Scales.add(new sound("Eb min", "scaleefminor"));
        Scales.add(new sound("E min","scaleenminor"));
        Scales.add(new sound("F min", "scalefnminor"));
        Scales.add(new sound("F# min", "scalefsminor"));
        Scales.add(new sound("G min","scalegnminor"));


        for(sound sound:Scales){
            ContentValues values = new ContentValues();
            values.put("name", sound.getName());
            values.put("file", sound.getFile());

            // Inserting Row
            db.insert(TABLE_NAME_SCALES, null, values);
            //db.close(); // Closing database connection
        }

    }
    //This method returns scales in the database as list
    public List<sound> getScales(String username){
        List<sound> scaleList = new ArrayList<>();
        // Select All Query
        getUserDetails();
        if(markscale>11 && levelscale<3)
            levelscale++;
        String selectQuery = "SELECT * FROM scales";
        database= this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sound chord = new sound();
                chord.setName(cursor.getString(0));
                chord.setFile(cursor.getString(1));

                scaleList.add(chord);
            } while (cursor.moveToNext());
        }

        // return scales list              list will be different according to level
        if(levelscale ==1) {
            List<sound> list = new ArrayList<>();
            list = scaleList.subList(0,7);
            return list;
        }else if(levelscale ==2){
            List<sound> list = new ArrayList<>();
            list = scaleList.subList(0,12);
            return list;
        }

        else return scaleList;
    }


    public void resetInfo(){
        database = this.getReadableDatabase();
        String query4 = "DROP TABLE IF EXISTS "+"notemarks";
        String query5 = "DROP TABLE IF EXISTS "+"chordmarks";
        String query6 = "DROP TABLE IF EXISTS "+"scalemarks";
        database.execSQL(query4);
        database.execSQL(query5);
        database.execSQL(query6);
        database.execSQL(TABLE_NOTES_MARKS_CREATE);
        database.execSQL(TABLE_CHORDS_MARKS_CREATE);
        database.execSQL(TABLE_SCALES_MARKS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//drops table if exist in initial stage
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        String query1 = "DROP TABLE IF EXISTS "+TABLE_NAME_CHORDS;
        String query2 = "DROP TABLE IF EXISTS "+TABLE_NAME_SCALES;
        String query3 = "DROP TABLE IF EXISTS "+TABLE_NAME_NOTES;
        String query4 = "DROP TABLE IF EXISTS "+"notemarks";
        String query5 = "DROP TABLE IF EXISTS "+"chordmarks";
        String query6 = "DROP TABLE IF EXISTS "+"scalemarks";

        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        db.execSQL(query6);

        this.onCreate(db);
    }
}
