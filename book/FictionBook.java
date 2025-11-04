package book;

// Sách văn học (tiểu thuyết)
public class FictionBook extends Book{

    // properties
    private String subGenre; // Thể loại phụ (ví dụ: khoa học viễn tưởng, trinh thám, lãng mạn,...)

    public String getSubGenre() {return subGenre;}
    public void setSubGenre(String subGenre) {this.subGenre = subGenre;}

    // Constructor
    public FictionBook() {
        super();
        this.subGenre = "null";
        bookID = "fictionB_" + (split_ID() + 1);
    }  
    public FictionBook(String name, String author, int quantity, int remaining, String subGenre) {
        super(author, name, quantity, remaining);
        this.subGenre = subGenre;
        bookID = "fictionB_" + (split_ID() + 1);
    }
    public FictionBook(FictionBook fictBook) {
        this.author = fictBook.author;
        this.name = fictBook.name;
        this.quantity = fictBook.quantity;
        this.remaining = fictBook.remaining;
        this.subGenre = fictBook.subGenre;
        bookID = "fictionB_" + (split_ID() + 1);
    }

    public double calcFine() {
        double result = 0;

        return result;
    }

}
