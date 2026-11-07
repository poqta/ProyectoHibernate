
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

import java.math.BigDecimal;
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
    private Long idSeleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        cargarLibros();
    }

    // Permite controlar la seleccion y visualizacion de los botones de FXML
    private void actualizarBotones(){
        boolean sinSeleccionado = idSeleccionado == null;
        btnModificar.setDisable(sinSeleccionado);
        btnEliminar.setDisable(sinSeleccionado);
    }

    private Libro obtenerLibroFormulario(){
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int anio = Integer.valueOf(txtAnio.getText());
        BigDecimal precio = new BigDecimal(txtPrecio.getText());
        return new Libro(
                0, titulo, autor, anio, precio);
    }

    @FXML
    private void guardarLibro(){
        try{
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int anio = Integer.valueOf(txtAnio.getText());
            BigDecimal precio = new BigDecimal(txtPrecio.getText());

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
        if(idSeleccionado == null){
            return;
        }

        try{
            Libro libro = obtenerLibroFormulario();
            libro.setId(idSeleccionado);
            boolean modificado = libroDAO.modificar(libro);
            if (!modificado){
                //Alerta sobre la transacción no realizado
                return;
            }
            //Alert de si se realizó la operación
            cargarLibros();
        } catch (Exception e){
            e.printStackTrace();
        }

    }



    @FXML
    public void eliminarLibro(ActionEvent event) {
        try {
            Libro libroSeleccionado = tblLibros.getSelectionModel().getSelectedItem();

            if (libroSeleccionado == null) {
                System.out.println("Seleccione un libro para eliminar.");
                return;
            }

            boolean eliminado = libroDAO.eliminar(libroSeleccionado.getId());

            if (eliminado) {
                System.out.println("Libro eliminado correctamente.");

                idSeleccionado = null;
                cargarLibros();
                actualizarBotones();
            } else {
                System.out.println("No se pudo eliminar el libro.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void limpiarCampos(ActionEvent event) {
    }

}
