package nichy.beancafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nichy.beancafe.dto.OrderCreatedRequestDto;
import nichy.beancafe.dto.OrderStatusUpdateRequestDto;
import nichy.beancafe.entity.Order;
import nichy.beancafe.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreatedRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(dto));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestParam(required = false) Order.OrderStatus status){
        return ResponseEntity.ok(orderService.getAllOrders(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequestDto dto
            ){
        return ResponseEntity.ok(orderService.updateOrderStatus(id, dto.getStatus()));
    }











}
