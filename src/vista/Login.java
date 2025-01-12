package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnIniciarSesion;
	private JButton btnregistrarte;


	public JTextField getTextField() {
		return textField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JButton getBtnregistrarte() {
		return btnregistrarte;
	}

	public void setBtnregistrarte(JButton btnregistrarte) {
		this.btnregistrarte = btnregistrarte;
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 501, 418);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnregistrarte = new JButton("Registrarte");
		btnregistrarte.setOpaque(false);
		btnregistrarte.setForeground(new Color(0, 0, 255));
		btnregistrarte.setBorder(null);
		btnregistrarte.setFont(new Font("Segoe UI Variable", Font.PLAIN, 22));
		btnregistrarte.setBounds(301, 302, 151, 39);
		contentPane.add(btnregistrarte);
		
		JLabel lblNewLabel = new JLabel("Si no tienes cuenta puedes");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Segoe UI Variable", Font.PLAIN, 22));
		lblNewLabel.setBounds(36, 306, 266, 30);
		contentPane.add(lblNewLabel);
		
		btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setFont(new Font("Segoe UI Variable", Font.BOLD, 26));
		btnIniciarSesion.setBounds(104, 226, 259, 49);
		contentPane.add(btnIniciarSesion);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Segoe UI Variable", Font.PLAIN, 32));
		passwordField.setOpaque(false);
		passwordField.setEchoChar('*');
		passwordField.setBounds(210, 145, 237, 49);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.setForeground(new Color(255, 255, 255));
		textField.setOpaque(false);
		textField.setFont(new Font("Segoe UI Variable", Font.PLAIN, 32));
		textField.setBounds(210, 64, 237, 49);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(Color.WHITE);
		lblContraseña.setFont(new Font("Segoe UI Variable", Font.BOLD, 32));
		lblContraseña.setBounds(19, 125, 186, 82);
		contentPane.add(lblContraseña);
		
		JLabel lblNombre = new JLabel("Usuario:");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Segoe UI Variable", Font.BOLD, 32));
		lblNombre.setBounds(70, 34, 158, 101);
		contentPane.add(lblNombre);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setForeground(new Color(0, 0, 255));
		lblFondo.setFont(new Font("Segoe UI Variable", Font.PLAIN, 32));
		lblFondo.setBounds(0, 0, 500, 500);
		lblFondo.setIcon(new ImageIcon(Login.class.getResource("/recursosInterfaz/fondo-general.jpg")));
		contentPane.add(lblFondo);
		this.setVisible(false);
	}

	public JButton getBtnIniciarSesion() {
		return btnIniciarSesion;
	}

	public void setBtnIniciarSesion(JButton btnIniciarSesion) {
		this.btnIniciarSesion = btnIniciarSesion;
	}

}
