package project.entity;

public class PostAgent {

    private Long id;
    private String name;
    private String website;
    
    public PostAgent() {}

    public PostAgent(Long id, String name, String website) {
	super();
	this.id = id;
	this.name = name;
	this.website = website;
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getWebsite() {
	return website;
    }

}
