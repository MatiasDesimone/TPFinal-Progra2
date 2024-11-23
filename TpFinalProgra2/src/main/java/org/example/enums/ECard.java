package org.example.enums;

public enum ECard {
    DEBIT(1, "Tarjeta de débito"),
    CREDIT(2, "Tarjeta de crédito");

    private final int typeId;
    private final String description;

    ECard(int value, String description) {
        this.typeId = value;
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getDescription() {
        return description;
    }
}
