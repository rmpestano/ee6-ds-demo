package org.os890.demo.ee6.test;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by rafael-pestano on 06/04/2015.
 */
@ApplicationScoped
public class TestEntityManagerProducer  {

  private EntityManager entityManager;
  private EntityManagerFactory emf;

  @PostConstruct
  public void initDB(){
    emf = Persistence.createEntityManagerFactory("TEST_PU");
  }

  @Produces
  @RequestScoped
  public EntityManager exposeDependentEntityManager()
  {
    entityManager = emf.createEntityManager();
    return entityManager;
  }

  public void closeEntityManager(@Disposes EntityManager entityManager)
  {
    if (entityManager != null && entityManager.isOpen())
    {
      entityManager.close();
    }
  }

}
