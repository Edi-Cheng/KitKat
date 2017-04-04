package com.kitkat.savingsmanagement;

import android.app.DatePickerDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddSavingsItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String TAG = "Add Savings";

    private EditText startDateEdit;
    private EditText endDateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings_item);

        startDateEdit = (EditText)findViewById(R.id.edit_start_date);
        endDateEdit = (EditText)findViewById(R.id.edit_end_date);

//        startDateEdit.setOnClickListener(showDatePicker);
//        endDateEdit.setOnClickListener(showDatePicker);
        startDateEdit.setOnFocusChangeListener(focusChangeListener);
        endDateEdit.setOnFocusChangeListener(focusChangeListener);

        Button saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddSavingsItemActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private final View.OnClickListener showDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDatePicker();
        }
    };

    private final View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                int vId = v.getId();
                if (vId == startDateEdit.getId() || vId == endDateEdit.getId() ) {
                    showDatePicker();
                }
            }
        }
    };

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int startYear = c.get(Calendar.YEAR);
        int startMonth = c.get(Calendar.MONTH);
        int startDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddSavingsItemActivity.this,
                AddSavingsItemActivity.this,
                startYear, startMonth, startDay);

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String dateString = dayOfMonth + "-" + month + "-" + year;

        Log.d(TAG, "onDateSet: "+ dateString);
        
        if (startDateEdit.isFocused()) {
            startDateEdit.setText(dateString);
        } else {
            endDateEdit.setText(dateString);
        }
    }
}
