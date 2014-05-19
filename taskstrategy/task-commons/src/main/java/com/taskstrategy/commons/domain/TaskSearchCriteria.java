package com.taskstrategy.commons.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/14/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskSearchCriteria {

    private String priorityId;
    private List<String> tags;

    public TaskSearchCriteria() {
        priorityId = "All";
        tags = new ArrayList<>();
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
