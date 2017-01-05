package project.entity;

public abstract class AbsEntity {

    protected Long id;

    public AbsEntity(Long id) {
	super();
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

}
