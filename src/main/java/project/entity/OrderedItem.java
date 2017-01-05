package project.entity;

public class OrderedItem {

    private Long id;
    private String name;
    private Integer quantity;
    private String price;
    private String price_sold;

    public OrderedItem(Long id, Integer quantity, String price, String price_sold) {
	super();
	this.id = id;
	this.quantity = quantity;
	this.price = price;
	this.price_sold = price_sold;
    }

    public OrderedItem(Long id, String name, Integer quantity, String price, String price_sold) {
	super();
	this.id = id;
	this.name = name;
	this.quantity = quantity;
	this.price = price;
	this.price_sold = price_sold;
    }

    public Long getId() {
	return id;
    }

    public Integer getQuantity() {
	return quantity;
    }

    public String getPrice() {
	return price;
    }

    public String getPrice_sold() {
	return price_sold;
    }

    public String getName() {
	return name;
    }

}
