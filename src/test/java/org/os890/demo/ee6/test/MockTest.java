package org.os890.demo.ee6.test;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.apache.deltaspike.testcontrol.api.mock.DynamicMockManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.os890.demo.ee6.ds.backend.user.UserRepository;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by rafael-pestano on 09/04/2015.
 */
@RunWith(CdiTestRunner.class)
public class MockTest {

  @Inject
  private DynamicMockManager mockManager;

  @Inject
  private UserRepository userRepository; //will inject the mocked instance

  @Test
  public void shouldMockTest() {
    UserRepository mockedUserRepository = mock(UserRepository.class);
    when(userRepository.test()).thenReturn("mock");
    mockManager.addMock(mockedUserRepository);
    assertEquals(userRepository.test(), "mock");//is returning "test" instead of "mock"

  }

}
