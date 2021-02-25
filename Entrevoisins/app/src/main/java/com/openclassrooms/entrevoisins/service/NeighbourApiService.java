package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     *
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     *
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get all favorite
     *
     * @return favorites
     */
    List<Neighbour> getFavorites();

    /**
     * Add to favorite
     *
     * @param neighbour
     */
    void addFavorite(Neighbour neighbour);

    /**
     * Delete from favorite
     *
     * @param neighbour
     */
    void deleteFavorite(Neighbour neighbour);
}
