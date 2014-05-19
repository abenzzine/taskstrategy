package com.taskstrategy.commons.domain.constraints;

import com.taskstrategy.commons.util.DateFormatUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by brian on 1/31/14.
 */
public class ValidTaskDateValidator implements ConstraintValidator<ValidTaskDate, Date> {

    private Date baseDate;

    public void initialize(ValidTaskDate constraintAnnotation) {
        try {
            baseDate = DateFormatUtil.parseDate("01/01/2001");
        } catch (ParseException e) {
        }
    }

    public boolean isValid(Date object, ConstraintValidatorContext constraintContext) {
        return object != null && baseDate != null && baseDate.before(object);
    }

}
