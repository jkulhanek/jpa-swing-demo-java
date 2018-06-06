package cvut.fel.kulhanek.hw7.viewmodel;

import cvut.fel.kulhanek.hw7.Presenter;
import cvut.fel.kulhanek.hw7.model.Category;
import cvut.fel.kulhanek.hw7.model.Product;
import cvut.fel.kulhanek.hw7.repository.CategoryRepository;
import cvut.fel.kulhanek.hw7.repository.ProductRepository;

import java.util.*;

public class UpdateProductViewModel extends ViewModel {
    private Integer productId;

    private List<CategoryTreeItemViewModel> categories = new ArrayList<>();
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final SelectedCategoryViewModelFactory selectedFactory;
    private final Presenter presenter;

    private CategoryTreeItemViewModel selectedCategory = null;

    public UpdateProductViewModel(SelectedCategoryViewModelFactory selectedFactory,
                                  Presenter presenter,
                                  CategoryRepository categoryRepository,
                                  ProductRepository productRepository, Integer productId){
        this.selectedFactory = selectedFactory;
        this.presenter = presenter;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productId = productId;

        this.load();
    }

    Product product;

    private void load() {
        try {
            if (this.productId != null) {
                product = this.productRepository.getProductById(this.productId);
                if (product == null) {
                    this.presenter.showError("The product does not exists. A new one will be created.");
                    this.productId = null;
                }
            }

            if (product == null)
                return;

            this.description = product.getDescription();
            this.name = product.getName();
            this.price = product.getPrice();
            this.productCode = product.getProduct_number();

            this.categories = new ArrayList<>();
            Set<Category> origCategories = product.getCategories();
            if (origCategories != null) {
                for (Category c : origCategories) {
                    this.categories.add(new CategoryTreeItemViewModel(c.getName(), c.getId()));
                }
            }
        }
        catch (Exception ex){
            this.presenter.showError("Product detail could not be loaded");
            return;
        }
    }

    public boolean canModifyProductCode(){
        return this.productId == null;
    }

    private boolean validateOnChange = false;

    private boolean stored = false;
    public void save(){
        try {
            if (product != null) {
                // TODO: implement this
            /*ProductChangedResult pr = this.productRepository.getProductChanges(product);

            if(){
                // Do we want to override the changes?
                presenter.askYesNo("The product was changed while you modified it. Do you want to rewrite it?")
            }*/
            }

            if (product == null) {
                product = new Product();
            }

            this.bindCategories(product);
            product.setName(this.name);
            product.setPrice(this.price);
            product.setDescription(this.description);
            product.setProduct_number(this.productCode);
            this.productId = productRepository.save(product);
            this.stored = true;
        }
        catch(Exception ex){
            this.presenter.showError("Changed could not be saved");
        }
    }

    private void bindCategories(Product product) throws Exception{
        Set<Integer> categoriesToAdd = new HashSet<>();
        Map<Integer,Category> oldCategories = new HashMap<>();
        for(CategoryTreeItemViewModel m : this.categories){
            categoriesToAdd.add(m.getId());
        }

        if(this.product != null) {
            for (Category c:
                 this.product.getCategories()) {
                oldCategories.put(c.getId(), c);
            }
        }

        int addedCategories = 0;

        for(Category c : categoryRepository.getCategories()){
            boolean wasOnProduct = oldCategories.containsKey(c.getId());
            if(categoriesToAdd.contains(c.getId())){
                if(!wasOnProduct) {
                    product.addCategory(c);
                }

                addedCategories++;
            }
            else{
                if(wasOnProduct){
                    product.removeCategory(oldCategories.get(c.getId()));
                }
            }
        }

        if(addedCategories != categories.size()){
            presenter.showError("Some categories were removed while you edited the entity. Those will not be added.");
        }
    }

    public void addCategoryAction(){
        SelectCategoryViewModel vm = selectedFactory.create();
        presenter.show(vm);
        
        if(vm.isSelected()){
            this.addCategory(vm.getSelectedCategory().getId());
        }
    }

    private void addCategory(int id) {
        try {
            Category category = null;
            for (Category cat : categoryRepository.getCategories()) {
                if (cat.getId() == id) {
                    category = cat;
                    break;
                }
            }

            if (category == null) {
                presenter.showError("Selected category no longer exists");
                return;
            }

            CategoryTreeItemViewModel newCat = new CategoryTreeItemViewModel(category.getName(), category.getId());
            this.categories.add(newCat);
            this.notifyChange("categories+", newCat);
        }
        catch (Exception ex){
            presenter.showError("Could not add the category.");
            return;
        }
    }

    public boolean isRemoveFromCategoryVisible(){
        return this.selectedCategory != null;
    }

    public void removeCategoryAction(){
        if(!isRemoveFromCategoryVisible()) return;

        CategoryTreeItemViewModel oldCategory = selectedCategory;
        this.categories.remove(selectedCategory);

        this.setSelectedCategory(null);
        this.notifyChange("categories-", oldCategory);
    }

    public CategoryTreeItemViewModel getSelectedCategory() {
         return selectedCategory;
    }

    public void setSelectedCategory(CategoryTreeItemViewModel selectedCategory) {
        boolean oldRemoveVisible = isRemoveFromCategoryVisible();
        if(this.selectedCategory == selectedCategory) return;

        this.selectedCategory = selectedCategory;

        this.notifyChange("selectedCategory", selectedCategory);

        if(isRemoveFromCategoryVisible() != oldRemoveVisible){
            this.notifyChange("isRemoveFromCategoryVisible", isRemoveFromCategoryVisible());
        }
    }

    private String name;
    private String productCode;
    private String description;
    private Double price;


    public String getName(){
        return (name != null)? name : "";
    }

    public String getProductCode(){
        return (productCode != null)? productCode : "";
    }

    public String getDescription(){
        return (description != null)? description : "";
    }

    public String getPrice(){
        return (price == null)? "" : price.toString();
    }

    public void setName(String name){
        if(validateOnChange) {
            if (name == null || name.isEmpty()) {
                presenter.showError("Name code cannot be empty");
                return;
            }
        }

        if((this.name != null && !this.name.equals(name)) || (this.name == null && name != null)){
            this.name = name;
            this.notifyChange("name", name);
        }
    }

    public void setProductCode(String code){
        if(validateOnChange) {
            if (code == null || code.isEmpty()) {
                presenter.showError("Product code cannot be empty");
                return;
            }
        }

        if((this.productCode != null && !this.productCode.equals(code)) || (this.productCode == null && code != null)){
            this.productCode = code;
            this.notifyChange("productCode", code);
        }
    }

    public void setDescription(String description){

        if(validateOnChange) {
            if (description == null || description.isEmpty()) {
                presenter.showError("Description cannot be empty");
                return;
            }
        }

        if((this.description != null && !this.description.equals(description)) || (this.description == null && description != null)){
            this.description = description;
            this.notifyChange("description", description);
        }
    }

    public boolean validate(){
        if(description == null || description.isEmpty()){
            presenter.showError("Description cannot be empty");
            return false;
        }

        if(name == null || name.isEmpty()){
            presenter.showError("Name code cannot be empty");
            return false;
        }

        if(productCode == null || productCode.isEmpty()){
            presenter.showError("Product code cannot be empty");
            return false;
        }

        if(price == null){
            presenter.showError("Price cannot be empty");
            return false;
        }

        if(price < 0){
            presenter.showError("Price cannot be negative");
            return false;
        }

        return true;
    }

    public void setPrice(String priceStr){
        Double price = null;
        if(priceStr != null && !priceStr.isEmpty()){
            try {
                price = Double.parseDouble(priceStr);
            }
            catch(NumberFormatException ex){
                presenter.showError("Price must be a number");
                return;
            }
        }

        if(validateOnChange) {
            if (price == null) {
                presenter.showError("Price cannot be empty");
                return;
            }

            if (price < 0) {
                presenter.showError("Price cannot be negative");
                return;
            }
        }

        if((this.price != null && !this.price.equals(price)) || (this.price == null && price != null)){
            this.price = price;
            this.notifyChange("price", this.getPrice());
        }
    }

    public ProductViewModel getStoredProduct() {
        if(this.stored){
            return new ProductViewModel(this.getName(), this.getProductCode(), this.productId);
        }
        return null;
    }

    public List<CategoryTreeItemViewModel> getCategories() {
        return this.categories;
    }
}
