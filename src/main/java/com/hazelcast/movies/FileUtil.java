package com.hazelcast.movies;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class FileUtil {

    public static final String SEPARATOR = "::";
    public static final String ALT_SEPARATOR = "\\|";

    private FileUtil() {
    }

    public static void loadMovies(final String fileName, Map<Integer, Movie> map)
            throws Exception {

        InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));

        final Map<Integer, Movie> movieMap = reader.lines()
                .map(mapToMovie)
                .collect(toMap(
                        Movie::getMovieId,
                        movie -> movie
                ));

        is.close();
        reader.close();
        map.putAll(movieMap);
    }

    /**
     * Movie Mapper
     */
    private static Function<String, Movie> mapToMovie = (line) -> {
        final String[] strings = line.split(SEPARATOR);

        final Movie m = new Movie();
        m.setMovieId(Integer.valueOf(strings[0]));
        m.setTitle(strings[1]);
        m.setYear(Integer.valueOf(strings[2]));
        m.setDuration(Integer.valueOf(strings[3]));
        m.setCountry(strings[4]);
        m.setRating(Double.parseDouble(strings[5]));
        m.setVotes(Integer.valueOf(strings[6]));
        m.setGenres(splitWithPipeToList(strings[7]));
        m.setActors(splitWithPipeToList(strings[8]));
        m.setDirectors(splitWithPipeToList(strings[9]));
        m.setComposers(splitWithPipeToList(strings[10]));
        m.setScreenWriters(splitWithPipeToList(strings[11]));
        m.setCinematographers(splitWithPipeToList(strings[12]));
        m.setProductionCompanies(splitWithPipeToList(strings[13]));
        return m;
    };

    private static List<String> splitWithPipeToList(String s) {
        return Arrays.asList(s.split(ALT_SEPARATOR));
    }
}