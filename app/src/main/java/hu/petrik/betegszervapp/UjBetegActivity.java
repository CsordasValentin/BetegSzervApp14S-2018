package hu.petrik.betegszervapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UjBetegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uj_beteg);

        Button ujbeteg = (Button)findViewById(R.id.ujbeteg_gomb);
        ujbeteg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Komponensektől kérdezzük le a beszúrandó értékeket
                String nev = "";
                // ...

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper helper = DbHelper.getInstance(UjBetegActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();

                        // TODO: Adatbázisba szúrjuk be

                        db.close();
                        UjBetegActivity.this.finish();
                    }
                });
            }
        });
    }
}
