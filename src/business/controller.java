package business;
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import business.LogicReservation;
import domain.Flight;
import domain.Passenger;
import presentation.BoardingPanel;
import presentation.FlightPanel;
import presentation.MainView;
import presentation.ReservationPanel;
import presentation.TripsPanel;


public class Controller implements ActionListener {

	private MainView view;
	private FlightPanel flightPanel;
	private ReservationPanel reservationPanel;
	private TripsPanel tripsPanel;
	private BoardingPanel boardingPanel;

	private boolean updating = false;

	public Controller() {
		view = new MainView();
		flightPanel = new FlightPanel();
		reservationPanel = new ReservationPanel();
		tripsPanel = new TripsPanel();
		boardingPanel = new BoardingPanel();

		addListeners();
		view.init();
		showFlights();
	}

	private void addListeners() {
	
		view.btnFlights.addActionListener(this);
		view.btnReservations.addActionListener(this);
		view.btnTrips.addActionListener(this);
		view.btnBoarding.addActionListener(this);

		flightPanel.btnPrevious.addActionListener(this);
		flightPanel.btnNext.addActionListener(this);
		flightPanel.btnPrioritize.addActionListener(this);
		reservationPanel.btnRegister.addActionListener(this);

		// Abordaje
		boardingPanel.cbxFlight.addActionListener(this);
		boardingPanel.btnBoardNext.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (updating) {
			return;
		}
		try {
			Object source = e.getSource();

			if (source == view.btnFlights) {
				showFlights();
			} else if (source == view.btnReservations) {
				showReservations();
			} else if (source == view.btnTrips) {
				showTrips();
			} else if (source == view.btnBoarding) {
				showBoarding();
			} else if (source == flightPanel.btnNext) {
				refreshFlightCard();
			} else if (source == flightPanel.btnPrioritize) {
				prioritizeFlights();

			} else if (source == reservationPanel.btnRegister) {
				registerReservation();

			} else if (source == boardingPanel.cbxFlight) {
				refreshBoardingPanel();
			}  else if (source == boardingPanel.btnBoardNext) {
				boardNext();
			}
		} catch (Exception ex) {
			showError("	ERROR " + ex.getMessage());
		}
	}


	private void showFlights() {
		refreshFlightCard();
		view.setContent(flightPanel, "Vuelos disponibles");
	}

	private void showReservations() {
		fillFlightCombo(reservationPanel.cbxFlight);
		view.setContent(reservationPanel, "Reservar");
	}

	private void showPassengers() {
		fillFlightCombo(passengersPanel.cbxFlight);          //Esta parte en espera ya que no se si ponerlo
		refreshPassengersPanel();
		view.setContent(passengersPanel, "Pasajeros y asientos");
	}

	private void showTrips() {
		tripsPanel.taTrips.setCaretPosition(0);
		view.setContent(tripsPanel, "Mis viajes");
	}

	private void showBoarding() {
		fillFlightCombo(boardingPanel.cbxFlight);
		refreshBoardingPanel();
		view.setContent(boardingPanel, "Abordaje");
	}


	private void registerReservation() {
		int flightNumber = getSelectedNumber(reservationPanel.cbxFlight);
		if (flightNumber == -1) {
			showWarning("Seleccione un vuelo");
			return;
		}
		String id = reservationPanel.tId.getText().trim();
		String name = reservationPanel.tName.getText().trim();
		String ageText = reservationPanel.tAge.getText().trim();

		if (id.isEmpty() || name.isEmpty() || ageText.isEmpty()) {
			showWarning("Complete la identificacion, el nombre y la edad");
			return;
		}

		int age;
		try {
			age = Integer.parseInt(ageText);
		} catch (NumberFormatException ex) {
			showWarning("La edad debe ser un numero entero");
			return;
		}


		if (error != null) {
			showWarning(error);
			return;
		}

	
		
		JOptionPane.showMessageDialog(view,
				"Reserva registrada para " + name + " en el vuelo " + flightNumber + ".\nAsiento actual: " + seat,
				"Reserva exitosa", JOptionPane.INFORMATION_MESSAGE);
		reservationPanel.clearForm();
	}

	/*

	private void refreshPassengersPanel() {
		int flightNumber = getSelectedNumber(passengersPanel.cbxFlight);
		LogicReservation reservation = airport.getReservation(flightNumber);
		if (reservation == null) {
			passengersPanel.lblCapacity.setText("Capacidad m\u00e1xima: -");
			passengersPanel.lblRegistered.setText("Registrados: -");
			passengersPanel.lblAvailable.setText("Disponibles: -");
			passengersPanel.taPassengers.setText("");
			return;
		}
		passengersPanel.lblCapacity.setText("Capacidad m\u00e1xima: " + reservation.getCapacity());
		passengersPanel.lblRegistered.setText("Registrados: " + reservation.getRegistered());
		passengersPanel.lblAvailable.setText("Disponibles: " + reservation.getAvailable());
		passengersPanel.taPassengers.setText("Orden actual: " + reservation.getOrderDescription() + "\n\n"
				+ reservation.getPassengersStartToEnd());
		passengersPanel.taPassengers.setCaretPosition(0);
	}

	// mode: 0 inicio->fin, 1 fin->inicio, 2 alfabetico, 3 edad asc, 4 edad desc
	private void showPassengerList(int mode) {
		int flightNumber = getSelectedNumber(passengersPanel.cbxFlight);
		LogicReservation reservation = airport.getReservation(flightNumber);
		if (reservation == null) {
			showWarning("Seleccione un vuelo.");
			return;
		}
		String header;
		String body;

		if (mode == 1) {
			header = "Recorrido de fin a inicio (orden actual: " + reservation.getOrderDescription() + ")";
			body = reservation.getPassengersEndToStart();
		} else {
			if (mode == 2) {
				reservation.sortPassengersByName();
			} else if (mode == 3) {
				reservation.sortPassengersByAge(true);
			} else if (mode == 4) {
				reservation.sortPassengersByAge(false);
			}
			header = "Orden actual: " + reservation.getOrderDescription();    
			body = reservation.getPassengersStartToEnd();
		}
		passengersPanel.taPassengers.setText(header + "\n\n" + body);
		passengersPanel.taPassengers.setCaretPosition(0);
	}
*/
/*

	private void refreshBoardingPanel() {
		int flightNumber = getSelectedNumber(boardingPanel.cbxFlight);
		LogicReservation reservation = airport.getReservation(flightNumber);

		updating = true;
		boardingPanel.cbxPassenger.removeAllItems();
		if (reservation != null) {
			String[] labels = reservation.getPassengerLabels();
			int i = 0;
			while (i < labels.length) {
				boardingPanel.cbxPassenger.addItem(labels[i]);
				i++;
			}
		}
		updating = false;

		if (reservation == null) {
			boardingPanel.taQueue.setText("");
			boardingPanel.taBoarded.setText("");
			return;
		}
		boardingPanel.taQueue.setText(reservation.showQueue());
		boardingPanel.taBoarded.setText(reservation.showBoarded());
	}

	private void addToBoardingQueue() {
		int flightNumber = getSelectedNumber(boardingPanel.cbxFlight);
		if (flightNumber == -1) {
			showWarning("Seleccione un vuelo.");
			return;
		}
		String passengerId = getSelectedId(boardingPanel.cbxPassenger);
		if (passengerId == null) {
			showWarning("Seleccione un pasajero. Si no aparece ninguno, primero debe reservar en este vuelo.");
			return;
		}
		String error = airport.enqueueBoarding(flightNumber, passengerId);
		if (error != null) {
			showWarning(error);
			return;
		}
		LogicReservation reservation = airport.getReservation(flightNumber);
		boardingPanel.taQueue.setText(reservation.showQueue());
	}

	private void boardNext() {
		int flightNumber = getSelectedNumber(boardingPanel.cbxFlight);
		if (flightNumber == -1) {
			showWarning("Seleccione un vuelo.");
			return;
		}
		Passenger passenger = airport.boardNext(flightNumber);
		if (passenger == null) {
			showWarning("No hay pasajeros en la cola de abordaje.");
			return;
		}
		LogicReservation reservation = airport.getReservation(flightNumber);
		boardingPanel.taQueue.setText(reservation.showQueue());
		boardingPanel.taBoarded.setText(reservation.showBoarded());
		JOptionPane.showMessageDialog(view, "Abordo: " + passenger.getName(), "Abordaje",
				JOptionPane.INFORMATION_MESSAGE);
	}

*/
	private void fillFlightCombo(JComboBox<String> combo) {
		int selected = getSelectedNumber(combo);
		String[] labels = airport.getFlightLabels();

		updating = true;
		combo.removeAllItems();
		int i = 0;
		while (i < labels.length) {
			combo.addItem(labels[i]);
			if (selected != -1 && numberOf(labels[i]) == selected) {
				combo.setSelectedIndex(i);
			}
			i++;
		}
		updating = false;
	}

	
	private String firstPart(Object item) {
		if (item == null) {
			return null;
		}
		String text = item.toString();
		int separator = text.indexOf(" | ");
		if (separator < 0) {
			return null;
		}
		return text.substring(0, separator);
	}

	private int numberOf(String label) {
		try {
			return Integer.parseInt(firstPart(label));
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	private int getSelectedNumber(JComboBox<String> combo) {
		String first = firstPart(combo.getSelectedItem());
		if (first == null) {
			return -1;
		}
		try {
			return Integer.parseInt(first);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	private String getSelectedId(JComboBox<String> combo) {
		return firstPart(combo.getSelectedItem());
	}

	private void showWarning(String message) {
		JOptionPane.showMessageDialog(view, message, "Aviso", JOptionPane.WARNING_MESSAGE);
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}