package com.ecommerce.order.controller;

import com.ecommerce.delivery.model.DeliveryBoy;
import com.ecommerce.delivery.service.DeliveryBoyService;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DeliveryBoyService deliveryBoyService;

    @Operation(summary = "Place a new order")
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.placeOrder(order));
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Get all orders of a customer")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    @Operation(summary = "Delete an order by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update order status")
    @PatchMapping("/{id}/order-status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable("id") UUID id,
                                                   @RequestParam("status") String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @Operation(summary = "Update payment status")
    @PatchMapping("/{id}/payment-status")
    public ResponseEntity<Order> updatePaymentStatus(@PathVariable("id") UUID id,
                                                     @RequestParam("status") String status) {
        return ResponseEntity.ok(orderService.updatePaymentStatus(id, status));
    }

    @GetMapping("/delivery-boy/{deliveryBoyId}")
    public ResponseEntity<List<Order>> getOrdersByDeliveryBoy(@PathVariable("deliveryBoyId") UUID deliveryBoyId) {
        return ResponseEntity.ok(orderService.getOrdersByDeliveryBoyId(deliveryBoyId));
    }

    @PostMapping("/{orderId}/confirm-payment")
    public ResponseEntity<String> confirmPayment(@PathVariable("orderId") UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setPaymentStatus("paid");
        order.setUpdatedAt(Instant.now());

        DeliveryBoy deliveryBoy = deliveryBoyService.assignAvailableDeliveryBoy();
        deliveryBoy.setAvailable(false);
        deliveryBoy.setUpdatedAt(Instant.now());
        deliveryBoyService.updateDeliveryBoy(deliveryBoy);

        order.setDeliveryBoy(deliveryBoy);
        orderService.save(order);

        return ResponseEntity.ok("Payment confirmed and delivery boy assigned.");
    }
}
