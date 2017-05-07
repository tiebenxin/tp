package com.example.haoyuban111.mubanapplication.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.utils.DateDFUtils;
import com.example.haoyuban111.mubanapplication.utils.DateUtil;
import com.example.haoyuban111.mubanapplication.utils.DensityUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 自定义日历卡
 *
 * @author wuwenjie
 */
public class CalendarCard extends View {

    private static final int TOTAL_COL = 7; // 7列
    private static final int TOTAL_ROW = 6; // 6行

    private static final long MILSDAY = 1000 * 60 * 60 * 24; //一天的毫秒值
    private static final long MAXDAYRANGE = 180; //最大可选范围180天

    private Paint mCirclePaint; // 绘制圆形的画笔
    private Paint mTextPaint; // 绘制文本的画笔
    private int mViewWidth; // 视图的宽度
    private int mViewHeight; // 视图的高度
    private int mCellSpace; // 单元格间距
    //    private int mCellSpaceWidth; // 单元格宽度
//    private int mCellSpaceHeight; // 单元格高度
    private int mColumnSize, mRowSize;//单元格宽高
    private Row rows[] = new Row[TOTAL_ROW]; // 行数组，每个元素代表一行
    private static CustomDate mShowDate; // 自定义的日期，包括year,month,day
    private OnCellClickListener mCellClickListener; // 单元格点击回调事件
    private int touchSlop; //
    private boolean callBackCellSpace;
    private DisplayMetrics mDisplayMetrics;
    private TextView tv_arravel, tv_depart, tv_title;
    private ImageView iv_left_dele, iv_right_dele;

    private static ArrayList<String> listDate = new ArrayList<String>();
    private static ArrayList<String> listReturnDate = new ArrayList<String>();
    private ArrayList<String> listDateTemp = new ArrayList<String>();
//    private static ArrayList<CustomDate> listCustomDate = new ArrayList<CustomDate>();//静态变量在对象销毁后任保存数据

    private Cell mClickCell;
    private float mDownX;
    private float mDownY;
    private boolean isCurrentMonth;
    private boolean isPreMonth;
    private Boolean isBeyond = false;
    private Context mContext;
    CustomCalendarCardDialog mDialog;
    private Paint mLinePaint;
//    private OnLeftDeteListener mOnLeftDeteListener;
//    private OnRightDeteListener mOnRightDeteListener;


    /**
     * 单元格点击的回调接口
     *
     * @author wuwenjie
     */
    public interface OnCellClickListener {
        void clickDate(CustomDate date); // 回调点击的日期

        void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
    }

    public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CalendarCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarCard(Context context, CustomCalendarCardDialog dialog, ArrayList<String> list) {
        super(context);
        mContext = context;
        mDisplayMetrics = getResources().getDisplayMetrics();
        mDialog = dialog;

        listReturnDate.clear();
        listReturnDate.addAll(list);
        checkHasSameDate(list);
        listDate.clear();
        listDate.addAll(list);
        init(context);

        if (null != mDialog) {
            mDialog.setOnNoticeClearDateListener(new CustomCalendarCardDialog.OnNoticeClearDateListener() {
                @Override
                public void clearSelDate() {
//                    if (null != listCustomDate && listCustomDate.size() > 0) {
//                        listCustomDate.clear();
//                    }

                    if (null != listDate && listDate.size() > 0) {
                        listDate.clear();
                    }
                }
            });


        }
    }


    public CalendarCard(Context context, OnCellClickListener listener) {
        super(context);
        this.mCellClickListener = listener;
        init(context);
    }

    private void init(Context context) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(DensityUtil.dip2px(context, 1));
        mLinePaint.setColor(Color.parseColor("#D8D9D4")); //线条颜色

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.parseColor("#2CC8B3")); // 圆形颜色
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        initDate();

    }

    private void initDate() {
        if (null != listDate && listDate.size() > 0) {//初始化的时候，检测是否有选中值，若有，显示日期小的
            String date = listDate.get(0);
            mShowDate = DateUtil.getCustomDateFromString(date);
        } else {
            mShowDate = new CustomDate();
        }

        fillDate();

        mDialog.setOnLeftDeteListener(new CustomCalendarCardDialog.OnLeftDeteListener() {
            @Override
            public void deleteLeftDate() {
                if (listDate.size() == 2) {
                    listDate.remove(0);
                    listReturnDate.remove(0);
                    listReturnDate.add(0, "");
                    update();
                } else if (listDate.size() == 1) {
                    listDate.remove(0);
                    listReturnDate.remove(0);
                    listReturnDate.add(0, "");
                    update();
                }

                setDateToController();
            }
        });


        mDialog.setOnRightDeteListener(new CustomCalendarCardDialog.OnRightDeteListener() {
            @Override
            public void deleteRightDate() {
                if (listDate.size() == 2) {
                    listDate.remove(1);
                    listReturnDate.remove(1);
                    listReturnDate.add(1, "");
                    update();
                } else if (listDate.size() == 1) {
                    listReturnDate.remove(1);
                    listReturnDate.add(1, "");
                    update();

                } else {
                    update();
                }
                setDateToController();
            }
        });
    }

    private void checkHasSameDate(ArrayList<String> list) {
        if (list != null) {
            if (list.size() == 2) {
                String firstDate = list.get(0);
                String lastDate = list.get(1);
                if (null != firstDate && !"".equals(firstDate) && null != lastDate && !"".equals(lastDate)) {
                    if (firstDate.equals(lastDate)) {//移除一个相同的
                        list.remove(1);
                    }
                } else if (null != firstDate && !"".equals(firstDate)) {
                    list.remove(1);//移除后面一个
                } else if (null != lastDate && !"".equals(lastDate)) {
                    list.remove(0);//移除前面一个
                } else {
                    list.clear();//都一处
                }
            } else if (list.size() == 1) {
                String date = list.get(0);
                if (null == date || "".equals(date)) {
                    list.clear();
                }
            }
        }
    }

    /*
    * fill date and draw
    *
    * @param isSecond ,is it the second filling date？And if the cell has background.
    *
    * if it was true,according if it was current date
    * if it was false,according if it was selected or in selected range
    * */
    private void fillDate() {//初始化的时候，当前日期有选中背景
        int monthDay = DateUtil.getCurrentMonthDay(); // 今天
        int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month - 1); // 上个月的天数
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month); // 当前月的天数
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
                mShowDate.month);

        isCurrentMonth = false;
        isPreMonth = false;

        if (DateUtil.isCurrentMonth(mShowDate)) {
            isCurrentMonth = true;
        }

        if (DateUtil.isPreMonth(mShowDate)) {
            isPreMonth = true;
        }

        if (listDate.size() == 0) {//初始化的时候，如果没有选中日期当前日期有选中背景
            if (listDateTemp.size() == 0) {
                int day = 0;
                for (int j = 0; j < TOTAL_ROW; j++) {
                    rows[j] = new Row(j);
                    for (int i = 0; i < TOTAL_COL; i++) {
                        int position = i + j * TOTAL_COL; // 单元格位置
                        // 这个月的
                        if (position >= firstDayWeek
                                && position < firstDayWeek + currentMonthDays) {
                            day++;
                            if (isCurrentMonth) {
                                rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                        mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                            } else {
                                if (isPreMonth) {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                                } else {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.UNREACH_DAY, i, j);
                                }
                            }

                            if (isCurrentMonth && day >= monthDay) { // 如果比这个月的今天要大，表示还没到
                                rows[j].cells[i] = new Cell(
                                        CustomDate.modifiDayForObject(mShowDate, day),
                                        State.UNREACH_DAY, i, j);
                            }

                            // 今天
//                            if (isCurrentMonth && day == monthDay) {
//                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
//                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
//                            }


                        }
                        // 过去一个月
//                else if (position < firstDayWeek) {
//                    rows[j].cells[i] = new Cell(new CustomDate(mShowDate.year,
//                            mShowDate.month - 1, lastMonthDays
//                            - (firstDayWeek - position - 1)),
//                            State.PAST_MONTH_DAY, i, j);
//                    // 下个月
//                } else if (position >= firstDayWeek + currentMonthDays) {
//                    rows[j].cells[i] = new Cell((new CustomDate(mShowDate.year,
//                            mShowDate.month + 1, position - firstDayWeek
//                            - currentMonthDays + 1)),
//                            State.NEXT_MONTH_DAY, i, j);
//                }
                    }
                }
            } else {//没有选中状态
                int day = 0;
                for (int j = 0; j < TOTAL_ROW; j++) {
                    rows[j] = new Row(j);
                    for (int i = 0; i < TOTAL_COL; i++) {
                        int position = i + j * TOTAL_COL; // 单元格位置
                        // 这个月的
                        if (position >= firstDayWeek
                                && position < firstDayWeek + currentMonthDays) {
                            day++;
                            if (isCurrentMonth) {
                                rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                        mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                            } else {
                                if (isPreMonth) {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                                } else {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.UNREACH_DAY, i, j);
                                }
                            }

                            if (isCurrentMonth && day >= monthDay) { // 如果比这个月的今天要大，表示还没到,包括今天
                                rows[j].cells[i] = new Cell(
                                        CustomDate.modifiDayForObject(mShowDate, day),
                                        State.UNREACH_DAY, i, j);
                            }


                        }
                    }
                }

            }
//        mCellClickListener.changeDate(mShowDate);
        } else if (listDate.size() > 0) {//第二次重新绘制的时候，是以是否选中为判断依据
            if (listDate.size() == 0) {//没有选中的时候，根据是否是当前日期，来选中(这种情况不存在了)
                int day = 0;
                for (int j = 0; j < TOTAL_ROW; j++) {
                    rows[j] = new Row(j);
                    for (int i = 0; i < TOTAL_COL; i++) {
                        int position = i + j * TOTAL_COL; // 单元格位置

                        if (position >= firstDayWeek
                                && position < firstDayWeek + currentMonthDays) {
                            day++;

                            if (isCurrentMonth) {
                                rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                        mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                            } else {
                                if (isPreMonth) {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                                } else {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.UNREACH_DAY, i, j);
                                }
                            }

                            if (isCurrentMonth && day >= monthDay) { // 如果比这个月的今天要大，表示还没到
                                rows[j].cells[i] = new Cell(
                                        CustomDate.modifiDayForObject(mShowDate, day),
                                        State.UNREACH_DAY, i, j);
                            }

                            // 今天
                            if (isCurrentMonth && day == monthDay) {
                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                            }


                        }
                    }
                }

            } else if (listDate.size() == 1) {//有一个选中的时候
                CustomDate selDate = DateUtil.getCustomDateFromString(listDate.get(0));
                Boolean isSelYear = false;
                Boolean isSelMonth = false;
                if (DateUtil.isSelectYear(selDate, mShowDate)) {
                    isSelYear = true;
                }
                if (DateUtil.isSelectMonth(selDate, mShowDate)) {
                    isSelMonth = true;
                }

                int day = 0;
                for (int j = 0; j < TOTAL_ROW; j++) {
                    rows[j] = new Row(j);
                    for (int i = 0; i < TOTAL_COL; i++) {
                        int position = i + j * TOTAL_COL; // 单元格位置

                        if (position >= firstDayWeek
                                && position < firstDayWeek + currentMonthDays) {
                            day++;

                            if (isCurrentMonth) {
                                rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                        mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                            } else {
                                if (isPreMonth) {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                                } else {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.UNREACH_DAY, i, j);
                                }
                            }

                            if (isCurrentMonth && day >= monthDay) { // 如果比这个月的今天要大，表示还没到
                                rows[j].cells[i] = new Cell(
                                        CustomDate.modifiDayForObject(mShowDate, day),
                                        State.UNREACH_DAY, i, j);
                            }


                            //选中的日期,1
                            if (isSelYear && isSelMonth && day == selDate.day) {
                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                            }


                        }
                    }
                }
            } else if (listDate.size() == 2) {//有两个的时候
                CustomDate selDateFirst = DateUtil.getCustomDateFromString(listDate.get(0));
                CustomDate selDateLast = DateUtil.getCustomDateFromString(listDate.get(1));
                checkCustomDate(selDateFirst, selDateLast);
                int diffYearAbs = Math.abs(diffYear);
                int diffMonthAbs = Math.abs(diffMonth);
                int diffDayAbs = Math.abs(diffDay);

                Boolean isSelYear = false;
                Boolean isSelYearFirst = false;
                Boolean isSelYearLast = false;
                Boolean isSelMonth = false;
                Boolean isSelMonthFirst = false;
                Boolean isSelMonthLast = false;

                if (DateUtil.isSelectYear(selDateFirst, mShowDate) || DateUtil.isSelectYear(selDateLast, mShowDate)) {
                    isSelYear = true;
                }
                if (DateUtil.isSelectYear(selDateFirst, mShowDate)) {
                    isSelYearFirst = true;
                }
                if (DateUtil.isSelectYear(selDateLast, mShowDate)) {
                    isSelYearLast = true;
                }
                if (DateUtil.isSelectMonth(selDateFirst, mShowDate) || DateUtil.isSelectMonth(selDateLast, mShowDate)) {
                    isSelMonth = true;
                }

                if (DateUtil.isSelectMonth(selDateFirst, mShowDate)) {
                    isSelMonthFirst = true;
                }

                if (DateUtil.isSelectMonth(selDateLast, mShowDate)) {
                    isSelMonthLast = true;
                }

                int day = 0;
                for (int j = 0; j < TOTAL_ROW; j++) {
                    rows[j] = new Row(j);
                    for (int i = 0; i < TOTAL_COL; i++) {
                        int position = i + j * TOTAL_COL; // 单元格位置

                        if (position >= firstDayWeek
                                && position < firstDayWeek + currentMonthDays) {
                            day++;

                            if (isCurrentMonth) {
                                rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                        mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                            } else {
                                if (isPreMonth) {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                                } else {
                                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                            mShowDate, day), State.UNREACH_DAY, i, j);
                                }
                            }

                            if (isCurrentMonth && day >= monthDay) { // 如果比这个月的今天要大，表示还没到
                                rows[j].cells[i] = new Cell(
                                        CustomDate.modifiDayForObject(mShowDate, day),
                                        State.UNREACH_DAY, i, j);
                            }

                            if (isSelYear) {//是选中年
                                if (diffYearAbs == 0) {
                                    if (isSelMonth) {//是选中月
                                        if (diffMonthAbs == 0) {//在同一年同一个月
                                            if (day >= selDateFirst.day && day <= selDateLast.day) {//在选中区间内
                                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                            }
                                        } else {//在同一年不同一个月
                                            if (isSelMonthFirst) {
                                                if (day >= selDateFirst.day) {//在前一个选中区间内
                                                    CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                                    rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                                }
                                            } else {
                                                if (day <= selDateLast.day) {//在后一个选中区间内
                                                    CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                                    rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                                }
                                            }

                                        }
                                    } else {//在同一年选中年中的非选中月
                                        if (mShowDate.month >= selDateFirst.month && mShowDate.month <= selDateLast.month) {
                                            CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                            rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                        }

                                    }
                                } else {//是选中年但非同一年
                                    if (isSelYearFirst) {//在前一个选中年
                                        if (isSelMonthFirst) {//是否是前一个选中月
                                            if (day >= selDateFirst.day) {//在前一个选中区间内
                                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                            }
                                        } else {
                                            if (mShowDate.month >= selDateFirst.month) {
                                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                            }
                                        }
                                    } else {//是选中年，后一个选中年
                                        if (isSelMonthLast) {//是否是前一个选中月
                                            if (day <= selDateLast.day) {//在前一个选中区间内
                                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                            }
                                        } else {
                                            if (mShowDate.month <= selDateLast.month) {
                                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                                rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                                            }
                                        }
                                    }
                                }
                            } else {//非选中年
                            }

                        }
                    }
                }

            }

        }
//        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initSize();
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null) {
                rows[i].drawCells(canvas);
            }
        }
    }

    /**
     * 初始化列宽行高
     */

    private void initSize() {
        mColumnSize = getWidth() / TOTAL_COL;
        mRowSize = getHeight() / TOTAL_ROW;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        mCellSpace = mViewWidth / TOTAL_COL;//以单元格大小宽度为准
        if (!callBackCellSpace) {
            callBackCellSpace = true;
        }

        mTextPaint.setTextSize(DensityUtil.px2sp(ContextHelper.getApplicationContext(), DensityUtil.dip2px(mContext, mCellSpace / 3)));//设置字体大小
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float disX = event.getX() - mDownX;
                float disY = event.getY() - mDownY;
                if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
                    int col = (int) (mDownX / mCellSpace);
                    int row = (int) (mDownY / mCellSpace);
                    measureClickCell(col, row);
                }
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 计算点击的单元格
     *
     * @param col
     * @param row
     */
    private void measureClickCell(int col, int row) {
        if (col >= TOTAL_COL || row >= TOTAL_ROW)
            return;
        if (mClickCell != null) {
            rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
        }
        if (rows[row] != null) {
            if (null != rows[row].cells[col] && null != rows[row].cells[col].date) {
                mClickCell = new Cell(rows[row].cells[col].date,
                        rows[row].cells[col].state, rows[row].cells[col].i,
                        rows[row].cells[col].j);

                CustomDate date = rows[row].cells[col].date;
                date.week = col;

                if (!DateUtil.checkIsPastDate(date)) {//检查是否是过期日期
                    checkDate(date);
                } else {

                }
                setReturnDate();
                // 刷新界面
                update();
                setDateToController();

            }
        }
    }

    private void setReturnDate() {
        listReturnDate.clear();
        if (listDate.size() == 2) {
            listReturnDate.add(listDate.get(0));
            listReturnDate.add(listDate.get(1));
        } else if (listDate.size() == 1) {
            listReturnDate.add(listDate.get(0));
            listReturnDate.add(listDate.get(0));
        } else {
            listReturnDate.add("");
            listReturnDate.add("");
        }
    }

    /**
     * 设置显示当前日期的控件
     *
     * @param tv_arravel 显示到达日期
     * @param tv_depart  显示离开日期
     * @param tv_title   显示当前年月
     */
    public void setTextView(TextView tv_arravel, TextView tv_depart, TextView tv_title, ImageView iv_left, ImageView iv_right) {
        this.tv_arravel = tv_arravel;
        this.tv_depart = tv_depart;
        this.tv_title = tv_title;
        this.iv_left_dele = iv_left;
        this.iv_right_dele = iv_right;
        invalidate();
    }

    /**
     * 组元素
     *
     * @author wuwenjie
     */
    class Row {
        public int j;

        Row(int j) {
            this.j = j;
        }

        public Cell[] cells = new Cell[TOTAL_COL];

        // 绘制单元格
        public void drawCells(Canvas canvas) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null) {
                    cells[i].drawSelf(canvas);
                }
            }
        }

    }

    /**
     * 单元格元素
     *
     * @author liszt
     */
    class Cell {
        public CustomDate date;
        public State state;
        public int i;
        public int j;

        public Cell(CustomDate date, State state, int i, int j) {
            super();
            this.date = date;
            this.state = state;
            this.i = i;
            this.j = j;
        }

        public void drawSelf(Canvas canvas) {
            switch (state) {
                case TODAY: // 今天
                    mTextPaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),//绘制背景
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mCirclePaint);
                    break;
                case CURRENT_MONTH_DAY: // 当前月日期
                    mTextPaint.setColor(Color.parseColor("#B0B0B0"));
                    break;
                case PAST_MONTH_DAY: // 过去一个月
                case NEXT_MONTH_DAY: // 下一个月
                    mTextPaint.setColor(Color.parseColor("#ffffff"));
                    break;
                case UNREACH_DAY: // 还未到的天
                    mTextPaint.setColor(Color.BLACK);
                    break;
                default:
                    break;
            }
            // 绘制文字
            String content = date.day + "";

            canvas.drawText(content,
                    (float) ((i + 0.5) * mCellSpace - mTextPaint
                            .measureText(content) / 2), (float) ((j + 0.5)
                            * mCellSpace + mTextPaint
                            .measureText(content, 0, 1) / 2), mTextPaint);

            //绘制线条
            if (j == 0) {//至今没搞懂，为什么画第一条的时候会窄一半
                mLinePaint.setStrokeWidth(DensityUtil.dip2px(mContext, 2));
            } else {
                mLinePaint.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
            }
            canvas.drawLine(i * mCellSpace, j * mCellSpace, (i + 1) * mCellSpace, j * mCellSpace, mLinePaint);
        }
    }

    /**
     * @author liszt 单元格的状态 当前月日期，过去的月的日期，下个月的日期
     */
    enum State {
        TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY;
    }

    // 从左往右划，上一个月
    public void leftSlide() {
        if (mShowDate.month == 1) {
            mShowDate.month = 12;
            mShowDate.year -= 1;
        } else {
            mShowDate.month -= 1;
        }
        update();
    }

    // 从右往左划，下一个月
    public void rightSlide() {
        if (mShowDate.month == 12) {
            mShowDate.month = 1;
            mShowDate.year += 1;
        } else {
            mShowDate.month += 1;
        }
        update();
    }

    /*
    * update
    * */
    public void update() {
        fillDate();
        invalidate();
        tv_title.setText(DateDFUtils.getSystemFormatTitleTime(mShowDate.year + "-" + mShowDate.month + "-" + mShowDate.day));

    }

    public void setOnCellClickListener(OnCellClickListener listener) {
        mCellClickListener = listener;
    }

    public CustomDate getSelectDate() {
        return mShowDate;
    }


    private void setDateToController() {
        if (null != listReturnDate) {
            if (listReturnDate.size() == 0) {
                tv_arravel.setText(R.string.no_sure);
                tv_depart.setText(R.string.no_sure);
                iv_left_dele.setVisibility(GONE);
                iv_right_dele.setVisibility(GONE);
            } else if (listReturnDate.size() == 1) {
                if (null != listReturnDate.get(0) && !"".equals(listReturnDate.get(0))) {
                    tv_arravel.setText(listReturnDate.get(0));
                    tv_depart.setText(listReturnDate.get(0));
                    iv_left_dele.setVisibility(VISIBLE);
                    iv_right_dele.setVisibility(VISIBLE);
                } else {
                    tv_arravel.setText(ContextHelper.getString(R.string.no_sure));
                    tv_depart.setText(ContextHelper.getString(R.string.no_sure));
                    iv_left_dele.setVisibility(GONE);
                    iv_right_dele.setVisibility(GONE);
                }
            } else if (listReturnDate.size() == 2) {
                if (null != listReturnDate.get(0) && !"".equals(listReturnDate.get(0))) {
                    tv_arravel.setText(listReturnDate.get(0));
                    iv_left_dele.setVisibility(VISIBLE);
                } else {
                    tv_arravel.setText(R.string.no_sure);
                    iv_left_dele.setVisibility(GONE);
                }
                if (null != listReturnDate.get(1) && !"".equals(listReturnDate.get(1))) {
                    tv_depart.setText(listReturnDate.get(1));
                    iv_right_dele.setVisibility(VISIBLE);
                } else {
                    tv_depart.setText(R.string.no_sure);
                    iv_right_dele.setVisibility(GONE);
                }
            }
        }
        tv_title.setText(DateDFUtils.getSystemFormatTitleTime(mShowDate.year + "-" + mShowDate.month + "-" + mShowDate.day));
    }


    /*
    *
    * check the selected date
    * @author liszt
    *
    * */
    private void checkDate(CustomDate date) {
        if (null != date) {
            String mDate = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (listDate.size() > 0) {
                try {
                    if (listDate.size() == 1) {
                        Date oldDate = format.parse(listDate.get(0));
                        Date newDate = format.parse(mDate);
                        long diff = newDate.getTime() - oldDate.getTime();
                        if (Math.abs(diff / MILSDAY) <= 180) {//取绝对值
                            isBeyond = false;
                            if (diff > 0) {
                                listDate.add(mDate);
                            } else if (diff < 0) {
                                listDate.add(0, mDate);
                            } else {
                                listDateTemp.clear();
                                listDateTemp.addAll(listDate);
                                listDate.clear();
                            }
                        } else {
                            isBeyond = true;
                            Toast.makeText(mContext, R.string.calendar_max_range, Toast.LENGTH_SHORT).show();
                        }
                    } else if (listDate.size() == 2) {
                        if (listDate.get(0).equals(listDate.get(1)) && mDate.equals(listDate.get(0))) {//如果是两个相同的元素,且所选也是相同，则不添加
                            listDateTemp.clear();
                            listDateTemp.addAll(listDate);
                            listDate.clear();
                        } else {
                            listDate.clear();
                            listDate.add(mDate);
                        }

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                listDate.add(mDate);
            }
        }
    }

    int diffYear;
    int diffMonth;
    int diffDay;

    /*
    * get the  difference between the selected dates
    * */
    private void checkCustomDate(CustomDate dateFirst, CustomDate dateLast) {
        if (null != dateFirst && null != dateLast) {
            diffYear = dateLast.year - dateFirst.year;
            diffMonth = dateLast.month - dateFirst.month;
            diffDay = dateLast.day - dateFirst.day;
        }
    }


    public ArrayList<String> getListDate() {
        return listReturnDate;
    }


}

