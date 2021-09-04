package com.auto.utils;

import com.auto.enums.EducationBusinessErrorCodeEnum;
import com.auto.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:spring实战
 **/
public class DateUtils {

    /**
     * 日志管理组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String Y_M_D = "yyyy-MM-dd";
    public static final int Y_M_D_LENGTH = 10;
    public static final int FULL_TIME_LENGTH = 19;
    public static final String Y_M = "yyyy-MM";

    /**
     * 计算两个时间相隔年份
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 结果
     */
    public static int betweenOfYear(Object startDate,Object endDate) {
        if (startDate == null || endDate == null){
            throw new BusinessException(EducationBusinessErrorCodeEnum.PARAMETER_NOT_EMPTY.getMsg());
        }
        LocalDate startLocalDate = toLocalDate(startDate,null);
        LocalDate endLocalDate = toLocalDate(endDate,null);
        return Period.between(startLocalDate,endLocalDate).getYears();
    }

    /**
     * 计算两个时间相隔天数
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     * @return 结果
     */
    public static long betweenOfDay(Object startDateTime,Object endDateTime) {
        if (startDateTime == null || endDateTime == null){
            throw new BusinessException(EducationBusinessErrorCodeEnum.PARAMETER_NOT_EMPTY.getMsg());
        }
        LocalDateTime startLocalDateTime = toLocalDateTime(startDateTime,null);
        LocalDateTime endLocalDateTime = toLocalDateTime(endDateTime,null);
        return Duration.between(startLocalDateTime,endLocalDateTime).toDays();
    }

    /**
     * 将时间转换为LocalDate对象
     * @param dateTimeObject 要转换的时间对象
     * @param formatter 如果要转换的时间对象是字符串时，指定转换格式
     * @return 转换结果
     */
    private static LocalDateTime toLocalDateTime(Object dateTimeObject,String formatter) {
        if (dateTimeObject instanceof String){
            String dateTimeString = (String) dateTimeObject;
            if (!StringUtils.isEmpty(formatter)){
                return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(formatter));
            }
            if (dateTimeString.length() == FULL_TIME_LENGTH){
                return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(FULL_TIME_SPLIT_PATTERN));
            }
        }else if (dateTimeObject instanceof LocalDateTime){
            return ((LocalDateTime) dateTimeObject);
        }
        throw new BusinessException(EducationBusinessErrorCodeEnum.UNKNOWN_DATE_FORMAT.getMsg());
    }

    /**
     * 将时间转换为LocalDate对象
     * @param dateObject 要转换的时间对象
     * @param formatter 如果要转换的时间对象是字符串时，指定转换格式
     * @return 转换结果
     */
    public static LocalDate toLocalDate(Object dateObject,String formatter) {
        if (dateObject instanceof String){
            String dateString = (String) dateObject;
            if (!StringUtils.isEmpty(formatter)){
                try {
                    // 先尝试转换为LocalDateTime
                    return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(formatter)).toLocalDate();
                }catch (RuntimeException e){
                    try {
                        // 再尝试转换为LocalDate
                        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(formatter));
                    }catch (RuntimeException e1){
                        // 无法转换为LocalDateTime和LocalDate时，抛出异常
                        LOGGER.error("时间转换异常，指定的时间格式为：{}",formatter,e1);
                        throw new BusinessException(EducationBusinessErrorCodeEnum.UNKNOWN_DATE_FORMAT.getMsg());
                    }
                }
            }
            if (dateString.length() == Y_M_D_LENGTH) {
                return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(Y_M_D));
            }else if (dateString.length() == FULL_TIME_LENGTH){
                return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(FULL_TIME_SPLIT_PATTERN)).toLocalDate();
            }
        }else if (dateObject instanceof LocalDate){
            return (LocalDate) dateObject;
        }else if (dateObject instanceof LocalDateTime){
            return ((LocalDateTime) dateObject).toLocalDate();
        }
        throw new BusinessException(EducationBusinessErrorCodeEnum.UNKNOWN_DATE_FORMAT.getMsg());
    }


}
