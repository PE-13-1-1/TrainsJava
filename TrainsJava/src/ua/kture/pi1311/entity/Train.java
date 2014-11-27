package ua.kture.pi1311.entity;

import java.util.List;

public class Train {

	private int trainId;

	private String startPoint;

	private String finalPoint;

	private List<Station> stations;

	private String status;

	private int trainNumber;

	private String trainUrl;

	private int scheduleId;

	public Train() {

	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Train train = (Train) o;
		boolean result = (this.startPoint.equals(train.startPoint)
				&& this.finalPoint.equals(train.finalPoint)
				&& this.trainNumber == train.trainNumber);
		return result;
	}

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getFinalPoint() {
		return finalPoint;
	}

	public void setFinalPoint(String finalPoint) {
		this.finalPoint = finalPoint;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(int trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainUrl() {
		return trainUrl;
	}

	public void setTrainUrl(String trainUrl) {
		this.trainUrl = trainUrl;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

}
