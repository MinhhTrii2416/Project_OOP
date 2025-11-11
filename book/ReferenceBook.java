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
        bookID = "referenceB_" + automatic_ID3;
        automatic_ID3++;
    }  
    public ReferenceBook(String name, String author, int quantity, int remaining, double price, Boolean isReadOnly) {
        super(author, name, quantity, remaining);
        this.isReadOnly = isReadOnly;
        this.price = price;
        this.setBookType("Reference Book");
        bookID = "referenceB_" + automatic_ID3;
        automatic_ID3++;
    }
    public ReferenceBook(ReferenceBook referBook) {
        this.author = referBook.author;
        this.name = referBook.name;
        this.quantity = referBook.quantity;
        this.remaining = referBook.remaining;
        this.isReadOnly = referBook.isReadOnly;
        this.bookType = referBook.bookType;
        bookID = "referenceB_" + automatic_ID3;
        automatic_ID3++;
    }


// methods
    @Override
    public double calcFine() {
        double result = 0;
        
        return result;
    }
    
    @Override
    public void showINFO() {
        String formatRB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-12.0f | %-15s | %-20s |\n";

        String boolToText = this.isReadOnly() ? "Chi doc" : "Duoc muon/mua";
        System.out.printf(formatRB, this.getBookID(), this.getName(), this.getAuthor(),
                                    this.getQuantity(), this.getRemaining(), this.getPrice(),
                                    this.getBookType(), boolToText);
    }

    @Override
    public void showInfo_1() {
        System.out.println("Book ID: " + this.getBookID());
        System.out.println("Ten sach: " + this.getName());
        System.out.println("Tac gia: " + this.getAuthor());
        System.out.println("So luong: " + this.getQuantity());
        System.out.println("Con lai: " + this.getRemaining());
        System.out.println("Gia: " + this.getPrice() + " VND");
        System.out.println("Loai sach: " + this.getBookType());
        System.out.println("Trang thai: " + (this.isReadOnly() ? "Chi doc" : "Duoc muon/mua"));
    }


    @Override
    public void enterInfo(){
        String name, author;
        int quantity, remaining;
        double price;
        boolean ok, isReadOnly;

        do {
            ok = true;
            System.out.print("Nhap ten sach: ");
            name = scan.nextLine();
            System.out.print("Nhap tac gia: ");
            author = scan.nextLine();
            System.out.print("Sach co duoc mua/muon khong?\n1: Co \n2: Khong");
            int choice = scan.nextInt();
            isReadOnly = (choice == 2);  // 2 = Không mua = Chi đọc (ReadOnly)
            System.out.print("Nhap so luong: ");
            quantity = scan.nextInt();
            System.out.print("Nhap so luong con lai: ");
            remaining = scan.nextInt();
            System.out.print("Nhap gia: ");
            price = scan.nextDouble();
            scan.nextLine();

            if(name == "" || author == "" || quantity < 0 || remaining < 0 || quantity < remaining || price < 0) {
                System.out.println("1 vai thong tin khong hop le!");
                System.out.println("vui long nhap lai!");
                ok = false;
            }

        }while(!ok);

        this.setAuthor(author);
        this.setBookType("Reference book");
        this.setName(name);
        this.setQuantity(quantity);
        this.setRemaining(remaining);
        this.setPrice(price);
        this.setReadOnly(isReadOnly);

    }
}
