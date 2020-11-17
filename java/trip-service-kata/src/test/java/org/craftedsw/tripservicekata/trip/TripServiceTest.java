package org.craftedsw.tripservicekata.trip;

import com.sun.tools.javac.code.Attribute;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class TripServiceTest {

    User user;

    @BeforeEach
    void setUp() {
        user = null;
    }

    @Test
    void getTripsByUserWhenNoFriendsShouldReturnEmptyTripList() {
        // Given
        TripService tripService = new TripServiceForTest();
        user = new User();

        // When
        List<Trip> trips = tripService.getTripsByUser(tripService.getLoggedUser());

        // Then
        Assertions.assertEquals(trips, Collections.emptyList());
    }

    @Test
    void getTripsByUserWhenUserNotLoggedShouldThrowException() {
        // Given
        TripService tripService = new TripServiceForTest();

        // Then
        Assertions.assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(null));
    }

    public class TripServiceForTest extends TripService {

        @Override
        protected User getLoggedUser() {
            return user;
        }
    }
}
