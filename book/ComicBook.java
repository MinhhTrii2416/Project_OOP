package book;


// Truyện tranh
public class ComicBook  extends Book{

// properties
    private String artist; // Họa sĩ
    private int volumeNumber; // số tập

// getter - setter
    public void setArtist(String artist) {this.artist = artist;}
    public void setVolumeNumber(int volumeNumber) {this.volumeNumber = volumeNumber;}
    public int getVolumeNumber() {return volumeNumber;}
    public String getArtist() {return artist;}

// Constructor
    public ComicBook() {
        super();
        this.artist = "null";
        this.volumeNumber = 0;
        this.setBookType("Comic Book");
        this.bookID = "comicB_" + automatic_ID;
    }  
    public ComicBook(String name, String author, int quantity, int remaining, String artist, int volNumber) {
        super(author, name, quantity, remaining);
        this.artist = artist;
        this.volumeNumber = volNumber;
        this.setBookType("Comic Book");
        this.bookID = "comicB_" + automatic_ID;
    }
    public ComicBook(ComicBook comicBook) {
        this.author = comicBook.author;
        this.name = comicBook.name;
        this.quantity = comicBook.quantity;
        this.remaining = comicBook.remaining;
        this.artist = comicBook.artist;
        this.volumeNumber = comicBook.volumeNumber;
        this.bookType = comicBook.bookType;
        this.bookID = "comicB_" + automatic_ID;
    }


// methods
    @Override
    public double calcFine() {
        double result = 0;
        
        return result;
    }

    @Override
    public void showINFO() {
        String formatCB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-15s | %-20s | %-5s |\n";

        System.out.printf(formatCB, this.getBookID(), this.getName(), this.getAuthor(),
                                    this.getQuantity(), this.getRemaining(), this.getBookType(),
                                    this.getArtist(), this.getVolumeNumber());
    }

    @Override
    public void showInfo_1() {
        System.out.println("Book ID: " + this.getBookID());
        System.out.println("Ten truyen: " + this.getName());
        System.out.println("Tac gia: " + this.getAuthor());
        System.out.println("So luong: " + this.getQuantity());
        System.out.println("Con lai: " + this.getRemaining());
        System.out.println("Loai sach: " + this.getBookType());
        System.out.println("Hoa si: " + this.getArtist());
        System.out.println("So tap: " + this.getVolumeNumber());
    }

    @Override
    public void enterInfo(){
        String name, author, artist;
        int quantity, remaining, volNumber;
        boolean ok = false;

        do {
            ok = true;
            System.out.print("Nhap ten truyen: ");
            name = scan.nextLine();
            System.out.print("Nhap tac gia: ");
            author = scan.nextLine();
            System.out.print("Nhap hoa si: ");
            artist = scan.nextLine();
            System.out.print("Nhap so luong: ");
            quantity = scan.nextInt();
            System.out.print("Nhap so luong con lai: ");
            remaining = scan.nextInt();
            System.out.print("Nhap tap cua truyen: ");
            volNumber = scan.nextInt();
            scan.nextLine();

            if(name == "" || author == "" || artist == "" || volNumber < 0 ||quantity < 0 || remaining < 0 || quantity < remaining) {
                System.out.println("1 vai thong tin khong hop le!");
                System.out.println("vui long nhap lai!");
                ok = false;
            }

        }while(!ok);

        this.setArtist(artist); this.setAuthor(author);
        this.setBookType("Comic book");
        this.setName(name);
        this.setQuantity(quantity);
        this.setRemaining(remaining);
        this.setVolumeNumber(volNumber);
    }
}
