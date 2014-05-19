package com.taskstrategy.commons.domain;


import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/19/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserTests {

    @Test
    public void verifyPojo() {
        User user = new User("user.email@test.com", null, null);
        assertNotNull(user.getId());
        user.setId("ID1");
        Date date = new Date(8978943789L);
        user.setCreateDate(date);
        Date lastModified = new Date(8978943790L);
        user.setLastModifiedDate(lastModified);

        assertEquals("ID1", user.getId());
        assertEquals("user.email@test.com", user.getUsername());
        assertEquals(8978943789L, user.getCreateDate().getTime());
        assertEquals(8978943790L, user.getLastModifiedDate().getTime());


        user = new User("user.email@test.com1", null, null);
        assertNotNull(user.getId());
        assertEquals("user.email@test.com1", user.getUsername());

        user = new User("user.email@test.com1", null, null);
        assertNotNull(user.getId());
        assertEquals("user.email@test.com1", user.getUsername());

        user = new User("user.email@test.com1", null, null);
        assertNotNull(user.getId());
        assertEquals("user.email@test.com1", user.getUsername());

        user = new User("FirstN1", "LastN1", null);
        assertNotNull(user.getId());
        assertEquals("FirstN1", user.getUsername());
    }
}
