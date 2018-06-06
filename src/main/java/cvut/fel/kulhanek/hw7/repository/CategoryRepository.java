package cvut.fel.kulhanek.hw7.repository;

import cvut.fel.kulhanek.hw7.model.Category;
import cvut.fel.kulhanek.hw7.model.EntityContext;
import cvut.fel.kulhanek.hw7.model.EntityContextProvider;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends Repository {
    private List<Category> cached;

    public CategoryRepository(EntityContextProvider provider){ super(provider);}


    public List<Category> getCategories() throws Exception{
        if(this.cached != null){
            return new ArrayList<>(this.cached);
        }

        try(EntityContext context = this.open()){
            this.cached = context
                    .getManager()
                    .createQuery("SELECT c FROM Category c", Category.class)
                    .getResultList();
        }

        return new ArrayList<>(this.cached);
    }
}
