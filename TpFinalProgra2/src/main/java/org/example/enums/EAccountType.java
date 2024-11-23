package org.example.enums;

public enum EAccountType {
    CAJA_AHORRO(1, "Caja de ahorro.", 0.0),
    CUENTA_CORRIENTE(2, "Cuenta corriente.", 3000.0),
    CUENTA_DOLARES(3, "Cuenta en dólares.", 1.5);

    private final int typeId;
    private final String description;
    private final double maintenanceFee;

    EAccountType(int typeId, String description, double maintenanceFee) {
        this.typeId = typeId;
        this.description = description;
        this.maintenanceFee = maintenanceFee;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getDescription() {
        return description;
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }
}
