public class Librarian extends Person {
    private String librarianID;
    private String shift;
    private double salary;
    private String password;
    // set & get
    public void setLibrarianID(String librarianID){this.librarianID = librarianID;}
    public String getLibrarianID(){return this.librarianID;}    
    public void setShift(String shift){this.shift = shift;}
    public String getShift(){return this.shift;}
    public void setSalary(double salary){this.salary = salary;}
    public double getSalary(){return this.salary;}
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}
    // constructor  
    public Librarian(){}
    public Librarian(String Name, String gender, String phoneNumber, String email, String address, String librarianID, String shift, double salary, String password){
        super(Name, gender, phoneNumber, email, address);
        this.librarianID = librarianID;     
        this.shift = shift;
        this.salary = salary;
        this.password = password;   
    }
    @Override
    public void showINFO(){
        System.out.println("Thong tin thu thu");
        System.out.println('Name: ' + this.Name);
        System.out.println('Person ID: ' + this.personID);  
        System.out.println('Gender: ' + this.gender);
        System.out.println('Phone Number: ' + this.phoneNumber);        
        System.out.println('Email: ' + this.email);    
        System.out.println('Address: ' + this.address);
        System.out.println('Librarian ID: ' + this.librarianID);     
        System.out.println('Shift: ' + this.shift);     
        System.out.println('Salary: ' + this.salary);
        System.out.println('Password: ' + this.password);
    }
}
