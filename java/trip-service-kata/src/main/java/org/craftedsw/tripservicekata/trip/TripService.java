package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

interface TripServiceInterface {
	User getLoggedUser();
	List<Trip> getTrips(User user);
}

public class TripService {

	protected TripServiceInterface tripServiceInterface;

	TripService() {
		this.tripServiceInterface = new TripServiceInterface() {
			@Override
			public User getLoggedUser() {
				return UserSession.getInstance().getLoggedUser();
			}

			@Override
			public List<Trip> getTrips(User user) {
				return TripDAO.findTripsByUser(user);
			}
		};
	}

	TripService(TripServiceInterface tripServiceInterface) {
		this.tripServiceInterface = tripServiceInterface;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedUser = getLoggedUser();
		if (loggedUser != null) {

			User connection = (User) user.getFriends().stream().filter((friend) ->
					friend.equals(loggedUser)
			).findFirst();
			if (connection == null){
				return Collections.emptyList();
			}
			return getTrips(connection);

		} else {
			throw new UserNotLoggedInException();
		}
	}

	protected User getLoggedUser() {
		return tripServiceInterface.getLoggedUser();
	}

	protected List<Trip> getTrips(User user) {
		return tripServiceInterface.getTrips(user);
	}
}
