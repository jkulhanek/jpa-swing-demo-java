package cvut.fel.kulhanek.hw7.viewmodel;

import cvut.fel.kulhanek.hw7.Presenter;
import cvut.fel.kulhanek.hw7.repository.ProductRepository;

public class HomeViewModelFactory {

    private final UpdateProductViewModelFactory updateProductViewModelFactory;
    private final Presenter presenter;
    private final ProductRepository productRepository;

    public HomeViewModelFactory(UpdateProductViewModelFactory updateProductViewModelFactory,
                                Presenter presenter,
                                ProductRepository productRepository){
        this.updateProductViewModelFactory = updateProductViewModelFactory;
        this.presenter = presenter;

        this.productRepository = productRepository;
    }

    public HomeViewModel create(){
        return new HomeViewModel(productRepository, presenter, updateProductViewModelFactory);
    }
}
