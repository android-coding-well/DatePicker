package cn.aigestudio.datepicker.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickDatePicker(View view) {
        Intent intent =new Intent(this,DatePickerActivity.class);
        startActivity(intent);
    }

    public void onClickDialog(View view) {

        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
        dialog.show();
        DatePicker picker = new DatePicker(MainActivity.this);
        picker.setDisplayDate("2017-10-02");
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(MainActivity.this, date, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    public void onClickMonth(View view) {
        Intent intent =new Intent(this,MonthViewActivity.class);
        startActivity(intent);
    }
}
