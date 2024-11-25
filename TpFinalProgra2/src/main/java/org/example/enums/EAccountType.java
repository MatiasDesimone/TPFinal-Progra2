package org.example.enums;

public enum EAccountType {
    CAJA_AHORRO(1, "Caja de ahorro."),
    CUENTA_CORRIENTE(2, "Cuenta corriente."),
    CUENTA_DOLARES(3, "Cuenta en dólares.");

    private final int typeId;
    private final String description;

    EAccountType(int typeId, String description) {
        this.typeId = typeId;
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getDescription() {
        return description;
    }
}
