package book;


// Sách tài liệu
public class TextBook extends Book{
    
    // properties
    private String subject; // môn học
    private int edition; // số lần xuất bản

// getter - setter
    public String getSubject() {return subject;}
    public int getEdition() {return edition;}
    public void setEdition(int edition) {this.edition = edition;}
    public void setSubject(String subject) {this.subject = subject;}

    // Constructor
    public TextBook() {
        super();
        this.subject = "null";
        this.edition = 0;
        this.setBookType("Text Book");
        this.bookID = "textB_" + automatic_ID;
    }  
    public TextBook(String name, String author, int quantity, int remaining, String subject, int edition) {
        super(author, name, quantity, remaining);
        this.subject  = subject;
        this.edition = edition;
        this.setBookType("Text Book");
        this.bookID = "textB_" + automatic_ID;
    }
    public TextBook(TextBook textBook) {
        this.author = textBook.author;
        this.name = textBook.name;
        this.quantity = textBook.quantity;
        this.remaining = textBook.remaining;
        this.subject = textBook.subject;
        this.edition = textBook.edition;
        this.bookType = textBook.bookType;
        this.bookID = "textB_" + automatic_ID;
    }


// methods
    @Override
    public double calcFine() {
        double result = 0;
        
        return result;
    }

    
    @Override
    public void showINFO() {
        System.out.println("ID: " + this.getBookID());
        System.out.println("Ten sach: " + this.getName());
        System.out.println("The loai: " + this.getBookType());
        System.out.println("Tac gia: " + this.getAuthor());
        System.out.println("So luong: " + this.getQuantity());
        System.out.println("So luong con lai: " + this.getRemaining());
        System.out.println("Mon hoc: " + this.getSubject());
        System.out.println("Xuat ban " + this.getEdition() + " lan");
    }
}
