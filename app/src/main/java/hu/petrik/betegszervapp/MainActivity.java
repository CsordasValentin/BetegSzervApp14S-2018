package hu.petrik.betegszervapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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
                DbHelper helper = new DbHelper(MainActivity.this);
                db = helper.getWritableDatabase();

                ListView betegLista = (ListView)findViewById(R.id.beteg_lista);

                ArrayList<Beteg> lista = new ArrayList<>();
                Cursor cursor = db.query(
                        "beteg",
                        new String[] { "id", "nev", "taj", "szerv", "tipus", "szerv_id" },
                        null,
                        null,
                        null,
                        null,
                        "nev",
                        null);

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(0);
                    String nev = cursor.getString(1);
                    String taj = cursor.getString(2);
                    String szerv = cursor.getString(3);
                    String tipus = cursor.getString(4);
                    long szerv_id = cursor.getLong(5);
                    lista.add(new Beteg(id, nev, taj, szerv, tipus, szerv_id));
                }
                cursor.close();

                ArrayAdapter<Beteg> adapter = new ArrayAdapter<Beteg>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        lista
                );

                betegLista.setAdapter(adapter);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
