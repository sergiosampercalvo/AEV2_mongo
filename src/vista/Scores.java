package vista;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Scores extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;

	/**
	 * Create the frame.
	 */
	public Scores() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 501, 502);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setForeground(new Color(255, 255, 255));
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));
		textArea.setBounds(10, 84, 465, 368);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("SCORES");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Segoe UI Variable", Font.BOLD, 40));
		lblNewLabel.setBounds(167, 11, 156, 50);
		contentPane.add(lblNewLabel);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 500, 500);
		lblFondo.setIcon(new ImageIcon(Login.class.getResource("/recursosInterfaz/fondo-general.jpg")));
		contentPane.add(lblFondo);
		this.setVisible(false);
		
		
		
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	
}
