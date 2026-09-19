package presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import domain.Flight;
import data.DoubleListPassenger;

public class PrincipalView {

	private JFrame frame;
	private JPanel panelContenido;
	private CardLayout cardLayout;


	private JLabel lblFlightCodeValue;
	private JLabel lblRouteValue;
	private JLabel lblPlaneTypeValue;
	private JLabel lblMaxCapacityValue;
	private JLabel lblRegisteredPassengersValue;
	private JLabel lblAvailableSpacesValue;
	private JTextField txtIdReserva;
	private JTextField txtNombreReserva;
	private JTextField txtEdadReserva;
	private JComboBox<String> comboVueloReserva;
	private JTextField txtBuscarPasajero;
	private JComboBox<String> comboFiltroVueloPasajeros;
	private JTable tablaPasajeros;
	private DefaultTableModel modeloTablaPasajeros;
	private JLabel lblRutaViajeActual;
	private JLabel lblVueloViajeActual;

	private JList<String> listPasajerosRegistrados;
	private DefaultListModel<String> modelPasajerosRegistrados;
	private JList<String> listColaAbordaje;
	private DefaultListModel<String> modelColaAbordaje;
	private JLabel lblPasajeroAbordando;

	private final Color COLOR_MENU_BG = new Color(30, 41, 59);
	private final Color COLOR_MENU_BTN = new Color(51, 65, 85);
	private final Color COLOR_TEXT_WHITE = Color.WHITE;
	private final Color COLOR_PRIMARY = new Color(14, 165, 233);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalView window = new PrincipalView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PrincipalView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("SISTEMA DE CONTROL AEROPORTUARIO");
		frame.setBounds(100, 100, 900, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(COLOR_MENU_BG);
		panelMenu.setPreferredSize(new Dimension(200, 580));
		panelMenu.setLayout(new GridLayout(8, 1, 5, 5));
		panelMenu.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

		JLabel lblTituloMenu = new JLabel("SISTEMA DE CONTROL AEROPUERTERIO", SwingConstants.CENTER);
		lblTituloMenu.setPreferredSize(new Dimension(220, 12));
		lblTituloMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblTituloMenu.setForeground(COLOR_PRIMARY);
		panelMenu.add(lblTituloMenu);

		JButton btnVuelos = crearBotonMenu("Vuelos");
		JButton btnReservas = crearBotonMenu("Reservas");
		JButton btnPasajeros = crearBotonMenu(" Pasajeros");
		JButton btnMisViajes = crearBotonMenu(" Mis Viajes");
		JButton btnAbordaje = crearBotonMenu(" Abordaje");

		panelMenu.add(btnVuelos);
		panelMenu.add(btnReservas);
		panelMenu.add(btnPasajeros);
		panelMenu.add(btnMisViajes);
		panelMenu.add(btnAbordaje);

		frame.getContentPane().add(panelMenu, BorderLayout.WEST);

		cardLayout = new CardLayout();
		panelContenido = new JPanel(cardLayout);
		frame.getContentPane().add(panelContenido, BorderLayout.CENTER);

		panelContenido.add(crearPanelVuelos(), "Vuelos");
		panelContenido.add(crearPanelReservas(), "Reservas");
		panelContenido.add(crearPanelPasajeros(), "Pasajeros");
		panelContenido.add(crearPanelMisViajes(), "Mis Viajes");
		panelContenido.add(crearPanelAbordaje(), "Abordaje");

	
		btnVuelos.addActionListener(e -> cardLayout.show(panelContenido, "Vuelos"));
		btnReservas.addActionListener(e -> cardLayout.show(panelContenido, "Reservas"));
		btnPasajeros.addActionListener(e -> cardLayout.show(panelContenido, "Pasajeros"));
		btnMisViajes.addActionListener(e -> cardLayout.show(panelContenido, "Mis Viajes"));
		btnAbordaje.addActionListener(e -> cardLayout.show(panelContenido, "Abordaje"));
	}

	private JButton crearBotonMenu(String texto) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn.setBackground(COLOR_MENU_BTN);
		btn.setForeground(COLOR_TEXT_WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		return btn;
	}

   //panel de los vuelos 
	private JPanel crearPanelVuelos() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel("VUELOS DISPONIBLES", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panel.add(lblTitulo, BorderLayout.NORTH);

		JPanel panelCenter = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel panelInfoVuelo = new JPanel(new GridLayout(3, 2, 5, 5));
		panelInfoVuelo.setBorder(BorderFactory.createTitledBorder("Información del Vuelo"));

		lblFlightCodeValue = new JLabel("---");
		lblRouteValue = new JLabel("---");
		lblPlaneTypeValue = new JLabel("---");

		panelInfoVuelo.add(new JLabel("Número de Vuelo:"));
		panelInfoVuelo.add(lblFlightCodeValue);
		panelInfoVuelo.add(new JLabel("Ruta:"));
		panelInfoVuelo.add(lblRouteValue);
		panelInfoVuelo.add(new JLabel("Tipo de Avión:"));
		panelInfoVuelo.add(lblPlaneTypeValue);

		JPanel panelInfoAvion = new JPanel(new GridLayout(3, 2, 5, 5));
		panelInfoAvion.setBorder(BorderFactory.createTitledBorder("Capacidad de Asientos"));

		lblMaxCapacityValue = new JLabel("---");
		lblRegisteredPassengersValue = new JLabel("---");
		lblAvailableSpacesValue = new JLabel("---");

		panelInfoAvion.add(new JLabel("Capacidad Máxima:"));
		panelInfoAvion.add(lblMaxCapacityValue);
		panelInfoAvion.add(new JLabel("Pasajeros Registrados:"));
		panelInfoAvion.add(lblRegisteredPassengersValue);
		panelInfoAvion.add(new JLabel("Espacios Disponibles:"));
		panelInfoAvion.add(lblAvailableSpacesValue);

		panelCenter.add(panelInfoVuelo);
		panelCenter.add(panelInfoAvion);
		panel.add(panelCenter, BorderLayout.CENTER);

		JPanel panelNavegacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JButton btnAnterior = new JButton("ANTERIOR");
		JButton btnSiguiente = new JButton("SIGUIENTE");
		panelNavegacion.add(btnAnterior);
		panelNavegacion.add(btnSiguiente);
		panel.add(panelNavegacion, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel crearPanelReservas() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel("REGISTRAR RESERVA", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panel.add(lblTitulo, BorderLayout.NORTH);

		JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 15));
		panelForm.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

		txtIdReserva = new JTextField();
		txtNombreReserva = new JTextField();
		txtEdadReserva = new JTextField();
		comboVueloReserva = new JComboBox<>();

		panelForm.add(new JLabel("ID:"));
		panelForm.add(txtIdReserva);
		panelForm.add(new JLabel("Nombre:"));
		panelForm.add(txtNombreReserva);
		panelForm.add(new JLabel("Edad:"));
		panelForm.add(txtEdadReserva);
		panelForm.add(new JLabel("Seleccionar Vuelo:"));
		panelForm.add(comboVueloReserva);

		JButton btnRegistrar = new JButton("REGISTRAR RESERVA");
		panelForm.add(new JLabel(""));
		panelForm.add(btnRegistrar);

		panel.add(panelForm, BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelPasajeros() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel("PASAJEROS Y ASIENTOS", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panel.add(lblTitulo, BorderLayout.NORTH);

		JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));

		txtBuscarPasajero = new JTextField(15);
		JButton btnBuscar = new JButton(" BUSCAR");
		comboFiltroVueloPasajeros = new JComboBox<>();
		comboFiltroVueloPasajeros.addItem("Todos los vuelos");

		panelFiltros.add(new JLabel("Buscar por nombre:"));
		panelFiltros.add(txtBuscarPasajero);
		panelFiltros.add(btnBuscar);
		panelFiltros.add(new JLabel("Filtrar Vuelo:"));
		panelFiltros.add(comboFiltroVueloPasajeros);

		panel.add(panelFiltros, BorderLayout.NORTH);

		String[] columnas = {"Cédula", "Nombre", "Edad", "Vuelo", "Asiento"};
		modeloTablaPasajeros = new DefaultTableModel(columnas, 0);
		tablaPasajeros = new JTable(modeloTablaPasajeros);

		JScrollPane scrollTabla = new JScrollPane(tablaPasajeros);
		panel.add(scrollTabla, BorderLayout.CENTER);

		return panel;
	}

	private JPanel crearPanelMisViajes() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel("MIS VIAJES", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panel.add(lblTitulo, BorderLayout.NORTH);

		JPanel panelCardViaje = new JPanel(new GridLayout(3, 1, 10, 10));
		panelCardViaje.setBorder(BorderFactory.createTitledBorder("Viaje Actual"));

		lblRutaViajeActual = new JLabel("Ruta: ---", SwingConstants.CENTER);
		lblVueloViajeActual = new JLabel("Vuelo: ---", SwingConstants.CENTER);
		lblRutaViajeActual.setFont(new Font("Segoe UI", Font.BOLD, 16));

		panelCardViaje.add(lblRutaViajeActual);
		panelCardViaje.add(lblVueloViajeActual);

		JButton btnSiguienteViaje = new JButton("SIGUIENTE VIAJE");
		panelCardViaje.add(btnSiguienteViaje);

		panel.add(panelCardViaje, BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelAbordaje() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel("COLA DE ABORDAJE", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panel.add(lblTitulo, BorderLayout.NORTH);

		JPanel panelListas = new JPanel(new GridLayout(1, 2, 15, 0));

		modelPasajerosRegistrados = new DefaultListModel<>();
		listPasajerosRegistrados = new JList<>(modelPasajerosRegistrados);
		JScrollPane scrollRegistrados = new JScrollPane(listPasajerosRegistrados);
		scrollRegistrados.setBorder(BorderFactory.createTitledBorder("PASAJEROS REGISTRADOS"));

		modelColaAbordaje = new DefaultListModel<>();
		listColaAbordaje = new JList<>(modelColaAbordaje);
		JScrollPane scrollCola = new JScrollPane(listColaAbordaje);
		scrollCola.setBorder(BorderFactory.createTitledBorder("COLA DE ABORDAJE"));

		panelListas.add(scrollRegistrados);
		panelListas.add(scrollCola);
		panel.add(panelListas, BorderLayout.CENTER);

		JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JButton btnAgregarACola = new JButton("AGREGAR");
		JButton btnQuitarDeCola = new JButton("QUITAR");
		panelAcciones.add(btnAgregarACola);
		panelAcciones.add(btnQuitarDeCola);

		JPanel panelSouth = new JPanel(new GridLayout(2, 1, 5, 5));
		JButton btnAbordarSiguiente = new JButton("ABORDAR SIGUIENTE");
		btnAbordarSiguiente.setFont(new Font("Segoe UI", Font.BOLD, 13));

		lblPasajeroAbordando = new JLabel("Pasajero abordando: ---", SwingConstants.CENTER);
		lblPasajeroAbordando.setFont(new Font("Segoe UI", Font.ITALIC, 14));

		panelSouth.add(btnAbordarSiguiente);
		panelSouth.add(lblPasajeroAbordando);

		JPanel panelPie = new JPanel(new BorderLayout());
		panelPie.add(panelAcciones, BorderLayout.NORTH);
		panelPie.add(panelSouth, BorderLayout.SOUTH);

		panel.add(panelPie, BorderLayout.SOUTH);

		return panel;
	}
   
	public void updateFlightData(Flight flight) {

	    lblFlightCodeValue.setText(String.valueOf(flight.getNumberFlight()));
	    lblRouteValue.setText(flight.getRoute());
	    lblPlaneTypeValue.setText(flight.getAircraftType());

	    lblMaxCapacityValue.setText(flight.getMaximumCapacity() + " asientos");
	    lblRegisteredPassengersValue.setText(flight.getPassengerList().getQuantityNode() + " pasajeros");
	    lblAvailableSpacesValue.setText((flight.getMaximumCapacity() - flight.getPassengerList().getQuantityNode()) + " disponibles");
	}
}