package com.example.haoyuban111.mubanapplication.ui.view;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CustomDragLayout extends FrameLayout {

	private ViewDragHelper mHelper;
	private ViewGroup mLeftContent;
	private ViewGroup mMainContent;
	private int mHeight;
	private int mWidth;
	private int mRange;

	public static enum Status {
		Close, Open, Draging
	}

	private Status status = Status.Close;

	public interface OnDragChangeListener {
		void onClose();

		void onOpen();

		void onDraging(float percent);
	}

	private OnDragChangeListener onDragChangeListener;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public OnDragChangeListener getOnDragChangeListener() {
		return onDragChangeListener;
	}

	public void setOnDragChangeListener(
			OnDragChangeListener onDragChangeListener) {
		this.onDragChangeListener = onDragChangeListener;
	}

	public CustomDragLayout(Context context) {
		this(context, null);

	}

	public CustomDragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomDragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mHelper = ViewDragHelper.create(this, 1.0f, callback);
	}

	ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
		@Override
		public boolean tryCaptureView(View child, int pointerId) {

			return true;
		}

		// 2. ������ק�ķ�Χ. ����һ�� >0 ��ֵ, �����˶�����ִ��ʱ��, ˮƽ�����Ƿ���Ա�����
		@Override
		public int getViewHorizontalDragRange(View child) {

			return mRange;
		}

		// 3. ������Viewˮƽ�����λ��. ��ʱ��û�з����������ƶ�.
		// ����ֵ������View�����ƶ�����λ��left
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			int oldLeft = mMainContent.getLeft();
			if (child == mMainContent) {
				left = fixLeft(left);
			}
			return left;

		};

		// ���ؼ�λ�ñ仯ʱ����,������:���涯��,״̬����,�¼��ص�
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			// left���µ�ˮƽ�����λ��
			// dx �ոշ�����ˮƽ�仯��

			if (changedView == mLeftContent) {
				// ����ƶ����������,�Ż�ԭ��λ��
				mLeftContent.layout(0, 0, 0 + mWidth, 0 + mHeight);
				// ������ƶ��仯��dxת�ݸ������
				int newLeft = mMainContent.getLeft() + dx;
				// ������ߵ�ֵ
				newLeft = fixLeft(newLeft);
				mMainContent.layout(newLeft, 0, newLeft + mWidth, 0 + mHeight);
			}
			dispatchDragEvent();
			// Ϊ�˼��ݵͰ汾, �ֶ��ػ������������.
			invalidate();
		}

		// �������ֺ�Ҫ������
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			// releasedChild���ͷŵĺ���
			// xvel ˮƽ�����ٶ�,����Ϊ-.����Ϊ+
			if (xvel == 0 && mMainContent.getLeft() > mRange * 0.5f) {
				open();
			} else if (xvel > 0) {
				open();
			} else {
				close();
			}

		}

		@Override
		public void onViewDragStateChanged(int state) {
			super.onViewDragStateChanged(state);
		}

		/**
		 * �ַ���ק�¼�,���涯��,����״̬
		 */
		protected void dispatchDragEvent() {
			// 0.0~1.0
			float percent = mMainContent.getLeft() * 1.0f / mRange;

			if (onDragChangeListener != null) {
				onDragChangeListener.onDraging(percent);
			}
			// ����״̬
			Status lastStatus = status;
			status = updateStatus(percent);
			if (lastStatus != status && onDragChangeListener != null) {
				if (status == Status.Close) {
					onDragChangeListener.onClose();
				} else if (status == Status.Open) {
					onDragChangeListener.onOpen();
				}
			}
			// ִ�ж���
			animViews(percent);

		}

		/**
		 * ����״̬
		 * 
		 * @param percent
		 *            ��ǰ����ִ�еİٷֱ�
		 * @return
		 */
		private Status updateStatus(float percent) {
			if (percent == 0) {
				return Status.Close;
			} else if (percent == 1) {
				return Status.Open;
			}
			return Status.Draging;
		}

		private void animViews(float percent) {
			// �����:���Ŷ���,ƽ�ƶ���,͸���ȶ���
			// ���Ŷ�������0.0 -> 1.0 >>> 0.0 -> 0.5 >>>0.5 -> 1.0
			// mLeftContent.setScaleX(percent * 0.5f + 0.5f);
			// mLeftContent.setScaleY(percent * 0.5f + 0.5f);
			ViewHelper.setScaleX(mLeftContent, evaluate(percent, 0.5f, 1.0f));
			ViewHelper.setScaleY(mLeftContent, evaluate(percent, 0.5f, 1.0f));

			// ƽ�ƶ��� -mWidth / 2.0f-->0
			ViewHelper.setTranslationX(mLeftContent,
					evaluate(percent, -mWidth / 2.0f, 0));

			// ͸���ȶ��� 0.2f -> 1.0
			ViewHelper.setAlpha(mLeftContent, evaluate(percent, 0.2f, 1.0f));

			// �����,���Ŷ��� 1.0 -> 0.8
			ViewHelper.setScaleX(mMainContent, evaluate(percent, 1.0f, 0.8f));
			ViewHelper.setScaleY(mMainContent, evaluate(percent, 1.0f, 0.8f));

			// �������ȱ仯,�ɺڵ�͸��
			getBackground().setColorFilter(
					(Integer) evaluateColor(percent, Color.BLACK,
							Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
		}

		/**
		 * ����ʵ�ֵı���,��ʼֵ
		 */
		private float evaluate(float fraction, Number startValue,
				Number endValue) {
			float startFloat = startValue.floatValue();
			return startFloat + fraction * (endValue.floatValue() - startFloat);
		}

		/**
		 * ����ɫ
		 * 
		 * @param fraction
		 *            ����
		 * @param startValue
		 *            ��ʼ��ɫ
		 * @param endValue
		 *            ������ɫ
		 * @return
		 */
		public Object evaluateColor(float fraction, Object startValue,
				Object endValue) {
			int startInt = (Integer) startValue;
			int startA = (startInt >> 24) & 0xff;
			int startR = (startInt >> 16) & 0xff;
			int startG = (startInt >> 8) & 0xff;
			int startB = startInt & 0xff;

			int endInt = (Integer) endValue;
			int endA = (endInt >> 24) & 0xff;
			int endR = (endInt >> 16) & 0xff;
			int endG = (endInt >> 8) & 0xff;
			int endB = endInt & 0xff;

			return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
					| (int) ((startR + (int) (fraction * (endR - startR))) << 16)
					| (int) ((startG + (int) (fraction * (endG - startG))) << 8)
					| (int) ((startB + (int) (fraction * (endB - startB))));
		}

	};

	/**
	 * ����λ��
	 * 
	 * @param left
	 * @return
	 */
	private int fixLeft(int left) {
		if (left < 0) {
			System.out.println("fixLeft" + left);
			return 0;
		} else if (left > mRange) {
			System.out.println("fixLeft + left" + left);
			System.out.println("fixLeft + mRange" + mRange);

			return mRange;
		}
		return left;
	}

	/**
	 * �������
	 */
	public void open() {
		open(true);
	}

	public void open(boolean isSmooth) {
		int finalLeft = mRange;
		System.out.println("open " + mRange);
		if (isSmooth) {
			// ����һ��ƽ������
			if (mHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)) {
				// �ػ涯��,һ��Ҫ����View���ڵ�����
				ViewCompat.postInvalidateOnAnimation(this);
			} else {
				mMainContent.layout(finalLeft, 0, finalLeft + mWidth,
						0 + mHeight);
			}
		}
	}

	public void close() {
		close(true);
	}

	public void close(boolean isSmooth) {
		int finalLeft = 0;
		if (isSmooth) {
			// ��ƽ������
			// 1. ����һ��ƽ������.
			if (mHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)) {
				// �����ǰλ�ò���ָ��������λ��. ����true
				// ��Ҫ�ػ����, һ��Ҫ�� ��View ���ڵ�����
				ViewCompat.postInvalidateOnAnimation(this);
			}

		} else {
			System.out.println("open");
			mMainContent.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
		}
	}

	// 2. ά�ֶ����ļ���, ��Ƶ�ʵ���.
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mHelper.continueSettling(true)) {
			// �����ǰλ�û�û���ƶ�������λ��. ����true.��Ҫ�����ػ����
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mHelper.shouldInterceptTouchEvent(ev);

	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		try { // ??????????
			mHelper.processTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// ���ؼ��ߴ�仯��ʱ�����
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mHeight = getMeasuredHeight();
		mWidth = getMeasuredWidth();

		mRange = (int) (mWidth * 0.6f);
		System.out.println("onSizeChanged + mRange" + mRange);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// ���뽡׳��,���������ӿؼ�
		if (getChildCount() < 2) {
			throw new IllegalStateException();
		}

		if (!(getChildAt(0) instanceof ViewGroup)
				|| !((getChildAt(1)) instanceof ViewGroup)) {
			throw new IllegalArgumentException(
					"Child must be an instance of ViewGroup . ���ӱ�����ViewGroup������");
		}

		mLeftContent = (ViewGroup) getChildAt(0);
		mMainContent = (ViewGroup) getChildAt(1);
	}

}
