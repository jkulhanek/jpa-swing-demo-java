package cvut.fel.kulhanek.hw7.model;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    public Category(){

    }

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }

    @GeneratedValue
    @Column(name = "category_id")
    @Id
    private int id;

    @Column(nullable = true)
    private Integer parent_category_id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = true)
    @PrimaryKeyJoinColumn(name = "parent_category_id", referencedColumnName = "category_id")
    private Category parent;

    public int getId() {
        return id;
    }

    public Integer getParentCategoryId() {
        return parent_category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
        this.parent_category_id = parent.id;
    }
}
