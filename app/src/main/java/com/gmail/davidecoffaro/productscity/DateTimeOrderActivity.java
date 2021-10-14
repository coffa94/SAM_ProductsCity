package com.gmail.davidecoffaro.productscity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class DateTimeOrderActivity extends AppCompatActivity implements View.OnClickListener {
    CalendarView calendar;
    EditText time;
    Button back;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_order);

        calendar = (CalendarView) findViewById(R.id.calendarViewOrder);
        time = (EditText) findViewById(R.id.editTextTime);
        back = (Button) findViewById(R.id.buttonBack);
        back.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.buttonConfirm);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==back){
            //TODO click back button
        }
        if(v==confirm){
            //TODO click confirm button
        }
    }
}