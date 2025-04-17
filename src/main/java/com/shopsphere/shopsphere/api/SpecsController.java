package com.shopsphere.shopsphere.api;

import com.shopsphere.shopsphere.domain.models.Specs;
import com.shopsphere.shopsphere.domain.services.SpecsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/specs")
public class SpecsController {

    private final SpecsService specsService;

    public SpecsController(SpecsService specsService) {
        this.specsService = specsService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Specs>> getSpecsByProductId(@PathVariable int productId) {
        try {
            List<Specs> specs = specsService.getSpecsByProductId(productId);
            return new ResponseEntity<>(specs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specs> getSpecById(@PathVariable int id) {
        try {

            Specs spec = specsService.getSpecById(id);
            return new ResponseEntity<>(spec, HttpStatus.OK);

        } catch (EntityNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inanttendue est survenue",e);

        }
    }

    @PostMapping("")
    public ResponseEntity<Specs> createSpec(@RequestBody Specs specs) {

        return new ResponseEntity<>(specsService.createSpec(specs),HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Specs> updateSpec(@RequestBody Specs spec,@PathVariable int id) {
        try {
            return new ResponseEntity<>(specsService.updateSpec(spec),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Une erreur inanttendue est survenue",e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpec(@PathVariable int id) {
            try  {

                specsService.deleteSpec(id);
                return ResponseEntity.noContent().build();

            } catch (EntityNotFoundException exception) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue",exception);
            }
        }

}
