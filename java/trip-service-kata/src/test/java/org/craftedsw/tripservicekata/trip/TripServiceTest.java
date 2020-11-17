package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TripServiceTest {

    User loggedUser;

    List<Trip> trips;

    TripServiceInterface tripServiceInterface = new TripServiceInterface() {
        @Override
        public User getLoggedUser() {
            return loggedUser;
        }

        @Override
        public List<Trip> getTrips(User user) {
            return trips;
        }
    };

    @BeforeEach
    void setUp() {
        loggedUser = null;
        trips = new ArrayList<>();
    }

    @Test
    void getTripsByUserWhenNoFriendsShouldReturnEmptyTripList() {
        // Given
        TripService tripService = new TripService(tripServiceInterface);
        loggedUser = new User();

        // When
        List<Trip> trips = tripService.getTripsByUser(tripService.getLoggedUser());

        // Then
        Assertions.assertEquals(trips, Collections.emptyList());
    }

    @Test
    void getTripsByUserWhenUserNotLoggedShouldThrowException() {
        // Given
        TripService tripService = new TripService(tripServiceInterface);

        // Then
        Assertions.assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(null));
    }

    @Test
    void getTripsByUserWhenLoggedUserIsFriendShouldReturnFriendTripList() {
        // Given
        TripService tripService = new TripService(tripServiceInterface);
        Trip trip = new Trip();
        User user = new User();
        loggedUser = new User();
        user.addFriend(new User());
        user.addFriend(loggedUser);
        user.addTrip(trip);
        trips.add(trip);

        // When
        List<Trip> trips = tripService.getTripsByUser(user);

        // Then
        Assertions.assertEquals(trips, Arrays.asList(trip));
    }
}
