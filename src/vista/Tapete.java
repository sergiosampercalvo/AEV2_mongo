package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



public class Tapete extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Clip clip;
	private JLabel lblMute;
	private JLabel lblSound;
	private JLabel carta1_jugador;
	private JLabel carta2_jugador;
	private JLabel carta3_jugador;
	private JLabel carta4_jugador;
	private JLabel carta5_jugador;
	private JLabel carta1_Rival;
	private JLabel carta2_Rival;
	private JLabel carta3_Rival;
	private JLabel carta4_Rival;
	private JLabel carta5_Rival;
	private JButton btnPlantarse;
	private JButton btnPulsa;
	private JButton btnSalir;
	private JButton btnCarta1_Jugador;
	private JButton btnCarta2_Jugador;
	private JButton btnCarta3_Jugador;
	private JButton btnCarta4_Jugador;
	private JButton btnCarta5_Jugador;
	private JButton btnCarta1_Rival;
	private JButton btnCarta2_Rival;
	private JButton btnCarta3_Rival;
	private JButton btnCarta4_Rival;
	private JButton btnCarta5_Rival;
	private JLabel lblPuntos_Player;
	private JLabel lblPuntosIA_1;
	

	public Tapete() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tapete.class.getResource("/recursosInterfaz/—Pngtree—poker chip_8470970.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1063, 747);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		carta2_jugador = new JLabel("New label");
		carta2_jugador.setVisible(false);

		carta3_jugador = new JLabel("New label");
		carta3_jugador.setVisible(false);

		carta4_jugador = new JLabel("New label");
		carta4_jugador.setVisible(false);
		
		carta1_Rival = new JLabel("New label");
		carta1_Rival.setVisible(false);
		
		carta2_Rival = new JLabel("New label");
		carta2_Rival.setVisible(false);
		
		carta3_Rival = new JLabel("New label");
		carta3_Rival.setVisible(false);
		
		carta4_Rival = new JLabel("New label");
		carta4_Rival.setVisible(false);
		
		carta5_Rival = new JLabel("New label");
		carta5_Rival.setVisible(false);
		
		btnPulsa = new JButton("PULSA!");
		btnPulsa.setVisible(false);
		
		btnCarta1_Jugador = new JButton("");
		btnCarta1_Jugador.setVisible(false);
		
		btnCarta5_Jugador = new JButton("");
		btnCarta5_Jugador.setVisible(false);
		
		btnCarta5_Rival = new JButton("");
		btnCarta5_Rival.setVisible(false);
		btnCarta5_Rival.setBounds(197, 44, 115, 172);
		contentPane.add(btnCarta5_Rival);
		
		btnCarta4_Rival = new JButton("");
		btnCarta4_Rival.setVisible(false);
		btnCarta4_Rival.setBounds(334, 44, 115, 172);
		contentPane.add(btnCarta4_Rival);
		
		btnCarta3_Rival = new JButton("");
		btnCarta3_Rival.setVisible(false);
		btnCarta3_Rival.setBounds(473, 44, 115, 172);
		contentPane.add(btnCarta3_Rival);
		
		btnCarta2_Rival = new JButton("");
		btnCarta2_Rival.setVisible(false);
		btnCarta2_Rival.setBounds(610, 44, 115, 172);
		contentPane.add(btnCarta2_Rival);
		
		btnCarta1_Rival = new JButton("");
		btnCarta1_Rival.setVisible(false);
		btnCarta1_Rival.setBounds(749, 44, 115, 172);
		contentPane.add(btnCarta1_Rival);
		btnCarta5_Jugador.setBounds(749, 511, 115, 172);
		contentPane.add(btnCarta5_Jugador);
		
		btnCarta4_Jugador = new JButton("");
		btnCarta4_Jugador.setVisible(false);
		btnCarta4_Jugador.setBounds(610, 511, 115, 172);
		contentPane.add(btnCarta4_Jugador);
		
		btnCarta3_Jugador = new JButton("");
		btnCarta3_Jugador.setVisible(false);
		btnCarta3_Jugador.setBounds(473, 511, 115, 172);
		contentPane.add(btnCarta3_Jugador);
		
		btnCarta2_Jugador = new JButton("");
		btnCarta2_Jugador.setVisible(false);
		btnCarta2_Jugador.setBounds(334, 511, 115, 172);
		contentPane.add(btnCarta2_Jugador);
		btnCarta1_Jugador.setBounds(197, 511, 115, 172);
		contentPane.add(btnCarta1_Jugador);
		
		btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Segoe UI Variable", Font.BOLD, 16));
		btnSalir.setBounds(10, 20, 80, 37);
		contentPane.add(btnSalir);
		btnPulsa.setFont(new Font("Segoe UI Variable", Font.PLAIN, 18));
		btnPulsa.setBounds(473, 344, 103, 37);
		contentPane.add(btnPulsa);
		
		btnPlantarse = new JButton("Plantarse");
		btnPlantarse.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		btnPlantarse.setOpaque(false);
		btnPlantarse.setBounds(895, 640, 142, 43);
		contentPane.add(btnPlantarse);
		carta5_Rival.setInheritsPopupMenu(false);
		carta5_Rival.setIgnoreRepaint(true);
		carta5_Rival.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta5_Rival.setBounds(197, 44, 115, 172);
		contentPane.add(carta5_Rival);
		carta4_Rival.setInheritsPopupMenu(false);
		carta4_Rival.setIgnoreRepaint(true);
		carta4_Rival.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta4_Rival.setBounds(334, 44, 115, 172);
		contentPane.add(carta4_Rival);
		carta3_Rival.setInheritsPopupMenu(false);
		carta3_Rival.setIgnoreRepaint(true);
		carta3_Rival.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta3_Rival.setBounds(472, 44, 115, 172);
		contentPane.add(carta3_Rival);
		carta2_Rival.setInheritsPopupMenu(false);
		carta2_Rival.setIgnoreRepaint(true);
		carta2_Rival.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta2_Rival.setBounds(610, 44, 115, 172);
		contentPane.add(carta2_Rival);
		carta1_Rival.setInheritsPopupMenu(false);
		carta1_Rival.setIgnoreRepaint(true);
		carta1_Rival.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta1_Rival.setBounds(749, 44, 115, 172);
		contentPane.add(carta1_Rival);
		
		lblPuntos_Player = new JLabel("0");
		lblPuntos_Player.setForeground(Color.WHITE);
		lblPuntos_Player.setFont(new Font("Segoe UI Variable", Font.BOLD, 56));
		lblPuntos_Player.setBounds(207, 423, 115, 66);
		contentPane.add(lblPuntos_Player);
		
		lblPuntosIA_1 = new JLabel("0");
		lblPuntosIA_1.setForeground(Color.WHITE);
		lblPuntosIA_1.setFont(new Font("Segoe UI Variable", Font.BOLD, 56));
		lblPuntosIA_1.setBounds(207, 244, 115, 66);
		contentPane.add(lblPuntosIA_1);
		
		JLabel lblPuntosIA = new JLabel("Puntos:");
		lblPuntosIA.setForeground(Color.WHITE);
		lblPuntosIA.setFont(new Font("Segoe UI Variable", Font.BOLD, 28));
		lblPuntosIA.setBounds(93, 244, 115, 66);
		contentPane.add(lblPuntosIA);
		
		JLabel lblPuntos = new JLabel("Puntos:");
		lblPuntos.setForeground(new Color(255, 255, 255));
		lblPuntos.setFont(new Font("Segoe UI Variable", Font.BOLD, 28));
		lblPuntos.setBounds(93, 423, 115, 66);
		contentPane.add(lblPuntos);

		carta5_jugador = new JLabel("New label");
		carta5_jugador.setVisible(false);
		carta5_jugador.setInheritsPopupMenu(false);
		carta5_jugador.setIgnoreRepaint(true);
		carta5_jugador.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta5_jugador.setBounds(749, 511, 115, 172);
		contentPane.add(carta5_jugador);
		carta4_jugador.setInheritsPopupMenu(false);
		carta4_jugador.setIgnoreRepaint(true);
		carta4_jugador.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta4_jugador.setBounds(610, 511, 115, 172);
		contentPane.add(carta4_jugador);
		carta3_jugador.setInheritsPopupMenu(false);
		carta3_jugador.setIgnoreRepaint(true);
		carta3_jugador.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta3_jugador.setBounds(472, 511, 115, 172);
		contentPane.add(carta3_jugador);
		carta2_jugador.setInheritsPopupMenu(false);
		carta2_jugador.setIgnoreRepaint(true);
		carta2_jugador.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
		carta2_jugador.setBounds(334, 511, 115, 172);
		contentPane.add(carta2_jugador);
		
				carta1_jugador = new JLabel("New label");
				carta1_jugador.setVisible(false);
				carta1_jugador.setInheritsPopupMenu(false);
				carta1_jugador.setIgnoreRepaint(true);
				carta1_jugador.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/carta.png")));
				carta1_jugador.setBounds(197, 511, 115, 172);
				contentPane.add(carta1_jugador);

		lblSound = new JLabel("");
		lblSound.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/sound.png")));
		lblSound.setBounds(10, 647, 50, 50);
		contentPane.add(lblSound);

		lblMute = new JLabel("");
		lblMute.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/mute.png")));
		lblMute.setBounds(10, 647, 50, 50);
		lblMute.setVisible(false);
		contentPane.add(lblMute);

		JLabel tapete = new JLabel("");
		tapete.setFont(new Font("Segoe UI Variable", Font.BOLD, 22));
		tapete.setBounds(0, 0, 1053, 724);
		tapete.setIcon(new ImageIcon(Tapete.class.getResource("/recursosInterfaz/tapete.png")));
		contentPane.add(tapete);

		lblSound.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				detenerMusica();
				lblSound.setVisible(false);
				lblMute.setVisible(true);
			}
		});

		lblMute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reproducirMusica("/recursosInterfaz/musica.wav");
				lblMute.setVisible(false);
				lblSound.setVisible(true);
			}
		});
		
		this.setVisible(false);
	}

	public void reproducirMusica(String rutaArchivo) {
		try {
			File archivo = new File("./src/main/resources" + rutaArchivo);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);

			clip = AudioSystem.getClip();
			clip.open(audioStream);

			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.err.println("Error al reproducir música: " + e.getMessage());
		}
	}

	public void detenerMusica() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public Clip getClip() {
		return clip;
	}

	public JLabel getLblMute() {
		return lblMute;
	}

	public JLabel getLblSound() {
		return lblSound;
	}


	public JButton getBtnSalir() {
		return btnSalir;
	}

	public JLabel getCarta1_jugador() {
		return carta1_jugador;
	}

	public JLabel getCarta2_jugador() {
		return carta2_jugador;
	}

	public JLabel getCarta3_jugador() {
		return carta3_jugador;
	}

	public JLabel getCarta4_jugador() {
		return carta4_jugador;
	}

	public JLabel getCarta5_jugador() {
		return carta5_jugador;
	}

	public JLabel getCarta1_Rival() {
		return carta1_Rival;
	}

	public JLabel getCarta2_Rival() {
		return carta2_Rival;
	}

	public JLabel getCarta3_Rival() {
		return carta3_Rival;
	}

	public JLabel getCarta4_Rival() {
		return carta4_Rival;
	}

	public JLabel getCarta5_Rival() {
		return carta5_Rival;
	}

	public JButton getBtnPlantarse() {
		return btnPlantarse;
	}

	public JButton getBtnPulsa() {
		return btnPulsa;
	}

	public JButton getBtnCarta1_Jugador() {
		return btnCarta1_Jugador;
	}

	public JLabel getLblPuntos_Player() {
		return lblPuntos_Player;
	}

	public JButton getBtnCarta2_Jugador() {
		return btnCarta2_Jugador;
	}

	public JButton getBtnCarta3_Jugador() {
		return btnCarta3_Jugador;
	}

	public JButton getBtnCarta4_Jugador() {
		return btnCarta4_Jugador;
	}

	public JButton getBtnCarta5_Jugador() {
		return btnCarta5_Jugador;
	}

	public JButton getBtnCarta1_Rival() {
		return btnCarta1_Rival;
	}

	public JButton getBtnCarta2_Rival() {
		return btnCarta2_Rival;
	}

	public JButton getBtnCarta3_Rival() {
		return btnCarta3_Rival;
	}

	public JButton getBtnCarta4_Rival() {
		return btnCarta4_Rival;
	}

	public JButton getBtnCarta5_Rival() {
		return btnCarta5_Rival;
	}

	public JLabel getLblPuntosIA_1() {
		return lblPuntosIA_1;
	}
	
	
	
	
}
