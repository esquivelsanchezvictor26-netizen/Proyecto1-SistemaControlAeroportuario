package business.controller;

import javax.swing.JOptionPane;

import business.Logic.LogicControlTower;
import business.Logic.LogicFlight;
import business.Logic.LogicReservation;
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
	private StackHistoryTravels globalTravelStack;
	private StackHistoryTravels consultedStack;

	private MainView mainView;

	// RUTAS DE ARCHIVOS JSON
	private String pathFlights = "flights.json"; //Lista Circular Doble de Vuelos
	private String pathPassenger = "passengers.json"; //Lista Doble de Pasajeros
	private String pathTripsHistory = "tripsHistory.json";    // Pila principal (Por consultar)
	private String pathTripsConsulted = "tripsConsulted.json";  // Pila secundaria (Consultados)
	private String pathBoard = "boardQueue.json"; //Cola de abordaje

	public ControllerAirport() {
		this.logicFlight = new LogicFlight();
		this.listReservation = new SimpleListReservation();
		this.filesJson = new FilesJson();
		this.globalTravelStack = new StackHistoryTravels();
		this.consultedStack = new StackHistoryTravels();
		this.mainView = new MainView();
	}

	public void init() {
		// 1. Cargar datos desde JSON
		loadData();

		// 2. Inicializar la Torre de Control con la lista cargada
		this.controlTower = new LogicControlTower(logicFlight.getFlightList());

		// 3. Registrar eventos de la barra de navegación lateral (MainView)
		mainView.getBtnFlights().addActionListener(e -> showFlightsView());
		mainView.getBtnReservations().addActionListener(e -> showReservationsView());
		mainView.getBtnTrips().addActionListener(e -> showTripsView());
		mainView.getBtnBoarding().addActionListener(e -> showBoardingView());

		// 4. Mostrar la ventana principal
		mainView.init();

		// 5. Vista por defecto al abrir la aplicación
		showFlightsView();
	}

	private void loadData() {
		// Cargar vuelos
		DoubleCircleListFlight loadedFlights = filesJson.readFlights(pathFlights);
		if (loadedFlights != null && !loadedFlights.isEmpty()) {
			logicFlight = new LogicFlight();
			NodeDoubleList<Flight> current = loadedFlights.getFirtsNodeCircleDoubleList();
			do {
				logicFlight.addFlight(current.getData());
				current = current.getNextNode();
			} while (current != loadedFlights.getFirtsNodeCircleDoubleList());
		} else {
			// Datos quemados por defecto si el archivo no existe
			logicFlight.addFlight(new Flight(101, "San José - Miami", "Boeing 737", 3, true));
			logicFlight.addFlight(new Flight(202, "San José - Madrid", "Airbus A350", 5, true));
			logicFlight.addFlight(new Flight(303, "San José - Cancún", "Boeing 787", 4, true));
			logicFlight.addFlight(new Flight(404, "San José - Bogotá", "Airbus A320", 4, true));
			logicFlight.addFlight(new Flight(505, "San José - Ciudad de México", "Boeing 737", 6, true));
			logicFlight.addFlight(new Flight(606, "San José - Panamá", "Embraer 190", 3, true));
			logicFlight.addFlight(new Flight(707, "San José - Los Ángeles", "Boeing 787", 5, true));
			logicFlight.addFlight(new Flight(808, "San José - Lima", "Airbus A320", 4, true));
			logicFlight.addFlight(new Flight(909, "San José - Toronto", "Airbus A330", 5, true));
			logicFlight.addFlight(new Flight(1010, "San José - Nueva York", "Boeing 777", 6, true));
			saveFlights();
		}

		// Cargar las dos pilas al iniciar el sistema
		StackHistoryTravels loadedPending = filesJson.readTravelHistory(pathTripsHistory);
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

	// VISTA 1: VUELOS DISPONIBLES (Navegación Circular)
	public void showFlightsView() {
		FlightPanel v = new FlightPanel();

		// Mostrar el vuelo actual
		updateFlightDisplay(v);

		// Botón "Anterior" (Navegación circular a la izquierda)
		v.getBtnPrevious().addActionListener(e -> {
			logicFlight.getFlightList().changeAirplanePreviousNode();
			updateFlightDisplay(v);
		});

		// Botón "Siguiente" (Navegación circular a la derecha)
		v.getBtnNext().addActionListener(e -> {
			logicFlight.getFlightList().changeAirplanetoNext();
			updateFlightDisplay(v);
		});

		// Botón "Priorizar Vuelos" (QuickSort Torre de Control)
		v.getBtnPrioritize().addActionListener(e -> {
			controlTower.calculateOccupancyRates(listReservation);
			controlTower.prioritizeFlights();
			updateFlightDisplay(v);
			JOptionPane.showMessageDialog(mainView, "Vuelos priorizados por ocupación mediante QuickSort con éxito.");
			saveFlights();
		});

		mainView.setContent(v, "Vuelos Disponibles");
	}

	private void updateFlightDisplay(FlightPanel v) {
		DoubleCircleListFlight flightList = logicFlight.getFlightList();

		if (flightList.isEmpty()) {
			v.getLblNumber().setText("-");
			v.getLblRoute().setText("Sin vuelos registrados");
			v.getLblType().setText("-");
			v.getLblCapacity().setText("0");
			v.getLblAvailable().setText("0");
			v.getLblStatus().setText("-");
			return;
		}

		if (flightList.getCurrentNode() == null) {
			flightList.setCurrentNode(flightList.getFirtsNodeCircleDoubleList());
		}

		Flight f = flightList.getCurrentNode().getData();

		// Buscar cuántas reservas tiene este vuelo
		int occupied = getPassengerCountForFlight(f.getNumberFlight());
		int available = f.getMaximumCapacity() - occupied;

		v.getLblNumber().setText(String.valueOf(f.getNumberFlight()));
		v.getLblRoute().setText(f.getRoute());
		v.getLblType().setText(f.getAircraftType());
		v.getLblCapacity().setText(String.valueOf(f.getMaximumCapacity()));
		v.getLblAvailable().setText(String.valueOf(available));
		//Evalua el estado dinámicamente según los espacios disponibles
		if (!f.isStatusAircraft()) {
			v.getLblStatus().setText("Inactivo");
		} else if (available <= 0) {
			v.getLblStatus().setText("Agotado");
		} else {
			v.getLblStatus().setText("Disponible");
		}
	}

	// VISTA 2: RESERVAR ASIENTOS
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

				// 1. Realizar la reserva
				String result = reservation.reserveSeat(id, name, age, globalTravelStack);
				JOptionPane.showMessageDialog(mainView, result);

				if (!result.startsWith("ERROR")) {
					v.clearForm();

					// 1. Guardar pasajeros del vuelo específico
					String fileName = "vuelo" + flightNum + ".json";
					filesJson.writePassengers(reservation.getPassengerList(), fileName);

					// 2. Guardar el estado de la pila global
					// Guardar únicamente la pila de pendientes cuando se agrega una nueva reserva
					filesJson.writeTravelHistory(globalTravelStack, pathTripsHistory);
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(mainView, "La edad debe ser un número entero válido.");
			}
		});

		mainView.setContent(v, "Registrar Reserva");
	}

	// VISTA 3: MIS VIAJES (Pila LIFO)
	public void showTripsView() {
		TripsPanel v = new TripsPanel();

		// Actualizar el estado visual de ambas pilas
		updateTripsDisplay(v);

		// Evento del botón Consultar
		v.getBtnConsult().addActionListener(e -> {
			if (globalTravelStack.isEmpty()) {
				JOptionPane.showMessageDialog(mainView, "No hay reservas pendientes por consultar.");
				return;
			}

			// 1. Desapilar de la pila principal (LIFO)
			String consultedRecord = globalTravelStack.pop();

			// 2. Apilar en la pila de consultados (LIFO)
			consultedStack.push(consultedRecord);

			// 3. Guardar el estado actualizado de AMBAS pilas en sus respectivos JSON
			filesJson.writeTravelHistory(globalTravelStack, pathTripsHistory);
			filesJson.writeTravelHistory(consultedStack, pathTripsConsulted);

			// 4. Mostrar información al usuario
			JOptionPane.showMessageDialog(mainView, "INFORMACIÓN DE RESERVA CONSULTADA:\n\n" + consultedRecord,
					"Detalle de Consulta", JOptionPane.INFORMATION_MESSAGE);

			// 5. Refrescar los dos JTextArea de la interfaz
			updateTripsDisplay(v);
		});

		mainView.setContent(v, "Mis Viajes");
	}

	private void updateTripsDisplay(TripsPanel v) {
		// Cuadro Izquierdo: Pila principal por consultar
		v.getTaPendingTrips().setText(globalTravelStack.showStack());

		// Cuadro Derecho: Pila de consultados
		v.getTaConsultedTrips().setText(consultedStack.showStack());
	}

	// VISTA 4: COLA DE ABORDAJE (FIFO)
	public void showBoardingView() {
		BoardingPanel v = new BoardingPanel();

		populateFlightComboBox(v.getCbxFlight());

		// Al cambiar de vuelo en el ComboBox, refrescar las áreas de texto de la cola
		v.getCbxFlight().addActionListener(e -> updateBoardingDisplay(v));

		// Actualización inicial
		updateBoardingDisplay(v);

		// Botón "Abordar siguiente" (Saca de la cola FIFO)
		v.getBtnBoardNext().addActionListener(e -> {
			String selected = (String) v.getCbxFlight().getSelectedItem();
			if (selected == null || selected.isEmpty()) return;

			int flightNum = Integer.parseInt(selected.split(" ")[1]);
			LogicReservation reservation = getOrCreateReservationForFlight(flightNum);

			String msg = reservation.boardNextPassenger();
			JOptionPane.showMessageDialog(mainView, msg);

			updateBoardingDisplay(v);
		});

		mainView.setContent(v, "Cola de Abordaje");
	}

	private void updateBoardingDisplay(BoardingPanel v) {
		String selected = (String) v.getCbxFlight().getSelectedItem();
		if (selected == null || selected.isEmpty()) {
			v.getTaQueue().setText("Sin datos");
			return;
		}

		int flightNum = Integer.parseInt(selected.split(" ")[1]);
		LogicReservation reservation = getOrCreateReservationForFlight(flightNum);

		// Mostrar pasajeros registrados esperando abordar
		v.getTaQueue().setText(reservation.getPassengersAscending());
	}

	// MÉTODOS AUXILIARES DE BÚSQUEDA Y NAVEGACIÓ
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
			if (current.getData().getFlight().getNumberFlight() == flightNumber) {
				return current.getData();
			}
			current = current.getNextNode();
		}

		// Si no existe la reserva para este vuelo, se crea e inserta en la lista simple
		Flight f = logicFlight.getFlightList().showAirplaneById(flightNumber);
		LogicReservation newReservation = new LogicReservation(f);
		listReservation.saveElementLast(newReservation);
		return newReservation;
	}

	private int getPassengerCountForFlight(int flightNumber) {
		NodeSimpleList<LogicReservation> current = listReservation.getFirstReservation();
		while (current != null) {
			if (current.getData().getFlight().getNumberFlight() == flightNumber) {
				return current.getData().getQuantityPassengersByFlight();
			}
			current = current.getNextNode();
		}
		return 0;
	}

	private DoubleListPassenger getPassengerListForFlight(int flightNumber) {
		LogicReservation res = getOrCreateReservationForFlight(flightNumber);
		return res != null ? null : null; // Se gestiona directo dentro de LogicReservation
	}
}