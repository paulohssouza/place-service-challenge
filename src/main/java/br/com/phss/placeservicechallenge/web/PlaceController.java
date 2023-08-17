package br.com.phss.placeservicechallenge.web;

import br.com.phss.placeservicechallenge.api.PlaceRequest;
import br.com.phss.placeservicechallenge.api.PlaceResponse;
import br.com.phss.placeservicechallenge.domain.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public ResponseEntity<Mono<PlaceResponse>> create(@Valid @RequestBody PlaceRequest placeRequest) {
        var placeResponse = placeService.create(placeRequest).map(PlaceMapper::fromPlaceToResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
    }

    @PatchMapping("{id}")
    public Mono<PlaceResponse> edit(@PathVariable("id") Long id, @RequestBody PlaceRequest placeRequest) {
        return placeService.edit(id, placeRequest).map(PlaceMapper::fromPlaceToResponse);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<PlaceResponse>> get(@PathVariable("id") Long id) {
        return placeService.get(id)
                .map(place -> ResponseEntity.ok(PlaceMapper.fromPlaceToResponse(place)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<PlaceResponse> list(@RequestParam(required = false) String name) {
        return placeService.list(name).map(PlaceMapper::fromPlaceToResponse);
    }
}
