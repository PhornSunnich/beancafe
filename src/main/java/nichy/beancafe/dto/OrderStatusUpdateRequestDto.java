package nichy.beancafe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nichy.beancafe.entity.Order;

@Data
public class OrderStatusUpdateRequestDto {
    @NotNull(message = "Order status is required")
    private Order.OrderStatus status;
}
