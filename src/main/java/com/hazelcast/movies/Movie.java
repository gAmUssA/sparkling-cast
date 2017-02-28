package com.hazelcast.movies;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Movie implements Serializable {
    //movie_id::title::year::duration::country::rating::votes::genres::actors::directors::composers::screenwriters::cinematographers::production_companies

    private Integer movieId;
    private String title;
    private Integer year;
    private Integer duration;
    private String country;
    private Double rating;
    private Integer votes;
    private List<String> genres;
    private List<String> actors;
    private List<String> directors;
    private List<String> composers;
    private List<String> screenWriters;
    private List<String> cinematographers;
    private List<String> productionCompanies;
}
