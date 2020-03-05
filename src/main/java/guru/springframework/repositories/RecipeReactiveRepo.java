package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RecipeReactiveRepo extends ReactiveMongoRepository<Recipe, String> {
}
