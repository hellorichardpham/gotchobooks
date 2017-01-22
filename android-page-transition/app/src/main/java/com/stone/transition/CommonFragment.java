package com.stone.transition;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.util.Log;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Timer;

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener {
    private ImageView imageView;
    private View address1, address2, address3, address4, address5;
    //private RatingBar ratingBar;
    //private View head1, head2, head3, head4;

    private String imageUrl;
    private String bookTitle;
    private String authorName;
    private String bookIndex;
    private TextView tempAddress4, tempAddress5;
    String[] bookIndexArray = new String[5];
    int indexCounter = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common, null);
        DragLayout dragLayout = (DragLayout) rootView.findViewById(R.id.drag_layout);
        imageView = (ImageView) dragLayout.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        address1 = dragLayout.findViewById(R.id.address1);
        //address2 = dragLayout.findViewById(R.id.address2);
        address3 = dragLayout.findViewById(R.id.address3);
        address4 = dragLayout.findViewById(R.id.address4);
        address5 = dragLayout.findViewById(R.id.address5);
        //ratingBar = (RatingBar) dragLayout.findViewById(R.id.rating);


        final CircularProgressButton circularButton1 = (CircularProgressButton) dragLayout.findViewById(R.id.donateMyOwn);

        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    bookIndexArray[0] = bookIndex;
                    simulateSuccessProgress(circularButton1);
                } else {
                    circularButton1.setProgress(0);
                }
            }
        });

        final CircularProgressButton circularButton2 = (CircularProgressButton) dragLayout.findViewById(R.id.buyBook);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton2.getProgress() == 0) {
                    simulateSuccessProgress(circularButton2);
                    //hyperlink
                    Uri uri = Uri.parse("https://www.amazon.com/s/ref=nb_sb_noss/163-2741974-5054844?url=search-alias%3Daps&field-keywords="
                            + bookTitle+" book"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    circularButton2.setProgress(0);
                }
            }
        });


       /*
        head1 = dragLayout.findViewById(R.id.head1);
        head2 = dragLayout.findViewById(R.id.head2);
        head3 = dragLayout.findViewById(R.id.head3);
        head4 = dragLayout.findViewById(R.id.head4);
*/

        /*
        This is where we are setting the titles of the book and author name
        using the values obtained by bindData()
         */
        dragLayout.setGotoDetailListener(this);
        tempAddress4 = (TextView) dragLayout.findViewById(R.id.address4);
        tempAddress4.setText(bookTitle);

        tempAddress5 = (TextView) dragLayout.findViewById(R.id.address5);
        tempAddress5.setText(authorName);
        return rootView;
    }

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(750);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        System.out.println("I'm in saveArray and the size is: " + array.length);

        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair(imageView, DetailActivity.IMAGE_TRANSITION_NAME),
                new Pair(address1, DetailActivity.ADDRESS1_TRANSITION_NAME),
                //new Pair(address2, DetailActivity.ADDRESS2_TRANSITION_NAME),
                new Pair(address3, DetailActivity.ADDRESS3_TRANSITION_NAME),
                new Pair(address4, DetailActivity.ADDRESS4_TRANSITION_NAME),
                new Pair(address5, DetailActivity.ADDRESS5_TRANSITION_NAME)
                //new Pair(ratingBar, DetailActivity.RATINGBAR_TRANSITION_NAME),
                /*
                new Pair(head1, DetailActivity.HEAD1_TRANSITION_NAME),
                new Pair(head2, DetailActivity.HEAD2_TRANSITION_NAME),
                new Pair(head3, DetailActivity.HEAD3_TRANSITION_NAME),
                new Pair(head4, DetailActivity.HEAD4_TRANSITION_NAME)
                                 */

        );
        Log.d("Book Title", DetailActivity.ADDRESS4_TRANSITION_NAME);

        /*This is where we send information about the book cover to display, title, and author
        to the detailed activity
         */
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_IMAGE_URL, imageUrl);
        intent.putExtra(DetailActivity.EXTRA_BOOK_TITLE, bookTitle);
        intent.putExtra(DetailActivity.EXTRA_AUTHOR_NAME, authorName);
        intent.putExtra(DetailActivity.EXTRA_BOOK_INDEX, bookIndex);

        saveArray(bookIndexArray, "BookIndexArray", this.getContext());
        System.out.println("I am in Common Fragment putting in the imageUrl: " + imageUrl);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    /*
    This is where we store the information and display it. Note that we have attributes
    bookTitle, authorName, and imageUrl which are being used above to set the value
    of textboxes or set the image.
     */
    public void bindData(String bookTitle, String authorName, String imageUrl, String bookIndex) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.bookIndex = bookIndex;
    }
}
