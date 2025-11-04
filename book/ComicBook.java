package book;


// Truyện tranh
public class ComicBook  extends Book{
    
    // properties
    private String artist; // Họa sĩ
    private int volumeNumber; // số tập

    // Constructor
    public ComicBook() {
        super();
        this.artist = "null";
        this.volumeNumber = 0;
        bookID = "comicB_" + (split_ID() + 1);
    }  
    public ComicBook(String name, String author, int quantity, int remaining, String artist, int volNumber) {
        super(author, name, quantity, remaining);
        this.artist = artist;
        this.volumeNumber = volNumber;
        bookID = "comicB_" + (split_ID() + 1);
    }
    public ComicBook(ComicBook comicBook) {
        this.author = comicBook.author;
        this.name = comicBook.name;
        this.quantity = comicBook.quantity;
        this.remaining = comicBook.remaining;
        this.artist = comicBook.artist;
        this.volumeNumber = comicBook.volumeNumber;
        bookID = "comicB_" + (split_ID() + 1);
    }

    public double calcFine() {
        double result = 0;
        
        return result;
    }
}
