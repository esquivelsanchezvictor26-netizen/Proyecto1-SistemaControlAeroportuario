package business.controller;

import presentation.PassengersPanel;
import presentation.PrioritizedFlightsPanel;

import javax.swing.JOptionPane;

import business.Logic.LogicControlTower;
import business.Logic.LogicFlight;
import business.Logic.LogicReservation;
import data.BoardingQueue;
import data.DoubleCircleListFlight;
import data.DoubleListPassenger;
import data.FilesJson.FilesJson;
import data.SimpleListReservation;
import data.StackHistoryTravels;
import domain.Flight;
import domain.Node.NodeDoubleList;
import domain.Node.NodeSimpleList;
import domain.Passenger;
import presentation.BoardingPanel;
import presentation.FlightPanel;
import presentation.MainView;
import presentation.ReservationPanel;
import presentation.TripsPanel;

public class ControllerAirport extends Functions {

    private LogicFlight logicFlight;
    private SimpleListReservation listReservation;
    private LogicControlTower controlTower;
    private FilesJson filesJson;
    private boolean passengersStartToEnd = true;

    private MainView mainView;

    // --- PILAS DE HISTORIAL DE VIAJES ---
    private StackHistoryTravels globalTravelStack;
    private StackHistoryTravels consultedStack;
    private String pathTripsPending = "trips_pending.json";
    private String pathTripsConsulted = "trips_consulted.json";

    // --- RUTAS DE ARCHIVOS JSON ---
    private String pathFlights = "flights.json";

    public ControllerAirport() {
        this.logicFlight = new LogicFlight();
        this.listReservation = new SimpleListReservation();
        this.filesJson = new FilesJson();
        this.globalTravelStack = new StackHistoryTravels();
        this.consultedStack = new StackHistoryTravels();
        this.mainView = new MainView();
    }

    public void init() {
        loadData();
        this.controlTower = new LogicControlTower(logicFlight.getFlightList());

        mainView.getBtnFlights().addActionListener(e -> showFlightsView());
        mainView.getBtnReservations().addActionListener(e -> showReservationsView());
        mainView.getBtnPassengers().addActionListener(e -> showPassengersView());
        mainView.getBtnTrips().addActionListener(e -> showTripsView());
        mainView.getBtnBoarding().addActionListener(e -> showBoardingView());

        mainView.init();
        showFlightsView();
    }

    private void loadData() {
        // Cargar vuelos
        DoubleCircleListFlight loadedFlights = filesJson.readFlights(pathFlights);
        if (loadedFlights != null && !loadedFlights.isEmpty()) {
            logicFlight = new LogicFlight();
            NodeDoubleList<Flight> current = loadedFlights.getFirtsNodeCircleDoubleList();
            do {
                Flight.updateNextNumber(current.getData().getNumberFlight());
                logicFlight.addFlight(current.getData());
                current = current.getNextNode();
            } while (current != loadedFlights.getFirtsNodeCircleDoubleList());
        } else {
            logicFlight.addFlight(new Flight("San José - Miami", "Boeing 737", 3, true));
            logicFlight.addFlight(new Flight("San José - Madrid", "Airbus A350", 5, true));
            logicFlight.addFlight(new Flight("San José - Cancún", "Boeing 787", 4, true));
            logicFlight.addFlight(new Flight("San José - Bogotá", "Airbus A320", 4, true));
            logicFlight.addFlight(new Flight("San José - Ciudad de México", "Boeing 737", 6, true));
            saveFlights();
        }

        // Cargar pilas de historial de viajes
        StackHistoryTravels loadedPending = filesJson.readTravelHistory(pathTripsPending);
        if (loadedPending != null && !loadedPending.isEmpty()) {
            this.globalTravelStack = loadedPending;
        }

        StackHistoryTravels loadedConsulted = filesJson.readTravelHistory(pathTripsConsulted);
        if (loadedConsulted != null && !loadedConsulted.isEmpty()) {
            this.consultedStack = loadedConsulted;
        }
    }

    private void saveFlights() {
        filesJson.writeFlights(logicFlight.getFlightList(), pathFlights);
    }

    // ==========================================
    // VISTA 1: VUELOS DISPONIBLES
    // ==========================================
    public void showFlightsView() {
        FlightPanel v = new FlightPanel();

        updateFlightDisplay(v);

        v.getBtnPrevious().addActionListener(e -> {
            logicFlight.getFlightList().changeAirplanePreviousNode();
            updateFlightDisplay(v);
        });

        v.getBtnNext().addActionListener(e -> {
            logicFlight.getFlightList().changeAirplanetoNext();
            updateFlightDisplay(v);
        });

        v.getBtnPrioritize().addActionListener(e -> {
            controlTower.calculateOccupancyRates(listReservation);
            controlTower.prioritizeFlights();
            saveFlights();
            showPrioritizedFlightsView();
        });

        mainView.setContent(v, "Vuelos Disponibles");
    }

    public void showPrioritizedFlightsView() {
        PrioritizedFlightsPanel v = new PrioritizedFlightsPanel();

        controlTower.calculateOccupancyRates(listReservation);
        controlTower.prioritizeFlights();
        saveFlights();

        v.setData(controlTower.getPrioritizedTableData(listReservation));
        v.getBtnBack().addActionListener(e -> showFlightsView());

        mainView.setContent(v, "Vuelos Priorizados");
    }

    // ==========================================
    // VISTA PASAJEROS POR VUELO
    // ==========================================
    public void showPassengersView() {
        PassengersPanel v = new PassengersPanel();
        passengersStartToEnd = true;

        populateFlightComboBox(v.getCbxFlight());

        v.getCbxFlight().addActionListener(e -> updatePassengersTable(v));

        v.getBtnStartToEnd().addActionListener(e -> {
            passengersStartToEnd = true;
            updatePassengersTable(v);
        });

        v.getBtnEndToStart().addActionListener(e -> {
            passengersStartToEnd = false;
            updatePassengersTable(v);
        });

        updatePassengersTable(v);

        mainView.setContent(v, "Pasajeros");
    }

    private void updatePassengersTable(PassengersPanel v) {
        String selected = (String) v.getCbxFlight().getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            v.getLblInfo().setText("No hay vuelos registrados");
            v.setData(new Object[0][4]);
            return;
        }

        int flightNum = Integer.parseInt(selected.split(" ")[1]);
        LogicReservation reservation = getOrCreateReservationForFlight(flightNum);

        int capacity = reservation.getFlight().getMaximumCapacity();
        int registered = reservation.getQuantityPassengersByFlight();
        int available = capacity - registered;
        if (available < 0) available = 0;

        v.getLblInfo().setText("Capacidad máxima: " + capacity + "   |   Registrados: " + registered
                + "   |   Disponibles: " + available);

        v.setData(reservation.getPassengersTable(passengersStartToEnd));
    }

    private void updateFlightDisplay(FlightPanel v) {
        DoubleCircleListFlight flightList = logicFlight.getFlightList();

        if (flightList.isEmpty()) {
            v.getLblNumber().setText("-");
            v.getLblRoute().setText("Sin vuelos registrados");
            v.getLblType().setText("-");
            v.getLblCapacity().setText("0");
            v.getLblReserved().setText("0");
            v.getLblAvailable().setText("0");
            v.getLblStatus().setText("-");
            return;
        }

        if (flightList.getCurrentNode() == null) {
            flightList.setCurrentNode(flightList.getFirtsNodeCircleDoubleList());
        }

        Flight f = flightList.getCurrentNode().getData();

        int occupied = getPassengerCountForFlight(f.getNumberFlight());
        int available = f.getMaximumCapacity() - occupied;

        String status;
        if (!f.isStatusAircraft()) {
            status = "Inactivo";
        } else if (available <= 0) {
            status = "Lleno";
            available = 0;
        } else {
            status = "Disponible";
        }

        v.getLblNumber().setText(String.valueOf(f.getNumberFlight()));
        v.getLblRoute().setText(f.getRoute());
        v.getLblType().setText(f.getAircraftType());
        v.getLblCapacity().setText(String.valueOf(f.getMaximumCapacity()));
        v.getLblReserved().setText(String.valueOf(occupied));
        v.getLblAvailable().setText(String.valueOf(available));
        v.getLblStatus().setText(status);
    }

    // ==========================================
    // VISTA 2: RESERVAR ASIENTOS
    // ==========================================
    public void showReservationsView() {
        ReservationPanel v = new ReservationPanel();

        populateFlightComboBox(v.getCbxFlight());

        v.getBtnRegister().addActionListener(e -> {
            String selected = (String) v.getCbxFlight().getSelectedItem();
            if (selected == null || selected.isEmpty()) {
                JOptionPane.showMessageDialog(mainView, "Debe seleccionar un vuelo válido.");
                return;
            }

            int flightNum = Integer.parseInt(selected.split(" ")[1]);

            String id = v.gettId().getText().trim();
            String name = v.gettName().getText().trim();
            String ageStr = v.gettAge().getText().trim();

            if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(mainView, "Por favor complete todos los campos.");
                return;
            }

            try {
                int age = Integer.parseInt(ageStr);

                LogicReservation reservation = getOrCreateReservationForFlight(flightNum);
                String result = reservation.reserveSeat(id, name, age, globalTravelStack);
                JOptionPane.showMessageDialog(mainView, result);

                if (!result.startsWith("ERROR")) {
                    v.clearForm();

                    // Guardar pasajeros del vuelo
                    filesJson.writePassengers(reservation.getPassengerList(), "vuelo" + flightNum + ".json");
                    // Guardar la pila global
                    filesJson.writeTravelHistory(globalTravelStack, pathTripsPending);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainView, "La edad debe ser un número entero válido.");
            }
        });

        mainView.setContent(v, "Registrar Reserva");
    }

    // ==========================================
    // VISTA 3: MIS VIAJES (PILAS LIFO)
    // ==========================================
    public void showTripsView() {
        TripsPanel v = new TripsPanel();

        updateTripsDisplay(v);

        v.getBtnConsult().addActionListener(e -> {
            if (globalTravelStack.isEmpty()) {
                JOptionPane.showMessageDialog(mainView, "No hay reservas pendientes por consultar.");
                return;
            }

            String consultedRecord = globalTravelStack.pop();
            consultedStack.push(consultedRecord);

            filesJson.writeTravelHistory(globalTravelStack, pathTripsPending);
            filesJson.writeTravelHistory(consultedStack, pathTripsConsulted);

            JOptionPane.showMessageDialog(mainView, "INFORMACIÓN DE RESERVA CONSULTADA:\n\n" + consultedRecord,
                    "Detalle de Consulta", JOptionPane.INFORMATION_MESSAGE);

            updateTripsDisplay(v);
        });

        mainView.setContent(v, "Mis Viajes");
    }

    private void updateTripsDisplay(TripsPanel v) {
        v.getTaPendingTrips().setText(globalTravelStack.showStack());
        v.getTaConsultedTrips().setText(consultedStack.showStack());
    }

    // ==========================================
    // VISTA 4: COLA DE ABORDAJE (FIFO)
    // ==========================================
    public void showBoardingView() {
        BoardingPanel v = new BoardingPanel();

        populateFlightComboBox(v.getCbxFlight());
        v.getCbxFlight().addActionListener(e -> updateBoardingDisplay(v));

        v.getBtnAddToQueue().addActionListener(e -> {
            Passenger selectedPassenger = v.getListReservations().getSelectedValue();
            if (selectedPassenger == null) {
                JOptionPane.showMessageDialog(mainView, "Debe seleccionar un pasajero de la lista.");
                return;
            }

            String selectedFlight = (String) v.getCbxFlight().getSelectedItem();
            if (selectedFlight == null) return;

            int flightNum = Integer.parseInt(selectedFlight.split(" ")[1]);
            LogicReservation reservation = getOrCreateReservationForFlight(flightNum);

            String msg = reservation.addPassengerToBoardingQueue(selectedPassenger.getId());
            JOptionPane.showMessageDialog(mainView, msg);

            if (!msg.startsWith("ERROR")) {
                filesJson.writeBoardingQueue(reservation.getBoardingQueue(), "queue_vuelo" + flightNum + ".json");
            }

            updateBoardingDisplay(v);
        });

        v.getBtnBoardNext().addActionListener(e -> {
            String selectedFlight = (String) v.getCbxFlight().getSelectedItem();
            if (selectedFlight == null) return;

            int flightNum = Integer.parseInt(selectedFlight.split(" ")[1]);
            LogicReservation reservation = getOrCreateReservationForFlight(flightNum);

            String msg = reservation.boardNextPassenger();
            JOptionPane.showMessageDialog(mainView, msg);

            filesJson.writeBoardingQueue(reservation.getBoardingQueue(), "queue_vuelo" + flightNum + ".json");
            filesJson.writePassengers(reservation.getBoardedPassengerList(), "boarded_vuelo" + flightNum + ".json");

            updateBoardingDisplay(v);
        });

        updateBoardingDisplay(v);
        mainView.setContent(v, "Cola de Abordaje");
    }

    private void updateBoardingDisplay(BoardingPanel v) {
        String selected = (String) v.getCbxFlight().getSelectedItem();

        v.getModelReservations().clear();
        v.getTaQueue().setText("");
        v.getTaBoarded().setText("");

        if (selected == null || selected.isEmpty()) return;

        int flightNum = Integer.parseInt(selected.split(" ")[1]);
        LogicReservation reservation = getOrCreateReservationForFlight(flightNum);

        // 1. Mostrar Pasajeros con Reserva (Lista Doble)
        NodeDoubleList<Passenger> currentRes = reservation.getPassengerList().getHead();
        while (currentRes != null) {
            v.getModelReservations().addElement(currentRes.getData());
            currentRes = currentRes.getNextNode();
        }

        // 2. Mostrar Pasajeros en Cola FIFO
        NodeSimpleList<Passenger> currentQueue = reservation.getBoardingQueue().getFirst();
        StringBuilder queueText = new StringBuilder();
        while (currentQueue != null) {
            queueText.append(currentQueue.getData().getName())
                     .append(" (ID: ").append(currentQueue.getData().getId()).append(")\n");
            currentQueue = currentQueue.getNextNode();
        }
        v.getTaQueue().setText(queueText.length() > 0 ? queueText.toString() : "No hay pasajeros en cola.");

        // 3. Mostrar Pasajeros Ya Abordados
        NodeDoubleList<Passenger> currentBoarded = reservation.getBoardedPassengerList().getHead();
        StringBuilder boardedText = new StringBuilder();
        while (currentBoarded != null) {
            boardedText.append("✔️ ").append(currentBoarded.getData().getName())
                       .append(" (ID: ").append(currentBoarded.getData().getId()).append(")\n");
            currentBoarded = currentBoarded.getNextNode();
        }
        v.getTaBoarded().setText(boardedText.length() > 0 ? boardedText.toString() : "Ningún pasajero ha abordado.");
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================

    private void populateFlightComboBox(javax.swing.JComboBox<String> cbx) {
        clearComboBox(cbx);
        DoubleCircleListFlight flightList = logicFlight.getFlightList();

        if (flightList.isEmpty()) return;

        NodeDoubleList<Flight> current = flightList.getFirtsNodeCircleDoubleList();
        do {
            Flight f = current.getData();
            cbx.addItem("Vuelo " + f.getNumberFlight() + " - " + f.getRoute());
            current = current.getNextNode();
        } while (current != flightList.getFirtsNodeCircleDoubleList());
    }

    private LogicReservation getOrCreateReservationForFlight(int flightNumber) {
        NodeSimpleList<LogicReservation> current = listReservation.getFirstReservation();

        while (current != null) {
            if (current.getData() != null && current.getData().getFlight() != null) {
                if (current.getData().getFlight().getNumberFlight() == flightNumber) {
                    return current.getData();
                }
            }
            current = current.getNextNode();
        }

        Flight f = logicFlight.getFlightList().showAirplaneById(flightNumber);
        if (f == null) {
            NodeDoubleList<Flight> nodeFlight = logicFlight.getFlightList().getFirtsNodeCircleDoubleList();
            if (nodeFlight != null) {
                do {
                    if (nodeFlight.getData().getNumberFlight() == flightNumber) {
                        f = nodeFlight.getData();
                        break;
                    }
                    nodeFlight = nodeFlight.getNextNode();
                } while (nodeFlight != logicFlight.getFlightList().getFirtsNodeCircleDoubleList());
            }
        }

        if (f != null) {
            LogicReservation newReservation = new LogicReservation(f);

            DoubleListPassenger loadedPassengers = filesJson.readPassengers("vuelo" + flightNumber + ".json", f.getMaximumCapacity());
            if (loadedPassengers != null) {
                newReservation.setPassengerList(loadedPassengers);
            }

            BoardingQueue loadedQueue = filesJson.readBoardingQueue("queue_vuelo" + flightNumber + ".json");
            if (loadedQueue != null) {
                newReservation.setBoardingQueue(loadedQueue);
            }

            DoubleListPassenger loadedBoarded = filesJson.readPassengers("boarded_vuelo" + flightNumber + ".json", f.getMaximumCapacity());
            if (loadedBoarded != null) {
                newReservation.setBoardedPassengerList(loadedBoarded);
            }

            listReservation.saveElementLast(newReservation);
            return newReservation;
        }

        return null;
    }

    private int getPassengerCountForFlight(int flightNumber) {
        if (listReservation == null) return 0;

        NodeSimpleList<LogicReservation> current = listReservation.getFirstReservation();
        while (current != null) {
            if (current.getData() != null && current.getData().getFlight() != null) {
                if (current.getData().getFlight().getNumberFlight() == flightNumber) {
                    return current.getData().getQuantityPassengersByFlight();
                }
            }
            current = current.getNextNode();
        }
        return 0;
    }

    public Object[][] getFlightTableData() {
        if (logicFlight == null || logicFlight.getFlightList() == null || logicFlight.getFlightList().isEmpty()) {
            return new Object[0][8];
        }

        int count = logicFlight.getFlightList().getQuantityNode();
        Object[][] data = new Object[count][8];

        NodeDoubleList<Flight> curr = logicFlight.getFlightList().getFirtsNodeCircleDoubleList();
        int index = 0;

        do {
            Flight f = curr.getData();

            int reservedSeats = getPassengerCountForFlight(f.getNumberFlight());
            int maxCapacity = f.getMaximumCapacity();
            int availableSeats = maxCapacity - reservedSeats;

            String status;
            if (!f.isStatusAircraft()) {
                status = "Inactivo";
            } else if (availableSeats <= 0) {
                status = "Lleno";
                availableSeats = 0;
            } else {
                status = "Disponible";
            }

            data[index][0] = index + 1;
            data[index][1] = "Vuelo " + f.getNumberFlight();
            data[index][2] = f.getRoute();
            data[index][3] = f.getAircraftType();
            data[index][4] = maxCapacity;
            data[index][5] = reservedSeats;
            data[index][6] = availableSeats;
            data[index][7] = status;

            index++;
            curr = curr.getNextNode();
        } while (curr != logicFlight.getFlightList().getFirtsNodeCircleDoubleList());

        return data;
    }

    private DoubleListPassenger getPassengerListForFlight(int flightNumber) {
        LogicReservation res = getOrCreateReservationForFlight(flightNumber);
        return res != null ? res.getPassengerList() : null;
    }
}