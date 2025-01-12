package modelo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * Clase que representa una carta con atributos de palo, puntos e imagen codificada en Base64.
 * Proporciona funcionalidades para gestionar cartas en una base de datos MongoDB.
 */
public class Carta {

    // Atributos de la carta
    private String suit; // Palo de la carta
    private int points;  // Puntos de la carta
    private String base64; // Imagen de la carta en formato Base64

    // Listas para almacenar cartas
    private ArrayList<Carta> baraja;
    private ArrayList<Carta> barajaJugador;
    private ArrayList<Carta> barajaRival;

    // Carta seleccionada aleatoriamente
    private Carta cartaelegida;

    // Base de datos MongoDB
    MongoDatabase database;

    /**
     * Constructor por defecto
     */
    public Carta() {

    }

    /**
     * Constructor con parámetros.
     * 
     * @param sui  Palo de la carta
     * @param poi  Puntos de la carta
     * @param bas  Imagen en Base64 de la carta
     */
    public Carta(String sui, int poi, String bas) {
        this.suit = sui;
        this.points = poi;
        this.base64 = bas;
    }

    /**
     * Establece la conexión con la base de datos MongoDB.
     */
    public void crearConexion() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("casino");
    }

    /**
     * Crea cartas españolas a partir de imágenes JPG en una carpeta especificada y las guarda en MongoDB.
     * 
     * @param carpetaPath Ruta de la carpeta que contiene las imágenes
     * @throws IOException Si ocurre un error al leer los archivos
     */
    public void crearCartaEsp(String carpetaPath) throws IOException {

        File carpeta = new File(carpetaPath);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            JOptionPane.showMessageDialog(null, "La ruta es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".jpg"));

        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay imágenes en la carpeta.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (File archivo : archivos) {
            try {
                String eliminarExtension = archivo.getName().substring(0, archivo.getName().lastIndexOf('.'));
                String[] partes = eliminarExtension.split("_");

                String suit = partes[0];
                int points = Integer.parseInt(partes[1]);
                if (points > 10) {
                    points = 10;
                }

                byte[] fileContent = Files.readAllBytes(archivo.toPath());
                String encodedString = Base64.encodeBase64String(fileContent);

                MongoCollection<Document> coleccion = database.getCollection("card_es");
                Document doc = new Document();
                doc.append("suit", suit);
                doc.append("points", points);
                doc.append("base64", encodedString);
                coleccion.insertOne(doc);

            } catch (Exception e) {
                System.err.println("Error procesando el archivo: " + archivo.getName() + " - " + e.getMessage());
            }
        }
    }

    /**
     * Crea cartas francesas a partir de imágenes PNG en una carpeta especificada y las guarda en MongoDB.
     * 
     * @param carpetaPath Ruta de la carpeta que contiene las imágenes
     * @throws IOException Si ocurre un error al leer los archivos
     */
    public void crearCartaFra(String carpetaPath) throws IOException {

        File carpeta = new File(carpetaPath);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            JOptionPane.showMessageDialog(null, "La ruta es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".png"));

        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay imágenes en la carpeta.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (File archivo : archivos) {
            try {
                String eliminarExtension = archivo.getName().substring(0, archivo.getName().lastIndexOf('.'));
                String[] partes = eliminarExtension.split("_");

                String suit = partes[0];
                int points = Integer.parseInt(partes[1]);
                if (points > 10) {
                    points = 10;
                }

                byte[] fileContent = Files.readAllBytes(archivo.toPath());
                String encodedString = Base64.encodeBase64String(fileContent);

                MongoCollection<Document> coleccion = database.getCollection("card_fr");
                Document doc = new Document();
                doc.append("suit", suit);
                doc.append("points", points);
                doc.append("base64", encodedString);
                coleccion.insertOne(doc);

            } catch (Exception e) {
                System.err.println("Error procesando el archivo: " + archivo.getName() + " - " + e.getMessage());
            }
        }

        JOptionPane.showMessageDialog(null, "Cartas creadas correctamente.", "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Elimina las colecciones de cartas de MongoDB.
     */
    public void borrarDB() {
        MongoCollection<Document> coleccion_es = database.getCollection("card_es");
        MongoCollection<Document> coleccion_fr = database.getCollection("card_fr");
        coleccion_fr.drop();
        coleccion_es.drop();
    }

    /**
     * Carga cartas desde una colección de MongoDB y las agrega a la baraja.
     * 
     * @param colec Nombre de la colección de cartas
     */
    public void cargarCartas(String colec) {
        if (baraja == null) {
            baraja = new ArrayList<>();
        }
        MongoCollection<Document> coleccion = database.getCollection(colec);
        MongoCursor<Document> cursor = coleccion.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String tipo = doc.getString("suit");
            int puntos = doc.getInteger("points");
            String imagen = doc.getString("base64");

            Carta cart = new Carta(tipo, puntos, imagen);
            baraja.add(cart);
        }
    }

    /**
     * Saca una carta aleatoria de la baraja.
     * 
     * @return Carta seleccionada aleatoriamente
     */
    public Carta sacarCartaRandom() {
        Random rdm = new Random();
        int index = rdm.nextInt(baraja.size());
        Carta cartaelegida = baraja.get(index);
        baraja.remove(index);

        return cartaelegida;
    }

    /**
     * Elimina todas las cartas de la baraja.
     */
    public void eliminarBaraja() {
        if (baraja != null) {
            baraja.clear();
        } else {
            return;
        }
    }

    // Métodos getter y setter para los atributos
    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public ArrayList<Carta> getBaraja() {
        return baraja;
    }

    public ArrayList<Carta> getBarajaJugador() {
        return barajaJugador;
    }

    public ArrayList<Carta> getBarajaRival() {
        return barajaRival;
    }
}
