package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TripsPanel extends JPanel {

	private JTextArea taPendingTrips;
	private JTextArea taConsultedTrips;
	private JButton btnConsult;

	public TripsPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 20));

		// Centro: Dos paneles (Pendientes de Consultar vs Consultados)
		JPanel panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(1, 2, 20, 0));

		// Panel Izquierdo: Pila Por Consultar (LIFO)
		JPanel panelPending = new JPanel();
		panelPending.setBackground(Color.WHITE);
		panelPending.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Pila de Reservas (Por Consultar)", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Segoe UI", Font.BOLD, 16), new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		panelCenter.add(panelPending);
		panelPending.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPending = new JScrollPane();
		scrollPending.setBorder(new LineBorder(new Color(186, 204, 228), 1));
		panelPending.add(scrollPending, BorderLayout.CENTER);

		taPendingTrips = new JTextArea();
		taPendingTrips.setEditable(false);
		taPendingTrips.setForeground(new Color(30, 41, 59));
		taPendingTrips.setFont(new Font("Consolas", Font.PLAIN, 15));
		taPendingTrips.setMargin(new Insets(10, 12, 10, 12));
		scrollPending.setViewportView(taPendingTrips);

		// Panel Derecho: Pila de Consultados
		JPanel panelConsulted = new JPanel();
		panelConsulted.setBackground(Color.WHITE);
		panelConsulted.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Pasajeros Consultados", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Segoe UI", Font.BOLD, 16), new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		panelCenter.add(panelConsulted);
		panelConsulted.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollConsulted = new JScrollPane();
		scrollConsulted.setBorder(new LineBorder(new Color(186, 204, 228), 1));
		panelConsulted.add(scrollConsulted, BorderLayout.CENTER);

		taConsultedTrips = new JTextArea();
		taConsultedTrips.setEditable(false);
		taConsultedTrips.setForeground(new Color(30, 41, 59));
		taConsultedTrips.setFont(new Font("Consolas", Font.PLAIN, 15));
		taConsultedTrips.setMargin(new Insets(10, 12, 10, 12));
		scrollConsulted.setViewportView(taConsultedTrips);

		// Botón inferior
		JPanel panelButton = new JPanel();
		panelButton.setOpaque(false);
		add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		btnConsult = new JButton("Consultar más reciente");
		btnConsult.setPreferredSize(new Dimension(280, 54));
		btnConsult.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnConsult.setForeground(new Color(10, 36, 84));
		btnConsult.setBackground(new Color(187, 222, 251));
		btnConsult.setOpaque(true);
		btnConsult.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnConsult.setFocusPainted(false);
		panelButton.add(btnConsult);
	}

	public JTextArea getTaPendingTrips() {
		return taPendingTrips;
	}

	public JTextArea getTaConsultedTrips() {
		return taConsultedTrips;
	}

	public JButton getBtnConsult() {
		return btnConsult;
	}
}