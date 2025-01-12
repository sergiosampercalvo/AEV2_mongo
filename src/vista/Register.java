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


public class Register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField passwordField;
	private JButton btnRegistrarse;
	private JPasswordField passwordFieldConfirmation;

	
	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}

	public JTextField getTextFieldUsuario() {
		return textFieldUsuario;
	}
	
	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JPasswordField getPasswordFieldConfirmation() {
		return passwordFieldConfirmation;
	}


	/**
	 * Create the frame.
	 */
	public Register() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 501, 418);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordFieldConfirmation = new JPasswordField();
		passwordFieldConfirmation.setOpaque(false);
		passwordFieldConfirmation.setFont(new Font("Segoe UI Variable", Font.PLAIN, 32));
		passwordFieldConfirmation.setEchoChar('*');
		passwordFieldConfirmation.setBounds(210, 189, 237, 49);
		contentPane.add(passwordFieldConfirmation);
		
		JLabel lblRepiteLa = new JLabel("Repite la ");
		lblRepiteLa.setForeground(Color.WHITE);
		lblRepiteLa.setFont(new Font("Segoe UI Variable", Font.BOLD, 32));
		lblRepiteLa.setBounds(52, 148, 186, 82);
		contentPane.add(lblRepiteLa);
		
		JLabel lblContraseña_1 = new JLabel("Contraseña:");
		lblContraseña_1.setForeground(Color.WHITE);
		lblContraseña_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 32));
		lblContraseña_1.setBounds(14, 187, 186, 82);
		contentPane.add(lblContraseña_1);
		
		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Segoe UI Variable", Font.BOLD, 26));
		btnRegistrarse.setBounds(134, 280, 213, 49);
		contentPane.add(btnRegistrarse);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Segoe UI Variable", Font.PLAIN, 32));
		passwordField.setOpaque(false);
		passwordField.setEchoChar('*');
		passwordField.setBounds(210, 101, 237, 49);
		contentPane.add(passwordField);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setForeground(new Color(255, 255, 255));
		textFieldUsuario.setOpaque(false);
		textFieldUsuario.setFont(new Font("Segoe UI Variable", Font.PLAIN, 32));
		textFieldUsuario.setBounds(210, 38, 237, 49);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(Color.WHITE);
		lblContraseña.setFont(new Font("Segoe UI Variable", Font.BOLD, 32));
		lblContraseña.setBounds(14, 84, 186, 82);
		contentPane.add(lblContraseña);
		
		JLabel lblNombre = new JLabel("Usuario:");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Segoe UI Variable", Font.BOLD, 32));
		lblNombre.setBounds(60, 12, 158, 101);
		contentPane.add(lblNombre);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setForeground(new Color(255, 255, 255));
		lblFondo.setFont(new Font("Segoe UI Variable", Font.PLAIN, 32));
		lblFondo.setBounds(0, 0, 500, 500);
		lblFondo.setIcon(new ImageIcon(Login.class.getResource("/recursosInterfaz/fondo-general.jpg")));
		contentPane.add(lblFondo);
		this.setVisible(false);
	}

}
