package data.FilesJson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import data.BoardingQueue;
import data.DoubleCircleListFlight;
import data.DoubleListPassenger;
import data.StackHistoryTravels;
import domain.Flight;
import domain.Node.NodeDoubleList;
import domain.Node.NodeSimpleList;
import domain.Passenger;

public class FilesJson {

	public FilesJson() {
	}

	// METODOS PARA LISTAS DOBLES CIRCULARES
	public void writeFlights(DoubleCircleListFlight listFlight, String address) {
		if (listFlight == null || listFlight.isEmpty()) {
			return;
		}

		// Pasa los elementos de la lista circular a un arreglo Flight[]
		Flight[] flightArray = new Flight[listFlight.getQuantityNode()];
		NodeDoubleList<Flight> current = listFlight.getFirtsNodeCircleDoubleList();
		int index = 0;

		do {
			flightArray[index] = current.getData();
			index++;
			current = current.getNextNode();
		} while (current != listFlight.getFirtsNodeCircleDoubleList());

		writeArrayToJson(flightArray, address);
	}

	public DoubleCircleListFlight readFlights(String address) {
		DoubleCircleListFlight listFlight = new DoubleCircleListFlight();
		Flight[] flightArray = readArrayFromJson(address, Flight[].class);

		if (flightArray != null) {
			int index = 0;
			while (index < flightArray.length) {
				listFlight.addLastAirplane(flightArray[index]);
				index++;
			}
		}
		return listFlight;
	}

	// METODOS PARA LISTAS DOBLES
	public void writePassengers(DoubleListPassenger listPassenger, String address) {
		if (listPassenger == null || listPassenger.isEmpty()) {
			return;
		}

		// Pasa los elementos de la lista doble a un arreglo Passenger[]
		Passenger[] passengerArray = new Passenger[listPassenger.getQuantityNode()];
		NodeDoubleList<Passenger> current = listPassenger.getHead();
		int index = 0;

		while (current != null) {
			passengerArray[index] = current.getData();
			index++;
			current = current.getNextNode();
		}

		writeArrayToJson(passengerArray, address);
	}

	public DoubleListPassenger readPassengers(String address, int maxCapacity) {
		DoubleListPassenger listPassenger = new DoubleListPassenger(maxCapacity);
		Passenger[] passengerArray = readArrayFromJson(address, Passenger[].class);

		if (passengerArray != null) {
			int index = 0;
			while (index < passengerArray.length) {
				listPassenger.addLast(passengerArray[index]);
				index++;
			}
			listPassenger.orderByNameAndAgeBubble();
		}
		return listPassenger;
	}

	// METODOS PARA COLAS
	public void writeBoardingQueue(BoardingQueue queue, String address) {
		if (queue == null || queue.isEmpty()) {
			return;
		}

		Passenger[] array = new Passenger[queue.getSize()];
		NodeSimpleList<Passenger> current = queue.getFirst();
		int index = 0;

		while (current != null) {
			array[index] = current.getData();
			index++;
			current = current.getNextNode();
		}

		writeArrayToJson(array, address);
	}

	public BoardingQueue readBoardingQueue(String address) {
		BoardingQueue queue = new BoardingQueue();
		Passenger[] array = readArrayFromJson(address, Passenger[].class);

		if (array != null) {
			int index = 0;
			while (index < array.length) {
				queue.addInQueue(array[index]);
				index++;
			}
		}
		return queue;
	}


	//METODOS PARA PILAS
	public void writeTravelHistory(StackHistoryTravels stack, String address) {
		if (stack == null || stack.isEmpty()) {
			return;
		}

		String[] array = new String[stack.getSize()]; 
		NodeSimpleList<String> current = stack.getTop();
		int index = 0;

		while (current != null) {
			array[index] = current.getData();
			index++;
			current = current.getNextNode();
		}

		writeArrayToJson(array, address);
	}

	public StackHistoryTravels readTravelHistory(String address) {
		StackHistoryTravels stack = new StackHistoryTravels();
		String[] array = readArrayFromJson(address, String[].class);

		if (array != null) {
			int index = array.length - 1;
			while (index >= 0) {
				stack.push(array[index]);
				index--;
			}
		}
		return stack;
	}

	// METODOS GENERICOS PARA ESCRIBIR Y GUARDAR LISTAS
	private <T> void writeArrayToJson(T[] dataArray, String address) {
		Gson gson = new Gson();
		try (FileWriter writer = new FileWriter(address)) {
			gson.toJson(dataArray, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private <T> T[] readArrayFromJson(String address, Class<T[]> clas) {
		File file = new File(address);
		if (!file.exists()) {
			return null;
		}

		Gson gson = new Gson();
		try (FileReader reader = new FileReader(file)) {
			return gson.fromJson(reader, clas);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
