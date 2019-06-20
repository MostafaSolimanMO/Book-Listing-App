package daii.bookListing;

public class Books {
    private String mTitle;
    private String mAuthor;
    private String mImageUrl;

    public Books(String title, String author, String imageUrl) {
        mTitle = title;
        mAuthor = author;
        mImageUrl = imageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }
    public String getmAuthor() {
        return mAuthor;
    }
    public String getmImageUrl() {
        return mImageUrl;
    }
}
