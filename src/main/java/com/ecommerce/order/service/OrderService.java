package com.ecommerce.order.service;

import com.ecommerce.delivery.model.DeliveryBoy;
import com.ecommerce.delivery.service.DeliveryBoyService;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryBoyService deliveryBoyService;

    public Order placeOrder(Order order) {
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        double total = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);

        order.setOrderStatus("processing");
        order.setPaymentStatus("pending");

        // Save initial order (without delivery boy)
        Order savedOrder = orderRepository.save(order);

        // Check payment status
        if ("paid".equalsIgnoreCase(order.getPaymentStatus())) {
            // Assign available delivery boy
            DeliveryBoy deliveryBoy = deliveryBoyService.assignAvailableDeliveryBoy();
            deliveryBoy.setAvailable(false);
            deliveryBoy.setUpdatedAt(Instant.now());
            deliveryBoyService.updateDeliveryBoy(deliveryBoy);

            // Attach to order
            savedOrder.setDeliveryBoy(deliveryBoy);
            savedOrder.setUpdatedAt(Instant.now());

            return orderRepository.save(savedOrder);
        }

        return savedOrder;
    }


    public List<Order> getOrdersByCustomer(UUID customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id.toString())
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id.toString());
    }

    public Order updateOrderStatus(UUID orderId, String status) {
        Order order = getOrderById(orderId);
        List<String> validStatuses = List.of("processing", "shipped", "delivered", "cancelled");

        if (!validStatuses.contains(status.toLowerCase())) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        order.setOrderStatus(status.toLowerCase());
        order.setUpdatedAt(Instant.now());

        if ((status.equalsIgnoreCase("cancelled") || status.equalsIgnoreCase("delivered"))
                && order.getDeliveryBoy() != null) {
            deliveryBoyService.markAvailable(order.getDeliveryBoy().getId());
        }

        if ("delivered".equalsIgnoreCase(status)) {
            order.setDeliveredAt(Instant.now());
        }

        return orderRepository.save(order);
    }

    public Order updatePaymentStatus(UUID orderId, String status) {
        Order order = getOrderById(orderId);
        order.setPaymentStatus(status.toLowerCase());
        order.setUpdatedAt(Instant.now());

        // If payment is successful and no delivery boy assigned
        if ("paid".equalsIgnoreCase(status) && order.getDeliveryBoy() == null) {
            DeliveryBoy deliveryBoy = deliveryBoyService.assignAvailableDeliveryBoy();
            deliveryBoy.setAvailable(false);
            deliveryBoy.setUpdatedAt(Instant.now());
            deliveryBoyService.updateDeliveryBoy(deliveryBoy);

            order.setDeliveryBoy(deliveryBoy);
        }

        return orderRepository.save(order);
    }


    public List<Order> getOrdersByDeliveryBoyId(UUID deliveryBoyId) {
        return orderRepository.findByDeliveryBoy_Id(deliveryBoyId);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
