package com.vicryf.pla.bookapp;

import android.app.Application;
import android.content.Context;
import com.vicryf.pla.bookapp.gallery.GalleryBackend;
import com.vicryf.pla.bookapp.main_menu.MainMenu;



public class MyApp extends Application {
  private static MyApp instance;
  public MainMenu mainMenu;
  public GalleryBackend galleryBackend;
  public BookInfoActivity bookInfoActivity;
  public ManualAddMenu manualAddMenu;

  public static MyApp getInstance() {
    return instance;
  }

  public static Context getContext(){
    return instance;
    // or return instance.getApplicationContext();
  }

  @Override
  public void onCreate() {
    instance = this;
    super.onCreate();
  }
}
