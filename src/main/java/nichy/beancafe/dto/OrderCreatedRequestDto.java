package nichy.beancafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import nichy.beancafe.entity.OrderItem;

import java.util.List;

@Data
public class OrderCreatedRequestDto {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    private String customerPhone;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequestDto> items;
}
