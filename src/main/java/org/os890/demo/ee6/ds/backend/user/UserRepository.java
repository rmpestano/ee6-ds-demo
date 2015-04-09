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
package org.os890.demo.ee6.ds.backend.user;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.os890.demo.ee6.ds.domain.user.User;

@ApplicationScoped
public class UserRepository {
    @Inject
    private EntityManager entityManager;

    public boolean isRegistered(String userName) {
        return findUser(userName) != null;
    }

    public User findUser(String userName) {
        List<User> result = entityManager.createNamedQuery("findUserByName", User.class)
            .setParameter("currentUser", userName)
            .getResultList();

        if (result != null && result.size() == 1) {
            return result.iterator().next();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<User> all(){
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Transactional
    public User save(User user) {
        if (user.isTransient()) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }

    public Long count(){
        return (Long)entityManager.createQuery("select count(*) from User u").getSingleResult();
    }


    @Transactional
    public void remove(User user) {
        if(user == null || user.getId() == null){
            throw new RuntimeException("provide a valid user to remove");
        }

        entityManager.remove(entityManager.find(User.class,user.getId()));
    }

    public String test(){
        return "test";
    }
}
