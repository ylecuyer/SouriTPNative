package fr.ylecuyer.souritp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public class EditTextWithButton extends EditText implements OnTouchListener {

    public interface ButtonListener {
        void buttonClicked();
    }

    public void setButtonListener(ButtonListener listener) {
        this.listener = listener;
    }

    private Drawable buttonDrawable;
    private ButtonListener listener;

    public EditTextWithButton(Context context) {
        super(context);
        init();
    }

    public EditTextWithButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.l = l;
    }

    private OnTouchListener l;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - buttonDrawable.getIntrinsicWidth());
            if (tappedX) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (listener != null) {
                        listener.buttonClicked();
                    }
                }
                return true;
            }
        }
        if (l != null) {
            return l.onTouch(v, event);
        }
        return false;
    }

    private void init() {
        buttonDrawable = getCompoundDrawables()[2];
        if (buttonDrawable == null) {
            buttonDrawable = getResources().getDrawable(R.drawable.ic_search_black_18dp);
        }
        buttonDrawable.setBounds(0, 0, buttonDrawable.getIntrinsicWidth(), buttonDrawable.getIntrinsicHeight());
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], buttonDrawable, getCompoundDrawables()[3]);
        super.setOnTouchListener(this);
    }
}