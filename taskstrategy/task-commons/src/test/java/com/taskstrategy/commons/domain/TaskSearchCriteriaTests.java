package com.taskstrategy.commons.domain;


import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.ArrayList;
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
public class TaskSearchCriteriaTests {

    @Test
    public void verifyPojo() {
        TaskSearchCriteria searchCriteria = new TaskSearchCriteria();
        assertNotNull(searchCriteria.getPriorityId());
        assertNotNull(searchCriteria.getTags());
        assertEquals("All", searchCriteria.getPriorityId());
        assertEquals(0, searchCriteria.getTags().size());

        searchCriteria.setPriorityId("X");
        assertEquals("X", searchCriteria.getPriorityId());
        assertEquals(0, searchCriteria.getTags().size());

        List<String> tags = new ArrayList<>();
        tags.add("1");
        tags.add("2");
        searchCriteria.setTags(tags);
        assertEquals("X", searchCriteria.getPriorityId());
        assertEquals(2, searchCriteria.getTags().size());
        assertEquals("1", searchCriteria.getTags().get(0));
        assertEquals("2", searchCriteria.getTags().get(1));
    }

}
