package com.stone.transition;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class Wishlist extends FragmentActivity {

    private TextView indicatorTv;
    private View positionView;
    private ViewPager viewPager;
    private List<CommonFragment> fragments = new ArrayList<>(); // 供ViewPager使用
    private List<String> bookIndicesInCart = new ArrayList<>();
    String[] tempBooks = new String[4];
    String[] tempAuthors = new String[4];
    String[] tempLinks = new String[4];

    String child_index;



    //This is where we define the images to be used
    /*
    for (int i = 0; i < numberOfObjects; i++) {
        imageArray[i] = sqlObject.get(i).getImageURL;
        titleArray[i] = sqlObject.get(i).getBookTitle;
    }
    */

/*

    myDB =children.this.openOrCreateDatabase("dataBasedb", MODE_PRIVATE, null);
    Cursor crs = myDB.rawQuery("SELECT * FROM children", null);
*/
    /*
    Create a new array with your sql data
     */
    private final String[] imageArray = {"assets://testImage1.jpg", "assets://testImage2.jpg",
            "assets://testImage3.jpg", "assets://testImage4.jpg", "assets://testImage5(2).jpg"};
    private final String[] titleArray = {"Frankenstein", "What's Left of Me", "Star Wars",
            "A Game of Thrones", "Harry Potter and the Half Blood Prince"};
    private final String[] authorArray = {"Frankenstein's Author", "What's Left of Me's Author",
            "Star Wars's Author", "A Game of Thrones's Author", "Harry Potter and the Half Blood Prince's Author"};

    private final String[] indexArray = {"0", "1", "2", "3", "4"};

    SQLiteDatabase myDB = null;
    String TableName = "dbTable100";

    String Data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_main);

        // 1. 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        positionView = findViewById(R.id.position_view);
        dealStatusBar(); // 调整状态栏高度

        // 2. 初始化ImageLoader
        initImageLoader();

        // 3. 填充ViewPager
        fillViewPager();



        Intent intent = getIntent(); //get data from ChildView
        child_index = intent.getExtras().getString("child_index");
//        System.out.println("ChildView child_index: " + child_index);
//        TextView nameAndAgeText = (TextView) findViewById(R.id.nameAndAge);
//        TextView descriptionText = (TextView) findViewById(R.id.description);





        try {
            myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);

            //table done
            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName + " WHERE iD = " + child_index, null);



            if(c.moveToFirst()) {
                System.out.println("Created cursor c");
                TextView wishlist = (TextView) findViewById(R.id.wishlist);
                int Name_index = c.getColumnIndex("Name");

                String name = c.getString(Name_index);
                wishlist.setText(name + "'s WISHLIST");
//                int Name_index = c.getColumnIndex("Name");
                int Age_index = c.getColumnIndex("Age");
                int Book1_index = c.getColumnIndex("Book1");
                int Book2_index = c.getColumnIndex("Book2");
                int Book3_index = c.getColumnIndex("Book3");
                int Book4_index = c.getColumnIndex("Book4");
                int Author1_index = c.getColumnIndex("Author1");
                int Author2_index = c.getColumnIndex("Author2");
                int Author3_index = c.getColumnIndex("Author3");
                int Author4_index = c.getColumnIndex("Author4");
                int link1_index = c.getColumnIndex("link1");
                int link2_index = c.getColumnIndex("link2");
                int link3_index = c.getColumnIndex("link3");
                int link4_index = c.getColumnIndex("link4");

                //error here
                //System.out.println("get column index name:" + c.getColumnIndex("Name"));
//                String Name = c.getString(1);
                //System.out.println("Name: " + Name);
//
                //c = myDB.rawQuery("SELECT * FROM " + TableName + " WHERE iD = 0", null);

//                int Age = c.getInt(Age_index);
//                System.out.println("age: " + Age);

                String[] Books = {c.getString(Book1_index), c.getString(Book2_index), c.getString(Book3_index), c.getString(Book4_index)};
                String[] Authors = {c.getString(Author1_index), c.getString(Author2_index), c.getString(Author3_index), c.getString(Author4_index)};
                String[] Links = {c.getString(link1_index), c.getString(link2_index), c.getString(link3_index), c.getString(link4_index)}; //Need last link or else pics wont show

                for(int i = 0; i<Books.length; i++ ) {
                    tempBooks[i] = Books[i];
                    System.out.println("TempBooks: " + tempBooks[i]);
                }

                for(int i = 0; i<Authors.length; i++ ) {
                    tempAuthors[i] = Authors[i];
                    System.out.println("tempAuthors: " + tempAuthors[i]);
                }

                for(int i = 0; i<Links.length; i++ ) {
                    System.out.println("links: " + Links[i]);

                    tempLinks[i] = Links[i];
                    System.out.println("tempLinks: " + tempLinks[i]);



                }

            }
//            String[] Links = {c.getString(link1_index), c.getString(link2_index), c.getString(link3_index), c.getString(link4_index)};


//            c.moveToFirst();
//            int i = 0;
//
//            if (c != null) {
//
//                // Loop through all Results and create array of each column
//                while (c.moveToNext()) {
//                    Name = c.getString(Name_index);
//                    Age = c.getInt(Age_index);
//
//                   // Person1Books = {c.getString(Book1_index), c.getString(Book2_index), c.getString(Book3_index), c.getString(Book4_index)} ;
//                    Book1[i] = c.getString(Book1_index);
//                    Book2[i] = c.getString(Book2_index);
//                    Book3[i] = c.getString(Book3_index);
//                    Book4[i] = c.getString(Book4_index);
//                    link1[i] = c.getString(link1_index);
//                    link2[i] = c.getString(link2_index);
//                    link3[i] = c.getString(link3_index);
//                    link4[i] = c.getString(link4_index);
//
//                    i++;
//                    //Data =Data +Name+"/"+Age+"\n";
//                }
//
//
//            }
        } catch (Exception E) {
            System.out.println("error: " + E.getLocalizedMessage());

        }

    }

    /**
     * 填充ViewPager
     */
    private void fillViewPager() {
        indicatorTv = (TextView) findViewById(R.id.indicator_tv);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

        // 2. viewPager添加adapter
        for (int i = 0; i < 10; i++) {
            // 预先准备10个fragment
            fragments.add(new CommonFragment());
        }
        /*
        This is where we pass information about the title, author, image url
        to the CommonFragment.java
        Look at the bindData() function at the end of CommonFragment.java to see how we use
        this passed information
         */
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CommonFragment fragment = fragments.get(position % 10);
                fragment.bindData(tempBooks[position % tempBooks.length], tempAuthors[position % tempAuthors.length],
                        tempLinks[position % tempLinks.length], indexArray[position % indexArray.length]);
                System.out.println("This is fragment.bindData: " + tempBooks[position % tempBooks.length] + " "  + tempAuthors[position % tempAuthors.length]+ " " + tempLinks[position % tempLinks.length]);

                return fragment;

            }

            @Override
            public int getCount() {
                return 4;
            }
        });


        // 3. viewPager滑动时，调整指示器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateIndicatorTv();
    }

    /**
     * 更新指示器
     */
    private void updateIndicatorTv() {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem = viewPager.getCurrentItem() + 1;
        indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
    }

    /**
     * 调整沉浸式菜单的title
     */
    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                .discCacheFileCount(100) // 缓冲文件数目
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // 2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
//"select * from tableName WHERE id = variable_ID"

}
