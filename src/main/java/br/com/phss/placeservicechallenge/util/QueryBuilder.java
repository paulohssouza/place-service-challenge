package br.com.phss.placeservicechallenge.util;

import br.com.phss.placeservicechallenge.domain.Place;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
    private QueryBuilder(){}

    public static Example<Place> makeQuery(Place place) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(place, exampleMatcher);
    }
}
