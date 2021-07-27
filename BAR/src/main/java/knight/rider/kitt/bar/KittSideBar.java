package knight.rider.kitt.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import knight.rider.kitt.bar.listener.OnSelectLetterListener;

public class KittSideBar extends View {

    // 26个字母+特殊字符
    public static char[] mLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', '#'};

    // 选中的位置
    private int mChoose = -1;

    // 监听
    private OnSelectLetterListener mOnSelectLetterListener;

    // 对话框、内容
    private final AlertDialog mDialog;
    private final View mContentView;

    // 消息推送
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0 && mDialog != null && mDialog.isShowing())
                mDialog.dismiss();
        }
    };

    private final List<Character> mContainLetters = new ArrayList<>();

    public KittSideBar(Context context) {
        this(context, null);
    }

    public KittSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceType")
    public KittSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // dialog实际view
        mContentView = LayoutInflater.from(context).inflate(R.layout.kitt_slide_bar_letter_dialog, null);

        // 自定义无 背景层
        mDialog = new AlertDialog.Builder(context, R.style.KittDialogNoBackgroundDimStyle)
                .create();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();
        // 获取每一个字母的高度
        float letterHeight = 1.0f * height / mLetters.length;

        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(KittBarUtils.dip2px(getContext(), 10));

        for (int i = 0; i < mLetters.length; i++) {
            char letter = mLetters[i];
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(String.valueOf(letter)) / 2;

            if (i == mChoose && mContainLetters.contains(letter)) {
                // 包含
                paint.setColor(Color.parseColor("#0a59f7"));
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                paint.setColor(Color.parseColor("#666666"));
                paint.setTypeface(Typeface.DEFAULT);
            }

            // 将文字用一个矩形包裹，进而算出文字的长和宽
            Rect bounds = new Rect();
            paint.getTextBounds(String.valueOf(letter), 0, String.valueOf(letter).length(), bounds);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            // 计算高度
            int y = (int) (letterHeight * i + (letterHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top);
            canvas.drawText(String.valueOf(letter), xPos, y, paint);

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        int pos = (int) (y / getHeight() * mLetters.length);

        if (y < 0)
            pos = 0;

        if (y > getHeight())
            pos = mLetters.length - 1;

        if (pos == mChoose)
            return true;

        if (isPosInContainLetters(pos))
            mChoose = pos;

        // 重绘
        invalidate();


        // 支持展示，所划过的字符
        showDialog(String.valueOf(mLetters[pos]));

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (mOnSelectLetterListener != null)
                    mOnSelectLetterListener.onSelect(pos, mLetters[pos]);
                break;
        }
        return true;

    }

    // 内部弹窗使用
    private void showDialog(String msg) {

        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0, 500);

        mDialog.show();
        mDialog.setContentView(mContentView);

        ((TextView) mContentView.findViewById(R.id.kitt_slide_bar_tv)).setText(TextUtils.isEmpty(msg) ? "" : msg);

        Window window = mDialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

    }

    /**
     * 选择监听的事件
     */
    public final void setOnSelectLetterListener(OnSelectLetterListener onSelectLetterListener) {
        this.mOnSelectLetterListener = onSelectLetterListener;
    }

    /**
     * 设置选中的位置
     */
    public final void setChoosePosition(int pos) {

        if (pos >= mLetters.length)
            pos = mLetters.length - 1;

        if (pos < 0)
            pos = 0;

        if (mChoose != pos) {
            mChoose = pos;
            invalidate();
        }

    }


    /**
     * 设置数据源所包含的字符
     *
     * @param topItemChar the initials of the top visible Item
     */
    public final void setContainLetters(List<Character> containLetters, char topItemChar) {


        // 添加到集合
        mContainLetters.clear();
        if (containLetters != null) {

            for (int i = 0; i < containLetters.size(); i++) {

                char cVal = containLetters.get(i);

                // 转大写
                if (cVal >= 'a' && cVal <= 'z')
                    cVal -= 32;
                containLetters.set(i, cVal);
            }

            mContainLetters.addAll(containLetters);
        }

        if (mContainLetters.size() == 0) {
            mChoose = -1;
            invalidate();
            return;
        }

        if (topItemChar >= 'a' && topItemChar <= 'z')
            topItemChar -= 32;

        mChoose = -1;

        for (int i = 0; i < mLetters.length; i++) {
            if (mLetters[i] == topItemChar) {
                mChoose = i;
                break;
            }
        }

        invalidate();

    }


    // 当前滑动到的字符是否在用户包含的字符里
    private boolean isPosInContainLetters(int pos) {
        return mContainLetters.contains(mLetters[pos]);
    }
}
