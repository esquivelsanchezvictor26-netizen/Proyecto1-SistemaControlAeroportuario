package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import domain.Passenger;

public class BoardingPanel extends JPanel {

	private JComboBox<String> cbxFlight;
	private JTextArea taQueue;
	private JTextArea taBoarded;
	private JButton btnBoardNext;
	private JButton btnAddToQueue;
	private JList<Passenger> listReservations;
	private DefaultListModel<Passenger> modelReservations;

	/**
	 * Create the panel.
	 */
	public BoardingPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 20));

		// --- PANEL NORTE: SELECCIÓN DE VUELO ---
		JPanel panelFlight = new JPanel();
		panelFlight.setBackground(Color.WHITE);
		panelFlight.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Vuelo", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		add(panelFlight, BorderLayout.NORTH);
		panelFlight.setPreferredSize(new Dimension(10, 90));
		panelFlight.setLayout(null);

		JLabel lblFlight = new JLabel("Seleccione el vuelo:");
		lblFlight.setForeground(new Color(84, 98, 120));
		lblFlight.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFlight.setBounds(25, 40, 190, 22);
		panelFlight.add(lblFlight);

		cbxFlight = new JComboBox<String>();
		cbxFlight.setBounds(178, 32, 420, 38);
		cbxFlight.setForeground(new Color(30, 41, 59));
		cbxFlight.setBackground(Color.WHITE);
		cbxFlight.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		panelFlight.add(cbxFlight);

		// --- PANEL CENTRO: TRES COLUMNAS (RESERVAS, COLA ESPERA, ABORDADOS) ---
		JPanel panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(1, 3, 15, 0));

		// 1. Columna: Pasajeros con Reserva (JList)
		JPanel panelReservations = new JPanel();
		panelReservations.setBackground(Color.WHITE);
		panelReservations.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Reservas del Vuelo", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		panelReservations.setLayout(new BorderLayout(0, 0));
		panelCenter.add(panelReservations);

		modelReservations = new DefaultListModel<>();
		listReservations = new JList<>(modelReservations);
		listReservations.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		listReservations.setForeground(new Color(30, 41, 59));

		JScrollPane scrollReservations = new JScrollPane(listReservations);
		scrollReservations.setBorder(new LineBorder(new Color(186, 204, 228), 1));
		panelReservations.add(scrollReservations, BorderLayout.CENTER);

		// 2. Columna: Cola de Espera (FIFO)
		JPanel panelWaiting = new JPanel();
		panelWaiting.setBackground(Color.WHITE);
		panelWaiting.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"En espera de abordar", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		panelCenter.add(panelWaiting);
		panelWaiting.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollWaiting = new JScrollPane();
		scrollWaiting.setBorder(new LineBorder(new Color(186, 204, 228), 1));
		panelWaiting.add(scrollWaiting, BorderLayout.CENTER);

		taQueue = new JTextArea();
		taQueue.setEditable(false);
		taQueue.setForeground(new Color(30, 41, 59));
		taQueue.setFont(new Font("Consolas", Font.PLAIN, 15));
		taQueue.setMargin(new Insets(10, 12, 10, 12));
		scrollWaiting.setViewportView(taQueue);

		// 3. Columna: Ya abordaron
		JPanel panelBoarded = new JPanel();
		panelBoarded.setBackground(Color.WHITE);
		panelBoarded.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Ya abordaron", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		panelCenter.add(panelBoarded);
		panelBoarded.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollBoarded = new JScrollPane();
		scrollBoarded.setBorder(new LineBorder(new Color(186, 204, 228), 1));
		panelBoarded.add(scrollBoarded, BorderLayout.CENTER);

		taBoarded = new JTextArea();
		taBoarded.setEditable(false);
		taBoarded.setForeground(new Color(30, 41, 59));
		taBoarded.setFont(new Font("Consolas", Font.PLAIN, 15));
		taBoarded.setMargin(new Insets(10, 12, 10, 12));
		scrollBoarded.setViewportView(taBoarded);

		// --- PANEL SUR: BOTONES DE ACCIÓN ---
		JPanel panelButton = new JPanel();
		panelButton.setOpaque(false);
		add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

		btnAddToQueue = new JButton("Agregar a Cola");
		btnAddToQueue.setPreferredSize(new Dimension(240, 54));
		btnAddToQueue.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAddToQueue.setForeground(new Color(10, 36, 84));
		btnAddToQueue.setBackground(new Color(200, 230, 201));
		btnAddToQueue.setOpaque(true);
		btnAddToQueue.setBorder(new LineBorder(new Color(46, 125, 50), 2));
		btnAddToQueue.setFocusPainted(false);
		panelButton.add(btnAddToQueue);

		btnBoardNext = new JButton("Abordar siguiente");
		btnBoardNext.setPreferredSize(new Dimension(240, 54));
		btnBoardNext.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnBoardNext.setForeground(new Color(10, 36, 84));
		btnBoardNext.setBackground(new Color(187, 222, 251));
		btnBoardNext.setOpaque(true);
		btnBoardNext.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnBoardNext.setFocusPainted(false);
		panelButton.add(btnBoardNext);
	}

	// --- GETTERS Y SETTERS ---

	public JComboBox<String> getCbxFlight() {
		return cbxFlight;
	}

	public void setCbxFlight(JComboBox<String> cbxFlight) {
		this.cbxFlight = cbxFlight;
	}

	public JTextArea getTaQueue() {
		return taQueue;
	}

	public void setTaQueue(JTextArea taQueue) {
		this.taQueue = taQueue;
	}

	public JTextArea getTaBoarded() {
		return taBoarded;
	}

	public void setTaBoarded(JTextArea taBoarded) {
		this.taBoarded = taBoarded;
	}

	public JButton getBtnBoardNext() {
		return btnBoardNext;
	}

	public void setBtnBoardNext(JButton btnBoardNext) {
		this.btnBoardNext = btnBoardNext;
	}

	public JButton getBtnAddToQueue() {
		return btnAddToQueue;
	}

	public void setBtnAddToQueue(JButton btnAddToQueue) {
		this.btnAddToQueue = btnAddToQueue;
	}

	public JList<Passenger> getListReservations() {
		return listReservations;
	}

	public void setListReservations(JList<Passenger> listReservations) {
		this.listReservations = listReservations;
	}

	public DefaultListModel<Passenger> getModelReservations() {
		return modelReservations;
	}

	public void setModelReservations(DefaultListModel<Passenger> modelReservations) {
		this.modelReservations = modelReservations;
	}
}