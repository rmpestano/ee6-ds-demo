/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.os890.demo.ee6.test;

import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.testcontrol.api.TestControl;
import org.os890.demo.ee6.ds.backend.user.UserRepository;
import org.os890.demo.ee6.ds.domain.user.User;
import org.os890.demo.ee6.ds.view.config.Pages;
import org.os890.demo.ee6.ds.view.controller.user.RegistrationPage;
import org.os890.demo.ee6.ds.view.i18n.WebappMessageBundle;
import junit.framework.Assert;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.spi.scope.window.WindowContext;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

@RunWith(CdiTestRunner.class)
//@TestControl(projectStage = ProjectStage.UnitTest.class) also work with TestEntityManager
public class PageBeanTest
{
    @Inject
    private RegistrationPage registrationPage;

    @Inject
    private WindowContext windowContext;

    @Inject
    private WebappMessageBundle webappMessageBundle;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ContextControl contextControl;

    @Test
    public void registerUser()
    {
        final String userName = "gp";
        final String firstName = "Gerhard";
        final String lastName = "Petracek";
        this.windowContext.activateWindow("testWindow");

        this.registrationPage.getUser().setUserName(userName);
        this.registrationPage.getUser().setFirstName(firstName);
        this.registrationPage.getUser().setLastName(lastName);
        this.registrationPage.getUser().setPassword("123");

        Class<? extends Pages> targetPage = this.registrationPage.register();

        Assert.assertEquals(Pages.User.Login.class, targetPage);
        Assert.assertFalse(FacesContext.getCurrentInstance().getMessageList().isEmpty());
        Assert.assertEquals(webappMessageBundle.msgUserRegistered(userName), FacesContext.getCurrentInstance().getMessageList().iterator().next().getSummary());

        User user = this.userRepository.findUser(userName);
        Assert.assertNotNull(user);
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(lastName, user.getLastName());
    }

    @Test
    public void registerUserAndLogin()
    {
        final String userName = "tt";
        final String firstName = "Tom";
        final String lastName = "Tester";
        this.windowContext.activateWindow("testWindow");

        Assert.assertTrue(FacesContext.getCurrentInstance().getMessageList().isEmpty());

        this.registrationPage.getUser().setUserName(userName);
        this.registrationPage.getUser().setFirstName(firstName);
        this.registrationPage.getUser().setLastName(lastName);
        this.registrationPage.getUser().setPassword("123");

        Class<? extends Pages> targetPage = this.registrationPage.register();

        Assert.assertEquals(Pages.User.Login.class, targetPage);
        Assert.assertFalse(FacesContext.getCurrentInstance().getMessageList().isEmpty());
        Assert.assertEquals(webappMessageBundle.msgUserRegistered(userName), FacesContext.getCurrentInstance().getMessageList().iterator().next().getSummary());

        User user = this.userRepository.findUser(userName);
        Assert.assertNotNull(user);
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(lastName, user.getLastName());

        this.contextControl.stopContexts();
        this.contextControl.startContexts();
        this.windowContext.activateWindow("testWindow");

        Assert.assertTrue(FacesContext.getCurrentInstance().getMessageList().isEmpty());

        this.registrationPage.getUser().setUserName(userName);
        this.registrationPage.getUser().setFirstName(firstName);
        this.registrationPage.getUser().setLastName(lastName);
        this.registrationPage.getUser().setPassword("123");

        targetPage = this.registrationPage.login();
        Assert.assertEquals(Pages.InternalInfo.class, targetPage);
    }
}
