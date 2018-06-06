package cvut.fel.kulhanek.hw7;

import cvut.fel.kulhanek.hw7.model.EntityContextProvider;
import cvut.fel.kulhanek.hw7.repository.CategoryRepository;
import cvut.fel.kulhanek.hw7.repository.ProductRepository;
import cvut.fel.kulhanek.hw7.viewmodel.HomeViewModel;
import cvut.fel.kulhanek.hw7.viewmodel.HomeViewModelFactory;
import cvut.fel.kulhanek.hw7.viewmodel.SelectedCategoryViewModelFactory;
import cvut.fel.kulhanek.hw7.viewmodel.UpdateProductViewModelFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ServiceContainer {
    private EntityContextProvider emf;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private Presenter presenter;

    public ServiceContainer(){
        emf = new EntityContextProvider();
        productRepository = new ProductRepository(emf);
        categoryRepository = new CategoryRepository(emf);
        presenter = new Presenter();

    }

    public <T> T resolve(Class<T> type){
        if(type == ProductRepository.class){
            return (T)this.productRepository;
        }
        else if(type == CategoryRepository.class){
            return (T)this.categoryRepository;
        }
        else if(type == Presenter.class){
            return (T)this.presenter;
        }
        else if(type == HomeViewModelFactory.class){
            return (T)new HomeViewModelFactory(
                    this.resolve(UpdateProductViewModelFactory.class),
                    this.resolve(Presenter.class),
                    this.resolve(ProductRepository.class));
        }
        else if (type == SelectedCategoryViewModelFactory.class){
            return (T)new SelectedCategoryViewModelFactory(
                    this.resolve(CategoryRepository.class),
                    this.resolve(Presenter.class));
        }
        else if(type == UpdateProductViewModelFactory.class){
            return (T)new UpdateProductViewModelFactory(
                    this.resolve(SelectedCategoryViewModelFactory.class),
                    this.resolve(CategoryRepository.class),
                    this.resolve(Presenter.class), productRepository);
        }
        else{
            throw new IllegalArgumentException("Type is not registered" + type.toString());
        }
    }

    public void dispose(){

    }


    private static ServiceContainer currentServiceContainer;
    public static ServiceContainer getCurrent(){
        if(currentServiceContainer == null){
            currentServiceContainer = new ServiceContainer();
        }

        return currentServiceContainer;
    }
}
