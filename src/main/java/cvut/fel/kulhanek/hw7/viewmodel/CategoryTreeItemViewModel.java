package cvut.fel.kulhanek.hw7.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class CategoryTreeItemViewModel {
    private List<CategoryTreeItemViewModel> children= new ArrayList<>();
    private String name;
    private int id;

    public CategoryTreeItemViewModel(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addChild(CategoryTreeItemViewModel child){
        this.children.add(child);
    }

    public List<CategoryTreeItemViewModel> getChildren(){
        return children;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
