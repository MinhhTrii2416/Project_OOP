package book;

public abstract class Book {
    protected String name;
    protected String bookID;
    protected String author;
    protected int quantity;
    protected int remaining;

    public String getAuthor() {
        return author;
    }
    public String getBookID() {
        return bookID;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getRemaining() {
        return remaining;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
    
}
