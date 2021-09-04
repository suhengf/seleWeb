package com.auto.validator;

import com.auto.annotation.IsDate;
import com.auto.utils.DateUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * 校验是否为合法的 时间格式
 *
 * @author little
 */
public class DateValidator implements ConstraintValidator<IsDate, String> {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);

    @Override
    public void initialize(IsDate isDate) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            // 获取指定的时间格式化formatter
            String formatter =
                    (String) ((ConstraintDescriptorImpl) ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintDescriptor()).getAnnotationDescriptor().getAttribute("formatter");
            // 如果时间传值为 04-31，DateUtils.toLocalDate()返回结果会是 04-30，
            // 所以需要比较一下转换为时间之后的值与原值是否相同，不同则格式输入有误
            // 如果这里formatter格式不是yyyy-MM-dd，那么需要判断转换之前是否是以转换之后的日结尾，因为不同的话也只是最后的日不同
            LocalDate date = DateUtils.toLocalDate(value,formatter);
            return value.endsWith(String.valueOf(date.getDayOfMonth()));
        } catch (Exception e) {
            LOGGER.error("时间校验异常",e);
            return false;
        }
    }
}