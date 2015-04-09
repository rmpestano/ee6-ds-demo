package org.os890.demo.ee6.test;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.testcontrol.api.TestControl;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.os890.demo.ee6.ds.backend.user.UserRepository;
import org.os890.demo.ee6.ds.backend.user.UserRepositoryData;
import org.os890.demo.ee6.ds.domain.user.User;

/**
 * Created by rafael-pestano on 06/04/2015.
 */
@RunWith(CdiTestRunner.class)
@TestControl(projectStage = ProjectStage.UnitTest.class)
public class UserRepositoryDataTest {

  @Inject
  UserRepositoryData userRepositoryData;

  @Inject
  UserRepository userRepository;

  @Test
  public void shouldInserUser() {
    long countBefore =  userRepositoryData.count();
    User u = new User("testUser", "first", "last");
    userRepository.save(u);
    assertNotNull(u.getId());
    assertEquals(userRepository.count(), Long.valueOf(countBefore +1));
  }

  @Test
  public void shouldInserUserUsingDeltaspikeData() {
    long countBefore =  userRepositoryData.count();
    User u = new User("testUser", "first", "last");
    userRepositoryData.save(u);
    assertNotNull(u.getId());
    assertEquals(userRepositoryData.count(), Long.valueOf(countBefore +1));
  }

}
