package cvut.fel.kulhanek.hw7.viewmodel;

import cvut.fel.kulhanek.hw7.Presenter;
import cvut.fel.kulhanek.hw7.model.Category;
import cvut.fel.kulhanek.hw7.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCategoryViewModel extends ViewModel {
    private List<CategoryTreeItemViewModel> categoryTree;
    private CategoryTreeItemViewModel selectedCategory;
    private boolean wasSelected = false;
    private final CategoryRepository categoryRepository;
    private final Presenter presenter;

    public SelectCategoryViewModel(CategoryRepository categoryRepository, Presenter presenter){

        this.categoryRepository = categoryRepository;
        this.presenter = presenter;
    }

    public List<CategoryTreeItemViewModel> getCategoryTree(){
        try {
            if (categoryTree == null) {
                createCategoryTree();
            }

            return categoryTree;
        }catch (Exception ex){
            this.presenter.showError("The category list could not be downloaded. Please try later.");
            return new ArrayList<>();
        }
    }

    private void createCategoryTree() throws Exception{
        List<Category> categories = this.categoryRepository.getCategories();
        Map<Integer, CategoryTreeItemViewModel> models = new HashMap<>();
        List<CategoryTreeItemViewModel> result = new ArrayList<>();

        for(Category c : categories){
            models.put(c.getId(), new CategoryTreeItemViewModel(c.getName(), c.getId()));
        }

        for(Category c : categories){
            if(c.getParentCategoryId() != null){
                models.get(c.getParentCategoryId()).addChild(models.get(c.getId()));
            }
            else{
                result.add(models.get(c.getId()));
            }
        }

        this.categoryTree = result;
    }

    public void setSelectedCategory(CategoryTreeItemViewModel selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public CategoryTreeItemViewModel getSelectedCategory(){
        return this.selectedCategory;
    }

    public boolean isCategorySelected(){
        return getSelectedCategory() != null;
    }

    public boolean isSelected(){
        return wasSelected;
    }

    public void setSelected(boolean selected){
        wasSelected = selected;
    }
}
