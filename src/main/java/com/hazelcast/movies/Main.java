package com.hazelcast.movies;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.projection.Projection;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hazelcast.core.Hazelcast.newHazelcastInstance;
import static com.hazelcast.movies.FileUtil.loadMovies;

/**
 * TODO
 *
 * @author Viktor Gamov on 2/11/17.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final HazelcastInstance hazelcastInstance = newHazelcastInstance();
        final IMap<Integer, Movie> moviesDat = hazelcastInstance.getMap("movies");

        loadMovies("movies.dat", moviesDat);

        final Predicate predicate = Predicates.equal("actors[any]", "Robert Downey Jr.");
        long start = System.nanoTime();
        final Collection<Movie> movies = moviesDat.values(predicate);
        long end = System.nanoTime();

        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start));

        start = System.nanoTime();
        final Collection collection = moviesDat.project(new Projection<Map.Entry<Integer, Movie>, String>() {
            @Override
            public String transform(Map.Entry<Integer, Movie> input) {
                return input.getValue().getTitle();
            }
        }, predicate);
        end = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start));
        System.out.println(movies);
        System.out.println(collection);


    }
}
