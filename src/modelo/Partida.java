package modelo;

import static com.mongodb.client.model.Filters.eq;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

/**
 * Clase Partida
 * 
 * Esta clase maneja la conexión a la base de datos MongoDB y las operaciones relacionadas 
 * con usuarios, contraseñas y puntuaciones en el contexto de un juego.
 */
public class Partida {
    MongoDatabase database; // Objeto para gestionar la base de datos MongoDB
    private ArrayList<String> ganadores; // Lista para almacenar los ganadores de partidas

    /**
     * Constructor por defecto de la clase Partida.
     */
    public Partida() {}

    /**
     * Establece la conexión con la base de datos MongoDB.
     */
    public void crearConexion() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("casino");
    }

    /**
     * Comprueba si un usuario existe en la base de datos.
     * 
     * @param usuario El nombre del usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean comprobarUsuario(String usuario) {
        Bson query = eq("user", usuario);
        MongoCollection<Document> coleccion = database.getCollection("users");
        try (MongoCursor<Document> cursor = coleccion.find(query).iterator()) {
            return cursor.hasNext();
        }
    }

    /**
     * Comprueba si una contraseña, después de ser encriptada, coincide con la almacenada.
     * 
     * @param contra La contraseña a verificar.
     * @return true si la contraseña es correcta, false en caso contrario.
     */
    public boolean comprobarContraseña(String contra) {
        String contraseñaHasheada = encriptarContraseña(contra);
        Bson query = eq("password", contraseñaHasheada);
        MongoCollection<Document> coleccion = database.getCollection("users");
        try (MongoCursor<Document> cursor = coleccion.find(query).iterator()) {
            return cursor.hasNext();
        }
    }

    /**
     * Verifica si las credenciales de usuario y contraseña son correctas.
     * 
     * @param usuario    El nombre de usuario.
     * @param contrasena La contraseña.
     * @return true si la sesión es válida, false en caso contrario.
     */
    public boolean comprobarSesion(String usuario, String contrasena) {
        if (!comprobarUsuario(usuario)) {
            JOptionPane.showMessageDialog(null, "El usuario introducido no existe.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        } else if (!comprobarContraseña(contrasena)) {
            JOptionPane.showMessageDialog(null, "La contraseña introducida es incorrecta.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Sesión iniciada correctamente.", null, JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     * 
     * @param usuario             El nombre del usuario.
     * @param contraseña          La contraseña.
     * @param contraseñaConfirm   La confirmación de la contraseña.
     * @return true si el usuario fue creado correctamente, false en caso contrario.
     */
    public boolean crearUsuario(String usuario, String contraseña, String contraseñaConfirm) {
        if (usuario.isEmpty() || contraseña.isEmpty() || contraseñaConfirm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        } else if (comprobarUsuario(usuario)) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        } else if (!contraseña.equals(contraseñaConfirm)) {
            JOptionPane.showMessageDialog(null, "Contraseñas incorrectas, no coinciden entre ellas.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        } else {
            MongoCollection<Document> coleccion = database.getCollection("users");
            String contraseñaEncrip = encriptarContraseña(contraseña);
            Document doc = new Document();
            doc.append("user", usuario);
            doc.append("password", contraseñaEncrip);
            coleccion.insertOne(doc);
            JOptionPane.showMessageDialog(null, "Usuario creado correctamente.", null, JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * Encripta una contraseña utilizando el algoritmo SHA-256.
     * 
     * @param contra La contraseña a encriptar.
     * @return La contraseña encriptada como una cadena hexadecimal.
     */
    public String encriptarContraseña(String contra) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(contra.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando hash", e);
        }
    }

    /**
     * Pregunta al usuario quién debe iniciar el turno.
     * 
     * @return true si el jugador inicia, false si inicia el crupier.
     */
    public boolean elegirTurno() {
        String[] opciones = { "Jugador", "Crupier" };
        int opcion = JOptionPane.showOptionDialog(null, "¿Quieres empezar tú o el crupier?", "Selecciona una opción",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        return opcion == 0;
    }

    /**
     * Guarda la puntuación de un usuario en la base de datos.
     * 
     * @param usuario El nombre del usuario.
     * @param suit    El tipo de baraja (0 para española, otro valor para francesa).
     * @param puntos  Los puntos obtenidos.
     * @param fecha   La fecha de la partida.
     */
    public void guardarScore(String usuario, int suit, int puntos, String fecha) {
        String tipo = (suit == 0) ? "es" : "fr";
        MongoCollection<Document> coleccion = database.getCollection("scores");
        Document doc = new Document();
        doc.append("user", usuario);
        doc.append("suit", tipo);
        doc.append("points", puntos);
        doc.append("timespan", fecha);
        coleccion.insertOne(doc);
    }

    /**
     * Carga las puntuaciones desde la base de datos y las ordena de mayor a menor.
     * 
     * @return Una cadena con las puntuaciones formateadas.
     */
    public String cargarScores() {
        String scores = "";
        int contador = 1;
        MongoCollection<Document> coleccion = database.getCollection("scores");
        try (MongoCursor<Document> cursor = coleccion.find().sort(Sorts.descending("points")).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String user = doc.getString("user");
                String suit = doc.getString("suit");
                int points = doc.getInteger("points");
                String timestamp = doc.getString("timespan");
                scores += contador + ". " + user + " " + points + " puntos " + "(Baraja: " + suit + ", " + timestamp + " )\n";
                contador++;
            }
        }
        return scores;
    }
}
