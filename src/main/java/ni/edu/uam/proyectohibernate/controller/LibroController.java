
package ni.edu.uam.proyectohibernate.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.proyectohibernate.dao.LibroDAO;
import ni.edu.uam.proyectohibernate.model.Libro;

import java.util.List;

public class LibroController {

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutor;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtPrecio;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCargar;
    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Libro> tblLibros;

    @FXML
    private TableColumn<Libro, Long> colId;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, Integer> colAnio;
    @FXML
    private TableColumn<Libro, Double> colPrecio;

    // Instancia de LibroDAO
    private LibroDAO libroDAO = new LibroDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    @FXML
    private void guardarLibro(){
        try{
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int anio = Integer.valueOf(txtAnio.getText());
            double precio = Double.valueOf(txtPrecio.getText());

            Libro libro = new Libro(
                    0, titulo, autor, anio, precio
            );
            libroDAO.guardar(libro);
            cargarLibros();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarLibros(){
        try{
            List<Libro> libros = libroDAO.listar();
            tblLibros.setItems(FXCollections.observableArrayList(libros));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarLibro(ActionEvent event) {
    }

    public void eliminarLibro(ActionEvent event) {
    }

    public void limpiarCampos(ActionEvent event) {
    }
}
