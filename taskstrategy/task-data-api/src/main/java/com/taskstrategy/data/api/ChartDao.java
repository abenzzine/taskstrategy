package com.taskstrategy.data.api;

import com.taskstrategy.commons.domain.ChartTaskData;

/**
 * Created by brian on 1/30/14.
 */
public interface ChartDao {

    ChartTaskData getTaskChartInfo(String userId);
}
