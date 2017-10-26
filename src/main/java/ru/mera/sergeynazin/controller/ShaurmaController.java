package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/shaurma")
public class ShaurmaController {

    private ShaurmaService shaurmaService;

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        return getShaurmaById(id);
    }

    // TODO: 10/20/17 XML
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        return getShaurmaById(id);
    }

    private ResponseEntity<?> getShaurmaById(final Long id) {
        // fixme: delete!
        checkOrThrow(id);

        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    // TODO: Do I need value = "/" ???
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInJSON() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    // TODO: 10/20/17 XML
    // TODO: Do I need value = "/" ???
    @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInXML() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    /**
     * Convenience method for shaurmamaker only for to add new shaurma (if frontend would add functionality)
     */
    // TODO: 10/20/17 Aspect
    // todo: maybe send the full URI with HttpRequest
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> add(@RequestBody final Shaurma shaurma) {
        return ResponseEntity.created(URI.create(shaurmaService.save(shaurma).toString())).build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateShaurmaInJson(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return updateShaurma(id, shaurma);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> updateShaurmaInXML(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return updateShaurma(id, shaurma);
    }
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> updateShaurma(final Long id, final Shaurma newStateShaurma) {
        // fixme: delete!
        checkOrThrow(id);
        
        /**
         * проверяем сущ-ет ли с таким Id
         *      есди да то новый стейт из объекта копируем в старый и возвращаем новую шаурму
         *      если нет, то создаём новую шаурму итерируя по ингредиентам
         */
        return shaurmaService.optionalIsExist(id)
            .map(persistentOldShaurma ->
                ResponseEntity.ok(shaurmaService.updateShaurmaStateInDb(id, newStateShaurma)))
            .orElseGet(() -> ResponseEntity.created(
                URI.create(shaurmaService.loadAsPersistent(shaurmaService.save(newStateShaurma)).toString())
                ).body(newStateShaurma)
            );
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
    private ResponseEntity<?> delete(Long id) {

//        return shaurmaService.tryDelete(id)
//            ? ResponseEntity.ok().build()
//            : ResponseEntity.notFound().build();

        // FIXME: I know it checks 3 times.
        checkOrThrow(id);

        return shaurmaService.optionalIsExist(id)
            .map(order -> {
                shaurmaService.tryDelete(id);
                return ResponseEntity.ok(order);
            }).orElse(ResponseEntity.notFound().build());
    }

    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrow(final Long id) {
        shaurmaService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }
}
