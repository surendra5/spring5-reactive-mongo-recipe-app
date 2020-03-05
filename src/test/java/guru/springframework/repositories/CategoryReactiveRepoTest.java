package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepoTest {

    public static final String TEASPOON = "Teaspoon";
    @Autowired
    CategoryReactiveRepo categoryReactiveRepo;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepo.deleteAll().block();
        Category category = new Category();
        category.setDescription(TEASPOON);

        categoryReactiveRepo.save(category).block();
    }

    @Test
    public void save(){


        assertEquals(Long.valueOf(1), categoryReactiveRepo.count().block());
    }

    @Test
    public void findByDescription() {

        Mono<Category> cat = categoryReactiveRepo.findByDescription(TEASPOON);
        assertNotNull(cat.block().getId());
    }
}
