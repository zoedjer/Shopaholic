package project.entity;

public class MyNode {

	private String date;
	private String close;

	public MyNode(String date, String close) {
		super();
		this.date = date;
		this.close = close;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

}
