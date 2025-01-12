package controlador;
/**
 * Clase Controlador que maneja la lógica de negocio y la interacción entre las vistas y modelos
 * del juego. Implementa los controladores para las acciones de usuario y gestiona las transiciones
 * entre las distintas pantallas.
 */
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.io.ByteArrayInputStream;

import org.apache.commons.codec.binary.Base64;

import modelo.Carta;
import modelo.Partida;
import vista.Inicio;
import vista.Login;
import vista.Register;
import vista.Scores;
import vista.Tapete;

public class Controlador {
    // Vistas
    private Inicio ini;
    private Tapete tap;
    private Login log;
    private Register regis;
    private Scores scor;

    // Modelos
    private Partida part;
    private Carta carta;

    // ActionListeners
    private ActionListener actionLogin;
    private ActionListener actionStart;
    private ActionListener actionExit;
    private ActionListener actionScores;
    private ActionListener actionPantallaRegis;
    private ActionListener actionIniciarSesion;
    private ActionListener actionRegistrarse;
    private ActionListener actionLogOut;
    private ActionListener actionCargarCart;
    private ActionListener actionCogerCarta;
    private ActionListener actionPlantarse;
    private ActionListener actionSalirMenu;

    // Atributos de la partida
    private boolean plantadoJugador = false;
    private boolean plantadoRival = false;
    private int puntosJugador = 0;
    private int puntosRival = 0;
    private String usuarioIniciado;

    // Otros
    private boolean sesionIniciada = false;
    private boolean cartasCargadas = false;

    /**
     * Constructor principal que inicializa las vistas, modelos y controladores.
     */
    public Controlador() {
        ini = new Inicio();
        tap = new Tapete();
        log = new Login();
        regis = new Register();
        scor = new Scores();
        part = new Partida();
        carta = new Carta();
        controlInicio();
        controlLogin();
        controlTapete();
        controlRegister();
    }

    /**
     * Configura los controladores para la pantalla de inicio.
     */
    private void controlInicio() {
        part.crearConexion();
        carta.crearConexion();

        actionLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.setVisible(true);
            }
        };
        ini.getBtnLogin().addActionListener(actionLogin);

        actionStart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sesionIniciada) {
                    JOptionPane.showMessageDialog(null, "No has iniciado sesión.", "Alerta",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (!cartasCargadas) {
                    JOptionPane.showMessageDialog(null, "Faltan por cargar las cartas.", "Alerta",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    tap.setVisible(true);
                    ini.dispose();
                    tap.reproducirMusica("/recursosInterfaz/musica.wav");
                    plantadoJugador = false;
                    plantadoRival = false;
                    puntosJugador = 0;
                    puntosRival = 0;
                    tap.getBtnPlantarse().setVisible(true);
                    if (ini.getComboBox().getSelectedIndex() == 0) {
                        carta.cargarCartas("card_es");
                    } else {
                        carta.cargarCartas("card_fr");
                    }
                    if (part.elegirTurno()) {
                        tap.getBtnPulsa().setVisible(true);
                    } else {
                        try {
                            turnoCrupier();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        };
        ini.getBtnStart().addActionListener(actionStart);

        actionExit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ini.dispose();
            }
        };
        ini.getBtnExit().addActionListener(actionExit);

        actionScores = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scor.setVisible(true);
                scor.getTextArea().setText(part.cargarScores());
            }
        };
        ini.getBtnScores().addActionListener(actionScores);

        actionLogOut = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sesionIniciada = false;
                ini.getBtnLogOut().setVisible(false);
                usuarioIniciado = "";
            }
        };
        ini.getBtnLogOut().addActionListener(actionLogOut);

        actionCargarCart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    carta.borrarDB();
                    carta.crearCartaEsp("./img/cards_es");
                    carta.crearCartaFra("./img/cards_fr");
                    cartasCargadas = true;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
        ini.getBtnCargarCartas().addActionListener(actionCargarCart);
    }

    /**
     * Configura los controladores para la pantalla de inicio de sesión.
     */
    private void controlLogin() {
        actionPantallaRegis = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regis.setVisible(true);
                log.setVisible(false);
            }
        };
        log.getBtnregistrarte().addActionListener(actionPantallaRegis);

        actionIniciarSesion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] contrChar = log.getPasswordField().getPassword();
                String contraseña = new String(contrChar);
                String usuario = log.getTextField().getText();

                try {
                    if (sesionIniciada) {
                        JOptionPane.showMessageDialog(null, "Ya hay una sesión iniciada.", "Alerta",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else if (part.comprobarSesion(usuario, contraseña)) {
                        sesionIniciada = true;
                        log.setVisible(false);
                        ini.getBtnLogOut().setVisible(true);
                        log.getTextField().setText("");
                        log.getPasswordField().setText("");
                        usuarioIniciado = usuario;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        log.getBtnIniciarSesion().addActionListener(actionIniciarSesion);
    }

    /**
     * Configura los controladores para la pantalla de registro.
     */
    private void controlRegister() {
        actionRegistrarse = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] contrChar = regis.getPasswordField().getPassword();
                char[] contrChar2 = regis.getPasswordFieldConfirmation().getPassword();

                String usuario = regis.getTextFieldUsuario().getText();
                String contraseña1 = new String(contrChar);
                String contraseña2 = new String(contrChar2);

                try {
                    if (part.crearUsuario(usuario, contraseña1, contraseña2)) {
                        regis.getTextFieldUsuario().setText("");
                        regis.getPasswordField().setText("");
                        regis.getPasswordFieldConfirmation().setText("");
                        regis.setVisible(false);
                        log.setVisible(true);
                    } else {
                        regis.getPasswordField().setText("");
                        regis.getPasswordFieldConfirmation().setText("");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        regis.getBtnRegistrarse().addActionListener(actionRegistrarse);
    }


	private void controlTapete() {

		actionCogerCarta = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!plantadoJugador) {
					try {

						if (tap.getBtnCarta1_Jugador().getIcon() == null) {

							Carta cartaelegida = carta.sacarCartaRandom();
							byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
							BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
							Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
							tap.getBtnCarta1_Jugador().setIcon(new ImageIcon(scaledImage));
							if (cartaelegida.getPoints() == 1) {
								String[] opciones = { "1", "11" };

								int opcion = JOptionPane.showOptionDialog(null,
										"TE HA TOCADO UN AS, ¿quieres que valga 1 o 11?", "Selecciona una opción",
										JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
										opciones[0]);

								if (opcion == 0) {
									puntosJugador += 1;
								} else if (opcion == 1) {
									puntosJugador += 11;
								} else {
									puntosJugador += 1;
								}
							} else {
								puntosJugador += cartaelegida.getPoints();
							}
							tap.getCarta1_jugador().setVisible(true);
							tap.getBtnPulsa().setVisible(false);
							Timer ocultarCarta = new Timer(1500, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									tap.getCarta1_jugador().setVisible(false);
									tap.getBtnCarta1_Jugador().setVisible(true);
									tap.getLblPuntos_Player().setText(String.valueOf(puntosJugador));
								}
							});
							ocultarCarta.setRepeats(false);
							ocultarCarta.start();

							// Temporizador para turnoCrupier
							Timer iniciarTurnoCrupier = new Timer(3000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										turnoCrupier();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							});
							iniciarTurnoCrupier.setRepeats(false);
							iniciarTurnoCrupier.start();
							return;
						} else if (tap.getBtnCarta2_Jugador().getIcon() == null) {

							Carta cartaelegida = carta.sacarCartaRandom();
							byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
							BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
							Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
							tap.getBtnCarta2_Jugador().setIcon(new ImageIcon(scaledImage));
							if (cartaelegida.getPoints() == 1) {
								String[] opciones = { "1", "11" };

								int opcion = JOptionPane.showOptionDialog(null,
										"TE HA TOCADO UN AS, ¿quieres que valga 1 o 11?", "Selecciona una opción",
										JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
										opciones[0]);

								if (opcion == 0) {
									puntosJugador += 1;
								} else if (opcion == 1) {
									puntosJugador += 11;
								} else {
									puntosJugador += 1;
								}
							} else {
								puntosJugador += cartaelegida.getPoints();
							}
							tap.getBtnPulsa().setVisible(false);
							tap.getCarta2_jugador().setVisible(true);
							Timer ocultarCarta = new Timer(1500, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									tap.getCarta2_jugador().setVisible(false);
									tap.getBtnCarta2_Jugador().setVisible(true);
									tap.getLblPuntos_Player().setText(String.valueOf(puntosJugador));
								}
							});
							ocultarCarta.setRepeats(false);
							ocultarCarta.start();

							// Temporizador para turnoCrupier
							Timer iniciarTurnoCrupier = new Timer(3000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										turnoCrupier();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							});
							iniciarTurnoCrupier.setRepeats(false);
							iniciarTurnoCrupier.start();

							return;
						} else if (tap.getBtnCarta3_Jugador().getIcon() == null) {

							Carta cartaelegida = carta.sacarCartaRandom();
							byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
							BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
							Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
							tap.getBtnCarta3_Jugador().setIcon(new ImageIcon(scaledImage));
							if (cartaelegida.getPoints() == 1) {
								String[] opciones = { "1", "11" };

								int opcion = JOptionPane.showOptionDialog(null,
										"TE HA TOCADO UN AS, ¿quieres que valga 1 o 11?", "Selecciona una opción",
										JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
										opciones[0]);

								if (opcion == 0) {
									puntosJugador += 1;
								} else if (opcion == 1) {
									puntosJugador += 11;
								} else {
									puntosJugador += 1;
								}
							} else {
								puntosJugador += cartaelegida.getPoints();
							}
							tap.getBtnPulsa().setVisible(false);
							tap.getCarta3_jugador().setVisible(true);

							Timer ocultarCarta = new Timer(1500, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									tap.getCarta3_jugador().setVisible(false);
									tap.getBtnCarta3_Jugador().setVisible(true);
									tap.getLblPuntos_Player().setText(String.valueOf(puntosJugador));
								}
							});
							ocultarCarta.setRepeats(false);
							ocultarCarta.start();

							// Temporizador para turnoCrupier
							Timer iniciarTurnoCrupier = new Timer(3000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										turnoCrupier();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							});
							iniciarTurnoCrupier.setRepeats(false);
							iniciarTurnoCrupier.start();
							return;
						} else if (tap.getBtnCarta4_Jugador().getIcon() == null) {
							Carta cartaelegida = carta.sacarCartaRandom();
							byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
							BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
							Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
							tap.getBtnCarta4_Jugador().setIcon(new ImageIcon(scaledImage));
							if (cartaelegida.getPoints() == 1) {
								String[] opciones = { "1", "11" };

								int opcion = JOptionPane.showOptionDialog(null,
										"TE HA TOCADO UN AS, ¿quieres que valga 1 o 11?", "Selecciona una opción",
										JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
										opciones[0]);

								if (opcion == 0) {
									puntosJugador += 1;
								} else if (opcion == 1) {
									puntosJugador += 11;
								} else {
									puntosJugador += 1;
								}
							} else {
								puntosJugador += cartaelegida.getPoints();
							}
							tap.getBtnPulsa().setVisible(false);
							tap.getCarta4_jugador().setVisible(true);

							Timer ocultarCarta = new Timer(1500, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									tap.getCarta4_jugador().setVisible(false);
									tap.getBtnCarta4_Jugador().setVisible(true);
									tap.getLblPuntos_Player().setText(String.valueOf(puntosJugador));
								}
							});
							ocultarCarta.setRepeats(false);
							ocultarCarta.start();

							// Temporizador para turnoCrupier
							Timer iniciarTurnoCrupier = new Timer(3000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										turnoCrupier();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							});
							iniciarTurnoCrupier.setRepeats(false);
							iniciarTurnoCrupier.start();
							return;
						} else {
							Carta cartaelegida = carta.sacarCartaRandom();
							byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
							BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
							Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
							tap.getBtnCarta5_Jugador().setIcon(new ImageIcon(scaledImage));
							if (cartaelegida.getPoints() == 1) {
								String[] opciones = { "1", "11" };

								int opcion = JOptionPane.showOptionDialog(null,
										"TE HA TOCADO UN AS, ¿quieres que valga 1 o 11?", "Selecciona una opción",
										JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
										opciones[0]);

								if (opcion == 0) {
									puntosJugador += 1;
								} else if (opcion == 1) {
									puntosJugador += 11;
								} else {
									puntosJugador += 1;
								}
							} else {
								puntosJugador += cartaelegida.getPoints();
							}
							tap.getBtnPulsa().setVisible(false);
							tap.getCarta5_jugador().setVisible(true);

							Timer ocultarCarta = new Timer(1500, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									tap.getCarta5_jugador().setVisible(false);
									tap.getBtnCarta5_Jugador().setVisible(true);
									tap.getLblPuntos_Player().setText(String.valueOf(puntosJugador));
								}
							});
							ocultarCarta.setRepeats(false);
							ocultarCarta.start();

							// Temporizador para turnoCrupier
							Timer iniciarTurnoCrupier = new Timer(3000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										turnoCrupier();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							});
							iniciarTurnoCrupier.setRepeats(false);
							iniciarTurnoCrupier.start();
							return;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		tap.getBtnPulsa().addActionListener(actionCogerCarta);

		actionPlantarse = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (plantadoRival) {
					plantadoJugador = true;
					comprobarGanador();
					tap.getBtnPulsa().setVisible(false);
					tap.getBtnPlantarse().setVisible(false);
				} else {
					plantadoJugador = true;
					while (puntosRival < 17) {

						try {
							turnoCrupier();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				}
				comprobarGanador();
				tap.getBtnPulsa().setVisible(false);
				tap.getBtnPlantarse().setVisible(false);

			}
		};
		tap.getBtnPlantarse().addActionListener(actionPlantarse);

		actionSalirMenu = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas salir al menu principal?", "Confirmación",
						JOptionPane.YES_NO_OPTION);

				if (opcion == JOptionPane.YES_OPTION) {
					tap.setVisible(false);
					ini.setVisible(true);
					tap.detenerMusica();
					tap.getBtnCarta1_Jugador().setIcon(null);
					tap.getBtnCarta2_Jugador().setIcon(null);
					tap.getBtnCarta3_Jugador().setIcon(null);
					tap.getBtnCarta4_Jugador().setIcon(null);
					tap.getBtnCarta5_Jugador().setIcon(null);
					tap.getBtnCarta1_Rival().setIcon(null);
					tap.getBtnCarta2_Rival().setIcon(null);
					tap.getBtnCarta3_Rival().setIcon(null);
					tap.getBtnCarta4_Rival().setIcon(null);
					tap.getBtnCarta5_Rival().setIcon(null);
					tap.getBtnCarta1_Jugador().setVisible(false);
					tap.getBtnCarta2_Jugador().setVisible(false);
					tap.getBtnCarta3_Jugador().setVisible(false);
					tap.getBtnCarta4_Jugador().setVisible(false);
					tap.getBtnCarta5_Jugador().setVisible(false);
					tap.getBtnCarta1_Rival().setVisible(false);
					tap.getBtnCarta2_Rival().setVisible(false);
					tap.getBtnCarta3_Rival().setVisible(false);
					tap.getBtnCarta4_Rival().setVisible(false);
					tap.getBtnCarta5_Rival().setVisible(false);
					tap.getLblPuntos_Player().setText("0");
					tap.getLblPuntosIA_1().setText("0");
					carta.eliminarBaraja();

				}

			}
		};
		tap.getBtnSalir().addActionListener(actionSalirMenu);
	}

	private void turnoCrupier() throws IOException {
		if (!plantadoRival) {
			if (puntosRival >= 17) {
				plantadoRival = true;
				JOptionPane.showMessageDialog(null, "Tu rival se ha plantado.", "Alerta",
						JOptionPane.INFORMATION_MESSAGE);
				tap.getBtnPulsa().setVisible(true);
			} else {
				if (tap.getBtnCarta1_Rival().getIcon() == null) {
					Carta cartaelegida = carta.sacarCartaRandom();
					byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
					BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
					Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
					tap.getBtnCarta1_Rival().setIcon(new ImageIcon(scaledImage));
					if (cartaelegida.getPoints() == 1) {
						if (puntosRival < 11) {
							puntosRival += 11;
						} else {
							puntosRival += 1;

						}
					} else {
						puntosRival += cartaelegida.getPoints();
					}
					tap.getCarta1_Rival().setVisible(true);

					Timer turnoCrupier = new Timer(1500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tap.getCarta1_Rival().setVisible(false);
							tap.getBtnCarta1_Rival().setVisible(true);
							tap.getLblPuntosIA_1().setText(String.valueOf(puntosRival));
							tap.getBtnPulsa().setVisible(true);

						}
					});
					turnoCrupier.setRepeats(false);
					turnoCrupier.start();
					return;
				} else if (tap.getBtnCarta2_Rival().getIcon() == null) {
					Carta cartaelegida = carta.sacarCartaRandom();
					byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
					BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
					Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
					tap.getBtnCarta2_Rival().setIcon(new ImageIcon(scaledImage));
					if (cartaelegida.getPoints() == 1) {
						if (puntosRival < 11) {
							puntosRival += 11;
						} else {
							puntosRival += 1;

						}
					} else {
						puntosRival += cartaelegida.getPoints();
					}
					tap.getCarta2_Rival().setVisible(true);

					Timer turnoCrupier = new Timer(1500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tap.getCarta2_Rival().setVisible(false);
							tap.getBtnCarta2_Rival().setVisible(true);
							tap.getLblPuntosIA_1().setText(String.valueOf(puntosRival));
							tap.getBtnPulsa().setVisible(true);

						}
					});
					turnoCrupier.setRepeats(false);
					turnoCrupier.start();
					return;
				} else if (tap.getBtnCarta3_Rival().getIcon() == null) {
					Carta cartaelegida = carta.sacarCartaRandom();
					byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
					BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
					Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
					tap.getBtnCarta3_Rival().setIcon(new ImageIcon(scaledImage));
					if (cartaelegida.getPoints() == 1) {
						if (puntosRival < 11) {
							puntosRival += 11;
						} else {
							puntosRival += 1;

						}
					} else {
						puntosRival += cartaelegida.getPoints();
					}
					tap.getCarta3_Rival().setVisible(true);

					Timer turnoCrupier = new Timer(1500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tap.getCarta3_Rival().setVisible(false);
							tap.getBtnCarta3_Rival().setVisible(true);
							tap.getLblPuntosIA_1().setText(String.valueOf(puntosRival));
							tap.getBtnPulsa().setVisible(true);

						}
					});

					turnoCrupier.setRepeats(false);
					turnoCrupier.start();

					return;
				} else if (tap.getBtnCarta4_Rival().getIcon() == null) {
					Carta cartaelegida = carta.sacarCartaRandom();
					byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
					BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
					Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
					tap.getBtnCarta4_Rival().setIcon(new ImageIcon(scaledImage));
					if (cartaelegida.getPoints() == 1) {
						if (puntosRival < 11) {
							puntosRival += 11;
						} else {
							puntosRival += 1;

						}
					} else {
						puntosRival += cartaelegida.getPoints();
					}
					tap.getCarta4_Rival().setVisible(true);

					Timer turnoCrupier = new Timer(1500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tap.getCarta4_Rival().setVisible(false);
							tap.getBtnCarta4_Rival().setVisible(true);
							tap.getLblPuntosIA_1().setText(String.valueOf(puntosRival));
							tap.getBtnPulsa().setVisible(true);

						}
					});
					;
					turnoCrupier.setRepeats(false);
					turnoCrupier.start();
					return;
				} else {
					Carta cartaelegida = carta.sacarCartaRandom();
					byte[] btDataFile = Base64.decodeBase64(cartaelegida.getBase64());
					BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(btDataFile));
					Image scaledImage = originalImage.getScaledInstance(-1, 172, Image.SCALE_SMOOTH);
					tap.getBtnCarta5_Rival().setIcon(new ImageIcon(scaledImage));
					if (cartaelegida.getPoints() == 1) {
						if (puntosRival < 11) {
							puntosRival += 11;
						} else {
							puntosRival += 1;

						}
					} else {
						puntosRival += cartaelegida.getPoints();
					}
					tap.getCarta5_Rival().setVisible(true);

					Timer turnoCrupier = new Timer(1500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tap.getCarta5_Rival().setVisible(false);
							tap.getBtnCarta5_Rival().setVisible(true);
							tap.getLblPuntosIA_1().setText(String.valueOf(puntosRival));
							tap.getBtnPulsa().setVisible(true);

						}
					});
					;
					turnoCrupier.setRepeats(false);
					turnoCrupier.start();
					return;
				}

			}
		} else {
			if (plantadoJugador) {
				
			} else {
				tap.getBtnPulsa().setVisible(true);
			}

		}
	}

	private void comprobarGanador() {
		tap.getBtnPulsa().setVisible(false);
		tap.getBtnPlantarse().setVisible(false);
		if (puntosJugador > 21 && puntosRival > 21) {
			JOptionPane.showMessageDialog(null, "No hay un ganador.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		} else if (puntosJugador == puntosRival) {
			JOptionPane.showMessageDialog(null, "Habéis empatado.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		} else if (puntosJugador > 21 && puntosRival <= 21) {
			JOptionPane.showMessageDialog(null, "Derrota, has perdido.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		} else if (puntosJugador <= 21 && puntosRival > 21) {
			int opcion = JOptionPane.showConfirmDialog(null, "ENHORABUENA, HAS GANADO. \n ¿Deseas guardar la partida?",
					"Confirmación", JOptionPane.YES_NO_OPTION);

			if (opcion == JOptionPane.YES_OPTION) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				LocalDateTime now = LocalDateTime.now();
				String formattedDate = now.format(formatter);
				part.guardarScore(usuarioIniciado, ini.getComboBox().getSelectedIndex(), puntosJugador, formattedDate);
			}
		} else if (puntosJugador < 21 && puntosRival < 21 && puntosJugador < puntosRival) {
			JOptionPane.showMessageDialog(null, "Derrota, has perdido.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		} else if (puntosJugador <= 21 && puntosRival <= 21 && puntosJugador > puntosRival) {
			int opcion = JOptionPane.showConfirmDialog(null, "ENHORABUENA, HAS GANADO.  ¿Deseas guardar la partida?",
					"Confirmación", JOptionPane.YES_NO_OPTION);

			if (opcion == JOptionPane.YES_OPTION) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				LocalDateTime now = LocalDateTime.now();
				String formattedDate = now.format(formatter);
				part.guardarScore(usuarioIniciado, ini.getComboBox().getSelectedIndex(), puntosJugador, formattedDate);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Datos incorrectos.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
