package ma.ensa.productservice.repo;

import ma.ensa.productservice.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
