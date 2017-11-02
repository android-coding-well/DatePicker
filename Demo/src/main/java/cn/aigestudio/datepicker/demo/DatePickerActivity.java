package cn.aigestudio.datepicker.demo;

import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.bizs.themes.DPCNTheme;
import cn.aigestudio.datepicker.cons.DPLanguage;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.demo.databinding.ActivityDatePickerBinding;
import cn.aigestudio.datepicker.views.DatePicker;

public class DatePickerActivity extends AppCompatActivity {

    ActivityDatePickerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_picker);
        initDatePicker();
        initDateSelectedMode();
        initTitleAndWeekView();
        initMonthView();
    }

    private void initMonthView() {
        binding.sHoliday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.dpDate.setHolidayDisplay(isChecked);
            }
        });
        binding.sDeferred.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.dpDate.setDeferredDisplay(isChecked);
            }
        });
        binding.sFestival.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.dpDate.setFestivalDisplay(isChecked);
            }
        });
        binding.sToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.dpDate.setTodayDisplay(isChecked);
            }
        });
        binding.sLang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.dpDate.setLanguage(isChecked?DPLanguage.ENGLISH: DPLanguage.CHINESE);
            }
        });


        //自定义主题
        binding.sTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                binding.dpDate.setTheme(new DPCNTheme(){
                    @Override
                    public int colorDeferred() {
                        return isChecked?0xFF123456:super.colorDeferred();
                    }
                    @Override
                    public int colorL() {
                        return isChecked?0xFF123456:super.colorL();
                    }
                    @Override
                    public int colorTitleBG() {
                        return isChecked?0xFFFF00FF:super.colorTitleBG();
                    }

                    @Override
                    public int colorToday() {
                        return isChecked?0xFF00FFFF:super.colorToday();
                    }

                    @Override
                    public int colorTitle() {
                        return isChecked?0xFFFFFF00:super.colorTitle();
                    }

                    @Override
                    public int colorBG() {
                        return isChecked?0xFFaaaaaa:super.colorBG();
                    }

                    @Override
                    public int colorBGCircle() {
                        return isChecked?0xFFa45F00:super.colorBGCircle();
                    }

                    @Override
                    public int colorG() {
                      return   isChecked?0xFFFF1234:super.colorG();
                    }

                    @Override
                    public int colorF() {
                        return   isChecked?0xFFFdf341:super.colorG();
                    }

                    @Override
                    public int colorWeekend() {
                        return   isChecked?0xFF0000FF:super.colorWeekend();
                    }

                    @Override
                    public int colorHoliday() {
                        return   isChecked?0xFFFF0000:super.colorHoliday();
                    }
                });
            }
        });
    }

    private void initTitleAndWeekView() {
        binding.sTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                binding.dpDate.showTitleBar(isChecked);
            }
        });
        binding.sWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.dpDate.showWeekBar(isChecked);
            }
        });
    }

    private void initDateSelectedMode() {
        binding.rbSingle.setChecked(true);
        binding.rgSelectedMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_none:
                        binding.dpDate.setMode(DPMode.NONE);
                        break;
                    case R.id.rb_single:
                        binding.dpDate.setMode(DPMode.SINGLE);
                        break;
                    case R.id.rb_multiple:
                        binding.dpDate.setMode(DPMode.MULTIPLE);
                        break;
                }
            }
        });
    }

    private void initDatePicker() {
        binding.dpDate.setMode(DPMode.SINGLE);
        binding.dpDate.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                Toast.makeText(DatePickerActivity.this, "多选："+date.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.dpDate.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(DatePickerActivity.this, "单选："+date, Toast.LENGTH_SHORT).show();

            }
        });
        binding.dpDate.setOnMonthChangeListener(new DatePicker.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int month) {
                Toast.makeText(DatePickerActivity.this, "月份:"+month, Toast.LENGTH_SHORT).show();
            }
        });
        binding.dpDate.setOnYearChangeListener(new DatePicker.OnYearChangeListener() {
            @Override
            public void onYearChange(int year) {
                Toast.makeText(DatePickerActivity.this, "年份:"+year, Toast.LENGTH_SHORT).show();
            }
        });

        binding.dpDate.setOnDateChangeListener(new DatePicker.OnDateChangeListener() {
            @Override
            public void onDateChange(int year, int month) {
                Toast.makeText(DatePickerActivity.this, "年份:"+year+",月份:"+month, Toast.LENGTH_SHORT).show();
            }
        });

        binding.dpDate.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTL(canvas, rect, paint, data);
                switch (data) {
                    case "2017-10-5":
                    case "2017-10-7":
                    case "2017-10-9":
                    case "2017-10-11":
                        paint.setColor(Color.GREEN);
                        canvas.drawRect(rect, paint);
                        break;
                    default:
                        paint.setColor(Color.RED);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
                        break;
                }
            }

            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTR(canvas, rect, paint, data);
                switch (data) {
                    case "2017-10-10":
                    case "2017-10-11":
                    case "2017-10-12":
                        paint.setColor(Color.BLUE);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
                        break;
                    default:
                        paint.setColor(Color.YELLOW);
                        canvas.drawRect(rect, paint);
                        break;
                }
            }


        });

        List<String> tmp = new ArrayList<>();
        tmp.add("2017-07-01");
        tmp.add("2017-10-16");
        tmp.add("2017-10-11");
        tmp.add("2017-7-09");
        DPCManager.getInstance().setDecorTR(tmp);
        List<String> tmp2 = new ArrayList<>();
        tmp2.add("2016-07-01");
        tmp2.add("2016-10-16");
        tmp2.add("2016-10-11");
        tmp2.add("2016-7-09");
        tmp2.add("2017-7-10");
        DPCManager.getInstance().setDecorTR(tmp2);


    }
}
