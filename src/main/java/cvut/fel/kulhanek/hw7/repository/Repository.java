package cvut.fel.kulhanek.hw7.repository;

import cvut.fel.kulhanek.hw7.model.EntityContext;
import cvut.fel.kulhanek.hw7.model.EntityContextProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Repository {
    private final EntityContextProvider factory;

    public Repository(EntityContextProvider factory){
        this.factory = factory;
    }

    public EntityContext open() {
        return factory.getContext();
    }
}
