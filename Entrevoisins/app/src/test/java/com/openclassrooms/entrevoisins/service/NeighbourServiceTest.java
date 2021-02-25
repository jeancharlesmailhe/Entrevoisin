package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class  NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getFavoritesWithSuccess() {
        List<Neighbour> favorites = service.getFavorites();
        assertEquals(0, favorites.size());
    }

    @Test
    public void addFavoriteWithSuccess() {
        assertEquals(0, service.getFavorites().size());
        service.addFavorite(new Neighbour(1, "name", "url", "adress", "0505", "me"));
        assertEquals(1, service.getFavorites().size());
    }

    @Test
    public void addFavoriteThenDeleteThenCheck () {
        assertEquals(0, service.getFavorites().size());
        Neighbour neighbour = new Neighbour(1, "name", "url", "adress", "0505", "me");
        service.addFavorite(neighbour);
        assertEquals(1, service.getFavorites().size());
        service.deleteFavorite(neighbour);
        assertEquals(0, service.getFavorites().size());
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbour);
        assertFalse(service.getNeighbours().contains(neighbour));
    }

    @Test
    public void addNeighbourWithSuccess() {
        int neighboursSize = service.getNeighbours().size();
        Neighbour neighbour = new Neighbour(1, "name", "url", "adress", "0505", "me");
        service.createNeighbour(neighbour);
        assertEquals(service.getNeighbours().size(), neighboursSize + 1);
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));

    }
    @Test
    public void getNeighboursEqualsDummyNeighbours() {
        int neighboursSize = service.getNeighbours().size();
        int dummyNeighboursSize = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.size();
        assertEquals(neighboursSize, dummyNeighboursSize);
    }

}
