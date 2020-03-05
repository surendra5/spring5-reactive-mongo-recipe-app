package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepoTest {

    @Autowired
    RecipeReactiveRepo recipeReactiveRepo;

    @Before
    public void setUp() throws Exception{
        recipeReactiveRepo.deleteAll().block();
    }

    @Test
    public void save() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setDescription("sdsds");
        recipeReactiveRepo.save(recipe).block();

        Long count = recipeReactiveRepo.count().block();
        assertEquals(Long.valueOf(1), count);
    }
}
