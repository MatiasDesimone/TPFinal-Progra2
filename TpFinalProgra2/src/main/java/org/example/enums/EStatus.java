package org.example.enums;

public enum EStatus {
    APPROVED(1, "Aprobado"),
    REJECTED(2, "Rechazado");

    private final int statusId;
    private final String description;

    EStatus(int value, String description) {
        this.statusId = value;
        this.description = description;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getDescription() {
        return description;
    }
}
