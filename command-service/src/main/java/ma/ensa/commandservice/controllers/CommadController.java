package ma.ensa.commandservice.controllers;

import ma.ensa.commandservice.dto.Product;
import ma.ensa.commandservice.model.Command;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CommadController {
    private final CommandRepository commandRepository;
    private final ProductGraphQLClient productGraphQLClient;

    public CommandGraphQLController(CommandRepository commandRepository, ProductGraphQLClient productGraphQLClient) {
        this.commandRepository = commandRepository;
        this.productGraphQLClient = productGraphQLClient;
    }

    @QueryMapping
    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    @QueryMapping
    public Command getCommandById(@Argument Long id) {
        return commandRepository.findById(id).orElseThrow(() -> new RuntimeException("Command not found"));
    }

    @MutationMapping
    public Command createCommand(@Argument Long productId, @Argument int quantity) {
        // Vérifiez le stock via GraphQL
        Product product = productGraphQLClient.getProductById(productId);
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product ID: " + productId);
        }

        // Enregistrez la commande
        Command command = new Command();
        command.setProductId(productId);
        command.setQuantity(quantity);
        commandRepository.save(command);

        // Mettez à jour le stock via GraphQL
        String mutation = """
            mutation {
                updateStock(id: %d, stock: %d) {
                    id
                }
            }
        """.formatted(productId, product.getStock() - quantity);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(String.format("{\"query\": \"%s\"}", mutation), headers);
        restTemplate.postForEntity("http://localhost:8080/graphql", request, Map.class);

        return command;
    }
}
