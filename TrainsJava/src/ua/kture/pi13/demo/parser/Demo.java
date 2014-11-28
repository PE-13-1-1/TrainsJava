package ua.kture.pi13.demo.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import ua.kture.pi1311.dao.DAOFactory;
import ua.kture.pi1311.dao.StationDAO;
import ua.kture.pi1311.dao.TrainDAO;
import ua.kture.pi1311.entity.Station;
import ua.kture.pi1311.entity.Train;

public class Demo {

	private static List<String> stations = new ArrayList<String>();
	private static ArrayList<Station> Stations = new ArrayList<Station>();
	private static List<String> stationsURL = new ArrayList<String>();
	private static Map<String, String> stationToURL = new HashMap<String, String>();
	private static ArrayList<Train> trains = new ArrayList<Train>();

	private static List<Integer> getTrainNumbers(Elements links) {

		List<Integer> list = new ArrayList<Integer>();
		for (Element link : links) {
			Elements elems = link.getElementsByTag("td");
			int count = 0;
			for (int i = 0; i < elems.size(); i++) {
				if (count == 0)
					list.add(Integer.parseInt(elems.get(0).text()));
				count++;
			}
		}
		return list;
	}

	private static List<String> getPoints(Elements links, boolean isBug) {
		List<String> list = new ArrayList<String>();
		for (Element link : links) {
			Elements elems = link.getElementsByTag("td");
			for (Element element : elems) {
				if (element.text().contains("-")
						|| element.text().contains("Ц")) {
					list.add(element.text());
				}
			}
		}
		return list;
	}

	private static void CheckTrains() {
		Document doc;
		try {
			TrainDAO trainDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
					.getTrainDAO();
			ArrayList<Train> Trains = trainDAO.findAllTrains();
			for (int i = 0; i < Trains.size(); i++) {
				try {
					doc = Jsoup.connect(
							"http://www.pz.gov.ua/prrasp/"
									+ Trains.get(i).getTrainUrl()).get();
					Elements links = doc.getElementsByClass("zagline");
					for (Element element : links) {
						String s1 = Trains.get(i).getStartPoint().trim();
						String s2 = Trains.get(i).getFinalPoint().trim();
						String s3 = "";
						// я таких костылей в жизни еще не делал
						for (int k = 1; k < s1.length(); k++) {
							s3 = s3 + s1.substring(k, k + 1);
						}
						if (element.text().contains(s3)
								&& element.text().contains(s2)) {
							continue;
						} else {
							System.out.println(Trains.get(i).getTrainNumber());
							System.out.println(s3);
							System.out.println(s2);
							System.out.println(element.text());
						}
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					i--;
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void CheckStationTable() {
		Document doc;
		StationDAO stationDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getStationDAO();
		ArrayList<Station> Station = stationDAO.findAllStations();
		try {
			for (int i = 0; i < Station.size(); i++) {
				try {
					doc = Jsoup.connect(
							"http://www.pz.gov.ua/prrasp/"
									+ Station.get(i).getStationURL()).get();
					// —айт не выдает нужный результат. ѕроверка или вручную или
					// как-то иначе
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					i--;
					continue;
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static List<String> getURLS(Elements links, int whichObject) {
		List<String> set = new ArrayList<String>();
		List<String> mainSet = new ArrayList<String>();
		for (Element link : links) {
			Elements elems = link.getElementsByTag("tr");
			for (Element element : elems) {
				String[] array = element.toString().split("\n");
				for (String str : array) {
					if (str.contains("onclick")) {
						set.add(str);
					}
				}
			}
		}
		for (String element : set) {
			if (whichObject == 1) {
				if (!mainSet.contains((String) element.subSequence(
						element.indexOf("rasptrain"),
						element.indexOf("train") + 20))) {
					mainSet.add((String) element.subSequence(
							element.indexOf("rasptrain"),
							element.indexOf("train") + 20));
				}
			} else if (whichObject == 2) {
				if (!mainSet
						.contains((String) element.subSequence(
								element.indexOf("rrep"),
								element.indexOf("onmouseover")))) {
					mainSet.add((String) element.subSequence(
							element.indexOf("rrep"),
							element.indexOf("onmouseover")));
				}
			}
		}
		return mainSet;
	}

	private static Map<Integer, String> getImages(Elements links) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		int key = 0;
		for (Element link : links) {
			Elements elems = link.getElementsByTag("tr");
			for (Element element : elems) {
				String[] array = element.toString().split("\n");
				for (String elem : array) {
					if (elem.contains("img")) {
						String sequence = (String) elem.subSequence(
								elem.indexOf("title=\"") + 7,
								elem.indexOf("\" border"));
						map.put(key, sequence);
					}
				}
				key++;
			}
		}
		return map;
	}

	private static void insertTrain(TrainDAO trainDAO, Train train) {
		trainDAO.insertTrain(train);
	}

	private static boolean isContainInTrains(Train train) {
		boolean result = false;
		for (int i = 0; i < trains.size(); i++) {
			if (trains.get(i).equals(train)) {
				result = true;
			}
		}
		return result;
	}

	private static void parseTrainPage(String URL) {
		TrainDAO trainDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getTrainDAO();
		Document doc;
		try {
			if (URL == null) {
				trainDAO.truncateTrain();
				doc = Jsoup.connect("http://www.pz.gov.ua/prrasp/s_khar1.php")
						.get();
			} else {
				trains = trainDAO.findAllTrains();
				doc = Jsoup.connect("http://www.pz.gov.ua/prrasp/" + URL).get();
			}
			Elements links = doc.getElementsByClass("bs1t");
			List<String> set = getURLS(links, 1);
			Map<Integer, String> map = getImages(links);
			List<String> list = getPoints(links, false);
			Set<Integer> ints = map.keySet();
			List<Integer> intList = getTrainNumbers(links);
			for (int i = 0; i < set.size(); i++) {
				Train train = new Train();
				train.setTrainId(i + 2);
				try {
					if (list.get(i).contains("-")) {
						if (list.get(i).split("-").length > 2) {
							try {
								train.setStartPoint(list.get(i).split("-")[0]);
								train.setFinalPoint(list.get(i).split("-")[2]);
							} catch (Exception exc) {
								System.out.println(list.get(i).toString());
							}
						} else {
							try {
								train.setStartPoint(list.get(i).split("-")[0]);
								train.setFinalPoint(list.get(i).split("-")[1]);
							} catch (Exception exc) {
								System.out.println(list.get(i).toString());
							}
						}
					} else if (list.get(i).contains("Ц")) {
						if (list.get(i).split("Ц").length > 2) {
							try {
								train.setStartPoint(list.get(i).split("Ц")[0]);
								train.setFinalPoint(list.get(i).split("Ц")[2]);
							} catch (Exception exc) {
								System.out.println(list.get(i).toString());
							}
						} else {
							try {
								train.setStartPoint(list.get(i).split("Ц")[0]);
								train.setFinalPoint(list.get(i).split("Ц")[1]);
							} catch (Exception exc) {
								System.out.println(list.get(i).toString());
							}

						}
					}
					train.setTrainUrl((String) set.toArray()[i]);
					if (ints.contains(i)) {
						Iterator<Integer> iterator = ints.iterator();
						while (iterator.hasNext()) {
							Integer iterValue = iterator.next();
							if (iterValue == i) {
								String status = map.get(iterValue);
								train.setStatus(status);
							}
						}
					} else {
						train.setStatus(" аждый день");
					}
					train.setTrainNumber(intList.get(i));
					if (URL == null) {
						insertTrain(trainDAO, train);
					} else {
						if (!isContainInTrains(train)) {
							trains.add(train);
							insertTrain(trainDAO, train);
						}
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					System.out.println(list.size());
					System.out.println(set.size());
					System.out.println(i);
					System.out.println(URL);
					List<String> list1 = getPoints(links, true);
					continue;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			parseTrainPage(URL);

		}

	}

	private static void LetsWorkWithStations() {
		TrainDAO trainDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getTrainDAO();
		Set<String> set = trainDAO.getTrainURL();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String url = "http://www.pz.gov.ua/prrasp/" + iterator.next();
			parseStationPage(url);
		}
		// System.out.println(stations.size());
		// System.out.println(stationsURL.size());
		makeStationToDB();
	}

	private static void parseStationPage(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.getElementsByClass("bs1t");
			List<String> set = getURLS(links, 2);
			List<String> newSet1 = new ArrayList<String>();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String string = iterator.next();
				if (!newSet1.contains((String) string.subSequence(
						string.indexOf("('") + 2, string.indexOf("')\"")))) {
					newSet1.add((String) string.subSequence(
							string.indexOf("('") + 2, string.indexOf("')\"")));
				}
			}
			List<String> stations1 = getStationNames(links);
			if (stations1.size() == newSet1.size()) {
				stations.addAll(stations1);
				stationsURL.addAll(newSet1);
			}
			// Iterator<String> stIter = stations1.iterator();
			// Iterator<String> urlIter = newSet1.iterator();
			// while (stIter.hasNext()) {
			// System.out.println(stIter.next());
			// System.out.println(urlIter.next());
			// }

		} catch (IOException e) {
			System.out.println(e.getMessage());
			parseStationPage(url);
		}

	}

	private static void makeStationToDB() {
		StationDAO stationDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getStationDAO();
		stationDAO.truncateStation();
		for (int i = 0; i < stations.size(); i++) {
			stationToURL.put(stations.get(i), stationsURL.get(i));
		}
		Set<String> keySet = stationToURL.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String element = iterator.next();
			String adressURL = stationToURL.get(element);
			Station station = new Station();
			station.setStationName(element);
			station.setStationURL(adressURL);
			stationDAO.insertStation(station);
		}

	}

	private static void findAllStations() {
		StationDAO stationDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
				.getStationDAO();
		Stations = stationDAO.findAllStations();
	}

	private static List<String> getStationNames(Elements links) {
		List<String> list = new ArrayList<String>();
		for (Element link : links) {
			int count = 0;
			Elements elems = link.getElementsByTag("td");
			for (Element element : elems) {
				if (count == 0 && !list.contains(element.text()))
					list.add(element.text());
				count++;
			}
		}
		return list;
	}

	public static void main(String[] args) {
		 TrainDAO trainDAO = DAOFactory.getDAOFactory(DAOFactory.MSSQL)
		 .getTrainDAO();
		 trainDAO.truncateTrain();
		 parseTrainPage(null);
		 findAllStations();
		 for (int i = 0; i < Stations.size(); i++) {
		 parseTrainPage(Stations.get(i).getStationURL());
		 System.out.println(i);
		 }
		 CheckTrains();
		 LetsWorkWithStations();
		ParseStops.ALaMain();
		System.out.println("Code 0");
	}
}
