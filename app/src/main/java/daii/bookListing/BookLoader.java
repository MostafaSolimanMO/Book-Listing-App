package daii.bookListing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class BookLoader extends AsyncTaskLoader {
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Books> books = QueryUntiles.fetchBooksData(mUrl);
        return books;
    }
}
