package cn.aigestudio.datepicker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.bizs.languages.CN;
import cn.aigestudio.datepicker.bizs.languages.DPLManager;
import cn.aigestudio.datepicker.bizs.languages.EN;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.cons.DPLanguage;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.utils.MeasureUtil;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * DatePicker
 */
public class DatePicker extends LinearLayout {


    /**
     * 日期变化监听器
     */
    public interface OnDateChangeListener{
        /**
         *
         * @param year 年份
         * @param month 月份1-12
         */
        void onDateChange(int year,int month);
    }

    /**
     * 日期单选监听器
     */
    public interface OnDatePickedListener {
        /**
         *
         * @param date 格式“yyyy-MM-dd”
         */
        void onDatePicked(String date);
    }

    /**
     * 日期多选监听器
     */
    public interface OnDateSelectedListener {
        /**
         *
         * @param date 格式“yyyy-MM-dd”
         */
        void onDateSelected(List<String> date);
    }

    /**
     * 年份变化监听器
     */
    public interface OnYearChangeListener {
        /**
         * @param year
         */
        void onYearChange(int year);
    }

    /**
     * 月份变化监听器
     */
    public interface OnMonthChangeListener {
        /**
         * @param month 1~12
         */
        void onMonthChange(int month);
    }

    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private MonthView monthView;// 月视图
    private TextSwitcher tsYear, tsMonth;
    private TextView tvEnsure;// 确定按钮显示


    private OnDateSelectedListener onDateSelectedListener;// 日期多选后监听
    private OnYearChangeListener onYearChangeListener;
    private OnMonthChangeListener onMonthChangeListener;
    private OnDateChangeListener onDateChangeListener;

    private RelativeLayout rlTitle;
    private LinearLayout llWeek;


    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        // 设置排列方向为竖向
        setOrientation(VERTICAL);
        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // --------------------------------------------------------------------------------标题栏
        initTitleBar(context, llParams);
        // --------------------------------------------------------------------------------周视图
        initWeekBar(context, llParams);
        // ------------------------------------------------------------------------------------月视图
        initMonthView(context, llParams);

    }

    /**
     * 初始化月视图
     *
     * @param context
     * @param llParams
     */
    private void initMonthView(Context context, LayoutParams llParams) {

        monthView = new MonthView(context);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                if (onMonthChangeListener != null) {
                    onMonthChangeListener.onMonthChange(month);
                }
                tsMonth.setText(mLManager.titleMonth()[month - 1]);

            }

            @Override
            public void onYearChange(int year) {
                if (onYearChangeListener != null) {
                    onYearChangeListener.onYearChange(year);
                }
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                tsYear.setText(tmp);
            }

            @Override
            public void onDateChange(int year, int month) {
                if (onDateChangeListener != null) {
                    onDateChangeListener.onDateChange(year,month);
                }
            }
        });
        addView(monthView, llParams);
    }

    /**
     * 初始化周视图
     *
     * @param context
     * @param llParams
     */
    private void initWeekBar(Context context, LayoutParams llParams) {
        // 周视图根布局
        llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(mTManager.colorTitle());
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

    }

    /**
     * 初始化标题栏视图
     *
     * @param context
     */
    private void initTitleBar(final Context context, LayoutParams llParams) {
        // 标题栏根布局
        rlTitle = new RelativeLayout(context);
        rlTitle.setBackgroundColor(mTManager.colorTitleBG());
        int rlTitlePadding = MeasureUtil.dp2px(context, 10);
        rlTitle.setPadding(rlTitlePadding, rlTitlePadding, rlTitlePadding, rlTitlePadding);

        // 标题栏子元素布局参数
        RelativeLayout.LayoutParams lpYear =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpYear.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams lpMonth =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpMonth.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lpEnsure =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpEnsure.addRule(RelativeLayout.CENTER_VERTICAL);
        lpEnsure.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);

        final Calendar calendar = Calendar.getInstance();
        // 年份显示
        tsYear = new TextSwitcher(context);
        tsYear.setInAnimation(in);
        tsYear.setOutAnimation(out);
        tsYear.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView d = new TextView(context);
                d.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                d.setTextColor(mTManager.colorTitle());
                return d;
            }
        });
        tsYear.setText(calendar.get(Calendar.YEAR) + "");

        // 月份显示
        tsMonth = new TextSwitcher(context);
        tsMonth.setInAnimation(in);
        tsMonth.setOutAnimation(out);
        tsMonth.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView d = new TextView(context);
                d.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                d.setTextColor(mTManager.colorTitle());
                return d;
            }
        });
        tsMonth.setText(mLManager.titleMonth()[calendar.get(Calendar.MONTH)]);
        // 确定显示
        tvEnsure = new TextView(context);
        tvEnsure.setText(mLManager.titleEnsure());
        tvEnsure.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvEnsure.setTextColor(mTManager.colorTitle());
        tvEnsure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDateSelectedListener) {
                    onDateSelectedListener.onDateSelected(monthView.getDateSelected());
                }
            }
        });

        rlTitle.addView(tsYear, lpYear);
        rlTitle.addView(tsMonth, lpMonth);
        rlTitle.addView(tvEnsure, lpEnsure);

        addView(rlTitle, llParams);
    }

    private void updateTitleAndWeekView() {
        for (int i = 0; i < llWeek.getChildCount(); i++) {
            ((TextView) llWeek.getChildAt(i)).setText(mLManager.titleWeek()[i]);
        }
        ((TextSwitcher) rlTitle.getChildAt(1)).setText(mLManager.titleMonth()[getDisplayMonth() - 1]);
        ((TextView) rlTitle.getChildAt(2)).setText(mLManager.titleEnsure());
    }

    //************************************************************************************************
    //-------------------------------------------开放接口---------------------------------------------
    //************************************************************************************************

    /**
     * 设置显示年月
     *
     * @param year  ...
     * @param month ...
     */
    public void setDisplayDate(int year, int month) {
        monthView.setDisplayDate(year, month);
    }

    /**
     * 设置显示年月
     *
     * @param date 日期
     */
    public void setDisplayDate(Date date) {
        monthView.setDisplayDate(date);
    }

    /**
     * 设置显示年月
     *
     * @param timestamp 时间戳
     */
    public void setDisplayDate(long timestamp) {
        monthView.setDisplayDate(timestamp);
    }

    /**
     * 设置显示年月
     *
     * @param dateFormat 格式化日志字符串，“yyyy-MM-dd”
     * @throws ParseException 字符串不符合规则
     */
    public void setDisplayDate(String dateFormat) {
        try {
            monthView.setDisplayDate(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置装饰器
     *
     * @param decor
     */
    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        tvEnsure.setVisibility(mode == DPMode.MULTIPLE ? VISIBLE : GONE);
        monthView.setDPMode(mode);
    }

    /**
     * 显示节日
     *
     * @param isFestivalDisplay
     */
    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    /**
     * 显示今天
     *
     * @param isTodayDisplay
     */
    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    /**
     * 显示假日
     *
     * @param isHolidayDisplay
     */
    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    /**
     * 显示延期
     *
     * @param isDeferredDisplay
     */
    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

    /**
     * 设置标题栏是否显示
     *
     * @param isShow
     */
    public void showTitleBar(boolean isShow) {
        rlTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置周视图栏是否显示
     *
     * @param isShow
     */
    public void showWeekBar(boolean isShow) {
        llWeek.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 获得当前显示年份
     *
     * @return
     */
    public int getDisplayYear() {
        return monthView.getDisplayYear();
    }

    /**
     * 获得当前显示月份（1-12）
     *
     * @return
     */
    public int getDisplayMonth() {
        return monthView.getDisplayMonth();
    }

    /**
     * 设置语言
     *
     * @param language
     */
    public void setLanguage(DPLanguage language) {
        DPCManager.getInstance().setLanguage(language);
        switch (language) {
            case CHINESE:
                setLanguage(new CN());
                break;
            case ENGLISH:
                setLanguage(new EN());
                break;
        }

    }

    /**
     * 设置语言
     *
     * @param language
     */
    public void setLanguage(DPLManager language) {
        this.mLManager = language;
        updateTitleAndWeekView();
    }


    /**
     * 设置单选监听器
     *
     * @param onDatePickedListener ...
     */
    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
       /* if (monthView.getDPMode() != DPMode.SINGLE) {
            throw new RuntimeException(
                    "Current DPMode does not SINGLE! Please call setMode set DPMode to SINGLE!");
        }*/
        monthView.setOnDatePickedListener(onDatePickedListener);
    }

    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        /*if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException(
                    "Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }*/
        this.onDateSelectedListener = onDateSelectedListener;
    }

    /**
     * 设置年份改变监听器
     *
     * @param onYearChangeListener
     */
    public void setOnYearChangeListener(OnYearChangeListener onYearChangeListener) {
        this.onYearChangeListener = onYearChangeListener;
    }

    /**
     * 设置月份改变监听器
     *
     * @param onMonthChangeListener
     */
    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
        this.onMonthChangeListener = onMonthChangeListener;
    }

    /**
     * 设置日期变化监听器
     * @param onDateChangeListener
     */
    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener){
        this.onDateChangeListener=onDateChangeListener;
    }



}
