package project.entity;

public class Item {

	private Long id;
	private String name;
	private String category;
	private String price;
	private String brand;
	
	public Item() {}

	public Item(Long id, String name, String category, String price, String brand) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.brand = brand;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public String getBrand() {
		return brand;
	}

	public String getCategory() {
		return category;
	}

}
