package com.example.opinionate;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RestaurantRepository extends ResourceRepositoryBase<Restaurant, Long> {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(3);

    private Map<Long, Restaurant> restaurants = new HashMap<>();

    public RestaurantRepository() {
        super(Restaurant.class);
        restaurants.put(
            (long) 1,
            new Restaurant((long) 1, "Sushi Place", "123 Main Street")
        );
        restaurants.put(
            (long) 2,
            new Restaurant((long) 2, "Burger Place", "456 Other Street")
        );
    }

    @Override
    public synchronized void delete(Long id) {
        restaurants.remove(id);
    }

    @Override
    public synchronized <S extends Restaurant> S save(S restaurant) {
        if (restaurant.getId() == null) {
            restaurant.setId(ID_GENERATOR.getAndIncrement());
        }
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    @Override
    public synchronized ResourceList<Restaurant> findAll(QuerySpec querySpec) {
        return querySpec.apply(restaurants.values());
    }
}