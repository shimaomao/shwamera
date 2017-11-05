package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.service.IngredientService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@EnableAsync
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Convenience method for shaurmamaker for to add new ingredient to db
     * @param ingredientName ...
     * @param ingredient xml body
     * @return 201 (Created) or 200 OK (for Caching at front) response
     * containing a Location header field that provides an identifier
     * for the primary resource created and a representation
     * that describes the status of the request while referring
     * to the new resource(s)
     */
    @Admin
    @Async
    @PostMapping(value = "/ingredients/create/{ingredient_name}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> createNewIngredient(@PathVariable("ingredient_name") final String ingredientName,
                                                                    @RequestBody final Ingredient ingredient) {
        return CompletableFuture.completedFuture(
            ingredientService.optionalIsExist(ingredient.getId())
            .flatMap(i -> ingredientService.optionalIsExist(ingredientName))
            .map(i -> ResponseEntity.unprocessableEntity().body(ingredient))
            .orElseGet(() -> {
                ingredient.setName(ingredientName);
                ingredientService.save(ingredient);
                final URI created = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .replacePath("/{id}")
                    .buildAndExpand(ingredient.getId()).toUri();
                return ResponseEntity.created(created).body(ingredient);
            }));
    }

    // FIXME:// FIXME:// FIXME:// FIXME:// FIXME:// FIXME:
    // FIXME:
    // FIXME:   Ничего не понятно с методами ниже. Нужны разъясниея
    // FIXME:
    // FIXME:// FIXME:// FIXME:// FIXME:// FIXME:// FIXME:

    @Async
    @PostMapping(value = "/ingredients/add/{ingredient_name}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> addIngredientToShaurma(@PathVariable("ingredient_name") final String ingredientName,
                                                    @RequestBody final Ingredient ingredientWithId) {
        // FIXME !!!!!!!!!!!!!!!
        return CompletableFuture.completedFuture(
            ingredientService.optionalIsExist(ingredientName)
            .map(ingredient -> ResponseEntity.ok().build()) //FIXME: НЕПОНЯТНО!
            .orElseGet(() -> {
                //ingredientService
                return ResponseEntity.noContent().build();
            }));
    }

    @Async
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<?>> getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    // TODO: 10/20/17 XML
    @Async
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<?>> getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    private ResponseEntity<?> get(final Long id) {
        return ingredientService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }




        CompletableFuture.supplyAsync(() -> ingredientService.optionalIsExist(ingredient.getId()))
            .thenApplyAsync(new Function<Optional<Ingredient>, Integer>() {
        @Override
        public Integer apply(Optional<Ingredient> ingredient1) {
            ingredient1
                .map()
                .orElseGet()
            return 1;
        }
    })
        .

    @Async
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(ingredientService.getAll()));
    }

    // TODO: 10/20/17 XML
    @Async
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(ingredientService.getAll()));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/ingredients/remove/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> deleteIngredientInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/ingredients/remove/{id}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> deleteIngredientInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    private ResponseEntity<?> delete(final Long id) {
        return ingredientService.optionalIsExist(id)
            .map(ingredient -> {
                ingredientService.delete(ingredient);
                return ResponseEntity.ok(ingredient);
            }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Helper methods
     * @param name/id identifier
     */
    private void checkOrThrowByName(final String name) {
            ingredientService.optionalIsExist(name)
                .orElseThrow(() -> new NotFoundException(name));
    }

    private void checkOrThrowById(final Long id) {
            ingredientService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
