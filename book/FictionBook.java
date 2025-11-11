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
        this.bookID = "fictionB_" + automatic_ID2;
        automatic_ID2++;
        setBookType("Fiction Book");
    }  
    public FictionBook(String name, String author, int quantity, int remaining, String subGenre) {
        super(author, name, quantity, remaining);
        this.subGenre = subGenre;
        setBookType("Fiction Book");
        this.bookID = "fictionB_" + automatic_ID2;
        automatic_ID2++;
    }
    public FictionBook(FictionBook fictBook) {
        this.author = fictBook.author;
        this.name = fictBook.name;
        this.quantity = fictBook.quantity;
        this.remaining = fictBook.remaining;
        this.subGenre = fictBook.subGenre;
        this.bookType = fictBook.bookType;
        this.bookID = "fictionB_" + automatic_ID2;
        automatic_ID2++;
    }


//methods
    @Override
    public double calcFine() {
        double result = 0;

        return result;
    }

    @Override
    public void showINFO() {
        String formatFB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-15s | %-25s |\n";

        System.out.printf(formatFB, this.getBookID(), this.getName(), this.getAuthor(),
                                    this.getQuantity(), this.getRemaining(), this.getBookType(),
                                    this.getSubGenre());
    }

    @Override
    public void showInfo_1() {
        System.out.println("Book ID: " + this.getBookID());
        System.out.println("Ten sach: " + this.getName());
        System.out.println("Tac gia: " + this.getAuthor());
        System.out.println("So luong: " + this.getQuantity());
        System.out.println("Con lai: " + this.getRemaining());
        System.out.println("Loai sach: " + this.getBookType());
        System.out.println("The loai phu: " + this.getSubGenre());
    }


    @Override
    public void enterInfo(){
        String name, author, subGenre;
        int quantity, remaining;
        boolean ok = false;

        do {
            ok = true;
            System.out.print("Nhap ten sach: ");
            name = scan.nextLine();
            System.out.print("Nhap tac gia: ");
            author = scan.nextLine();
            System.out.print("Nhap the loai phu: ");
            subGenre = scan.nextLine();
            System.out.print("Nhap so luong: ");
            quantity = scan.nextInt();
            System.out.print("Nhap so luong con lai: ");
            remaining = scan.nextInt();
            scan.nextLine();

            if(name == "" || author == "" || subGenre == "" || quantity < 0 || remaining < 0 || quantity < remaining) {
                System.out.println("1 vai thong tin khong hop le!");
                System.out.println("vui long nhap lai!");
                ok = false;
            }

        }while(!ok);

        this.setAuthor(author); this.setSubGenre(subGenre);
        this.setBookType("Fiction book");
        this.setName(name);
        this.setQuantity(quantity); this.setRemaining(remaining);

    }
}
