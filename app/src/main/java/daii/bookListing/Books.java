package daii.bookListing;

public class Books {
    private String mTitle;
    private String mAuthor;
    private String mBookLink;


    public Books(String title, String author, String bookLink) {
        mTitle = title;
        mAuthor = author;
        mBookLink = bookLink;

    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmBookLink() {
        return mBookLink;
    }

}
