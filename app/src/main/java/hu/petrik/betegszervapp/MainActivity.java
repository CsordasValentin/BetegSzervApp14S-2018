package hu.petrik.betegszervapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
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
                    "    szerv TEXT NOT NULL,\n" +
                    "    tipus TEXT NOT NULL,\n" +
                    "    szerv_id INTEGER,\n" +
                    "    FOREIGN KEY (szerv_id) REFERENCES szerv(id)\n" +
                    ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // Only one version exists, do nothing
        }
    }

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DbHelper helper = new DbHelper(getApplicationContext());
                db = helper.getWritableDatabase();
                
                // Feltöltjük a listát adatokkal
            }
        });
    }
}
