package com.trendyol.shipment;

public enum ShipmentSize {

    SMALL,
    MEDIUM,
    LARGE,
    X_LARGE;

    public ShipmentSize incrementShippingSize() {
        ShipmentSize[] shipmentSizes = ShipmentSize.values();
        int currentIndex = this.ordinal();
        int nextIndex = currentIndex + 1;

        return (nextIndex < shipmentSizes.length) ? shipmentSizes[nextIndex] : shipmentSizes[shipmentSizes.length - 1];
    }

    public boolean isLargerThan(ShipmentSize otherSize) {
        return this.ordinal() > otherSize.ordinal();
    }

    public static ShipmentSize getSmallestShipmentSize() {
        return values()[0];
    }
}
