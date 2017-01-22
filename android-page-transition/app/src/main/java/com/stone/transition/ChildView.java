package com.stone.transition;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.refactor.lib.colordialog.PromptDialog;

public class ChildView extends AppCompatActivity {
    SQLiteDatabase myDB = null;
    String TableName = "dbTable10";
    public static final String CHILD_INDEX = "-1";
    Activity activity;
    String child_index;
    String childName;
    String profieImage;
    /*Descriptive attributes of a child*/
    int age;
    String interests, genres;

    public void showPromptDialog(View view) {
        showPromptDlg();
    }

    private void showPromptDlg() {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText(getString(R.string.success))
                .setContentText("Thank you for your commitment to childrens' education. " +
                        "\nWe have set this child's status as claimed by you. Please follow the next instructions.")
                .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                        Intent i = new Intent(activity, InfoForm.class);
                        i.putExtra("CHILD_NAME", childName);

                        startActivity(i);
                    }
                }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_view);

        Intent intent = getIntent();
        child_index = intent.getExtras().getString("child_index");
        System.out.println("ChildView child_index: " + child_index);
        TextView nameAndAgeText = (TextView) findViewById(R.id.nameAndAge);
        TextView descriptionText = (TextView) findViewById(R.id.description);

        final Button wishListButton = (Button) findViewById(R.id.moveToWishlist);


        try {
            myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);
            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName + " WHERE iD = " + child_index, null);
            if(c.moveToFirst()) {
                int Name_index = c.getColumnIndex("Name");
                int Age_index = c.getColumnIndex("Age");
                int Interests_index = c.getColumnIndex("Interests");
                int Genres_index = c.getColumnIndex("Genres");
                int ProfileImage_index = c.getColumnIndex("picture");
                age = c.getInt(Age_index);
                childName = c.getString(Name_index);
                interests = c.getString(Interests_index);
                genres = c.getString(Genres_index);
                profieImage = c.getString(ProfileImage_index);
                System.out.println("Child age: " + age);
                System.out.println("Child Name: " + childName);
                nameAndAgeText.setText(childName + ", " + age);
                descriptionText.setText("Interests: " +  interests + "\n" + "Favorite Genres: " + genres );
                wishListButton.setText("Check out " + childName + "'s " + "wishlist");

                ImageView profile = (ImageView) findViewById(R.id.childImage);

                int resID = getResources().getIdentifier(profieImage , "drawable", getPackageName());

                profile.setBackgroundResource(resID);

            }

        } catch (Exception e) {
            System.out.println("error: " + e.getLocalizedMessage());
        }



        wishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("I have clicked the wishlist button and " +
//                        "should transition to the wishlist activity with the intent of the child's ID");
                Intent i = new Intent(activity, Wishlist.class);
                i.putExtra("child_index", child_index);
                startActivity(i);
            }
        });
    }
}
