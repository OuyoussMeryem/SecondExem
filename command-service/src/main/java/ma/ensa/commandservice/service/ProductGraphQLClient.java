package ma.ensa.commandservice.service;

import ma.ensa.commandservice.dto.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.Map;

@Service
public class ProductGraphQLClient {

    private final RestTemplate restTemplate;

    public ProductGraphQLClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProductById(Long id) {
        String query = """
query {
getProductById(id: %d) {
id
name
description
stock
}
}
""".formatted(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(String.format("{\"query\": \"%s\"}", query), headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8080/graphql", request, Map.class);

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> productData = (Map<String, Object>) data.get("getProductById");

        Product product = new Product();
        product.setId(Long.valueOf(productData.get("id").toString()));
        product.setName(productData.get("name").toString());
        product.setDescription(productData.get("description").toString());
        product.setStock(Integer.parseInt(productData.get("stock").toString()));

        return product;
    }
}
