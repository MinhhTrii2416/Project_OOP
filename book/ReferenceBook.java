package book;

// sách tài liệu tham khảo
public class ReferenceBook extends Book{
    
    // properties
    private boolean isReadOnly; // chỉ đọc

    public void setReadOnly(boolean isReadOnly) {this.isReadOnly = isReadOnly;}
    public boolean isReadOnly() {return this.isReadOnly;}

    // Constructor
    public ReferenceBook() {
        super();
        this.isReadOnly = true;
        this.setBookType("Reference Book");
        bookID = "referenceB_" + automatic_ID;
    }  
    public ReferenceBook(String name, String author, int quantity, int remaining, Boolean isReadOnly) {
        super(author, name, quantity, remaining);
        this.isReadOnly = isReadOnly;
        this.setBookType("Reference Book");
        bookID = "referenceB_" + automatic_ID;
    }
    public ReferenceBook(ReferenceBook referBook) {
        this.author = referBook.author;
        this.name = referBook.name;
        this.quantity = referBook.quantity;
        this.remaining = referBook.remaining;
        this.isReadOnly = referBook.isReadOnly;
        this.bookType = referBook.bookType;
        bookID = "referenceB_" + automatic_ID;
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
        String text = this.isReadOnly ? "Chi doc" : "Duoc phep muon/mua";
        System.out.println("The loai phu: " + text);
    }
}
