package co.davo.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.davo.habittracker.data.HabitContract.HabitEntry;
import co.davo.habittracker.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity {
    private EditText weightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        weightEditText = (EditText) findViewById(R.id.weight_editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertHabit();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertHabit() {
        int weight = Integer.parseInt(weightEditText.getText().toString().trim());
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy  h:mm a z");
        String dateString = simpleDateFormat.format(date);

        HabitDbHelper dbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_WEIGHT, weight);
        values.put(HabitEntry.COLUMN_DATE, dateString);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error saving weight", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Weight saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
