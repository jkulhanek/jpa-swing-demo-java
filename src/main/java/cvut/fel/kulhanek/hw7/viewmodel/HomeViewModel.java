package cvut.fel.kulhanek.hw7.viewmodel;

import cvut.fel.kulhanek.hw7.Presenter;
import cvut.fel.kulhanek.hw7.model.Product;
import cvut.fel.kulhanek.hw7.repository.ProductRepository;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public final class HomeViewModel extends ViewModel {
    private final ProductRepository repository;
    private final Presenter presenter;
    private final UpdateProductViewModelFactory updateProductViewModelFactory;
    private List<Product> products;
    public HomeViewModel(ProductRepository repository, Presenter presenter, UpdateProductViewModelFactory updateProductViewModelFactory){
        this.repository = repository;
        this.presenter = presenter;
        this.updateProductViewModelFactory = updateProductViewModelFactory;
    }


    public List<ProductViewModel> getProducts(){
        try {
            List<ProductViewModel> productList = new ArrayList<>();
            List<Product> products = repository.getProductList();

            for (Product p : products) {
                productList.add(new ProductViewModel(p.getName(), p.getProduct_number(), p.getId()));
            }

            return productList;
        }
        catch(Exception ex){
            this.presenter.showError("Product list cannot be shown.");
            return new ArrayList<>();
        }
    }

    public void addProductAction(){

        UpdateProductViewModel vm = updateProductViewModelFactory.create();
        presenter.show(vm);

        ProductViewModel createdProduct = vm.getStoredProduct();
        if(createdProduct != null){
            this.notifyChange("products+", createdProduct);
        }
    }

    private ProductViewModel selectedProduct;

    public ProductViewModel getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductViewModel productViewModel) {
        boolean oldIsSelected = isProductSelected();
        if (this.selectedProduct == productViewModel) return;

        this.selectedProduct = productViewModel;

        this.notifyChange("selectedProduct", selectedProduct);

        if (isProductSelected() != oldIsSelected) {
            this.notifyChange("isProductSelected", isProductSelected());
        }
    }

    public void deleteProductAction(){
        ProductViewModel selectedProduct = getSelectedProduct();
        if(selectedProduct == null) return;

        try {
            this.repository.deleteProduct(selectedProduct);
            this.setSelectedProduct(null);
            this.notifyChange("products-", selectedProduct);
        }
        catch(Exception ex){
            this.presenter.showError("Unknown error occured");
        }
    }

    public void editProductAction(){
        ProductViewModel selectedProduct = getSelectedProduct();
        if(selectedProduct == null) return;

        UpdateProductViewModel vm = updateProductViewModelFactory.create(selectedProduct.getId());
        presenter.show(vm);

        ProductViewModel createdProduct = vm.getStoredProduct();
        if(createdProduct != null) {
            // invalidate the selected product
            this.setSelectedProduct(null);
            this.notifyChange("products[]", new AbstractMap.SimpleEntry<>(selectedProduct.getId(), createdProduct));
            this.setSelectedProduct(createdProduct);
        }
    }

    public void refresh() {
        this.setSelectedProduct(null);
        this.notifyChange("products", getProducts());
    }

    public boolean isProductSelected() {
        return selectedProduct != null;
    }
}

