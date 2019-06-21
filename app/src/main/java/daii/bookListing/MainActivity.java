package daii.bookListing;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BooksAdapter mAdapter;
    private String mUrlGoogleBooks = "";
    private ProgressBar mProgressBar;
    private TextView notBooksAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.loading_spinner);
        mProgressBar.setVisibility(View.INVISIBLE);

        notBooksAvailable = findViewById(R.id.empty_view);
        notBooksAvailable.setText("Not book available");
        notBooksAvailable.setVisibility(View.VISIBLE);

        ListView listView = findViewById(R.id.list);
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Books bookLink = mAdapter.getItem(position);
                Uri bookUri = Uri.parse(bookLink.getmBookLink());
                Intent webSiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(webSiteIntent);
            }
        });

        final EditText searchEdit = findViewById(R.id.Search_key);
        ImageButton searchButton = findViewById(R.id.Search_Button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    notBooksAvailable.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    BooksAsyncTask task = new BooksAsyncTask();
                    task.execute(updateQueryUrl(searchEdit.getText().toString()));

                } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                    notBooksAvailable.setText("No Internet Connection");
                }
            }
        });
    }

    private String updateQueryUrl(String searchWord) {
        if (searchWord.contains(" ")) {
            searchWord = searchWord.replace(" ", "+");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.googleapis.com/books/v1/volumes?q=").append(searchWord).append("&filter=paid-ebooks");
        mUrlGoogleBooks = stringBuilder.toString();
        return mUrlGoogleBooks;
    }

    private class BooksAsyncTask extends AsyncTask<String, Void, List<Books>> {

        @Override
        protected List<Books> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Books> result = QueryUntiles.fetchBooksData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Books> data) {
            mProgressBar.setVisibility(View.GONE);
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}



