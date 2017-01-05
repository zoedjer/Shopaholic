package project.entity;

public class ProfitNode {

    private String date;
    private String profit;

    public ProfitNode(String date, String profit) {
	super();
	this.date = date;
	this.profit = profit;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public String getProfit() {
	return profit;
    }

    public void setProfit(String profit) {
	this.profit = profit;
    }

}
