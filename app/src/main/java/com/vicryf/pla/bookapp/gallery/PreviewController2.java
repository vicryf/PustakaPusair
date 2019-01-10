package com.vicryf.pla.bookapp.gallery;

//import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import com.vicryf.pla.bookapp.Book;
import com.vicryf.pla.bookapp.MyApp;
import com.vicryf.pla.bookapp.grid.GridAdapter2;
import com.vicryf.pla.bookapp.grid.WrapContentGridLayoutManager;

import java.util.ArrayList;




public class PreviewController2{

  private static GridAdapter2 gridAdapter;
  private RecyclerView recyclerView;
  private WrapContentGridLayoutManager glm;


  public PreviewController2(RecyclerView rv, final GalleryBackend parent){

    recyclerView = rv;

    glm = new WrapContentGridLayoutManager(
        parent,
        calculateNoOfColumns(parent)
    );
    recyclerView.setLayoutManager(glm);

  }

  public void setData(ArrayList<Book> books){

    if(gridAdapter!=null){
      final int oldSize = gridAdapter.getDataSet().size();
      gridAdapter.getDataSet().clear();
      gridAdapter.notifyItemRangeChanged(oldSize-1,0);
      //gridAdapter.notifyDataSetChanged();
      //esl.resetState();
      recyclerView.scrollToPosition(0);

      gridAdapter.getDataSet().addAll(books);
      gridAdapter.notifyItemRangeChanged(0,books.size()-1);
      //gridAdapter.notifyDataSetChanged();
            /*
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    recyclerView.setAdapter(gridAdapter);
                    parent.showGrid();
                }
            }, 1500);
            */
      recyclerView.setAdapter(gridAdapter);

      recyclerView.scrollToPosition(0);
    }else{
      PreviewController2.gridAdapter = new GridAdapter2(this, MyApp.getInstance().galleryBackend,books);
      //Handler handler = new Handler();
            /*
            handler.postDelayed(new Runnable() {
                public void run() {
                    recyclerView.setAdapter(gridAdapter);
                    parent.showGrid();
                }
            }, 1500);
            */
      recyclerView.setAdapter(gridAdapter);

    }


  }
/*
  private void loadNextDataFromApi(int page) {
    //MainMenu.loadingData = true;
    MyApp.getInstance().galleryBackend.requestMoreResults();
  }

  public void acceptResponseFromMainThread(int newDataSize , ArrayList<Book> newData){
    final int curSize = gridAdapter.getItemCount();
    gridAdapter.getDataSet().addAll(newData);
    final int finSize = gridAdapter.getItemCount();

    recyclerView.post(new Runnable() {
      @Override
      public void run() {
        gridAdapter.notifyItemRangeInserted(curSize, finSize);
        MainMenu.loadingData = false;
      }
    });
  }
*/
  public GalleryBackend getParent() {
    return MyApp.getInstance().galleryBackend;
  }

  private static int calculateNoOfColumns(Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
    int noOfColumns = (int) (dpWidth / 120);
    return noOfColumns;
  }

  public int getFirstVisibleItem(){
    return glm.findFirstVisibleItemPosition();
  }

  public void scrollToVisibleItem(int firstVisible){

    gridAdapter.notifyItemRangeChanged(0,0);
    gridAdapter.notifyItemRangeChanged(0,gridAdapter.getItemCount());
    glm.scrollToPosition(firstVisible);
  }

  public WrapContentGridLayoutManager getLayoutManager(){
    return glm;
  }
}