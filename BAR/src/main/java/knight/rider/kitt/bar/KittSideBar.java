package knight.rider.kitt.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AlertDialog;

import java.util.HashSet;
import java.util.Set;

import knight.rider.kitt.bar.listener.OnTouchSideListener;

public class KittSideBar extends View {

    // 26个字母+特殊字符
    public static char[] mLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', '#'};

    // 选中的位置
    private int mChoose = -1;
    // 提示的选中位置
    private int mNotice = -1;

    // 监听
    private OnTouchSideListener mOnTouchSideListener;

    // 对话框、内容
    private final AlertDialog mDialog;
    private final View mContentView;

    // 提示规则
    private final int noticeRule;
    // 提示背景
    private final Drawable mNoticeDrawable;

    // 消息推送
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0 && mDialog != null && mDialog.isShowing())
                mDialog.dismiss();
        }
    };

    // 包含字母集合
    private final Set<Character> mContainLetters = new HashSet<>();

    // 文字大小
    private final float mSideSize;
    private float mNoticeSize;
    // 提示框宽高
    private final float mNoticeWidth;
    private final float mNoticeHeight;
    // 文字颜色
    private final int mSideColor;
    private final int mSideSelectColor;
    private int mNoticeColor;


    public KittSideBar(Context context) {
        this(context, null);
    }

    public KittSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceType")
    public KittSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KittSideBar, defStyleAttr, 0);

        noticeRule = array.getInt(R.styleable.KittSideBar_ui_show_letter_notice, 0);
        mSideSize = array.getDimension(R.styleable.KittSideBar_ui_side_letter_textSize, dip2px(10));
        mNoticeSize = array.getDimension(R.styleable.KittSideBar_ui_notice_letter_textSize, dip2px(30));

        mNoticeWidth = array.getDimension(R.styleable.KittSideBar_ui_notice_width, dip2px(80));
        mNoticeHeight = array.getDimension(R.styleable.KittSideBar_ui_notice_height, dip2px(80));

        mSideColor = array.getColor(R.styleable.KittSideBar_ui_side_letter_color, Color.parseColor("#666666"));
        mSideSelectColor = array.getColor(R.styleable.KittSideBar_ui_side_letter_select_color, Color.parseColor("#0a59f7"));
        mNoticeColor = array.getColor(R.styleable.KittSideBar_ui_notice_letter_color, Color.parseColor("#FFFFFF"));
        setNoticeColor(mNoticeColor);

        mNoticeDrawable = array.getDrawable(R.styleable.KittSideBar_ui_notice_background);

        array.recycle();

        // dialog实际view
        mContentView = LayoutInflater.from(context).inflate(R.layout.kitt_slide_bar_letter_dialog, null);

        // 自定义无 背景层
        mDialog = new AlertDialog.Builder(context, R.style.KittDialogNoBackgroundDimStyle)
                .create();
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();
        // 获取每一个字母的高度
        float letterHeight = 1.0f * height / mLetters.length;

        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(mSideSize);

        for (int i = 0; i < mLetters.length; i++) {
            char letter = mLetters[i];
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(String.valueOf(letter)) / 2;

            if (i == mChoose && mContainLetters.contains(letter)) {
                // 包含
                paint.setColor(mSideSelectColor);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                paint.setColor(mSideColor);
                paint.setTypeface(Typeface.DEFAULT);
            }

            // 将文字用一个矩形包裹，进而算出文字的长和宽
            @SuppressLint("DrawAllocation") Rect bounds = new Rect();
            paint.getTextBounds(String.valueOf(letter), 0, String.valueOf(letter).length(), bounds);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            // 计算高度
            int y = (int) (letterHeight * i + (letterHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top);
            canvas.drawText(String.valueOf(letter), xPos, y, paint);

        }

    }

    @Override
    public final boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getAction();

        // 只监听按下以及移动的事件
        if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_MOVE)
            return true;


        final float y = event.getY();
        int pos = (int) (y / getHeight() * mLetters.length);

        // 只要一开始触摸点在控件内，移动出控件区域也可监听到

        if (y < 0)
            pos = 0;

        if (y > getHeight())
            pos = mLetters.length - 1;

        // 监听到的事件位置与上一次一致
        if (pos == mChoose)
            return true;

        // 如果实际数据里包含这个首字母才更改位置
        if (isPosInContainLetters(pos))
            mChoose = pos;

        // 重绘
        invalidate();

        showDialog(pos);

        if (mOnTouchSideListener != null && isPosInContainLetters(pos))
            mOnTouchSideListener.onTouch(mLetters[pos], mLetters[pos] >= 'A' && mLetters[pos] <= 'Z' ? (char) (mLetters[pos] + 32) : mLetters[pos]);

        return true;

    }


    /******************私有方法**********************/

    private float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    // 内部弹窗使用
    private void showDialog(int pos) {

        if (noticeRule == 8)
            return;

        if (noticeRule == 4 && !isPosInContainLetters(pos))
            return;

        if (pos == mNotice)
            return;
        else
            mNotice = pos;


        String msg = String.valueOf(mLetters[pos]);

        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0, 500);

        mDialog.show();
        mDialog.setContentView(mContentView);


        TextView t = ((TextView) mContentView.findViewById(R.id.kitt_side_bar_tv));
        FrameLayout f = ((FrameLayout) mContentView.findViewById(R.id.kitt_side_layout));

        t.setText(TextUtils.isEmpty(msg) ? "" : msg);
        t.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNoticeSize);
        t.setTextColor(mNoticeColor);

        ViewGroup.LayoutParams params = t.getLayoutParams();
        params.width = (int) mNoticeWidth;
        params.height = (int) mNoticeHeight;
        t.setLayoutParams(params);

        if (mNoticeDrawable != null)
            f.setBackground(mNoticeDrawable);


        Window window = mDialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

    }

    // 当前滑动到的字符是否在用户包含的字符里
    private boolean isPosInContainLetters(int pos) {
        return mContainLetters.contains(mLetters[pos]);
    }

    /**
     * 选择监听的事件
     */
    public final void setOnTouchSideListener(OnTouchSideListener onTouchSideListener) {
        this.mOnTouchSideListener = onTouchSideListener;
    }

    /**
     * 设置选中的位置
     */
    public final void setChoosePosition(char topItemChar) {

        if (topItemChar >= 'a' && topItemChar <= 'z')
            topItemChar -= 32;

        int oldChoose = mChoose;

        // 顶部char无效时使用
        mChoose = -1;


        for (int i = 0; i < mLetters.length; i++) {
            if (mLetters[i] == topItemChar) {
                mChoose = i;
                break;
            }
        }

        if (mChoose != oldChoose)
            invalidate();

    }


    /**
     * 设置数据源所包含的字符
     *
     * @param topItemChar the initials of the top visible Item
     */
    public final void setContainLetters(Set<Character> containLetters, char topItemChar) {


        // 添加到集合
        mContainLetters.clear();
        if (containLetters != null) {

            for (char cVal : containLetters) {

                // 转大写
                if (cVal >= 'a' && cVal <= 'z')
                    cVal -= 32;
                mContainLetters.add(cVal);
            }
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


    /**
     * 设置提示文字的大小，默认字体单位为px
     *
     * @param size The scaled pixel size.
     */
    public final KittSideBar setNoticeLetterTextSize(float size) {
        mNoticeSize = size;
        return this;
    }

    /**
     * 设置搜标题颜色
     *
     * @param color A color value in the form 0xAARRGGBB.
     */
    public final KittSideBar setNoticeColor(@ColorInt int color) {
        mNoticeColor = color;
        return this;
    }


    /**
     * 设置标题颜色
     *
     * <ul>
     *   <li><code>#RRGGBB</code></li>
     *   <li><code>#AARRGGBB</code></li>
     * </ul>
     * <p>
     * The following names are also accepted: <code>red</code>, <code>blue</code>,
     * <code>green</code>, <code>black</code>, <code>white</code>, <code>gray</code>,
     * <code>cyan</code>, <code>magenta</code>, <code>yellow</code>, <code>lightgray</code>,
     * <code>darkgray</code>, <code>grey</code>, <code>lightgrey</code>, <code>darkgrey</code>,
     * <code>aqua</code>, <code>fuchsia</code>, <code>lime</code>, <code>maroon</code>,
     * <code>navy</code>, <code>olive</code>, <code>purple</code>, <code>silver</code>,
     * and <code>teal</code>.</p>
     */
    public final KittSideBar setNoticeColor(@Size(min = 1) String colorString) {
        mNoticeColor = Color.parseColor(colorString);
        return this;
    }


    /**
     * 获得当前目标的字母
     */
    public final char getTargetLetter() {
        return mLetters[mChoose];
    }
}
