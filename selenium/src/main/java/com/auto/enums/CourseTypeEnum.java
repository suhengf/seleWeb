package com.auto.enums;

import java.util.Objects;


public enum CourseTypeEnum {
    /**
     * 语文学科
     */
    CHINESE("00", "语文"),

    /**
     * 数学学科
     */
    MATHS("01", "数学"),

    /**
     * 英语学科
     */
    ENGLISH("02", "英语"),

    /**
     * 物理学科
     */
    PHYSICS("03", "物理"),

    /**
     * 化学学科
     */
    CHEMISTRY("04", "化学"),

    /**
     * 生物学科
     */
    BIOLOGY("05", "生物"),

    /**
     * 政治学科
     */
    POLITICS("06", "政治"),

    /**
     * 历史学科
     */
    HISTORY("07", "历史"),

    /**
     * 地理学科
     */
    GEOGRAPHY("08", "地理");

    /**
     * 学科编码
     */
    private String code;

    /**
     * 学科名称
     */
    private String name;

    CourseTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    /**
     * 根据学科代码获取学科名称
     *
     * @param code 学科代码
     * @return 结果
     */
    public static String getNameByCode(String code) {
        CourseTypeEnum[] courseTypeEnums = CourseTypeEnum.values();
        for (CourseTypeEnum courseTypeEnum : courseTypeEnums) {
            if (Objects.equals(courseTypeEnum.getCode(), code)) {
                return courseTypeEnum.getName();
            }
        }
        return null;
    }

}
