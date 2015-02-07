package com.getbase.floatingactionbutton;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Floating menu with changing icons on the main button.
 * @author Wijnand
 */
public class ChangingIconFloatingActionsMenu extends FloatingActionsMenu {

    private int mCollapsedIconResourceId;
    private int mExpandedIconResourceId;

    public ChangingIconFloatingActionsMenu(Context context) {
        super(context);
    }

    public ChangingIconFloatingActionsMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangingIconFloatingActionsMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init(Context context, AttributeSet attributeSet) {
        super.init(context, attributeSet);

        TypedArray attr = context
                .obtainStyledAttributes(attributeSet, R.styleable.ChangingIconFloatingActionsMenu, 0, 0);
        mCollapsedIconResourceId = attr.getResourceId(R.styleable.ChangingIconFloatingActionsMenu_fab_collapsedIcon, 0);
        mExpandedIconResourceId = attr.getResourceId(R.styleable.ChangingIconFloatingActionsMenu_fab_expandedIcon, 0);

        if (mCollapsedIconResourceId == 0 || mExpandedIconResourceId == 0) {
            throw new IllegalStateException("Both 'fab_collapsedIcon' and 'fab_expandedIcon' attributes are required!");
        }

        attr.recycle();
        createAddButton(context);
    }

    @Override
    protected AddFloatingActionButton constructAddButton(Context context) {
        ChangingIconFloatingActionButton button = new ChangingIconFloatingActionButton(context);
        button.setColors(mAddButtonColorNormal, mAddButtonColorPressed, mAddButtonStrokeVisible);
        button.setDrawableResources(mCollapsedIconResourceId, mExpandedIconResourceId);

        Interpolator hideInterpolator = new DecelerateInterpolator(3f);
        Interpolator showInterpolator = new DecelerateInterpolator();

        Drawable collapsed = button.getCollapsedDrawable();
        Drawable expanded = button.getExpandedDrawable();
        String property = "alpha";
        int transparent = 0;
        int opaque = 255;

        ObjectAnimator collapsedShowAnimator = ObjectAnimator.ofInt(collapsed, property, transparent, opaque);
        collapsedShowAnimator.setInterpolator(showInterpolator);

        ObjectAnimator collapsedHideAnimator = ObjectAnimator.ofInt(collapsed, property, opaque, transparent);
        collapsedHideAnimator.setInterpolator(hideInterpolator);

        ObjectAnimator expandedShowAnimator = ObjectAnimator.ofInt(expanded, property, transparent, opaque);
        expandedShowAnimator.setInterpolator(showInterpolator);

        ObjectAnimator expandedHideAnimator = ObjectAnimator.ofInt(expanded, property, opaque, transparent);
        expandedHideAnimator.setInterpolator(hideInterpolator);

        mExpandAnimation.play(collapsedHideAnimator);
        mExpandAnimation.play(expandedShowAnimator);
        mCollapseAnimation.play(collapsedShowAnimator);
        mCollapseAnimation.play(expandedHideAnimator);

        return button;
    }

    @Override
    protected void createAddButton(Context context) {
        if (mCollapsedIconResourceId == 0 || mExpandedIconResourceId == 0) {
            return;
        }

        super.createAddButton(context);
    }
}
