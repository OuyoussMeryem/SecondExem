package ma.ensa.commandservice.resolver;

import ma.ensa.commandservice.dto.Product;
import ma.ensa.commandservice.model.Command;
import ma.ensa.commandservice.repo.CommandRepo;
import ma.ensa.commandservice.service.ProductGraphQLClient;
import org.springframework.stereotype.Component;

@Component
public class OrderMutationResolver implements GraphQLMutationResolver {

    private final CommandRepo orderRepository;
    private final ProductGraphQLClient productClient;

    public OrderMutationResolver(CommandRepo orderRepository, ProductGraphQLClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public Command createOrder(Long productId, int quantity) {
        // Fetch product details using GraphQL
        Product product = productClient.getProductById(productId);
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }


        // Create and save the order
        Command order = new Command();
        order.setProductId(productId);
        order.setQuantity(quantity);
        return orderRepository.save(order);
    }
}


