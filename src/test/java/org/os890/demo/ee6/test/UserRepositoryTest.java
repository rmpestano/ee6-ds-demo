package org.os890.demo.ee6.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.TestControl;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.os890.demo.ee6.ds.backend.user.UserRepository;
import org.os890.demo.ee6.ds.domain.user.User;

/**
 * Created by rafael-pestano on 06/04/2015.
 */
@RunWith(CdiTestRunner.class)
@TestControl(projectStage = ProjectStage.UnitTest.class)
public class UserRepositoryTest {


  @Inject
  UserRepository userRepository;


  @Before
  public void tearUp(){
    User u = new User("testUser","first","last");
    userRepository.save(u);
    assertFalse(u.isTransient());
  }

  @After
  public void tearDown(){
    em.remove(userRepository.findUser("testUser"));
  }


  @Test
  public void shouldFindUser() {
    User user = userRepository.findUser("testUser");
    assertNotNull(user);
    assertEquals(user.getUserName(),"testUser");
  }




}
