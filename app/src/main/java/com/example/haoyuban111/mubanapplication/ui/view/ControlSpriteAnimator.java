package com.example.haoyuban111.mubanapplication.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.haoyuban111.mubanapplication.help_class.ImageHelper;
import com.example.haoyuban111.mubanapplication.help_class.My;


public class ControlSpriteAnimator extends View {

    private int _duration;
    private Animator _animator;
    private Handler _handler = new Handler();
    private Runnable _task;
    private Paint _paint;

    private boolean _isShownOnce;

    public ControlSpriteAnimator(Context context) {
        this(context, null);
    }

    public ControlSpriteAnimator(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ControlSpriteAnimator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Paint getPaint() {
        if (_paint == null) {
            _paint = new Paint();
            _paint.setFlags(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
        return _paint;
    }

    public void setProgressStyle(int resourceId, int rows, int columns, int duration) {
        stop();

        _duration = duration;
        _animator = new Animator(resourceId, rows, columns);
    }

    public void setProgressStyle(ProgressStyleArgs args) {
        setProgressStyle(args.getResourceId(), args.getRows(), args.getColumns(), args.getDuration());
    }

    public void start() {
        if (_task != null) return;

        final int delay = _duration / _animator.getFramesCount();

        _task = new Runnable() {
            @Override
            public void run() {
                _handler.postDelayed(_task, delay);
                if (isShown()) {
                    _isShownOnce = true;
                    invalidate();
                } else {
                    if (_animator != null) {
                        _animator.dispose();
                    }
                    if (_isShownOnce) {
                        _isShownOnce = false;
                        stop();
                    }
                }
            }
        };
        _task.run();
    }

    public void stop() {
        if (_task != null) {
            _handler.removeCallbacks(_task);
            _task = null;
        }
        if (_animator != null) {
            _animator.dispose();
        }
        _isShownOnce = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isShown()) {
            if (_animator != null) {
                _animator.draw(canvas);
            }
        } else {
            if (_animator != null) {
                _animator.dispose();
            }
        }
    }

    private class Animator {

        private Integer _resourceId;
        private Integer _rows;
        private Integer _columns;

        private Bitmap _bitmap;
        private Rect[] _frames;
        private int _index = 0;

        Animator(int resourceId, int rows, int columns) {

            if (rows < 1 || columns < 1) throw new IllegalArgumentException();

            _resourceId = resourceId;
            _rows = rows;
            _columns = columns;
        }

        private int getFramesCount() {
            if (_rows == null || _columns == null)
                throw new ExceptionInInitializerError();

            return _rows * _columns;
        }

        private void draw(Canvas canvas) {
            checkData();
            if (_bitmap == null) {
                return;
            }

            if (_index == _frames.length) {
                _index = 0;
            }


            canvas.drawBitmap(_bitmap, _frames[_index], prepareRect(_frames[_index], canvas.getClipBounds()), getPaint());
            _index++;
        }

        private Rect prepareRect(Rect source, Rect destination) {
            float prop = Math.min((float) destination.width() / (float) source.width(), (float) destination.height() / (float) source.height());
            float width = source.width() * prop;
            float height = source.height() * prop;
            return new Rect((int) ((destination.width() - width) / 2), (int) ((destination.height() - height) / 2), (int) width, (int) height);
        }

        private void checkData() {
            if (_bitmap == null) {

                if (_resourceId == null || _rows == null || _columns == null)
                    throw new ExceptionInInitializerError();

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inScaled = false;

                int maxWidth = My.Device.getDisplayWidthPx();

                _bitmap = ImageHelper.decodeBitmap(getContext().getResources(), _resourceId, maxWidth, maxWidth);
                if (_bitmap == null) return;

                int columnWidth = _bitmap.getWidth() / _columns;
                int columnHeight = _bitmap.getHeight() / _rows;

                if ((float)_bitmap.getWidth() / columnWidth != _columns ||
                        (float)_bitmap.getHeight() / columnHeight != _rows) {

                    int size = Math.max(columnWidth * _columns, columnHeight * _rows);

                    Bitmap tmpBitmap = _bitmap;
                    _bitmap = ImageHelper.scaleImage(_bitmap, size, size);
                    if (!_bitmap.equals(tmpBitmap)) {
                        tmpBitmap.recycle();
                    }
                }

                int frameHeight = _bitmap.getHeight() / _rows;
                _frames = new Rect[_rows * _columns];

                int k = 0;
                for (int i = 0; i < _rows; i++) {
                    for (int j = 0; j < _columns; j++, k++) {
                        int left = j * columnWidth;
                        int top = i * frameHeight;
                        _frames[k] = new Rect(left, top, left + columnWidth, top + frameHeight);
                    }
                }
            }
        }

        private void dispose() {
            if (_bitmap != null) {
                _bitmap = null;
            }
        }
    }

    public static class ProgressStyleArgs {

        private Integer _resourceId;
        private Integer _rows;
        private Integer _columns;
        private Integer _duration;

        public ProgressStyleArgs() {
        }

        public ProgressStyleArgs(Integer resourceId, Integer rows, Integer columns, Integer duration) {
            _resourceId = resourceId;
            _rows = rows;
            _columns = columns;
            _duration = duration;
        }

        public Integer getResourceId() {
            return _resourceId;
        }

        public ProgressStyleArgs setResourceId(Integer resourceId) {
            _resourceId = resourceId;
            return this;
        }

        Integer getRows() {
            return _rows;
        }

        public ProgressStyleArgs setRows(Integer rows) {
            _rows = rows;
            return this;
        }

        public Integer getColumns() {
            return _columns;
        }

        public ProgressStyleArgs setColumns(Integer columns) {
            _columns = columns;
            return this;
        }

        public Integer getDuration() {
            return _duration;
        }

        public ProgressStyleArgs setDuration(Integer duration) {
            _duration = duration;
            return this;
        }
    }
}
