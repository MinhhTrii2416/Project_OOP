package book;


// Sách tài liệu
public class TextBook extends Book{
    
    // properties
    private String subject; // môn học
    private int edition; // số lần xuất bản

    public String getSubject() {return subject;}
    public int getEdition() {return edition;}
    public void setEdition(int edition) {this.edition = edition;}
    public void setSubject(String subject) {this.subject = subject;}

    // Constructor
    public TextBook() {
        super();
        this.subject = "null";
        this.edition = 0;
        bookID = "textB_" + (split_ID() + 1);
    }  
    public TextBook(String name, String author, int quantity, int remaining, String subject, int edition) {
        super(author, name, quantity, remaining);
        this.subject  = subject;
        this.edition = edition;
        bookID = "textB_" + (split_ID() + 1);
    }
    public TextBook(TextBook textBook) {
        this.author = textBook.author;
        this.name = textBook.name;
        this.quantity = textBook.quantity;
        this.remaining = textBook.remaining;
        this.subject = textBook.subject;
        this.edition = textBook.edition;
        bookID = "textB_" + (split_ID() + 1);
    }

    public double calcFine() {
        double result = 0;
        
        return result;
    }
}
