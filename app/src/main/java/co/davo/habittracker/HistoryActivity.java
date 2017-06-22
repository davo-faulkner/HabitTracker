package co.davo.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import co.davo.habittracker.data.HabitContract.HabitEntry;
import co.davo.habittracker.data.HabitDbHelper;

public class HistoryActivity extends AppCompatActivity {
    private HabitDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_WEIGHT,
                HabitEntry.COLUMN_DATE};

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_weight);

        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " weight entries.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_WEIGHT + " - " +
                    HabitEntry.COLUMN_DATE + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int weightColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_WEIGHT);
            int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DATE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                displayView.append("\n" + currentId + " - " +
                        currentWeight + " lbs - " +
                        currentDate);
            }
        } finally {
            cursor.close();
        }
    }
}