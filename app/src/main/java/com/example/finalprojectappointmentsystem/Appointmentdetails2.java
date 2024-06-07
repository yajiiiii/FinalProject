package com.example.finalprojectappointmentsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Appointmentdetails2 extends AppCompatActivity {

    private Map<TextView, LocalTime> timeSlots;
    private TextView selectedTimeSlot;
    private LocalDate selectedDate;
    private ImageView dateImageView;
    private TextView dateTextView;
    private Button btnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.appointmentdetails2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.backbutton3);

        // Set an OnClickListener to the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous one
                finish();
            }
        });

        // Initialize time slots
        initializeTimeSlots();

        // Initialize date picker
        dateImageView = findViewById(R.id.calendar2);
        dateTextView = findViewById(R.id.selectDate2);
        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Add validation to the booking button
        btnBook = findViewById(R.id.btnBook2);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to successfully.xml
                Intent intent = new Intent(Appointmentdetails2.this, Success.class);
                startActivity(intent);
            }
        });
        validateBooking();
    }
    private void initializeTimeSlots() {
        timeSlots = new HashMap<>();
        timeSlots.put(findViewById(R.id.tenAM), LocalTime.of(10, 0));
        timeSlots.put(findViewById(R.id.elevenAM), LocalTime.of(11, 0));
        timeSlots.put(findViewById(R.id.twelvePM), LocalTime.of(12, 0));
        timeSlots.put(findViewById(R.id.onePM), LocalTime.of(13, 0));
        timeSlots.put(findViewById(R.id.twoPM), LocalTime.of(14, 0));
        timeSlots.put(findViewById(R.id.threePM), LocalTime.of(15, 0));
        timeSlots.put(findViewById(R.id.fourPM), LocalTime.of(16, 0));
        timeSlots.put(findViewById(R.id.fivePM), LocalTime.of(17, 0));
        timeSlots.put(findViewById(R.id.sixPM), LocalTime.of(18, 0));

        for (Map.Entry<TextView, LocalTime> entry : timeSlots.entrySet()) {
            TextView timeSlot = entry.getKey();
            timeSlot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedTimeSlot != null) {
                        selectedTimeSlot.setBackground(getResources().getDrawable(R.drawable.hoursbg));
                    }
                    selectedTimeSlot = timeSlot;
                    selectedTimeSlot.setBackground(getResources().getDrawable(R.drawable.signinbg));
                    validateBooking();
                }
            });
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                dateTextView.setText(selectedDate.toString());
                validateBooking();
            }
        }, year, month, day);

        // Set the minimum and maximum date
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    private void validateBooking() {
        if (selectedTimeSlot == null || selectedDate == null) {
            btnBook.setEnabled(false);
            btnBook.setBackgroundColor(getResources().getColor(R.color.gray));
            return;
        }

        LocalTime currentTime = LocalTime.now();
        LocalTime selectedTime = timeSlots.get(selectedTimeSlot);
        LocalDate currentDate = LocalDate.now();

        if (selectedDate.isEqual(currentDate)) {
            if (selectedTime.isBefore(currentTime.plusHours(1)) || selectedTime.isBefore(currentTime)) {
                btnBook.setEnabled(false);
                btnBook.setBackground(getResources().getDrawable(R.drawable.signinbggray)); // Change to gray
                return;
            }
        }

        btnBook.setEnabled(true);
        btnBook.setBackground(getResources().getDrawable(R.drawable.signinbg)); // Change to original drawable
    }

    private void validateTimeSlots() {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();

        for (Map.Entry<TextView, LocalTime> entry : timeSlots.entrySet()) {
            TextView timeSlot = entry.getKey();
            LocalTime time = entry.getValue();

            if (selectedDate.isEqual(currentDate)) {
                if (time.isBefore(currentTime.plusHours(1))) {
                    timeSlot.setEnabled(false);
                    timeSlot.setBackground(getResources().getDrawable(R.drawable.hoursbg));
                } else {
                    timeSlot.setEnabled(true);
                    timeSlot.setBackground(getResources().getDrawable(R.drawable.hoursbg));
                }
            } else {
                timeSlot.setEnabled(true);
                timeSlot.setBackground(getResources().getDrawable(R.drawable.hoursbg));
            }
        }
    }
}