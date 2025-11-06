
import java.util.ArrayList;
import java.util.List;
import Person.LoanTicket;

public class LoanManager implements DataService{
    // - <list>(LoanTicket) loanTicketList
    private List<LoanTicket> loanTicketList;

    public LoanManager(){
        this.loanTicketList = new ArrayList<>();
    }
   
     
}
