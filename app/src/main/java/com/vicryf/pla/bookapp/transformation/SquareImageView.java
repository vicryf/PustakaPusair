package com.vicryf.pla.bookapp.transformation;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Xrhstos on 4/13/2018.
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {
  public SquareImageView(Context context) {
    super(context);
  }

  public SquareImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
  }
}