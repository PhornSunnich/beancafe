package nichy.beancafe.service;

import lombok.RequiredArgsConstructor;
import nichy.beancafe.dto.OrderCreatedRequestDto;
import nichy.beancafe.dto.OrderItemRequestDto;
import nichy.beancafe.dto.ProductRequestDto;
import nichy.beancafe.entity.Order;
import nichy.beancafe.entity.OrderItem;
import nichy.beancafe.entity.Product;
import nichy.beancafe.repository.OrderRepository;
import nichy.beancafe.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.BorderUIResource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(OrderCreatedRequestDto dto){
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        for (OrderItemRequestDto itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));
            if (!product.getIsAvailable()) {
                throw new RuntimeException("Product " + product.getName() + " is currently unvailable.");
            }

            BigDecimal itemSubTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalOrderPrice = totalOrderPrice.add(itemSubTotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(product.getPrice())
                    .subTotal(itemSubTotal)
                    .sugarLevel(itemDto.getSugarLevel())
                    .milkType(itemDto.getMilkType())
                    .note(itemDto.getNote()).build();
            orderItems.add(orderItem);
        }
        Order order = Order.builder()
                .name(dto.getCustomerName())
                .totalPrice(totalOrderPrice)
                .status(Order.OrderStatus.PENDING)
                .items(orderItems)
                .build();
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders(Order.OrderStatus status){
        if (status != null){
            return orderRepository.findByStatusOrderByCreatedAtDesc(status);
        }
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not fount with id: " + id));

    }

    @Transactional
    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }




}
