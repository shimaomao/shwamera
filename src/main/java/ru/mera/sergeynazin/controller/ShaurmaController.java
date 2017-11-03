package ru.mera.sergeynazin.controller;

import org.hibernate.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.IngredientService;
import ru.mera.sergeynazin.service.ShaurmaService;
import ru.mera.sergeynazin.repository.HibernateRepository;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/shaurma")
public class ShaurmaController {

    private ShaurmaService shaurmaService;

    private IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
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
        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInJSON() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    // TODO: 10/20/17 XML
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInXML() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> addInJSON(@RequestBody final Shaurma shaurma) {
        return add(shaurma);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> addInXML(@RequestBody final Shaurma shaurma) {
        return add(shaurma);
    }

    /**
     * Convenience method for shaurmamaker only for to add
     * new shaurma (if frontend would add functionality)
     */
    // TODO: 10/20/17 Aspect
    // todo: maybe send the full URI with HttpRequest
    private ResponseEntity<?> add(final Shaurma shaurma) {
        shaurmaService.save(shaurma);
        final URI created = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(shaurma.getId()).toUri();
        return ResponseEntity.created(created).build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateInJson(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return update(id, shaurma);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> updateInXML(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return update(id, shaurma);
    }

    /**
     * проверяем сущ-ет ли шаурма с таким Id
     *      есди да то новый стейт из объекта копируем в старый и возвращаем новую шаурму
     *      если нет, то создаём новую шаурму итерируя по ингредиентам
     *      @see Session#save(String,Object)
     */
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> update(final Long id, final Shaurma detached) {
        return shaurmaService.optionalIsExist(id)
            .map(persistentOldShaurma -> {
                detached.setId(id);
                /** TODO: Check if needs to be merged instead
                 * @see Session#merge(Object)
                 * @see HibernateRepository#mergeStateWithDbEntity(Object)
                 */
                shaurmaService.update(detached);
                return ResponseEntity.ok(detached);
            }).orElseGet(() -> {
                if (detached.getIngredientSet()
                    .parallelStream()
                    .allMatch(ingredient -> ingredientService.optionalIsExist(ingredient.getId()).isPresent())) {
                    return ResponseEntity.unprocessableEntity().body(detached);
                }
                shaurmaService.save(detached);
                return ResponseEntity.created(URI.create("/" + detached.getId())).body(detached);
            });
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteShaurmaInJson(@PathVariable("id") final Long id) {
        return delete(id);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> deleteShaurmaInXML(@PathVariable("id") final Long id) {
        return delete(id);
    }
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> delete(final Long id) {
        return shaurmaService.optionalIsExist(id)
            .map(shaurma -> {
                shaurmaService.delete(shaurma);
                return ResponseEntity.ok(shaurma);
            }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Helper method
     * @param id identifier
     */
    private void checkOrThrow(final Long id) {
            shaurmaService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
