package com.stone.transition;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    GridView grid;


    String[] names = {
            "Ryan",
            "Jacquelene",
            "Tiffany",
            "Richard"
    };
    int[] imageId = {
            R.drawable.ryan,
            R.drawable.jacquelene,
            R.drawable.tiffany,
            R.drawable.richard
    };

    SQLiteDatabase myDB = null;
    String TableName = "dbTable100";
    String[] tempBooks = new String[4];
    String[] tempAuthors = new String[4];
    String Data = "";
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        gridSetup();
        activity = this;
        try {
            myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);

            myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TableName
                    + " (iD INT(1), Name VARCHAR(20), Age INT(3), Interests VARCHAR(50), " +
                    "Genres VARCHAR(50), Book1 VARCHAR(30), Book2 VARCHAR(30), Book3 VARCHAR(30), " +
                    "Book4 VARCHAR(30), Author1 VARCHAR(20), Author2 VARCHAR(20), Author3 VARCHAR(20), " +
                    "Author4 VARCHAR(20), link1 VARCHAR(100), link2 VARCHAR(100), link3 VARCHAR(100), link4 VARCHAR(100), " +
                    "picture VARCHAR(150));");
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (iD, Name, Age, Interests, Genres, Book1, Book2, Book3, Book4," +
                    "Author1, Author2, Author3, Author4, " +
                    "link1, link2, link3, link4, picture)"
                    + " VALUES (0,'Ryan S.', 12, 'Volleyball, Homework, Eating', 'Romance, Science Fiction', " +
                    "'Diary of a Wimpy Kid', 'Goosebumps', 'Harry Potter', 'A Series of Unfortunate Events', " +
                    "'Jeff Kinney', 'R. L. Stine' , 'J. K. Rowling' , 'Daniel Handler' , " +
                    "'assets://Ryan1.jpg' , 'assets://Ryan2.jpg', 'assets://Ryan3.jpg', 'assets://Ryan4.jpg', " +
                    "'ryan');");
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (iD, Name, Age, Interests, Genres, Book1, Book2, Book3, Book4, " +
                    "Author1, Author2, Author3, Author4, link1, link2, link3, link4, picture)"
                    + " VALUES (1,'Jacquelene P.', 13, 'Surfing, Skiing', 'Mystery, Fantasy'," +
                    "'The Magic Tree House', 'Junie B. Jones', 'The Fault in Our Stars','Hunger Games', " +
                    "'Mary Pope Osborne', 'Barbara Park', 'John Green', 'Suzanne Collins', " +
                    "'assets://Lucy1.jpg', 'assets://Lucy2.jpg', 'assets://Lucy3.jpg', 'assets://Lucy4.jpg', " +
                    "'jacquelene');");
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (iD, Name, Age, Interests, Genres, " +
                    "Book1, Book2, Book3, Book4, Author1, Author2, Author3, Author4, " +
                    "link1, link2, link3, link4, picture)"
                    + " VALUES (2, 'Tiffany L.', 14, 'Cooking, Skateboarding', 'Poetry, History, Math'," +
                    "'MineCraft', 'How to Train Your Dragon', 'The Maze Runner', 'The Hobbit', " +
                    "'Stephanie Milton', 'Cressida Cowell', 'James Dashner', 'J. R. R. Tolkien', " +
                    "'assets://Tiffany1.jpg' , 'assets://Tiffany2.jpg', 'assets://Tiffany3.jpg', 'assets://Tiffany4.jpg', " +
                    "'tiffany');");
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (iD, Name, Age, Interests, Genres, Book1, Book2, Book3, Book4, " +
                    "Author1, Author2, Author3, Author4, " +
                    "link1, link2, link3, link4, picture)"
                    + " VALUES (3, 'Richard P.' , 5, 'Running, Swimming', 'Drama, Romance', " +
                    "'One Fish Two', 'The Rainbow Fish', 'Moanna Coloring Book', 'Disney Frozen', " +
                    "'Dr. Seuss', 'Marcus Pfister', 'Disney', 'Disney'," +
                    "'assets://Richard1.jpg', 'assets://Richard2.jpg', 'assets://Richard3.jpg', 'assets://Richard4.jpg', " +
                    "'richard' );");
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (iD, Name, Age, Interests, Genres, Book1, Book2, Book3, Book4, " +
                    "Author1, Author2, Author3, Author4, link1, link2, link3, link4)"
                    + " VALUES (4, 'Shaniqua N.', 6, 'Writing, Running, Soccer', 'Action and Adventure, Mystery', " +
                    "'The Adventures of Captain Underpants', 'The Cat in the Hat', 'Frog on a Log', 'The Giving Tree'," +
                    " 'Dav Pilkey', 'Dr. Seuss', 'Kes Gray', 'Shel Silverstein', " +
                    "'assets://Austin1.jpg', 'assets://Austin2.jpg', 'assets://Austin3.jpg', 'assets://Austin4.jpg', " +
                    "'shaniqua');");

            //table done

            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName + " WHERE iD = 1", null);
            if(c.moveToFirst()) {
                int Name_index = c.getColumnIndex("Name");
                int Age_index = c.getColumnIndex("Age");
                int Interests_index = c.getColumnIndex("Interests");
                int Genre_index = c.getColumnIndex("Genres");
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

                String link1 = c.getString(link1_index);
                System.out.println("link: " + link1);
            }



        } catch (Exception E) {
            System.out.println("error: " + E.getLocalizedMessage());

        }


    }

    public void gridSetup() {
        CustomGrid adapter = new CustomGrid(MainActivity.this, names, imageId);
        grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(activity, ChildView.class);
                String index = Integer.toString(position);
                i.putExtra("child_index", index);
                startActivity(i);
            }
        });
    }

    public void goToAustin() {
        Intent intent = new Intent(MainActivity.this, ChildView.class);
        startActivity(intent);
    }
}