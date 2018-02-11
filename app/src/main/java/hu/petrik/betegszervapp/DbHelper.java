package hu.petrik.betegszervapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hali on 2018.02.07..
 */
class DbHelper extends SQLiteOpenHelper {

    private static DbHelper instance;

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, "betegszerv.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE szerv (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    tipus TEXT NOT NULL\n" +
                ");");
        db.execSQL("CREATE TABLE beteg (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nev TEXT NOT NULL,\n" +
                "    taj TEXT NOT NULL,\n" +
                "    szerv TEXT NOT NULL,\n" +
                "    tipus TEXT NOT NULL,\n" +
                "    szerv_id INTEGER,\n" +
                "    FOREIGN KEY (szerv_id) REFERENCES szerv(id)\n" +
                ");");

        ContentValues beteg = new ContentValues();
        beteg.put("nev", "Kovacs Bela");
        beteg.put("taj", "123-456-789");
        beteg.put("szerv", "sziv");
        beteg.put("tipus", "AAA-12345");
        beteg.putNull("szerv_id");
        long betegId = db.insert("beteg", null, beteg);
        betegId = db.insert("beteg", null, beteg);
        betegId = db.insert("beteg", null, beteg);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Only one version exists, do nothing
    }
}
