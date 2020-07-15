package com.zixuan007.society.domain;

/**
 * @author zixuan007
 */

public enum PrivilegeType {
    /**
     * 普通特权
     */
    VIP("VIP"),
    /**
     * 高级特权
     */
    SVIP("SVIP");

    private String typeName;

    PrivilegeType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "VipType{" +
                "typeName='" + typeName + '\'' +
                '}';
    }
}
