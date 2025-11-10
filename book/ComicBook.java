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
        System.out.println("ID: " + this.getBookID());
        System.out.println("Ten sach: " + this.getName());
        System.out.println("The loai: " + this.getBookType());
        System.out.println("Tac gia: " + this.getAuthor());
        System.out.println("So luong: " + this.getQuantity());
        System.out.println("So luong con lai: " + this.getRemaining());
        System.out.println("Hoa si: " + this.getArtist());
        System.out.println("So tap: " + this.getVolumeNumber());
        
    }
}
