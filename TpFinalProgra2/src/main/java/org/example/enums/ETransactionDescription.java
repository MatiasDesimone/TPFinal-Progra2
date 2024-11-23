package org.example.enums;

public enum ETransactionDescription {
    ALQUILERES(1, "Alquileres"),
    APORTES_DE_CAPITAL(2, "Aportes de capital"),
    CUOTAS(3, "Cuotas"),
    EXPENSAS(4, "Expensas"),
    FACTURAS(5, "Facturas"),
    HABERES(6, "Haberes"),
    HONORARIOS(7, "Honorarios"),
    OPERACIONES_INMOBILIARIAS(8, "Operaciones inmobiliarias"),
    PAGO_DE_SERVICIOS(9, "Pago de servicios"),
    PRESTAMOS(10, "Prestamos"),
    SEGUROS(11, "Seguros"),
    SINIESTROS_DE_SEGUROS(12, "Siniestros de seguros"),
    VARIOS(13, "Varios");

    private final int typeId;
    private final String description;

    ETransactionDescription(int typeId, String description) {
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
