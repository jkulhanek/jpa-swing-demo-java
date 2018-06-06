package cvut.fel.kulhanek.hw7.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Closeable;
import java.io.IOException;

public class EntityContextProvider implements Closeable {
    private static String CONTEXT_NAME = "EshopContext";

    private EntityManagerFactory contextFactory = null;
    protected  EntityManagerFactory getFactory(){
        if(contextFactory == null){
            contextFactory = Persistence.createEntityManagerFactory(CONTEXT_NAME);
        }

        return contextFactory;
    }

    public EntityContext getContext(){
        return new EntityContext() {
            @Override
            public void close() throws Exception {
                if(this.context != null) { this.context.close(); this.context = null; }
            }

            private EntityManager context = null;


            @Override
            public EntityManager getManager() {
                if(this.context == null){
                    contextFactory = getFactory();
                    context = contextFactory.createEntityManager();
                }

                return this.context;
            }
        };
    }

    @Override
    public void close() throws IOException {
        if(contextFactory != null) { contextFactory.close();contextFactory = null; }
    }
}
