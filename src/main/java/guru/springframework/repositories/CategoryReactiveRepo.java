package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CategoryReactiveRepo  extends ReactiveMongoRepository<Category, String> {
    Mono<Category> findByDescription(String description);
}
