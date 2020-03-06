package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeReactiveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepo recipeReactiveRepo;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepo recipeReactiveRepo, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepo = recipeReactiveRepo;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        return recipeReactiveRepo.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {

        return recipeReactiveRepo.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {

        return recipeReactiveRepo.findById(id)
                .map(recipe -> { RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                recipeCommand.getIngredients().forEach(rc->{
                    rc.setRecipeId(recipeCommand.getId());
                });
                return recipeCommand;
                });

//        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id).block());
//
//        //enhance command object with id value
//        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
//            recipeCommand.getIngredients().forEach(rc -> {
//                rc.setRecipeId(recipeCommand.getId());
//            });
//        }
//
//        return recipeCommand;
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {

//        return recipeReactiveRepo.save(recipeCommandToRecipe.convert(command))
//                .map(recipeCommandToRecipe::convert);
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeReactiveRepo.save(detachedRecipe).block();
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return Mono.just(recipeToRecipeCommand.convert(savedRecipe));
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeReactiveRepo.deleteById(idToDelete).block();
    }
}
