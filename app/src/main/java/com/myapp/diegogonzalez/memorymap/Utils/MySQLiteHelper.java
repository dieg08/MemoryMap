package com.myapp.diegogonzalez.memorymap.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Diego Gonzalez on 6/8/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MemoriesDB";
    // Memories table name
    private static final String TABLE_MEMORIES = "memories";

    // Memories Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_NOTE = "note";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONG = "long";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_NOTE,KEY_LAT,KEY_LONG};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create memories table
        String CREATE_MEMORY_TABLE = "CREATE TABLE memories ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "note TEXT," +
                "lat TEXT," +
                "long TEXT )";

        // create memories table
        db.execSQL(CREATE_MEMORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older memories table if existed
        db.execSQL("DROP TABLE IF EXISTS memories");

        // create fresh memories table
        this.onCreate(db);
    }

    public void addMemory(Memory memory){
        // for logging
        Log.d("addMemory", memory.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, memory.getTitle()); // get Title
        values.put(KEY_NOTE, memory.getNote()); // get Note
        values.put(KEY_LAT, memory.getLatitude());
        values.put(KEY_LONG, memory.getLongitude());

        // 3. insert
        db.insert(TABLE_MEMORIES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Memory getMemory(int id) {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MEMORIES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build memory object
        Memory memory = new Memory();
        memory.setId(Integer.parseInt(cursor.getString(0)));
        memory.setTitle(cursor.getString(1));
        memory.setNote(cursor.getString(2));
        memory.setLatitude(cursor.getString(3));
        memory.setLongitude(cursor.getString(4));

        // 5. Log action
        Log.d("getMemory(" + id + ")", memory.toString());

        // 6. return memory
        return memory;
    }

    public ArrayList<Memory> getAllMemories() {
        ArrayList<Memory> memories = new ArrayList<Memory>();

        // 1. Build the query
        String query = "SELECT * FROM " + TABLE_MEMORIES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build memory and add it to list
        Memory memory = null;
        if (cursor.moveToFirst()) {
            do {
                memory = new Memory();
                memory.setId(Integer.parseInt(cursor.getString(0)));
                memory.setTitle(cursor.getString(1));
                memory.setNote(cursor.getString(2));
                memory.setLatitude(cursor.getString(3));
                memory.setLongitude(cursor.getString(4));

                // add memory to list
                memories.add(memory);
            } while (cursor.moveToNext());
        }

        // 4. log event
        Log.d("getAllMemories", memories.toString());

        // 5. return memories
        return memories;
    }

    public void deleteMemory(Memory memory) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MEMORIES, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(memory.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteMemory", memory.toString());
    }
}
