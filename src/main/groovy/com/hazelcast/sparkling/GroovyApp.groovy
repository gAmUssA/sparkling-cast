package com.hazelcast.sparkling

import com.hazelcast.core.Hazelcast
import com.hazelcast.movies.Movie
import com.hazelcast.projection.Projection
import groovy.transform.CompileStatic

import java.util.concurrent.TimeUnit

import static com.hazelcast.movies.FileUtil.loadMovies
import static com.hazelcast.query.Predicates.equal

/**
 * TODO
 *
 * @author Viktor Gamov on 2/28/17.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
@CompileStatic
class GroovyApp {
    static void main(String... args) {
        final def hazelcastInstance = Hazelcast.newHazelcastInstance()
        final def moviesDat = hazelcastInstance.getMap "movies"

        loadMovies("movies.dat", moviesDat)

        final def predicate = equal("actors[any]", "Robert Downey Jr.")
        long start = System.nanoTime()
        final Collection<Movie> movies = moviesDat.values(predicate)
        long end = System.nanoTime()

        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start))

        start = System.nanoTime()


        def projection = new Projection<Map.Entry<Integer, Movie>, String>() {
            @Override
            String transform(Map.Entry<Integer, Movie> input) {
                return input.getValue().getTitle()
            }
        }

        def projection2 = [
                transform: { Map.Entry<Integer, Movie> e ->
                    e.value.title
                }
        ] as Projection

        final Collection collection = moviesDat.project(projection2, predicate)


        end = System.nanoTime()
        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start))
        System.out.println(movies)
        System.out.println(collection)

    }
}

