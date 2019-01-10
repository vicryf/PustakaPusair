package com.vicryf.pla.bookapp.grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.vicryf.pla.bookapp.Book;
import com.vicryf.pla.bookapp.R;
import com.vicryf.pla.bookapp.gallery.PreviewController2;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import java.util.ArrayList;



// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class GridAdapter2 extends RecyclerView.Adapter<GridAdapter2.ViewHolder> {

  private Context mContext;
  private final PreviewController2 parentController;
  private ArrayList<Book> data;
  private int lastPosition = -1;
  private Picasso instance;

  // Pass in the contact array into the constructor
  public GridAdapter2(PreviewController2 par, Context c, ArrayList<Book> data){
    parentController = par;
    mContext = c;
    this.data = data;
    instance = Picasso.with(mContext);
    //setHasStableIds(true);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate the custom layout
    View gridInflater = inflater.inflate(R.layout.grid_item_inflater, parent, false);

    // Return a new holder instance
    ViewHolder viewHolder = new ViewHolder(gridInflater);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull GridAdapter2.ViewHolder holder, int position) {
    // Get the data model based on position
    final int pos = holder.getAdapterPosition();
    Book book = data.get(pos);

    setImage(holder.bookView, book.getBookCoverURL(),position,book);
    //holder.bookView.setImageBitmap(book.getBookCover());
    holder.bookView.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        parentController.getParent().bookClick(pos);
      }

    });
    //
    holder.titleView.setText(book.getBookTitle());
    holder.authorView.setText(book.getAuthor()[0]);
    //holder.titleView.setText(position+"");
    //holder.authorView.setText(position+"");
    //holder.bookView.setClipToOutline(true);
    //holder.bookView.setScaleType(ScaleType.FIT_CENTER);

    // Here you apply the animation when the view is bound
    setAnimation(holder.itemView, position);

  }

  /**
   * Here is the key method to apply the animation
   */
  private void setAnimation(View viewToAnimate, int position)
  {
    // If the bound view wasn't previously displayed on screen, it's animated
    if (position > lastPosition)
    {
      Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
      viewToAnimate.startAnimation(animation);
      lastPosition = position;
    }
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemViewType(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  // Provide a direct reference to each of the views within a data item
  // Used to cache the views within the item layout for fast access
  public class ViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    public ImageView bookView;
    public TextView titleView;
    public TextView authorView;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the context from any ViewHolder instance.
      super(itemView);

      bookView = (ImageView) itemView.findViewById(R.id.bookImageTag);
      titleView = (TextView) itemView.findViewById(R.id.bookTitleTag);
      authorView = (TextView) itemView.findViewById(R.id.bookPublisherTag);
    }

  }

  @Override
  public void onViewDetachedFromWindow(final GridAdapter2.ViewHolder holder)
  {
    ((ViewHolder)holder).itemView.clearAnimation();
  }


  private void setImage(final ImageView container, final String url,final int position,final Book book){

    if(url == null){
      container.setImageBitmap(book.getBookCover());
    }else{
      RequestCreator loader = instance.load(url);
      //loader.into((AppCompatImageView) container);
      loader.into(new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
          //container.invalidate();
          container.setImageBitmap(bitmap);
          container.invalidate();
          System.out.println("Picasso called : bitmap Loaded for " +book.getBookTitle());
          if(book.getBookCover() == null){
            book.setBookCover(bitmap);
            System.out.println("Picasso called : bitmap saved in class for " +book.getBookTitle());
          }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
      });
    }

  }

  public ArrayList<Book> getDataSet(){
    return data;
  }
}
