package book;

// sách tài liệu tham khảo
public class ReferenceBook extends Book{
    
    // properties
    private boolean isReadOnly; // chỉ đọc

    // Constructor
    public ReferenceBook() {
        super();
        this.isReadOnly = true;
        bookID = "referenceB_" + (split_ID() + 1);
    }  
    public ReferenceBook(String name, String author, int quantity, int remaining, Boolean isReadOnly) {
        super(author, name, quantity, remaining);
        this.isReadOnly = isReadOnly;
        bookID = "referenceB_" + (split_ID() + 1);
    }
    public ReferenceBook(ReferenceBook referBook) {
        this.author = referBook.author;
        this.name = referBook.name;
        this.quantity = referBook.quantity;
        this.remaining = referBook.remaining;
        this.isReadOnly = referBook.isReadOnly;
        bookID = "referenceB_" + (split_ID() + 1);
    }

    public double calcFine() {
        double result = 0;
        
        return result;
    }
}
