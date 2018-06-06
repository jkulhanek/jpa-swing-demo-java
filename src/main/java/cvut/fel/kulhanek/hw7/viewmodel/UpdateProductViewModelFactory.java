package cvut.fel.kulhanek.hw7.viewmodel;

import cvut.fel.kulhanek.hw7.Presenter;
import cvut.fel.kulhanek.hw7.repository.CategoryRepository;
import cvut.fel.kulhanek.hw7.repository.ProductRepository;

public class UpdateProductViewModelFactory {
    private final SelectedCategoryViewModelFactory selectedCategoryViewModelFactory;
    private final CategoryRepository categoryRepository;
    private final Presenter presenter;
    private final ProductRepository productRepository;

    public UpdateProductViewModelFactory(SelectedCategoryViewModelFactory selectedCategoryViewModelFactory,
                                         CategoryRepository categoryRepository,
                                         Presenter presenter, ProductRepository productRepository){
        this.selectedCategoryViewModelFactory = selectedCategoryViewModelFactory;
        this.categoryRepository = categoryRepository;
        this.presenter = presenter;
        this.productRepository = productRepository;
    }

    public UpdateProductViewModel create(Integer productId){
        return new UpdateProductViewModel(this.selectedCategoryViewModelFactory, this.presenter, this.categoryRepository, this.productRepository, productId);
    }

    public UpdateProductViewModel create(){
        return new UpdateProductViewModel(this.selectedCategoryViewModelFactory, this.presenter, this.categoryRepository, this.productRepository, null);
    }
}
