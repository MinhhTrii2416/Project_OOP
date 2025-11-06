abstract public class Person {
    protected String Name;
    protected String personID;
    protected String gender;
    protected String phoneNumber;
    protected String email;
    protected String address;
    // set & get
    public void setName(String name){ this.Name = name;}
    public String getName(){ return this.Name;}
    public void setPersonID(String personID){this.personID = personID;}
    public String getPersonID(){return this.personID;}
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
    public Person(String Name, String personID, String gender, String phoneNumber, String email, String address){
        this.Name = Name;
        this.personID = personID;               
        this.gender = gender;
        this.phoneNumber = phoneNumber;     
        this.email = email;
        this.address = address;     
    }
    abstract public void showINFO();
}
