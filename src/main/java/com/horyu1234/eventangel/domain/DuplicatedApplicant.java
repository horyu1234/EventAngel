package com.horyu1234.eventangel.domain;

public class DuplicatedApplicant {
    private String columnName;
    private int duplicatedCount;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getDuplicatedCount() {
        return duplicatedCount;
    }

    public void setDuplicatedCount(int duplicatedCount) {
        this.duplicatedCount = duplicatedCount;
    }
}
