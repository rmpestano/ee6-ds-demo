package org.os890.demo.ee6.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

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
    assertNotNull(u.getId());
    assertEquals(userRepository.count(), 1);
  }

  @After
  public void tearDown(){
    for (User user : userRepository.all()) {
      userRepository.remove(user);
    }
  }


  @Test
  public void shouldFindUser() {
    User user = userRepository.findUser("testUser");
    assertNotNull(user);
    assertEquals(user.getUserName(), "testUser");
  }

  @Test
  public void shouldRegisterUser(){
    User u = new User("new user","first","last");
    userRepository.save(u);
    assertTrue(userRepository.isRegistered("new user"));
  }

  @Test
  public void shouldFailToRemoveNonManagedUser(){
    User u = new User();
    String error = "";
    try {
      userRepository.remove(u);
    }catch (RuntimeException e){
      error = e.getMessage();
    }

    assertEquals(error,"provide a valid user to remove");

  }

}
