package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MainView extends JFrame {

	private JPanel contentPane;
	private JPanel panelContent;
	private JLabel lblTitle;

	public JButton btnFlights;
	public JButton btnReservations;
	public JButton btnTrips;
	public JButton btnBoarding;

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		setMinimumSize(new Dimension(1000, 650));
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		
		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(10, 36, 84));
		panelMenu.setPreferredSize(new Dimension(250, 10));
		contentPane.add(panelMenu, BorderLayout.WEST);
		panelMenu.setLayout(new BorderLayout(0, 0));

		JPanel panelButtonsWrapper = new JPanel();
		panelButtonsWrapper.setOpaque(false);
		panelMenu.add(panelButtonsWrapper, BorderLayout.CENTER);
		panelButtonsWrapper.setLayout(new BorderLayout(0, 0));

		JPanel panelButtons = new JPanel();
		panelButtons.setOpaque(false);
		panelButtons.setBorder(new EmptyBorder(0, 20, 0, 20));
		panelButtonsWrapper.add(panelButtons, BorderLayout.NORTH);
		panelButtons.setLayout(new GridLayout(0, 1, 0, 14));

		btnFlights = new JButton("Vuelos");
		btnFlights.setPreferredSize(new Dimension(200, 54));
		btnFlights.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnFlights.setForeground(new Color(10, 36, 84));
		btnFlights.setBackground(new Color(187, 222, 251));
		btnFlights.setOpaque(true);
		btnFlights.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnFlights.setFocusPainted(false);
		panelButtons.add(btnFlights);

		btnReservations = new JButton("Reservar");
		btnReservations.setPreferredSize(new Dimension(200, 54));
		btnReservations.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnReservations.setForeground(new Color(10, 36, 84));
		btnReservations.setBackground(new Color(187, 222, 251));
		btnReservations.setOpaque(true);
		btnReservations.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnReservations.setFocusPainted(false);
		panelButtons.add(btnReservations);

		btnTrips = new JButton("Mis viajes");
		btnTrips.setPreferredSize(new Dimension(200, 54));
		btnTrips.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnTrips.setForeground(new Color(10, 36, 84));
		btnTrips.setBackground(new Color(187, 222, 251));
		btnTrips.setOpaque(true);
		btnTrips.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnTrips.setFocusPainted(false);
		panelButtons.add(btnTrips);

		btnBoarding = new JButton("Abordaje");
		btnBoarding.setPreferredSize(new Dimension(200, 54));
		btnBoarding.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnBoarding.setForeground(new Color(10, 36, 84));
		btnBoarding.setBackground(new Color(187, 222, 251));
		btnBoarding.setOpaque(true);
		btnBoarding.setBorder(new LineBorder(new Color(21, 101, 192), 2));
		btnBoarding.setFocusPainted(false);
		panelButtons.add(btnBoarding);

		
		JPanel panelParent = new JPanel();
		contentPane.add(panelParent, BorderLayout.CENTER);
		panelParent.setLayout(new BorderLayout(0, 0));

		JPanel panelTitle = new JPanel();
		FlowLayout fl_panelTitle = (FlowLayout) panelTitle.getLayout();
		fl_panelTitle.setAlignment(FlowLayout.LEFT);
		panelTitle.setBorder(new EmptyBorder(18, 30, 18, 30));
		panelTitle.setBackground(new Color(21, 101, 192));
		panelParent.add(panelTitle, BorderLayout.NORTH);

		lblTitle = new JLabel("Control Aeropuertario");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
		panelTitle.add(lblTitle);

		panelContent = new JPanel();
		panelContent.setBackground(new Color(236, 243, 252));
		panelContent.setBorder(new EmptyBorder(25, 30, 25, 30));
		panelParent.add(panelContent, BorderLayout.CENTER);
		panelContent.setLayout(new BorderLayout(0, 0));
	}

	public void init() {
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setTitle("Sistema de Control Aeroportuario");
	}

	
	public void setContent(JComponent c, String title) {
		setTitle("Sistema de Control Aeroportuario - " + title);
		lblTitle.setText(title);
 
	
		panelContent.removeAll();
	
		panelContent.add(c, BorderLayout.CENTER);
	
		panelContent.repaint();
		panelContent.revalidate();
	}

}