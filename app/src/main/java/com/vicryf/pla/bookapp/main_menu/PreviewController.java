package com.vicryf.pla.bookapp.main_menu;

//import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import com.vicryf.pla.bookapp.Book;
import com.vicryf.pla.bookapp.MyApp;
import com.vicryf.pla.bookapp.grid.EndlessScrollListener;
import com.vicryf.pla.bookapp.grid.GridAdapter;
import com.vicryf.pla.bookapp.grid.WrapContentGridLayoutManager;
import java.util.ArrayList;


/**
 * Created by philip on 10/4/2018.
 */

public class PreviewController{

    private static GridAdapter gridAdapter;
    private RecyclerView recyclerView;
    private WrapContentGridLayoutManager glm;
    private EndlessScrollListener esl;


    public PreviewController(RecyclerView rv, final MainMenu parent){

        recyclerView = rv;

         glm = new WrapContentGridLayoutManager(
            parent,
            calculateNoOfColumns(parent)
        );
        recyclerView.setLayoutManager(glm);

        esl =new EndlessScrollListener(glm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                loadNextDataFromApi();
            }
        };

        recyclerView.addOnScrollListener(esl);

    }

    public void setData(ArrayList<Book> books){

        if(gridAdapter!=null){
            final int oldSize = gridAdapter.getDataSet().size();
            gridAdapter.getDataSet().clear();
            gridAdapter.notifyItemRangeChanged(oldSize-1,0);
            //gridAdapter.notifyDataSetChanged();
            esl.resetState();
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
            MyApp.getInstance().mainMenu.showGrid();
            recyclerView.setAdapter(gridAdapter);


            recyclerView.scrollToPosition(0);
        }else{
            PreviewController.gridAdapter = new GridAdapter(this,MyApp.getInstance().mainMenu,books);
            /*
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                public void run() {
                    recyclerView.setAdapter(gridAdapter);
                    parent.showGrid();
                }
            }, 1500);
            */
            MyApp.getInstance().mainMenu.showGrid();
            recyclerView.setAdapter(gridAdapter);


        }


    }

    private void loadNextDataFromApi() {
        MainMenu.loadingData = true;
        MyApp.getInstance().mainMenu.showLoading();
        MyApp.getInstance().mainMenu.requestMoreResults();
    }

    public void acceptResponseFromMainThread(ArrayList<Book> newData){
        final int curSize = gridAdapter.getItemCount();
        MyApp.getInstance().mainMenu.showGrid();
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

    public MainMenu getParent() {
        return MyApp.getInstance().mainMenu;
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