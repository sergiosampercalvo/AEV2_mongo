package vista;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCargarCartas;
	private JButton btnLogin;
	private JButton btnStart;
	private JButton btnExit;
	private JButton btnScores;
	private JButton btnLogOut;
	private JComboBox<String> comboBox;

	/**
	 * Create the frame.
	 */
	public Inicio() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("/recursosInterfaz/—Pngtree—poker chip_8470970.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 783);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnStart = new JButton("");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnStart.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/STAR2.png")));
				reproducirSonido("/recursosInterfaz/fichas_poker.wav");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnStart.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/STARt.png")));

			}
		});

		btnCargarCartas = new JButton("Cargar Cartas");
		btnCargarCartas.setBounds(411, 662, 106, 21);
		contentPane.add(btnCargarCartas);
		btnStart.setContentAreaFilled(false);
		btnStart.setBorderPainted(false);
		btnStart.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/STARt.png")));
		btnStart.setBounds(192, 375, 152, 75);
		contentPane.add(btnStart);

		btnLogin = new JButton("");
		btnLogin.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/LOGIN.png")));
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setBounds(192, 469, 152, 75);
		contentPane.add(btnLogin);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogin.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/LOGIN2.png")));
				reproducirSonido("/recursosInterfaz/fichas_poker.wav");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogin.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/LOGIN.png")));

			}
		});

		btnScores = new JButton("");
		btnScores.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/SCORES.png")));
		btnScores.setContentAreaFilled(false);
		btnScores.setBorderPainted(false);
		btnScores.setBounds(192, 564, 152, 75);
		contentPane.add(btnScores);
		btnScores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnScores.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/SCORES2.png")));
				reproducirSonido("/recursosInterfaz/fichas_poker.wav");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnScores.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/SCORES.png")));

			}
		});

		btnLogOut = new JButton("");
		btnLogOut.setVisible(false);
		btnLogOut.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/LOGOUT.png")));
		btnLogOut.setContentAreaFilled(false);
		btnLogOut.setBorderPainted(false);
		btnLogOut.setBounds(0, 680, 120, 66);
		contentPane.add(btnLogOut);
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogOut.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/LOGOUT2.png")));
				reproducirSonido("/recursosInterfaz/fichas_poker.wav");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogOut.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/LOGOUT.png")));

			}
		});

		btnExit = new JButton("");
		btnExit.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/EXIT.png")));
		btnExit.setContentAreaFilled(false);
		btnExit.setBorderPainted(false);
		btnExit.setBounds(210, 663, 120, 83);
		contentPane.add(btnExit);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/EXIT2.png")));
				reproducirSonido("/recursosInterfaz/fichas_poker.wav");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/EXIT.png")));

			}
		});

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Española ", "Fracesa" }));
		comboBox.setBounds(411, 683, 106, 53);
		contentPane.add(comboBox);

		JLabel Fondo = new JLabel("");
		Fondo.setBounds(0, 0, 540, 757);
		Fondo.setIcon(new ImageIcon(Inicio.class.getResource("/recursosInterfaz/FONDO.png")));
		contentPane.add(Fondo);
		this.setVisible(true);
	}

	public JButton getBtnExit() {
		return btnExit;
	}

	public void setBtnExit(JButton btnExit) {
		this.btnExit = btnExit;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public void setBtnStart(JButton btnStart) {
		this.btnStart = btnStart;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JButton getBtnScores() {
		return btnScores;
	}

	public void setBtnScores(JButton btnScores) {
		this.btnScores = btnScores;
	}

	public JButton getBtnCargarCartas() {
		return btnCargarCartas;
	}

	public void setBtnCargarCartas(JButton btnCargarCartas) {
		this.btnCargarCartas = btnCargarCartas;
	}
	
	

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public JButton getBtnLogOut() {
		return btnLogOut;
	}

	public void setBtnLogOut(JButton btnLogOut) {
		this.btnLogOut = btnLogOut;
	}
	

	public static void reproducirSonido(String rutaArchivo) {
		try {
			File archivoSonido = new File("./src/main/resources" + rutaArchivo);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
