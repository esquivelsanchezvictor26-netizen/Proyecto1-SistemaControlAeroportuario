package data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import domain.Flight;
import domain.NodeDoubleList;

public class FilesJsonFlight {

    public FilesJsonFlight() {}

    // Escribe la lista en JSON convirtiéndola a arreglo primitivo
    public void writeFileJsonFlight(DoubleCircleListFlight listFlight, String addressFile) {
        if (listFlight == null || listFlight.isEmpty()) {
            return;
        }

        // Convierte la lista propia a un arreglo primitivo Flight[]
        Flight[] flightArray = new Flight[listFlight.getQuantityNode()];
        NodeDoubleList<Flight> current = listFlight.getFirtsNodeCircleDoubleList();
        int index = 0;

        do {
            flightArray[index] = current.getData();
            index++;
            current = current.getNextNode();
        } while (current != listFlight.getFirtsNodeCircleDoubleList());

        // Serializa el arreglo primitivo a JSON
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(addressFile)) {
            gson.toJson(flightArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lee el archivo JSON y reconstruye la lista circular con nodos
    public DoubleCircleListFlight readJsonFlight(String addressFile) {
        DoubleCircleListFlight listFlight = new DoubleCircleListFlight();
        File file = new File(addressFile);

        if (!file.exists()) {
            return listFlight;
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            // Lee directamente a un arreglo nativo de Java
            Flight[] flightArray = gson.fromJson(reader, Flight[].class);

            if (flightArray != null) {
                int index = 0;
                while (index < flightArray.length) {
                    listFlight.addLastAirplane(flightArray[index]);
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listFlight;
    }
}
    
    //ESTO EN CASO DE HACER UN SOLO JSON PARA T
    /*
     package data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import domain.Flight;
import domain.NodeDoubleList;
import domain.Passenger;

public class FilesJson {

    public FilesJson() {}

    // ==========================================
    // 1. PERSISTENCIA DE VUELOS (DoubleCircleListFlight)
    // ==========================================

    public void saveFlights(DoubleCircleListFlight listFlight, String filePath) {
        if (listFlight == null || listFlight.isEmpty()) {
            return;
        }

        // Pasar los elementos de la lista circular a un arreglo primitivo Flight[]
        Flight[] flightArray = new Flight[listFlight.getQuantityNode()];
        NodeDoubleList<Flight> current = listFlight.getFirtsNodeCircleDoubleList();
        int index = 0;

        do {
            flightArray[index] = current.getData();
            index++;
            current = current.getNextNode();
        } while (current != listFlight.getFirtsNodeCircleDoubleList());

        saveArrayToJson(flightArray, filePath);
    }

    public DoubleCircleListFlight loadFlights(String filePath) {
        DoubleCircleListFlight listFlight = new DoubleCircleListFlight();
        Flight[] flightArray = loadArrayFromJson(filePath, Flight[].class);

        if (flightArray != null) {
            int index = 0;
            while (index < flightArray.length) {
                listFlight.addLastAirplane(flightArray[index]);
                index++;
            }
        }
        return listFlight;
    }

    // ==========================================
    // 2. PERSISTENCIA DE PASAJEROS (DoubleListPassenger)
    // ==========================================

    public void savePassengers(DoubleListPassenger listPassenger, String filePath) {
        if (listPassenger == null || listPassenger.isEmpty()) {
            return;
        }

        // Pasar los elementos de la lista doble a un arreglo primitivo Passenger[]
        Passenger[] passengerArray = new Passenger[listPassenger.getQuantityNode()];
        NodeDoubleList<Passenger> current = listPassenger.getHead();
        int index = 0;

        while (current != null) {
            passengerArray[index] = current.getData();
            index++;
            current = current.getNextNode();
        }

        saveArrayToJson(passengerArray, filePath);
    }

    public DoubleListPassenger loadPassengers(String filePath, int maxCapacity) {
        DoubleListPassenger listPassenger = new DoubleListPassenger(maxCapacity);
        Passenger[] passengerArray = loadArrayFromJson(filePath, Passenger[].class);

        if (passengerArray != null) {
            int index = 0;
            while (index < passengerArray.length) {
                listPassenger.addOrderedByAge(passengerArray[index]);
                index++;
            }
        }
        return listPassenger;
    }

    // ==========================================
    // MÉTODOS PRIVADOS AUXILIARES (GSON)
    // ==========================================

    private <T> void saveArrayToJson(T[] dataArray, String filePath) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(dataArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> T[] loadArrayFromJson(String filePath, Class<T[]> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
    */
