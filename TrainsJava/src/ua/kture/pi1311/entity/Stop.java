package ua.kture.pi1311.entity;

import java.sql.Date;

public class Stop {

	private int stopId;

	private int stationId;

	private int staying;

	private int listId;

	private Date timeArrival;

	private Date timeDeparture;

	public Stop() {

	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Stop stop = (Stop) o;
		boolean result = (this.stopId == stop.stopId
				&& this.listId == stop.listId);
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

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public Date getTimeArrival() {
		return timeArrival;
	}

	public void setTimeArrival(Date timeArrival) {
		this.timeArrival = timeArrival;
	}

	public Date getTimeDeparture() {
		return timeDeparture;
	}

	public void setTimeDeparture(Date timeDeparture) {
		this.timeDeparture = timeDeparture;
	}

}
