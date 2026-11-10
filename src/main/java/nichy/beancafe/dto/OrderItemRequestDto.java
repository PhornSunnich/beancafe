package nichy.beancafe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private String sugarLevel;
    private String milkType;
    private String note;
}
