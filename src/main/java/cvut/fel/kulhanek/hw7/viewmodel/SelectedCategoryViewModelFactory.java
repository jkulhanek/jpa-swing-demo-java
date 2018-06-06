package cvut.fel.kulhanek.hw7.viewmodel;

import cvut.fel.kulhanek.hw7.Presenter;
import cvut.fel.kulhanek.hw7.repository.CategoryRepository;

public class SelectedCategoryViewModelFactory {
    private final CategoryRepository categoryRepository;
    private final Presenter presenter;

    public SelectedCategoryViewModelFactory(CategoryRepository categoryRepository, Presenter presenter){

        this.categoryRepository = categoryRepository;
        this.presenter = presenter;
    }


    public SelectCategoryViewModel create(){
        return new SelectCategoryViewModel(categoryRepository, presenter);
    }
}
