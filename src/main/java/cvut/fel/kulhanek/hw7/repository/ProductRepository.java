package cvut.fel.kulhanek.hw7.repository;
import cvut.fel.kulhanek.hw7.model.EntityContext;
import cvut.fel.kulhanek.hw7.model.EntityContextProvider;
import cvut.fel.kulhanek.hw7.model.Product;
import cvut.fel.kulhanek.hw7.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends Repository {
    public ProductRepository(EntityContextProvider factory) {
        super(factory);
    }

    private List<Product> productCache;
    public List<Product> getProductList() throws Exception {
        if(productCache != null){
            return new ArrayList<>(productCache);
        }

        List<Product> result = null;
        try(EntityContext context = this.open()){
            result = context.getManager()
                    .createQuery("SELECT p FROM Product AS p", Product.class)
                    .getResultList();
        }

       /* List<Product> result = new ArrayList<>();

        Product r = new Product();
        r.setId(1);
        r.setName("Test product");
        r.setProduct_number("ABC12");
        r.setPrice(15.5);
        r.setDescription("Sample long description");
        result.add(r);

        r = new Product();
        r.setId(2);
        r.setName("Test product 2");
        r.setProduct_number("ABC13");
        r.setPrice(15.5);
        r.setDescription("Sample lsdong description");
        result.add(r);

        this.productCache = result;*/

        // We do not want to use cache for now
        return result;
    }

    public Product getProductById(int id) throws Exception{
        if(this.productCache != null) {
            for (Product p : this.productCache) {
                if (p.getId() == id) {
                    return p;
                }
            }
        }
        else{
            try(EntityContext context = this.open()){
                return context.getManager()
                        .createQuery("SELECT p FROM Product p WHERE p.id = :id", Product.class)
                        .setParameter("id", id)
                        .getSingleResult();
            }
        }

        return null;
    }

    public boolean wasProductChanged(Product product) {
        return false;
    }

    public int save(Product product) throws Exception {
        try(EntityContext context = this.open()) {
            context.getManager().getTransaction().begin();

            if(product.getId() > 0){
                // this is not a new product
                product = context.getManager().merge(product);
            }

            context.getManager().persist(product);
            context.getManager().getTransaction().commit();
        }

        return product.getId();
    }

    public void deleteProduct(ProductViewModel productViewModel) throws Exception {
        try(EntityContext context = this.open()) {
            context.getManager().getTransaction().begin();
            Product product = context.getManager().find(Product.class, productViewModel.getId());
            context.getManager().remove(product);
            context.getManager().getTransaction().commit();
        }

        Product pr = null;
        if(productCache != null) {
            for (Product p : productCache) {
                if (p.getId() == productViewModel.getId()) {
                    pr = p;
                    break;
                }
            }

            this.productCache.remove(pr);
        }
    }
}
