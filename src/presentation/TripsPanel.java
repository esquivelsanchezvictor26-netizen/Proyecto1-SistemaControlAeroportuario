package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TripsPanel extends JPanel {

	private JTextArea taTrips;

	/**
	 * Create the panel.
	 */
	public TripsPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		JPanel panelCard = new JPanel();
		panelCard.setBackground(Color.WHITE);
		panelCard.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Mis viajes", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Segoe UI", Font.BOLD, 16), new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		add(panelCard, BorderLayout.CENTER);
		panelCard.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(186, 204, 228), 1));
		panelCard.add(scrollPane, BorderLayout.CENTER);

		taTrips = new JTextArea();
		taTrips.setEditable(false);
		taTrips.setForeground(new Color(30, 41, 59));
		taTrips.setFont(new Font("Consolas", Font.PLAIN, 16));
		taTrips.setMargin(new Insets(10, 12, 10, 12));
		scrollPane.setViewportView(taTrips);
	}

	public JTextArea getTaTrips() {
		return taTrips;
	}

	public void setTaTrips(JTextArea taTrips) {
		this.taTrips = taTrips;
	}
}