package presentation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ReservationPanel extends JPanel {

	public JComboBox<String> cbxFlight;
	public JTextField tId;
	public JTextField tName;
	public JTextField tAge;
	public JButton btnRegister;

	/**
	 * Create the panel.
	 */
	public ReservationPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(890, 560));
		setLayout(null);

		JPanel panelCard = new JPanel();
		panelCard.setBackground(Color.WHITE);
		panelCard.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Registrar reserva", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		panelCard.setBounds(175, 20, 540, 470);
		add(panelCard);
		panelCard.setLayout(null);

		JLabel lblFlight = new JLabel("Vuelo");
		lblFlight.setForeground(new Color(84, 98, 120));
		lblFlight.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFlight.setBounds(40, 45, 460, 22);
		panelCard.add(lblFlight);

		cbxFlight = new JComboBox<String>();
		cbxFlight.setForeground(new Color(30, 41, 59));
		cbxFlight.setBackground(Color.WHITE);
		cbxFlight.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cbxFlight.setBounds(40, 69, 460, 38);
		panelCard.add(cbxFlight);

		JLabel lblId = new JLabel("Identificacion");
		lblId.setForeground(new Color(84, 98, 120));
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblId.setBounds(40, 125, 460, 22);
		panelCard.add(lblId);

		tId = new JTextField();
		tId.setForeground(new Color(30, 41, 59));
		tId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tId.setBorder(new CompoundBorder(new LineBorder(new Color(186, 204, 228), 1), new EmptyBorder(4, 8, 4, 8)));
		tId.setBounds(40, 149, 460, 38);
		panelCard.add(tId);
		tId.setColumns(10);

		JLabel lblName = new JLabel("Nombre completo");
		lblName.setForeground(new Color(84, 98, 120));
		lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblName.setBounds(40, 205, 460, 22);
		panelCard.add(lblName);

		tName = new JTextField();
		tName.setForeground(new Color(30, 41, 59));
		tName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tName.setBorder(new CompoundBorder(new LineBorder(new Color(186, 204, 228), 1), new EmptyBorder(4, 8, 4, 8)));
		tName.setBounds(40, 229, 460, 38);
		panelCard.add(tName);
		tName.setColumns(10);

		JLabel lblAge = new JLabel("Edad");
		lblAge.setForeground(new Color(84, 98, 120));
		lblAge.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblAge.setBounds(40, 285, 460, 22);
		panelCard.add(lblAge);

		tAge = new JTextField();
		tAge.setForeground(new Color(30, 41, 59));
		tAge.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tAge.setBorder(new CompoundBorder(new LineBorder(new Color(186, 204, 228), 1), new EmptyBorder(4, 8, 4, 8)));
		tAge.setBounds(40, 309, 460, 38);
		panelCard.add(tAge);
		tAge.setColumns(10);

		btnRegister = new JButton("Registrar reserva");
		btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnRegister.setForeground(new Color(10, 36, 84));
		btnRegister.setBackground(new Color(187, 222, 251));
		btnRegister.setOpaque(true);
		btnRegister.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnRegister.setFocusPainted(false);
		btnRegister.setBounds(40, 380, 460, 50);
		panelCard.add(btnRegister);
	}

	public void clearForm() {
		tId.setText("");
		tName.setText("");
		tAge.setText("");
	}
}