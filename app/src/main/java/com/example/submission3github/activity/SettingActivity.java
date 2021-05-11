package com.example.submission3github.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.submission3github.R;
import com.example.submission3github.fragment.DatePickerFragment;
import com.example.submission3github.fragment.TimePickerFragment;
import com.example.submission3github.notification.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {
    private AlarmReceiver alarmReceiver;

    private TextView tvDate;
    private TextView tvTime;

    private EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton btnDate = findViewById(R.id.btn_date);
        ImageButton btnTime = findViewById(R.id.btn_time);
        Button btnSetOnce = findViewById(R.id.btn_once_alarm);
        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tv_time);
        etMessage = findViewById(R.id.et_message);

        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnSetOnce.setOnClickListener(this);

        Switch switchSetRepeat = findViewById(R.id.switch_repeating_alarm);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        switchSetRepeat.setChecked(sharedPreferences.getBoolean("value", true));

        switchSetRepeat.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();
    }

    private final static String DATE_PICKER_TAG = "DatePicker";
    private final static String TIME_PICKER_TAG = "TimePicker";

    @Override
    public void onClick(View v) {
        Switch switchSetRepeat = findViewById(R.id.switch_repeating_alarm);

        if (v.getId() == R.id.btn_date) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        } else if (v.getId() == R.id.btn_time) {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_TAG);
        } else if (v.getId() == R.id.btn_once_alarm) {
            String onceDate = tvDate.getText().toString();
            String onceTime = tvTime.getText().toString();
            String onceMessage = etMessage.getText().toString();
            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTime, onceMessage);
        } else
            if (switchSetRepeat.isChecked()) {
                alarmReceiver.setRepeatingAlarm(getBaseContext());
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", true);
                editor.apply();
                switchSetRepeat.setChecked(true);
            } else {
                alarmReceiver.setAlarmRepeatingCancel(getBaseContext());
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", false);
                editor.apply();
                switchSetRepeat.setChecked(false);
            }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        tvDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        switch (tag) {
            case TIME_PICKER_TAG:
                tvTime.setText(dateFormat.format(calendar.getTime()));
                break;
            default:
                break;
        }
    }
}