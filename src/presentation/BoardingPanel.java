package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class BoardingPanel extends JPanel {

	public JComboBox<String> cbxFlight;
	public JTextArea taQueue;
	public JTextArea taBoarded;
	public JButton btnBoardNext;

	/**
	 * Create the panel.
	 */
	public BoardingPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 20));

		// ----- Flight selector -----
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
		cbxFlight.setBounds(220, 32, 420, 38);
		cbxFlight.setForeground(new Color(30, 41, 59));
		cbxFlight.setBackground(Color.WHITE);
		cbxFlight.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		panelFlight.add(cbxFlight);

		// ----- Waiting queue and boarded passengers -----
		JPanel panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(1, 2, 20, 0));

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
		taQueue.setFont(new Font("Consolas", Font.PLAIN, 16));
		taQueue.setMargin(new Insets(10, 12, 10, 12));
		scrollWaiting.setViewportView(taQueue);

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
		taBoarded.setFont(new Font("Consolas", Font.PLAIN, 16));
		taBoarded.setMargin(new Insets(10, 12, 10, 12));
		scrollBoarded.setViewportView(taBoarded);

		// ----- Board next button -----
		JPanel panelButton = new JPanel();
		panelButton.setOpaque(false);
		add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		btnBoardNext = new JButton("Abordar siguiente");
		btnBoardNext.setPreferredSize(new Dimension(280, 54));
		btnBoardNext.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnBoardNext.setForeground(new Color(10, 36, 84));
		btnBoardNext.setBackground(new Color(187, 222, 251));
		btnBoardNext.setOpaque(true);
		btnBoardNext.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnBoardNext.setFocusPainted(false);
		panelButton.add(btnBoardNext);
	}
}