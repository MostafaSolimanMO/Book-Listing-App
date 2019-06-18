package daii.bookListing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Books> books = new ArrayList<>();
        books.add(new Books("Android Application Development", "Ryan Cohen"));
        books.add(new Books("Android on x86", "Iggy Krajci"));
        BooksAdapter adapter = new BooksAdapter(this, books);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
}


