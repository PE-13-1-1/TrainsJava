package ua.kture.pi13.demo.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ua.kture.pi1311.dao.DAOFactory;
import ua.kture.pi1311.dao.StationDAO;
import ua.kture.pi1311.dao.StopDAO;
import ua.kture.pi1311.dao.TrainDAO;
import ua.kture.pi1311.entity.Station;
import ua.kture.pi1311.entity.Stop;
import ua.kture.pi1311.entity.Train;

public class ParseStops {
	/*
	 * Чтобы не забыть: в таблице с поездами есть айдишник графика Остановка
	 * указывает на айдишник списка, которому она принадлежит В принципе,
	 * таблица лист может быть и не нужна. Так как шедул уже содержит айдишник
	 * листа Алгоритм: загружаем поезд. Запоминаем айдишник шедула. Ходим по
	 * станциям поезда. Запоминаем остановки. Создаем объекты. Для одного поезда
	 * только один список остановок
	 */
	private static int getStationId(ArrayList<Station> Stations, String station) {
		int id = -1;
		for (Station stn : Stations) {
			if (stn.getStationName().replaceAll(" ", "").toLowerCase().trim()
					.contains(station.replaceAll(" ", "").toLowerCase().trim())) {
				return stn.getStationId();
			}
		}
		return id;
	}

	private static void LetsWorkWithStops() {
		TrainDAO trainDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getTrainDAO();
		StationDAO stationDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getStationDAO();
		StopDAO stopDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getStopDAO();
		try {
		stopDAO.truncateStop();
		ArrayList<Train> Trains = trainDAO.findAllTrains();
		ArrayList<Station> Stations = stationDAO.findAllStations();
		Document doc;
		try {
			for (int i = 0; i < Trains.size(); i++) {
				System.out.println(i);
				doc = Jsoup.connect(
						"http://www.pz.gov.ua/prrasp/"
								+ Trains.get(i).getTrainUrl()).get();
				Elements links = doc.getElementsByClass("bs1t");
				for (Element element : links) {
					Elements link = element.getElementsByTag("td");
					int count = 0;
					String station = null;
					java.sql.Date timeArrival = null;
					int staying = 0;
					java.sql.Date timeDeparture = null;
					for (Element elemen : link) {
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
						String string = elemen.text();
						switch (count) {
						case (0): {
							station = string;
							count++;
							break;
						}
						case (1): {
							if (string.length() != 1) {
								date = formatter.parse(string);
								timeArrival = new java.sql.Date(date.getTime());
							}
							count++;
							break;
						}
						case (2): {
							try {
								staying = Integer.parseInt(string);
							} catch (Exception ex) {
								count++;
								break;
							}
							count++;
							break;
						}
						case (3): {
							if (string.length() != 1) {
								date = formatter.parse(string);
								timeDeparture = new java.sql.Date(date.getTime());
							}
							count++;
							break;
						}
						}
						
					}
					Stop stop = new Stop();
					stop.setTrainId(Trains.get(i).getTrainId());
					stop.setTimeArrival(timeArrival);
					stop.setTimeDeparture(timeDeparture);
					stop.setStaying(staying);
					int id = getStationId(Stations, station);
					if (id != -1) {
						stop.setStationId(id);
					}
					else {
						System.out.println(station);
					}
					stopDAO.insertStop(stop);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		} catch (Exception ex1) {
			System.out.println(ex1.getMessage());
		}
	}

	public static void ALaMain() {
		LetsWorkWithStops();
	}
}
