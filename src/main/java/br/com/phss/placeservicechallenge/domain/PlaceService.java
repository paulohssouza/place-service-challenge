package br.com.phss.placeservicechallenge.domain;

import br.com.phss.placeservicechallenge.api.PlaceRequest;
import br.com.phss.placeservicechallenge.api.PlaceResponse;
import br.com.phss.placeservicechallenge.util.QueryBuilder;
import br.com.phss.placeservicechallenge.web.PlaceMapper;
import com.github.slugify.Slugify;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlaceService {
    private PlaceRepository placeRepository;
    private Slugify slugify;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        this.slugify = Slugify.builder().build();
    }

    public Mono<Place> create(PlaceRequest placeRequest) {
        var place = new Place(
                null,
                placeRequest.name(),
                slugify.slugify(placeRequest.name()),
                placeRequest.city(),
                placeRequest.state(),
                null,
                null
                );
        return placeRepository.save(place);
    }

    public Mono<Place> edit(Long id, PlaceRequest placeRequest) {
        return placeRepository.findById(id)
                .map(place -> PlaceMapper.updatePlaceFromDTO(placeRequest, place))
                .map(place -> place.withSlug(slugify.slugify(place.name())))
                .flatMap(placeRepository::save);
    }

    public Mono<Place> get(Long id) {
        return placeRepository.findById(id);
    }

    public Flux<Place> list(String name) {
        var place = new Place(null, name, null, null, null, null, null);
        Example<Place> query = QueryBuilder.makeQuery(place);
        return placeRepository.findAll(query, Sort.by("name").ascending());
    }
}
