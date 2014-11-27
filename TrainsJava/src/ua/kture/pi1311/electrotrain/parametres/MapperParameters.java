package ua.kture.pi1311.electrotrain.parametres;

public interface MapperParameters {
		public static final String DB_NAME = "KharkovTrain"; 
	// Station mapper parameters.
		public static final String STATION_ID = "stationId";
		public static final String STATION_NAME = "stationName";
		public static final String STATION_URL = "stationURL";
	//Train mapper parametres
		public static final String TRAIN_ID = "trainId";
		public static final String TRAIN_START_POINT = "startPoint";
		public static final String TRAIN_FINAL_POINT = "finalPoint";
		public static final String TRAIN_STATUS = "status";
		public static final String TRAIN_NUMBER = "trainNumber";
		public static final String TRAIN_URL = "trainURL";
	//Direction mapper parametres
		public static final String DIRECTION_ID ="directionId";
		public static final String DIRECTION_TITLE ="directionTitle";
	//Stop mapper parametres
		public static final String STOP_ID ="stopId";
		public static final String STOP_TIME_ARRIVAL ="timeArrival";
		public static final String STOP_TIME_DEPARTURE ="timeDeparture";
		public static final String STOP_STAYING="staying";
		public static final String STOP_STATION_ID="stationId";
		public static final String STOP_LIST_ID ="listId";
		public static final String STOP_TRAIN_ID ="trainId";
}
