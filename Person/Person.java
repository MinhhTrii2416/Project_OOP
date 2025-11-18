package Person;

import java.util.Scanner;

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
        if (gender.equalsIgnoreCase("nu")) gender = "Female";
        else if (gender.equalsIgnoreCase("Nam")) gender = "Male";
        else if (gender.equalsIgnoreCase("female")) gender = "Female";
        else if (gender.equalsIgnoreCase("Male")) gender = "Male";
        else gender = "Male";

        this.Name = Name;               
        this.gender = gender;
        this.phoneNumber = phoneNumber;     
        this.email = email;
        this.address = address;     
    }


    private static final String EMAIL_PATTERN = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    /*
     * ^[\\w._%+-]+ - Bắt đầu với chữ, số, dấu chấm, gạch dưới, %+-
     * @ - Phải có @
     * [\\w.-]+ - Domain name (chữ, số, gạch ngang, dấu chấm)
     */

    public boolean checkEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches(EMAIL_PATTERN);
    }
    public boolean checkPhoneNum(String Pnumber) {
        if(Pnumber.length() < 10 || Pnumber.length() > 11) return false;
        return true;
    }
    
    abstract public void showINFO();
}
