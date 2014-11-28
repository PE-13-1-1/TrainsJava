package ua.kture.pi1311.entity;

import java.sql.Time;

public class Stop {

	private int stopId;

	private int stationId;

	private int staying;

	private int trainId;

	private Time timeArrival;

	private Time timeDeparture;

	public Stop() {

	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Stop stop = (Stop) o;
		boolean result = (this.stopId == stop.stopId
				&& this.trainId == stop.trainId);
				return result;
	}

	public int getStopId() {
		return stopId;
	}

	public void setStopId(int stopId) {
		this.stopId = stopId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public int getStaying() {
		return staying;
	}

	public void setStaying(int staying) {
		this.staying = staying;
	}

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

	public Time getTimeArrival() {
		return timeArrival;
	}

	public void setTimeArrival(Time timeArrival) {
		this.timeArrival = timeArrival;
	}

	public Time getTimeDeparture() {
		return timeDeparture;
	}

	public void setTimeDeparture(Time timeDeparture) {
		this.timeDeparture = timeDeparture;
	}

}
