package cvut.fel.kulhanek.hw7.viewmodel;

public class ProductViewModel {
    private final Integer id;
    private final String name;
    private final String productCode;

    public ProductViewModel(String name, String code, Integer id){
        this.name = name;
        this.productCode = code;
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString(){
        return name + " (" + this.productCode + ")";
    }
}
