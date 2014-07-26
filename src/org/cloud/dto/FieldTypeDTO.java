package org.cloud.dto;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

/**
 * Created by reza on 7/24/14.
 */

public class FieldTypeDTO {
    public String funcName;
    public Integer argNum;
    public String fieldType;
    public String funcPrototype;
    public String description;

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public Integer getArgNum() {
        return argNum;
    }

    public void setArgNum(Integer argNum) {
        this.argNum = argNum;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFuncPrototype() {
        return funcPrototype;
    }

    public void setFuncPrototype(String funcPrototype) {
        this.funcPrototype = funcPrototype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String TOString()
    {

        return funcName+" "+funcPrototype+" "+argNum+" "+fieldType;

    }
    public FieldTypeDTO() {
    }
}
