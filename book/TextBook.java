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
        this.bookID = "textB_" + automatic_ID4;
        automatic_ID4++;
    }  
    public TextBook(String name, String author, int quantity, int remaining, double price, String subject, int edition) {
        super(author, name, quantity, remaining);
        this.subject  = subject;
        this.edition = edition;
        this.price = price;
        this.setBookType("Text Book");
        this.bookID = "textB_" + automatic_ID4;
        automatic_ID4++;
    }
    public TextBook(TextBook textBook) {
        this.author = textBook.author;
        this.name = textBook.name;
        this.quantity = textBook.quantity;
        this.remaining = textBook.remaining;
        this.subject = textBook.subject;
        this.edition = textBook.edition;
        this.bookType = textBook.bookType;
        this.bookID = "textB_" + automatic_ID4;
        automatic_ID4++;
    }


// methods
    @Override
    public double calcFine(int songay) {
        double result = 2000 * songay;
        return result;
    }

    
    @Override
    public void showINFO() {
        String formatTB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-12.0f | %-15s | %-25s | %-10s |\n";
        
        System.out.printf(formatTB, this.getBookID(), this.getName(), this.getAuthor(),
                                    this.getQuantity(), this.getRemaining(), this.getPrice(),
                                    this.getBookType(), this.getSubject(), this.getEdition());
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
        System.out.println("Mon hoc: " + this.getSubject());
        System.out.println("Lan xuat ban: " + this.getEdition());
    }


    @Override
    public void enterInfo(){
        String name, author, subject;
        int quantity, remaining, edition;
        double price;
        boolean ok;

        do {
            ok = true;
            System.out.print("Nhap ten sach: ");
            name = scan.nextLine();
            System.out.print("Nhap tac gia: ");
            author = scan.nextLine();
            System.out.print("Nhap mon hoc: ");
            subject = scan.nextLine();
            System.out.print("Nhap so luong: ");
            quantity = scan.nextInt();
            System.out.print("Nhap so luong con lai: ");
            remaining = scan.nextInt();
            System.out.print("Nhap gia: ");
            price = scan.nextDouble();
            System.out.print("Lan xuat ban thu: ");
            edition = scan.nextInt();
            scan.nextLine();

            if(name == "" || author == "" || subject == "" || edition < 0 || quantity < 0 || remaining < 0 || quantity < remaining || price < 0) {
                System.out.println("1 vai thong tin khong hop le!");
                System.out.println("vui long nhap lai!");
                ok = false;
            }

        }while(!ok);


        this.setAuthor(author);
        this.setBookType("Text book");
        this.setName(name);
        this.setQuantity(quantity);
        this.setRemaining(remaining);
        this.setPrice(price);
        this.setSubject(subject);
        this.setEdition(edition);


    }
}
