package daii.bookListing;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Books>> {

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
        notBooksAvailable.setText("No books available");
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

        ImageButton searchButton = findViewById(R.id.Search_Button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    notBooksAvailable.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    LoaderManager loaderManager = getSupportLoaderManager();
                    loaderManager.initLoader(1, null, MainActivity.this);
                    getSupportLoaderManager().restartLoader(1, null, MainActivity.this);

                } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                    notBooksAvailable.setText("No Internet Connection");
                }
            }
        });
    }

    @Override
    public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        EditText searchEdit = findViewById(R.id.Search_key);
        String searchWord = searchEdit.getText().toString();
        if (searchWord.contains(" ")) {
            searchWord = searchWord.replace(" ", "+");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.googleapis.com/books/v1/volumes?q=").append(searchWord).append("&maxResults=40");

        mUrlGoogleBooks = stringBuilder.toString();
        return new BookLoader(this, mUrlGoogleBooks);
    }

    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> data) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        mAdapter.clear();
    }

}



