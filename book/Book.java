package book;

import java.util.Scanner;

public abstract class Book {
    public Scanner scan = new Scanner(System.in);
// properties
    protected String name;
    // book ID tự động + 1 khi khởi tạo
    protected static int automatic_ID = 0;
    protected String bookID;
    protected String author;
    protected int quantity;
    protected int remaining;
    protected String bookType;

// Getter - Setter
    public String getAuthor() {return author;}
    public String getName() {return name;}
    public int getQuantity() {return quantity;}
    public int getRemaining() {return remaining;}
    public void setAuthor(String author) {this.author = author;}
    public void setName(String name) {this.name = name;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setRemaining(int remaining) {this.remaining = remaining;}
    public String getBookType() {return bookType;}
    public void setBookType(String bookType) {this.bookType = bookType;}
    public String getBookID() {return bookID;}

// Constructor
    public Book() {
        this.author = "null";
        this.name = "null";
        this.quantity = 0;
        this.remaining = 0;
        Book.automatic_ID++;
    }
    public Book(String author, String name, int quantity, int remaining){
        // kiểm tra params xem có phù hợp không
        author = fomatTheString(author);
        name = fomatTheString(name);

        while (remaining > quantity || remaining < 0 || quantity < 0) {
            if(remaining > quantity) {
                System.out.println("The remaining must be greater than the quantity. \n");
                System.out.println("Please try again! ");
                System.out.print("Enter the quantity: ");
                quantity = scan.nextInt();
                System.out.print("Enter the remaining: ");
                remaining = scan.nextInt();
            }
            else if(remaining < 0) {
                System.out.println("The remaining must be greater than zero. \n");
                System.out.println("Please try again! ");
                System.err.print("Enter the remaining: ");
                remaining = scan.nextInt();
            }
            else if(quantity < 0) {
                System.out.println("The quantity must be greater than zero. \n");
                System.out.println("Please try again! ");
                System.err.print("Enter the quantity: ");
                quantity = scan.nextInt();
            }
        }

        this.author = author; this.name = name;
        this.quantity = quantity; this.remaining = remaining;
        automatic_ID++;
    }
    public Book(Book book) {
        this.author = book.author;
        this.name = book.name;
        this.quantity = book.quantity;
        this.remaining = book.remaining;
        automatic_ID++;
    }

// methods - funtions
    public abstract double calcFine(); // Calculate the fine;
    public abstract void showINFO();
    public abstract void showInfo_1(); // Hiển thị thông tin trên 1 dòng
    public abstract void enterInfo();


// helper funtions

    protected String  capitalizeFirstLetter(String string) {
        // in hoa kí tự đầu
        if(string.isEmpty()) return "";
        string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return string;
    }

    protected String fomatTheString(String string) {
        // bỏ khoảng trắng dư thừa
        // định dạng mỗi kí tự đầu của từng chữ là viết hoa
        string = string.trim();
        String words[] = string.split("\\s+");
        String result = "";
        for(String word: words) {
            result += capitalizeFirstLetter(word) + " ";
        }
        result = result.trim();
        return result;
    }
}
