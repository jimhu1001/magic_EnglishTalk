package com.english.rockGod.pc.web.dto;

/**
 * Created by jimHu on 16/3/28.
 */
public enum PermissionEnums {
    USER_QUERY(1, "查看产品信息", "permissionNormalRangeCal");

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRangeCal() {
        return rangeCal;
    }

    public void setRangeCal(String rangeCal) {
        this.rangeCal = rangeCal;
    }

    PermissionEnums(int code, String desc, String rangeCal) {
        this.code = code;
        this.desc = desc;
        this.rangeCal = rangeCal;
    }

    private int code;
    private String desc;
    private String rangeCal;

    public static String getRangeCal(Integer code) {
        for (PermissionEnums permissionEnums : PermissionEnums.values()) {
            if (code.equals(permissionEnums.getCode())) {
                return permissionEnums.getRangeCal();
            }
        }

        return null;
    }

    public static String getDescByCode(Integer code) {
        for (PermissionEnums permissionEnums : PermissionEnums.values()) {
            if (code.equals(permissionEnums.getCode())) {
                return permissionEnums.getDesc();
            }
        }

        return null;
    }


}

