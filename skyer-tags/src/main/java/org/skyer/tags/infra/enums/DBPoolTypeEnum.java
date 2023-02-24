package org.skyer.tags.infra.enums;

/**
 * 
 * 连接池类型
 * 
 * @author
 */
public enum DBPoolTypeEnum {
    /**
     * C3p0
     */
    C3P0("C3p0"),

    /**
     * Dbcp
     */
    DBCP2("Dbcp2"),

    /**
     * Druid
     */
    DRUID("Druid"),

    /**
     * No
     */
    NO("No");

    private final String value;

    DBPoolTypeEnum(final String value) {
        this.value = value;
    }

    public static DBPoolTypeEnum valueOf2(String arg) {
        switch (arg) {
            case "C3p0":
                return C3P0;
            case "Dbcp2":
                return DBCP2;
            case "Druid":
                return DRUID;
            case "No":
                return NO;
            default:
                return NO;
        }
    }

    public static boolean isInEnum(String value) {
        for (DBPoolTypeEnum dbPoolType : DBPoolTypeEnum.values()) {
            if (dbPoolType.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return this.value;
    }
}
