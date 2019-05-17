package priv.cy.common.db.mapper.pojo;


import com.alibaba.excel.util.StringUtils;

import java.util.Collection;


public class CondDBField {

    private static final String UNDER_LINE = "_";
    private static final String PER_CENT = "%";
    private static final String REPLACE_PER_CENT = "\\\\%";
    private static final String REPLACE_UNDER_LINE = "\\\\_";

    private String fieldName;
    private Object condValue;
    private boolean isLike = false;
    private boolean isEqual = true;
    private boolean isNotEqual = false;
    private boolean isRangeBegin = false;
    private boolean isRangeEnd = false;
    private boolean isCollection = false;

    public CondDBField(String fieldName, Object condValue) {
        if (StringUtils.isEmpty(fieldName) || condValue == null) {
            throw new IllegalArgumentException("empty field or cond");
        }
        this.fieldName = fieldName;
        this.condValue = condValue;
        if (condValue instanceof Collection) {
            this.setCollection(true);
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getCondValue() {
        return condValue;
    }

    public void setCondValue(Object condValue) {
        this.condValue = condValue;
    }

    public boolean isLike() {
        return isLike;
    }

    public boolean isNotEqual() {
        return isNotEqual;
    }

    public void setLike(boolean like) {
        if (like) {
            isLike = like;
            isEqual = false;
            isNotEqual = false;
            isRangeBegin = false;

            isRangeEnd = false;
            isCollection = false;
        }

        this.condValue = escape(condValue.toString());
    }

    public boolean isEqual() {
        return isEqual;
    }

    public void setEqual(boolean equal) {
        if (equal) {
            isEqual = true;
            isNotEqual = false;
        } else {
            isEqual = false;
            isNotEqual = true;
        }
        isLike = false;
        isRangeBegin = false;
        isRangeEnd = false;
        isCollection = false;
    }

    public boolean isRangeBegin() {
        return isRangeBegin;
    }

    public void setRangeBegin(boolean rangeBegin) {
        if (rangeBegin) {
            isLike = false;
            isEqual = false;
            isNotEqual = false;
            isRangeBegin = rangeBegin;
            isRangeEnd = false;
            isCollection = false;
        }
    }

    public boolean isRangeEnd() {
        return isRangeEnd;
    }

    public void setRangeEnd(boolean rangeEnd) {
        if (rangeEnd) {
            isLike = false;
            isEqual = false;
            isNotEqual = false;
            isRangeBegin = false;
            isRangeEnd = rangeEnd;
            isCollection = false;
        }
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        if (collection) {
            isLike = false;
            isEqual = false;
            isNotEqual = false;
            isRangeBegin = false;
            isRangeEnd = false;
            isCollection = collection;
        }
    }

    /**
     * 对 % _ 进行转义
     *
     * @param param 需要转义的数据
     * @return 转义后的数据
     */
    private static String escape(String param) {
        try {
            if (param.startsWith(PER_CENT) && param.endsWith(PER_CENT) && param.length() >= 2) {
                param = param.substring(1, param.length() - 1);
                param = param.replaceAll(UNDER_LINE, REPLACE_UNDER_LINE);
                param = param.replaceAll(PER_CENT, REPLACE_PER_CENT);
                param = String.format("%%%s%%", param);
            } else if (param.startsWith(PER_CENT)) {
                param = param.substring(1, param.length());
                param = param.replaceAll(UNDER_LINE, REPLACE_UNDER_LINE);
                param = param.replaceAll(PER_CENT, REPLACE_PER_CENT);
                param = String.format("%%%s", param);
            } else if (param.endsWith(PER_CENT)) {
                param = param.substring(0, param.length() - 1);
                param = param.replaceAll(UNDER_LINE, REPLACE_UNDER_LINE);
                param = param.replaceAll(PER_CENT, REPLACE_PER_CENT);
                param = String.format("%s%%", param);
            }
        } catch (Exception e) {
            return param;
        }
        return param;
    }
}
