package com.example.haoyuban111.mubanapplication.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.utils.DateDFUtils;
import com.example.haoyuban111.mubanapplication.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by haoyuban111 on 2016/10/10.
 */
public class CustomCalendarCardDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private CalendarViewAdapter adapter;
    private ViewPager mViewPager;
    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_title;
    private CalendarCard[] mShowViews;
    private SildeDirection mDirection = SildeDirection.NO_SILDE;
    private int mCurrentIndex = 0;
    private CalendarCard mCalendarCardView;
    ArrayList<String> listDate = new ArrayList<String>();
    ArrayList<String> listDateTemp = new ArrayList<String>();
    private ImageView iv_right;
    private ImageView iv_left;
    private Button bt_ok;
    private Button bt_cancel;
    private ArrayList<String> mList;
    private OnClickSureListenter okListener;
    private OnNoticeClearDateListener mClearDateListener;
    private ImageView iv_left_dele;
    private ImageView iv_right_dele;
    private OnLeftDeteListener mOnLeftDeteListener;
    private OnRightDeteListener mOnRightDeteListener;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:

                if (null != mCalendarCardView) {
                    if (checkIsPastDate(mCalendarCardView.getSelectDate())) {//如果是过去日期，则不让左滑

                    } else {
                        mCalendarCardView.leftSlide();
                    }
                }
                break;

            case R.id.iv_right:
                if (null != mCalendarCardView) {
                    mCalendarCardView.rightSlide();
                }
                break;

            case R.id.btn_travel_ok:
                if (null != mCalendarCardView) {
                    mList = mCalendarCardView.getListDate();
                }
                okListener.getSelectDate(mList);

                if (null != mClearDateListener) {
                    mClearDateListener.clearSelDate();
                }
                dismiss();
                break;
            case R.id.btn_travel_cancel:
                if (null != mClearDateListener) {
                    mClearDateListener.clearSelDate();
                }
                dismiss();
                break;
            case R.id.iv_left_dele:
                if (null != mOnLeftDeteListener) {
                    mOnLeftDeteListener.deleteLeftDate();
                    mCalendarCardView.rightSlide();
                    mCalendarCardView.leftSlide();
                }
                break;
            case R.id.iv_right_dele:
                if (null != mOnRightDeteListener) {
                    mOnRightDeteListener.deleteRightDate();
                    mCalendarCardView.rightSlide();
                    mCalendarCardView.leftSlide();
                }
                break;
        }
    }


    enum SildeDirection {
        RIGHT, LEFT, NO_SILDE;


    }

    public CustomCalendarCardDialog(Context context, int theme, List<String> list) {
        super(context, theme);
        mContext = context;
        checkLength(list);//截取字符串长度
        checkIsHasPastDate(list);//去掉过期日期
        listDate.clear();
        listDate.addAll(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout();

    }

    private void initLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_dialog, null);
        setContentView(view);

        tv_left = (TextView) view.findViewById(R.id.tv_left);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        tv_title = (TextView) view.findViewById(R.id.tv_title);

        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_left_dele = (ImageView) view.findViewById(R.id.iv_left_dele);
        iv_right_dele = (ImageView) view.findViewById(R.id.iv_right_dele);
        bt_ok = (Button) findViewById(R.id.btn_travel_ok);
        bt_cancel = (Button) findViewById(R.id.btn_travel_cancel);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        iv_left_dele.setOnClickListener(this);
        iv_right_dele.setOnClickListener(this);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        CalendarCard[] views = new CalendarCard[3];
        listDateTemp.clear();
        listDateTemp.addAll(listDate);
        for (int i = 0; i < 3; i++) {
            views[i] = new CalendarCard(mContext, this, listDate);
            listDate.clear();
            listDate.addAll(listDateTemp);
            views[i].setTextView(tv_left, tv_right, tv_title, iv_left_dele, iv_right_dele);
        }
        adapter = new CalendarViewAdapter<CalendarCard>(views);

        mShowViews = (CalendarCard[]) adapter.getAllItems();
        mCalendarCardView = mShowViews[0 % mShowViews.length];
        setViewPager();
        setDateToController();

    }

    private void setDateToController() {
        if (null != listDate) {
            if (listDate.size() == 0) {
                tv_left.setText(R.string.no_sure);
                tv_right.setText(R.string.no_sure);
                iv_left_dele.setVisibility(View.GONE);
                iv_right_dele.setVisibility(View.GONE);
            } else if (listDate.size() == 1) {
                if (null != listDate.get(0) && !"".equals(listDate.get(0))) {
                    tv_left.setText(listDate.get(0));
                    tv_right.setText(listDate.get(0));
                    iv_left_dele.setVisibility(View.VISIBLE);
                    iv_right_dele.setVisibility(View.VISIBLE);
                } else {
                    tv_left.setText(R.string.no_sure);
                    tv_right.setText(R.string.no_sure);
                    iv_left_dele.setVisibility(View.GONE);
                    iv_right_dele.setVisibility(View.GONE);
                }

            } else if (listDate.size() == 2) {
                if (null != listDate.get(0) && !"".equals(listDate.get(0))) {
                    tv_left.setText(listDate.get(0));
                    iv_left_dele.setVisibility(View.VISIBLE);
                } else {
                    tv_left.setText(R.string.no_sure);
                    iv_left_dele.setVisibility(View.GONE);

                }

                if (null != listDate.get(1) && !"".equals(listDate.get(1))) {
                    tv_right.setText(listDate.get(1));
                    iv_right_dele.setVisibility(View.VISIBLE);
                } else {
                    tv_right.setText(R.string.no_sure);
                    iv_right_dele.setVisibility(View.GONE);
                }

            }
        }
    }


    private void setViewPager() {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        CustomDate mCustomDate = mCalendarCardView.getSelectDate();//初始化
        tv_title.setText(DateDFUtils.getSystemFormatTitleTime(mCustomDate.year + "-" + mCustomDate.month + "-" + mCustomDate.day));


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private void measureDirection(int arg0) {
        if (arg0 > mCurrentIndex) {
            mDirection = SildeDirection.RIGHT;

        } else if (arg0 < mCurrentIndex) {
            mDirection = SildeDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }

    // 更新日历视图
    private void updateCalendarView(int arg0) {
        mCalendarCardView = mShowViews[arg0 % mShowViews.length];
        CustomDate mCustomDate = mCalendarCardView.getSelectDate();

        if (mDirection == SildeDirection.RIGHT) {
            mCalendarCardView.rightSlide();
        } else if (mDirection == SildeDirection.LEFT) {
            if (!checkIsPastDate(mCustomDate)) {//检查是否是已经过去的日期
                mCalendarCardView.leftSlide();
            } else {
                if (mViewPager.getLeft() < 0) {
                    mViewPager.removeViewAt(mViewPager.getLeft());
                }
            }

        }
        mDirection = SildeDirection.NO_SILDE;


//        tv_title.setText(mCustomDate.year + "-" + mCustomDate.month);//第一次初始化
        tv_title.setText(DateDFUtils.getSystemFormatTitleTime(mCustomDate.year + "-" + mCustomDate.month + "-" + mCustomDate.day));

    }


    //检测当前日历显示日期是否是已经过去的日期
    private Boolean checkIsPastDate(CustomDate date) {
        if (date.year < DateUtil.getYear()) {
            return true;
        } else if (date.year > DateUtil.getYear()) {
            return false;
        } else {
            if (date.month <= DateUtil.getMonth()) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void checkIsHasPastDate(List<String> list) {
        if (list != null) {
            if (list.size() == 0) {

            } else if (list.size() == 1) {
                String date = list.get(0);
                if (checkPastDate(date)) {//是过期日期
                    list.clear();
                    list.add(DateDFUtils.getNowTime());//添加今天

                }

            } else if (list.size() == 2) {
                String firstDate = list.get(0);
                String lastDate = list.get(1);
                if (null != firstDate && !"".equals(firstDate) && null != lastDate && !"".equals(lastDate)) {
                    if (firstDate.equals(lastDate)) {//移除一个相同的
                        if (checkPastDate(firstDate)) {
                            list.clear();
                        }
                    } else {
                        if (checkPastDate(firstDate)) {
                            if (checkPastDate(lastDate)) {
                                list.clear();
                            } else {
                                list.remove(0);
                                list.add(0, DateDFUtils.getNowTime());
                            }
                        }
                    }
                } else if (null != firstDate && !"".equals(firstDate)) {
//                    list.remove(1);
                } else if (null != lastDate && !"".equals(lastDate)) {
//                    list.remove(0);
                }


            }
        }
    }

    private List<String> checkLength(List<String> list) {
        if (list != null && list.size() == 2) {
            String first = DateDFUtils.getSplitDate(list.get(0));
            String last = DateDFUtils.getSplitDate(list.get(1));
            list.clear();
            list.add(first);
            list.add(last);

        }
        return list;

    }

    private boolean checkPastDate(String date) {
        if (null != date && !"".equals(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String today = DateDFUtils.getNowTime();

            Date mDate = null;
            Date mToday = null;
            try {
                mDate = format.parse(date);
                mToday = format.parse(today);
                long diff = mDate.getTime() - mToday.getTime();
                if (diff >= 0) {//今天也行
                    return false;
                } else {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return false;

    }

    /*
    * 点击ok的监听回调。返回当前dialog选中的时间
    * */
    public interface OnClickSureListenter {
        void getSelectDate(ArrayList<String> list);
    }

    public void setOnClickSureListenter(OnClickSureListenter listenter) {
        okListener = listenter;
    }

    /*
    * 通知当前CalendarCard 清除缓存数据
    * */
    public interface OnNoticeClearDateListener {
        void clearSelDate();
    }

    public void setOnNoticeClearDateListener(OnNoticeClearDateListener listener) {
        mClearDateListener = listener;
    }

    public interface OnLeftDeteListener {
        void deleteLeftDate();
    }

    public void setOnLeftDeteListener(OnLeftDeteListener listener) {
        mOnLeftDeteListener = listener;
    }

    public interface OnRightDeteListener {
        void deleteRightDate();
    }

    public void setOnRightDeteListener(OnRightDeteListener listener) {
        mOnRightDeteListener = listener;
    }


}
