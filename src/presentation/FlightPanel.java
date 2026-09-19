package presentation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FlightPanel extends JPanel {

	// Current flight data (filled by the controller)
	public JLabel lblNumber;
	public JLabel lblRoute;
	public JLabel lblType;
	public JLabel lblCapacity;
	public JLabel lblAvailable;
	public JLabel lblStatus;

	// Circular navigation and Control Tower mode
	public JButton btnPrevious;
	public JButton btnNext;
	public JButton btnPrioritize;

	/**
	 * Create the panel.
	 */
	public FlightPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 20));

		JPanel panelCard = new JPanel();
		panelCard.setBackground(Color.WHITE);
		panelCard.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Vuelo actual", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		add(panelCard, BorderLayout.CENTER);
		panelCard.setLayout(new GridLayout(0, 2, 20, 10));

		JLabel lblNumberTitle = new JLabel("N\u00famero de vuelo");
		lblNumberTitle.setForeground(new Color(84, 98, 120));
		lblNumberTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panelCard.add(lblNumberTitle);

		lblNumber = new JLabel("-");
		lblNumber.setForeground(new Color(30, 41, 59));
		lblNumber.setFont(new Font("Segoe UI", Font.BOLD, 22));
		panelCard.add(lblNumber);

		JLabel lblRouteTitle = new JLabel("Ruta");
		lblRouteTitle.setForeground(new Color(84, 98, 120));
		lblRouteTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panelCard.add(lblRouteTitle);

		lblRoute = new JLabel("-");
		lblRoute.setForeground(new Color(30, 41, 59));
		lblRoute.setFont(new Font("Segoe UI", Font.BOLD, 22));
		panelCard.add(lblRoute);

		JLabel lblTypeTitle = new JLabel("Tipo de avi\u00f3n");
		lblTypeTitle.setForeground(new Color(84, 98, 120));
		lblTypeTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panelCard.add(lblTypeTitle);

		lblType = new JLabel("-");
		lblType.setForeground(new Color(30, 41, 59));
		lblType.setFont(new Font("Segoe UI", Font.BOLD, 22));
		panelCard.add(lblType);

		JLabel lblCapacityTitle = new JLabel("Capacidad m\u00e1xima");
		lblCapacityTitle.setForeground(new Color(84, 98, 120));
		lblCapacityTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panelCard.add(lblCapacityTitle);

		lblCapacity = new JLabel("-");
		lblCapacity.setForeground(new Color(30, 41, 59));
		lblCapacity.setFont(new Font("Segoe UI", Font.BOLD, 22));
		panelCard.add(lblCapacity);

		JLabel lblAvailableTitle = new JLabel("Espacios disponibles");
		lblAvailableTitle.setForeground(new Color(84, 98, 120));
		lblAvailableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panelCard.add(lblAvailableTitle);

		lblAvailable = new JLabel("-");
		lblAvailable.setForeground(new Color(30, 41, 59));
		lblAvailable.setFont(new Font("Segoe UI", Font.BOLD, 22));
		panelCard.add(lblAvailable);

		JLabel lblStatusTitle = new JLabel("Estado");
		lblStatusTitle.setForeground(new Color(84, 98, 120));
		lblStatusTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panelCard.add(lblStatusTitle);

		lblStatus = new JLabel("-");
		lblStatus.setForeground(new Color(30, 41, 59));
		lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 22));
		panelCard.add(lblStatus);

		JPanel panelNav = new JPanel();
		panelNav.setOpaque(false);
		add(panelNav, BorderLayout.SOUTH);
		panelNav.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

		btnPrevious = new JButton("< Anterior");
		btnPrevious.setPreferredSize(new Dimension(190, 50));
		btnPrevious.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPrevious.setForeground(new Color(10, 36, 84));
		btnPrevious.setBackground(new Color(187, 222, 251));
		btnPrevious.setOpaque(true);
		btnPrevious.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnPrevious.setFocusPainted(false);
		panelNav.add(btnPrevious);

		btnNext = new JButton("Siguiente >");
		btnNext.setPreferredSize(new Dimension(190, 50));
		btnNext.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnNext.setForeground(new Color(10, 36, 84));
		btnNext.setBackground(new Color(187, 222, 251));
		btnNext.setOpaque(true);
		btnNext.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnNext.setFocusPainted(false);
		panelNav.add(btnNext);

		btnPrioritize = new JButton("Priorizar vuelos");
		btnPrioritize.setPreferredSize(new Dimension(220, 50));
		btnPrioritize.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPrioritize.setForeground(new Color(10, 36, 84));
		btnPrioritize.setBackground(new Color(187, 222, 251));
		btnPrioritize.setOpaque(true);
		btnPrioritize.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnPrioritize.setFocusPainted(false);
		panelNav.add(btnPrioritize);
	}
}