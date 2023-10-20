package com.trendyol.shipment;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Basket {

    private List<Product> products;
    private static final int BASKET_SHIPMENT_SIZE_THRESHOLD = 3;
    private final EnumMap<ShipmentSize, Integer> productSizeCounts = new EnumMap<>(ShipmentSize.class);

    public ShipmentSize getShipmentSize() {
        initializeProductSizeCounts();
        ShipmentSize determinedPackageSize = findLargestProductSize();

        for (Map.Entry<ShipmentSize, Integer> entry : productSizeCounts.entrySet()) {
            int count = entry.getValue();
            ShipmentSize size = entry.getKey();

            if(count >= BASKET_SHIPMENT_SIZE_THRESHOLD)
            {
                determinedPackageSize = size.incrementShippingSize();
            }
        }
        return determinedPackageSize;
    }

    private void initializeProductSizeCounts() {
        for (Product product : getProducts()) {
            ShipmentSize currentProductSize = product.getSize();
            int newCount = productSizeCounts.getOrDefault(currentProductSize, 0) + 1;
            productSizeCounts.put(currentProductSize, newCount);
        }
    }

    private ShipmentSize findLargestProductSize() {
        AtomicReference<ShipmentSize> largestProductSize = new AtomicReference<>(ShipmentSize.getSmallestShipmentSize());

        getProducts().forEach(product -> {
            if (product.isLargerSizeThan(largestProductSize.get())) largestProductSize.set(product.getSize());
        });

        return largestProductSize.get();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
