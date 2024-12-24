package ma.ensa.notificationservice.service;

@MutationMapping
public Command createCommand(@Argument Long productId, @Argument int quantity) {
        Product product = productGraphQLClient.getProductById(productId);
        if (product.getStock() < quantity) {
        throw new RuntimeException("Insufficient stock for product ID: " + productId);
        }

        Command command = new Command();
        command.setProductId(productId);
        command.setQuantity(quantity);
        commandRepository.save(command);

        // Publiez un message Kafka
        String message = "New command created: {id: " + command.getId() + ", productId: " + productId + ", quantity: " + quantity + "}";
        kafkaProducer.sendMessage(message);

        return command;
        }
