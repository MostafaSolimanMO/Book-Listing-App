package daii.bookListing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Books> books = new ArrayList<>();
        books.add(new Books("Android Application Development", "Ryan Cohen", "sa"));
        BooksAdapter adapter = new BooksAdapter(this, books);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        final EditText searchEdit = findViewById(R.id.Search_key);
        ImageButton searchButton = findViewById(R.id.Search_Button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = searchEdit.getText().toString();
                Toast.makeText(getApplicationContext(), searchKey, Toast.LENGTH_SHORT).show();
            }
        });

    }

}



