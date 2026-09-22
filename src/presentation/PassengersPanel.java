package presentation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

// Consulta de pasajeros/asientos por vuelo, en tabla, con recorrido de inicio a fin y de fin a inicio
public class PassengersPanel extends JPanel {

	private static final String[] COLUMNS = { "Asiento", "Identificacion", "Nombre completo", "Edad" };

	private JComboBox<String> cbxFlight;
	private JLabel lblInfo;
	private JTable table;
	private JButton btnStartToEnd;
	private JButton btnEndToStart;

	public PassengersPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 20));

		JPanel panelFlight = new JPanel();
		panelFlight.setBackground(Color.WHITE);
		panelFlight.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Vuelo", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		add(panelFlight, BorderLayout.NORTH);
		panelFlight.setPreferredSize(new Dimension(10, 130));
		panelFlight.setLayout(null);

		JLabel lblFlight = new JLabel("Seleccione el vuelo:");
		lblFlight.setForeground(new Color(84, 98, 120));
		lblFlight.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFlight.setBounds(25, 35, 190, 22);
		panelFlight.add(lblFlight);

		cbxFlight = new JComboBox<String>();
		cbxFlight.setBounds(200, 28, 420, 38);
		cbxFlight.setForeground(new Color(30, 41, 59));
		cbxFlight.setBackground(Color.WHITE);
		cbxFlight.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		panelFlight.add(cbxFlight);

		lblInfo = new JLabel("-");
		lblInfo.setForeground(new Color(30, 41, 59));
		lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblInfo.setBounds(25, 82, 640, 22);
		panelFlight.add(lblInfo);

		// ---- Centro: tabla de pasajeros ----
		JPanel panelCard = new JPanel();
		panelCard.setBackground(Color.WHITE);
		panelCard.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Pasajeros y asientos", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16),
				new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
		add(panelCard, BorderLayout.CENTER);
		panelCard.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(186, 204, 228), 1));
		panelCard.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setRowHeight(34);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		table.setForeground(new Color(30, 41, 59));
		table.setGridColor(new Color(186, 204, 228));
		table.setSelectionBackground(new Color(187, 222, 251));
		table.setSelectionForeground(new Color(10, 36, 84));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
		table.getTableHeader().setBackground(new Color(187, 222, 251));
		table.getTableHeader().setForeground(new Color(10, 36, 84));

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, center);

		scrollPane.setViewportView(table);
		setData(new Object[0][COLUMNS.length]);

		// ---- Abajo: botones de recorrido ----
		JPanel panelButtons = new JPanel();
		panelButtons.setOpaque(false);
		add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

		btnStartToEnd = createButton("Inicio a fin");
		panelButtons.add(btnStartToEnd);

		btnEndToStart = createButton("Fin a inicio");
		panelButtons.add(btnEndToStart);
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(220, 50));
		button.setFont(new Font("Segoe UI", Font.BOLD, 16));
		button.setForeground(new Color(10, 36, 84));
		button.setBackground(new Color(187, 222, 251));
		button.setOpaque(true);
		button.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		button.setFocusPainted(false);
		return button;
	}

	// Carga las filas en la tabla (las celdas no se pueden editar)
	public void setData(Object[][] data) {
		DefaultTableModel model = new DefaultTableModel(data, COLUMNS) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
	}

	public JComboBox<String> getCbxFlight() {
		return cbxFlight;
	}

	public JLabel getLblInfo() {
		return lblInfo;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getBtnStartToEnd() {
		return btnStartToEnd;
	}

	public JButton getBtnEndToStart() {
		return btnEndToStart;
	}
}