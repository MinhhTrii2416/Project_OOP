package book;

// Sách văn học (tiểu thuyết)
public class FictionBook extends Book{

// properties
    private String subGenre; // Thể loại phụ (ví dụ: khoa học viễn tưởng, trinh thám, lãng mạn,...)


// getter - setter
    public String getSubGenre() {return subGenre;}
    public void setSubGenre(String subGenre) {this.subGenre = subGenre;}

// Constructor
    public FictionBook() {
        super();
        this.subGenre = "null";
        bookID = "fictionB_" + (split_ID() + 1);
        setBookType("Fiction Book");
    }  
    public FictionBook(String name, String author, int quantity, int remaining, String subGenre) {
        super(author, name, quantity, remaining);
        this.subGenre = subGenre;
        setBookType("Fiction Book");
        bookID = "fictionB_" + (split_ID() + 1);
    }
    public FictionBook(FictionBook fictBook) {
        this.author = fictBook.author;
        this.name = fictBook.name;
        this.quantity = fictBook.quantity;
        this.remaining = fictBook.remaining;
        this.subGenre = fictBook.subGenre;
        this.bookType = fictBook.bookType;
        bookID = "fictionB_" + (split_ID() + 1);
    }


//methods
    @Override
    public double calcFine() {
        double result = 0;

        return result;
    }

    @Override
    public void showINFO() {
        System.out.println("ID: " + Book.bookID);
        System.out.println("Ten sach: " + this.getName());
        System.out.println("The loai: " + this.getBookType());
        System.out.println("Tac gia: " + this.getAuthor());
        System.out.println("So luong: " + this.getQuantity());
        System.out.println("So luong con lai: " + this.getRemaining());
        System.out.println("The loai phu: " + this.getSubGenre());
    }

}
