package Person;
public class Reader extends Person{
    private String readerID;
    // set & get 
    public void setReaderID(String readerID){
        this.readerID = readerID;
    }
    public String getReaderID(){
        return this.readerID;
    }
    // contructor
    public Reader(){
        super();
        this.readerID = null;
    }
    public Reader(String readerID, String Name, String gender, String address, String phoneNumber, String email){
        super(Name, gender, phoneNumber, email, address);
        this.readerID = readerID;
    }
    //@Override
    public void showINFO(){
        System.out.println("Thong tin nguoi doc: ");
        System.out.println("Name: " + this.Name);
        System.out.println("Gender: " + this.gender);
        System.out.println("Phone Number: " + this.phoneNumber);        
        System.out.println("Email: " + this.email);    
        System.out.println("Address: " + this.address);
        System.out.println("ReaderID: " + this.readerID);
    }
}
