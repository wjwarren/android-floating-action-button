package com.getbase.floatingactionbutton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;

/**
 * Action button with a different icon for the collapsed and expanded state.
 * @author Wijnand
 */
public class ChangingIconFloatingActionButton extends AddFloatingActionButton {

    private LayerDrawable mIconDrawable;
    private Drawable mCollapsedDrawable;
    private Drawable mExpandedDrawable;

    public ChangingIconFloatingActionButton(Context context) {
        super(context);
    }

    public ChangingIconFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangingIconFloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    void init(Context context, AttributeSet attributeSet) {
        super.init(context, attributeSet);
        mIconDrawable = null;
    }

    @Override
    Drawable getIconDrawable() {
        if (mIconDrawable == null) {
            return new ColorDrawable(Color.TRANSPARENT);
        } else {
            return mIconDrawable;
        }
    }

    public void setColors(int colorNormal, int colorPressed, boolean showStroke) {
        mPlusColor = 0;
        mColorNormal = colorNormal;
        mColorPressed = colorPressed;
        mStrokeVisible = showStroke;
        updateBackground();
    }

    public void setDrawableResources(int collapsedResourceId, int expandedResourceId) {
        mCollapsedDrawable = getResources().getDrawable(collapsedResourceId);
        mExpandedDrawable = getResources().getDrawable(expandedResourceId);
        mExpandedDrawable.setAlpha(0);

        mIconDrawable = new LayerDrawable(new Drawable[] {mCollapsedDrawable, mExpandedDrawable});
        updateBackground();
    }

    public Drawable getExpandedDrawable() {
        return mExpandedDrawable;
    }

    public Drawable getCollapsedDrawable() {
        return mCollapsedDrawable;
    }
}
