package com.vicryf.pla.bookapp;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import info.hoang8f.widget.FButton;



public class Buttons {

  private Context mCtx;
  private BookInfoActivity parent;

  private FButton butTop;
  private FButton butBot;

  public Buttons(Context ctx){
    mCtx = ctx;

    parent = (BookInfoActivity) ctx;

    butTop = ((BookInfoActivity) ctx).findViewById(R.id.addCollection);
    butBot = ((BookInfoActivity) ctx).findViewById(R.id.addWishlist_read);

  }

  public FButton add(){

    butTop.setText(R.string.AddtoCollection);
    butTop.setButtonColor(mCtx.getResources().getColor(R.color.green800));
    butTop.setShadowColor(mCtx.getResources().getColor(R.color.green900));
    butTop.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        remove();
        parent.addBook();
      }
    });
    return butTop;
  }

  public FButton remove(){
    butTop.setText(R.string.RemovefromCollection);
    butTop.setButtonColor(mCtx.getResources().getColor(R.color.red400));
    butTop.setShadowColor(mCtx.getResources().getColor(R.color.red600));
    butTop.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        add();
        parent.removeBook();
      }
    });
    return butTop;
  }

  public FButton wishAdd(){
    butBot.setText(R.string.AddtoWishlist);
    butBot.setButtonColor(mCtx.getResources().getColor(R.color.red400));
    butBot.setShadowColor(mCtx.getResources().getColor(R.color.red600));
    butBot.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        wishRemove();
        parent.wishlistAddBook();
      }
    });
    return butBot;
  }

  public FButton wishRemove(){
    butBot.setText(R.string.RemovefromWishlist);
    butBot.setButtonColor(mCtx.getResources().getColor(R.color.red400));
    butBot.setShadowColor(mCtx.getResources().getColor(R.color.red600));
    butBot.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        wishAdd();
        parent.wishlistRemoveBook();
      }
    });
    return butBot;
  }

  public FButton markRead(){
    butBot.setText(R.string.MarkAsRead);
    butBot.setButtonColor(mCtx.getResources().getColor(R.color.fbutton_color_belize_hole));
    butBot.setShadowColor(mCtx.getResources().getColor(R.color.buttonShadow));
    butBot.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        markNotRead();
        parent.addReadBook();
      }
    });
    return butBot;
  }

  public FButton markNotRead(){
    butBot.setText(R.string.MarkAsNotRead);
    butBot.setButtonColor(mCtx.getResources().getColor(R.color.fbutton_color_belize_hole));
    butBot.setShadowColor(mCtx.getResources().getColor(R.color.buttonShadow));
    butBot.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        markRead();
        parent.removeReadBook();
      }
    });
    return butBot;
  }

  public void swapToWish(boolean isInWishlist){
    if(isInWishlist){
      wishRemove();
      //layoutBot.addView(wishRemove);
    }else{
      wishAdd();
      //layoutBot.addView(wishAdd);
    }
  }

  public void swapToRead(boolean isRead){
    if(isRead){
      markNotRead();
    }else {
      markRead();
    }
  }


}
