package Person;

    
abstract public class Person {
    protected String Name;
    protected String gender;
    protected String phoneNumber;
    protected String email;
    protected String address;
    // set & get
    public void setName(String name){ this.Name = name;}
    public String getName(){ return this.Name;}
    public void setGender(String gender){this.gender = gender;}
    public String getGender(){return this.gender;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
    public String getPhoneNumber(){return this.phoneNumber;}        
    public void setEmail(String email){this.email = email;} 
    public String getEmail(){return this.email;}    
    public void setAddress(String address){this.address = address;} 
    public String getAddress(){return this.address;}    
    // contructor
    public Person(){}
    public Person(String Name, String gender, String phoneNumber, String email, String address){
        this.Name = Name;               
        this.gender = gender;
        this.phoneNumber = phoneNumber;     
        this.email = email;
        this.address = address;     
    }
    abstract public void showINFO();
}
