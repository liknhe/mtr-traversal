package mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

import metro.MetroLine;
import metro.MetroStation;
import metro.MetroSystem;

/**
 * MTR is a concrete class of controller implementing the four methods required for the 
 * coursework
 * @author Ben Withington, Li He, Hannah Miller
 */
public class WorkingController implements Controller {

	private MetroSystem collection;

	public WorkingController(String path) {

		long startTime = new Date().getTime();
		collection = new MetroSystem();

		String sentence = "";
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			// While there are still lines left to read in the csv file
			while ((sentence = br.readLine()) != null) {
				String temp[] = sentence.split(",");
				MetroLine line = new MetroLine(temp[0]);

				// Add each station in the current line to the collection
				for (int i = 1; i < temp.length; i++) {
					MetroStation station = null;

					if (collection.getStations().containsKey(temp[i])) {
						station = collection.getStation(temp[i]);
						station.addLine(line);
					} else {
						station = new MetroStation(temp[i]);
						station.addLine(line);
						collection.addStation(station);
					}

					line.addStation(station);
				}
				//Add the current line to the collection
				collection.addLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (e instanceof FileNotFoundException) {
				System.out.println("File Not Found");
			}
		}

		//Adds the adjacent stations to each station
		for (String lineName : collection.getLines().keySet()) {
			MetroLine line = collection.getOneLine(lineName);
			line.addConnectingStations();
			for (MetroStation station : line.getAllStations().values()) {
				station.nextPotentialStations();
				line.addConnections(station.getLines());
			}
		}

		long endTime = new Date().getTime();
		long timeElapsed = endTime - startTime;
		System.out.println("Time taken to Inistialise: " + timeElapsed);
	}

	/**
	 * Lists all termini in this MTR network.
	 * @return the name of all MTR stations that are the end point of the lines in the MTR network. 
	 */
	@Override
	public String listAllTermini() {
		long startTime = (new Date().getTime());

		StringBuffer sb = new StringBuffer();

		// O(n)
		// Add the first and last element of the Line and add it to the StringBuffer
		for (String name : collection.getLines().keySet()) {
			MetroLine temp = collection.getOneLine(name); 					// O(1)
			sb.append("Train Line: " + temp.toString() + "\n"); 			// O(1)
			sb.append("Termini: " + temp.getFirst().toString() + ", "); 	// O(1)
			sb.append(temp.getLast().toString() + "\n"); 				// O(1)
			sb.append("\n"); 											// O(1)
		}
		
		long endTime = (new Date().getTime());
		long elapsedTime = endTime - startTime;

		sb.append("Elapsed Time: " + elapsedTime);
		return sb.toString();
	}

	/**
	 * Lists the stations in their respective order in the specified MTR line.
	 * @param line	a specified line in the MTR network
	 * @return	a String representation of all stations in the specified MTR line.
	 */
	@Override
	public String listStationsInLine(String line) {
		long startTime = (new Date().getTime());
		StringBuffer sb = new StringBuffer();

		MetroLine trainLine = collection.getOneLine(line);
		if (trainLine == null) {
			return "\nNot A Line";
		}
		sb.append(trainLine.toString() + ": ");
		Iterator<MetroStation> stationIterator = trainLine.iterator();

		// O(n)
		//While there is a next element which is not null add the element to the StringBuffer
		while (stationIterator.hasNext()) {
			MetroStation temp = stationIterator.next();
			if (!stationIterator.hasNext()) { 				// O(1)
				sb.append(temp.toString()); 					// O(1)
			} else {
				sb.append(temp.toString() + " <-> "); 		// O(1)
			}
		}

		long endTime = (new Date().getTime());
		long elapsedTime = endTime - startTime;

		return sb.toString() + "\nElapsed Time: " + elapsedTime;
	}

	/**
	 * Lists the name of the line(s) that is/are directly connected with the specified MTR line.
	 * @param line	a specified line in the MTR network
	 * @return	a String representation of the name of the required line(s)
	 */
	@Override
	public String listAllDirectlyConnectedLines(String line) {
		long startTime = (new Date().getTime());

		StringBuffer sb = new StringBuffer();
		try {
			sb.append("Directly Connected Lines: \n");
			// Get desired line from the collection
			MetroLine trainLine = collection.getOneLine(line); 	// O(1)

			// O(n)
			for (String name : trainLine.getConnections().keySet()) {
				if (!name.equals(line)) {
					//Add all the lines to the StringBuffer that are not the current line
					sb.append("\n" + name); 					// O(1)
				}
			}
		} catch (Exception e) {
			sb.append("Not A Line");
		}

		long endTime = (new Date().getTime());
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed Time: " + elapsedTime);

		return sb.toString();
	}

	/**
	 * Lists a path between the specified stations.
	 * The path is represented as a sequence of the name of the stations between the specified stations. 
	 * @param stationA	the name of a station
	 * @param stationB	the name of another station
	 * @return	a String representation of a path between the specified stations
	 */
	@Override
	public String showPathBetween(String stationA, String stationB) {

		if (!collection.getStations().containsKey(stationA) || !collection.getStations().containsKey(stationB)) {
			return "\nOne Or More Stations Not In The Set";
		}

		long startTime = (new Date().getTime());
		StringBuffer sb = new StringBuffer();

		
		Stack<MetroStation> path = new Stack<MetroStation>();
		Stack<MetroStation> reversedPath = new Stack<MetroStation>();

		MetroStation end = collection.getStation(stationB);
		MetroStation start = collection.getStation(stationA);
		MetroStation current = start;

		path.push(current);
		current.markVisited();
		Queue<MetroStation> potential = current.potentialStations();

		
		//O(n)
		//While you have not reached the end of the path
		while (!current.equals(end)) {

			// if there are no more adjacent stations left
			if (potential.isEmpty()) {
				//Remove from the top of the path stack and update the current and potential variables.
				path.pop(); 								// O(1)
				current = path.peek(); 						// O(1)
				potential = current.potentialStations(); 	// O(1)
			} else {
			//If there are still adjacent stations remaining
				if (potential.element().isVisited()) {
					potential.remove(); 					// O(1)
				} else {
					//If there first element in the potential station queue has not yet been visited
					//Update the current station to the next potential station and remove from the list.
					current = potential.remove(); 			// O(1)
					path.push(current); 					// O(1)
					current.markVisited(); 					// O(1)
					potential = current.potentialStations();// O(1)
				}
			}
		}

		// O(n)
		// Reverses the path (End Station At Top of Stack)
		while (!path.isEmpty() && !(start.equals(path.peek()))) {
			reversedPath.push(path.pop());
		}

		sb.append(path.pop().toString() + " -> "); 			// O(1)

		// O(n)
		//Add All of the path to the StringBuffer
		while (!reversedPath.isEmpty()) {
			MetroStation station = reversedPath.pop();

			if (reversedPath.isEmpty()) {
				sb.append(station.toString()); 				// O(1)
			} else {
				sb.append(station.toString() + " -> "); 		// O(1)
			}
		}
		
		// O(n)
		// Sets all stations as not visited and reset potential next stations for method re-usability
		for (String stationName : collection.getStations().keySet()) {
			MetroStation temp = collection.getStation(stationName);
			temp.resetPotentialStations();
			temp.resetVisited();
		}

		long endTime = (new Date().getTime());
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed Time: " + elapsedTime);

		return sb.toString();
	}
}
