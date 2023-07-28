package com.gcu.base.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gcu.base.R;

public class InputEditText extends androidx.appcompat.widget.AppCompatEditText {

    private Drawable mRightDrawable;

    public InputEditText(@NonNull Context context) {
        super(context);
        init(context);
    }

    public InputEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化EditText
     */
    private void init(Context context) {
        mRightDrawable = context.getResources().getDrawable(R.drawable.delete_icon);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (TextUtils.isEmpty(text)) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mRightDrawable, null);
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    /**
     * 处理删除按钮的点击事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[2];
            if (drawable != null) {
                setText("");
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
        return super.onTouchEvent(event);
    }
}
