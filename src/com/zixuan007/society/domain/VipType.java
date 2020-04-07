package com.zixuan007.society.domain;

public enum VipType {
    VIP("VIP"),
    SVIP("SVIP");

    private String typeName;

    VipType(String typeName) {
        this.typeName=typeName;
    }

    @Override
    public String toString() {
        return "VipType{" +
                "typeName='" + typeName + '\'' +
                '}';
    }
}
