package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mera.sergeynazin.controller.advice.NotFoundExeption;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.service.IngredientService;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    // TODO: Inject
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
    // TODO: 10/20/17 Aspect
    @PostMapping(value = "/ingredients/create/{ingredient_name}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> createNewIngredient(@PathVariable("ingredient_name") final String ingredientName, @RequestBody final Ingredient ingredient) {
        return ingredientService.optionalIsExist(ingredient.getId())
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
            });
    }

    // FIXME:// FIXME:// FIXME:// FIXME:// FIXME:// FIXME:
    // FIXME:
    // FIXME:   Ничего не понятно с методами ниже. Нужны разъясниея
    // FIXME:
    // FIXME:// FIXME:// FIXME:// FIXME:// FIXME:// FIXME:

    @PostMapping(value = "/ingredients/add/{ingredient_name}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> addIngredientToShaurma(@PathVariable("ingredient_name") final String ingredientName, @RequestBody final Ingredient ingredientWithId) {

        return ingredientService.optionalIsExist(ingredientName)
            .map() //FIXME: НЕПОНЯТНО!
            .orElseGet(() -> {
                ingredientService
                return ResponseEntity.noContent().build();
            });
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        return get(id);
    }

    // TODO: 10/20/17 XML
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        return get(id);
    }

    private ResponseEntity<?> get(final Long id) {
        return ingredientService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Ingredient>> getAllIngredientsInJSON() {
        return ResponseEntity.ok(ingredientService.getAll());
    }

    // TODO: 10/20/17 XML
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Collection<Ingredient>> getAllIngredientsInXML() {
        return ResponseEntity.ok(ingredientService.getAll());
    }

    // TODO: 10/20/17 Aspect
    @DeleteMapping(value = "/ingredients/remove/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteIngredientInJSON(@PathVariable("id") final Long id) {
        return delete(id);
    }

    // TODO: 10/20/17 Aspect
    @DeleteMapping(value = "/ingredients/remove/{id}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> deleteIngredientInXML(@PathVariable("id") final Long id) {
        return delete(id);
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
                .orElseThrow(() -> new NotFoundExeption(name));
    }

    private void checkOrThrowById(final Long id) {
            ingredientService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }
}
