package com.example.ligast4;

import com.example.ligast4.model.Clasificacion;
import com.example.ligast4.model.Ligas;
import com.example.ligast4.model.Temporadas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private MenuItem menuDetalles;

    @FXML
    private ComboBox<Temporadas> comboTemporadas;

    @FXML
    private Button botonFiltrar;

    @FXML
    private ListView<Clasificacion> listaClasificacion;

    @FXML
    private ComboBox<Ligas> comboLigas;
    @FXML
    private ObservableList<Ligas> ligasList= FXCollections.observableArrayList();
    @FXML
    private ObservableList<Temporadas> temporadasList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Clasificacion> clasificacionList = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
            acciones();
            instancias();
            metodoComboLigas();
        comboLigas.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                String idLiga = newValue.getIdLeague();
                metodoComboTemporadas(idLiga);
            }
        });
    }

    public void metodoComboLigas(){
        String urlString = "https://www.thesportsdb.com/api/v1/json/3/all_leagues.php";
        try {
            URL urlMovie = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) urlMovie.openConnection();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            String linea = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((linea = reader.readLine()) != null) {
                stringBuffer.append(linea);
            }

            JSONObject response = new JSONObject(stringBuffer.toString());
            JSONArray results = response.getJSONArray("leagues");
            for (int i = 0; i < results.length(); i++) {
                JSONObject ligasJson = results.getJSONObject(i);
                String strLeague = ligasJson.getString("strLeague");
                String idLeague = ligasJson.getString("idLeague");
                Ligas ligas = new Ligas(strLeague, idLeague);

                ligasList.add(ligas);
                System.out.println(ligas);
            }

            comboLigas.setItems(ligasList);
            System.out.println("ComboBox cargado con " + ligasList.size() + " elementos.");




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void metodoComboTemporadas(String idLiga){
        String urlString = "https://www.thesportsdb.com/api/v1/json/3/search_all_seasons.php?id="+ idLiga;
        temporadasList.clear();
        try {
            URL urlMovie = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) urlMovie.openConnection();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            String linea = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((linea = reader.readLine()) != null) {
                stringBuffer.append(linea);
            }

            JSONObject response = new JSONObject(stringBuffer.toString());
            JSONArray results = response.getJSONArray("seasons");
            for (int i = 0; i < results.length(); i++) {
                JSONObject temporadasJson = results.getJSONObject(i);
                String strSeason = temporadasJson.getString("strSeason");
                Temporadas temporadas = new Temporadas(strSeason);

                temporadasList.add(temporadas);
            }

            comboTemporadas.setItems(temporadasList);




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void acciones(){
        botonFiltrar.setOnAction(this);
        menuDetalles.setOnAction(this);

    }

    private void instancias(){

    }


    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == botonFiltrar) {
            Ligas ligaSeleccionada = comboLigas.getSelectionModel().getSelectedItem();
            Temporadas temporadaSeleccionada = comboTemporadas.getSelectionModel().getSelectedItem();

            if (ligaSeleccionada != null && temporadaSeleccionada != null) {
                String idLiga = ligaSeleccionada.getIdLeague();
                String temporada = temporadaSeleccionada.getStrSeason();
                String urlString = "https://www.thesportsdb.com/api/v1/json/3/lookuptable.php?l=" + idLiga + "&s=" + temporada;

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String linea;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((linea = reader.readLine()) != null) {
                        stringBuilder.append(linea);
                    }

                    JSONObject response = new JSONObject(stringBuilder.toString());
                    JSONArray results = response.getJSONArray("table");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject clasificacionJson = results.getJSONObject(i);
                        String strTeam = clasificacionJson.getString("strTeam");
                        String strTeamBadge = clasificacionJson.getString("strTeamBadge");
                        String strForm = clasificacionJson.getString("strForm");
                        int intPoints = clasificacionJson.getInt("intPoints");
                        int intGoalsFor = clasificacionJson.getInt("intGoalsFor");
                        int intGoalsAgainst = clasificacionJson.getInt("intGoalsAgainst");


                        Clasificacion clasificacion = new Clasificacion(strTeam, intPoints, intGoalsFor, intGoalsAgainst, strTeamBadge, strForm);
                        clasificacionList.add(clasificacion);
                    }

                    listaClasificacion.setItems(clasificacionList);

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Selección Incompleta");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, selecciona una liga y una temporada.");
                alerta.showAndWait();
            }
        } else if (actionEvent.getSource() == menuDetalles) {
            Clasificacion equipoSeleccionado = listaClasificacion.getSelectionModel().getSelectedItem();
            if (equipoSeleccionado != null) {
                Alert detallesAlerta = new Alert(Alert.AlertType.INFORMATION);
                detallesAlerta.setTitle("Detalles del Equipo");
                detallesAlerta.setHeaderText(equipoSeleccionado.getStrTeam()); // Nombre del equipo

                StringBuilder contenido = new StringBuilder();
                contenido.append("Puntos: ").append(equipoSeleccionado.getIntPoints()).append("\n");
                contenido.append("Forma: ").append(equipoSeleccionado.getStrForm()).append("\n");

                ImageView escudo = new ImageView();
                escudo.setFitHeight(100);
                escudo.setFitWidth(100);
                String urlEscudo = equipoSeleccionado.getStrTeamBadge();
                if (urlEscudo != null && !urlEscudo.isEmpty()) {
                    escudo.setImage(new Image(urlEscudo, true));
                    detallesAlerta.setGraphic(escudo);
                }

                detallesAlerta.setContentText(contenido.toString());
                detallesAlerta.showAndWait();
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Ningún Equipo Seleccionado");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, selecciona un equipo de la lista.");
                alerta.showAndWait();
            }
        }


    }



}
