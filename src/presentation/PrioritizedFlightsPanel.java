package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
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


public class PrioritizedFlightsPanel extends JPanel {

	private static final String[] COLUMNS = { "Prioridad", "Vuelo", "Ruta", "Tipo de avion", "Capacidad",
			"Reservas", "Ocupacion" };

	private JTable table;
	private JButton btnBack;

	public PrioritizedFlightsPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 20));

		JPanel panelCard = new JPanel();
		panelCard.setBackground(Color.WHITE);
		panelCard.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(186, 204, 228), 1),
				"Vuelos priorizados", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Segoe UI", Font.BOLD, 16), new Color(21, 101, 192)), new EmptyBorder(10, 15, 15, 15)));
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

		JPanel panelButton = new JPanel();
		panelButton.setOpaque(false);
		add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		btnBack = new JButton("< Volver a vuelos");
		btnBack.setPreferredSize(new Dimension(240, 50));
		btnBack.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnBack.setForeground(new Color(10, 36, 84));
		btnBack.setBackground(new Color(187, 222, 251));
		btnBack.setOpaque(true);
		btnBack.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnBack.setFocusPainted(false);
		panelButton.add(btnBack);
	}

	
	public void setData(Object[][] data) {
		DefaultTableModel model = new DefaultTableModel(data, COLUMNS) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
	}

	public JTable getTable() {
		return table;
	}

	public JButton getBtnBack() {
		return btnBack;
	}
}