package app.mustansar.instali.models;



import java.io.Serializable;

public class Member implements Serializable {
    int id;
    String planname;
    int totalinstallments;
    int totalamount;
    String plandate;
    String planMembers;


    public Member(String planname, int totalinstallments, int totalamount, String plandate, String planMembers) {
        this.planname = planname;
        this.totalinstallments = totalinstallments;
        this.totalamount = totalamount;
        this.plandate = plandate;
        this.planMembers = planMembers;
    }
    public Member(int id, String planname, int totalinstallments, int totalamount, String plandate, String planMembers) {
        this.id = id;
        this.planname = planname;
        this.totalinstallments = totalinstallments;
        this.totalamount = totalamount;
        this.plandate = plandate;
        this.planMembers = planMembers;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    public int getTotalinstallments() {
        return totalinstallments;
    }

    public void setTotalinstallments(int totalinstallments) {
        this.totalinstallments = totalinstallments;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    public String getPlandate() {
        return plandate;
    }

    public void setPlandate(String plandate) {
        this.plandate = plandate;
    }

    public String getPlanMembers() {
        return planMembers;
    }

    public void setPlanMembers(String planMembers) {
        this.planMembers = planMembers;
    }

    @Override
    public String toString() {
        return planname;
    }
}
