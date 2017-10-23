package ru.mera.sergeynazin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/shaurma")
public class ShaurmaController {

    private ShaurmaService shaurmaService;

    private IngredientsService ingredientsService;

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Shaurma getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        checkOrThrow(id);
        return shaurmaService.loadAsPersistent(id);
    }

    // TODO: 10/20/17 XML
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public Shaurma getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        checkOrThrow(id);
        return shaurmaService.loadAsPersistent(id);
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Shaurma> getAllShaurmasInJSON() {
        return shaurmaService.getAll();
    }

    // TODO: 10/20/17 XML
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/xml")
    public List<Shaurma> getAllShaurmasInXML() {
        return shaurmaService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody final Shaurma shaurma) {

        checkOrThrow(shaurma.getId());

        return shaurmaService.optionalIsExist(shaurma.getId())
            .map(shaurma1 -> {

                return ResponseEntity.created(URI.create())

            }).orElse(ResponseEntity.noContent().build());
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, headers = "Accept=application/json")
    public void updateShaurmaInJson(@RequestBody final Shaurma shaurma) {
        shaurmaService.update(shaurma);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, headers = "Accept=application/xml")
    public void updateShaurmaInXML(@RequestBody final Shaurma shaurma) {
        shaurmaService.update(shaurma);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteShaurmaInJson(@PathVariable("id") final Long id) {
        shaurmaService.tryDelete(id);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public void deleteShaurmaInXML(@PathVariable("id") final Long id) {
        shaurmaService.tryDelete(id);
    }

    // TODO: 10/23/17 WHY IGNORED ??? switch to security with (also there is Principal)
    private void checkOrThrow(final Long id) {
        shaurmaService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExection(id));
    }
}
