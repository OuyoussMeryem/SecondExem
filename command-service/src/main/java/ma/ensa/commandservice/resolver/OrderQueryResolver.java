package ma.ensa.commandservice.resolver;

import ma.ensa.commandservice.repo.CommandRepo;
import org.hibernate.query.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderQueryResolver implements GraphQLQueryResolver {

    private final CommandRepo orderRepository;

    public OrderQueryResolver( CommandRepo  orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}