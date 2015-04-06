package org.os890.demo.ee6.test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by rafael-pestano on 06/04/2015.
 */
public class TestEntityManagerProducer  {

  private EntityManager entityManager;

  @Produces
  @ApplicationScoped
  public EntityManager exposeDependentEntityManager()
  {
    entityManager = Persistence.createEntityManagerFactory("TEST_PU").createEntityManager();
    return entityManager;
  }

  public void closeEntityManager(@Disposes EntityManager entityManager)
  {
    if (entityManager.isOpen())
    {
      entityManager.close();
    }
  }

}
