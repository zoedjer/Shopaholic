package project.entity;

public class Customer extends AbsEntity {

	private String first_name;
	private String last_name;
	private String phone;
	private String email;
	private String line_one;
	private String line_two;
	private String city;
	private String county;
	private String country;
	
	public Customer(Long id) {
	    super(id);
	}

	public Customer(Long id, String first_name, String last_name, String phone, String email, String line_one,
			String line_two, String city, String county, String country) {
		super(id);
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone = phone;
		this.email = email;
		this.line_one = line_one;
		this.line_two = line_two;
		this.city = city;
		this.county = county;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getLine_one() {
		return line_one;
	}

	public String getLine_two() {
		return line_two;
	}

	public String getCity() {
		return city;
	}

	public String getCounty() {
		return county;
	}

	public String getCountry() {
		return country;
	}

}
