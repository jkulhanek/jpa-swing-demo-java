package cvut.fel.kulhanek.hw7.model;

import javax.persistence.EntityManager;

public interface EntityContext extends AutoCloseable {
    EntityManager getManager();
}
