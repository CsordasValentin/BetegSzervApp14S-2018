package hu.petrik.betegszervapp;

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

                ArrayList<String> lista = new ArrayList<>();
                Cursor cursor = db.query(
                        "beteg",
                        new String[] { "nev", "taj" },
                        null,
                        null,
                        null,
                        null,
                        "nev",
                        null);

                while (cursor.moveToNext()) {
                    String nev = cursor.getString(0);
                    String taj = cursor.getString(1);
                    String sor = nev + " (" + taj + ")";
                    lista.add(sor);
                }
                cursor.close();

                lista.add("Kovacs Bela (123-456-789)");
                lista.add("Aasdf Jkl; (111-222-333)");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
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
