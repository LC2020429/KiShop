package org.luiscordova.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.luiscordova.bean.Compra;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

public class CompraVistaController implements Initializable {

    private ObservableList<Compra> listaCompra;
    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView tvCompras;

    @FXML
    private TableColumn ColNumeroDocumento;

    @FXML
    private TableColumn colFechaCompra;

    @FXML
    private TableColumn colDescripcionCompra;

    @FXML
    private TableColumn colTotalCompra;

    @FXML
    private TextField txtNumeroDocumento;
    // este es una manera de conseguir la fecha mediante lo que nos da Scene Builder
    @FXML
    private DatePicker FechaDatePicker;

    @FXML
    private TextField txtDescripcionCompra;

    @FXML
    private TextField txtTotalCompra;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnReporte;

    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();

    }

    public void cargarDatos() {
        tvCompras.setItems(getCompra());
        ColNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("numeroDocumento"));
        // Indicamos que lo usaremos como un dato String 
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compra, String>("fechaDocumento"));
        colDescripcionCompra.setCellValueFactory(new PropertyValueFactory<Compra, String>("descripcion"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compra, String>("totalDocumento"));
    }

    public void seleccionarElemento() {
        try {
            Compra compraSeleccionada = (Compra) tvCompras.getSelectionModel().getSelectedItem();

            txtNumeroDocumento.setText(String.valueOf(compraSeleccionada.getNumeroDocumento()));
            txtDescripcionCompra.setText(compraSeleccionada.getDescripcion());
            txtTotalCompra.setText(String.valueOf(compraSeleccionada.getTotalDocumento()));

            String fechaCompraObtener = compraSeleccionada.getFechaDocumento(); // Obtener la fecha de compra como cadena de texto

            LocalDate fechaCompra = LocalDate.parse(fechaCompraObtener); // Convertir la cadena de texto a LocalDate

            FechaDatePicker.setValue(fechaCompra); // Asignar la fecha de compra al DatePicker
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ObservableList<Compra> getCompra() {
        ArrayList<Compra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compra(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCompra = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                //imgAgregar.setImage(new Image("/resources/images/"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eiminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Compra registro = new Compra();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
        // Usamos por propiedades de DatePicker el GetValue y no el el get text luego lo combertimos a texto con el toString
        registro.setFechaDocumento(FechaDatePicker.getValue().toString());
        registro.setDescripcion(txtDescripcionCompra.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotalCompra.getText()));
        try {
            // se usa procedimiento por ser local y solo permite una conexion
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompra(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompra.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                // Código para actualizar
                break;
            default:
                if (tvCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminación del registro", "Eliminar Compra",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            int numeroDocumento = ((Compra) tvCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento();

                            // Eliminar las filas en la tabla detallecompra que hacen referencia a la compra
                            PreparedStatement eliminarDetallesCompra = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            eliminarDetallesCompra.setInt(1, numeroDocumento);
                            eliminarDetallesCompra.execute();

                            // Eliminar la fila de la tabla compras
                            PreparedStatement eliminarCompra = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompra(?)}");
                            eliminarCompra.setInt(1, numeroDocumento);
                            eliminarCompra.execute();

                            limpiarControles();
                            listaCompra.remove(tvCompras.getSelectionModel().getSelectedItem()); // Eliminar el elemento seleccionado de listaCompra
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una Compra para eliminar");
                    }
                }
                break;
        }
    }

    // LLEVA EL MISMO CONCEPTO QUE AGRAGAR Y ELIMINAR
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtNumeroDocumento.setDisable(true);
                    activarControles();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe SELECCIONAR un proveedor para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                txtNumeroDocumento.setDisable(true);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                txtNumeroDocumento.setDisable(false);
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCompra (?,?,?,?)}");
            Compra registro = (Compra) tvCompras.getSelectionModel().getSelectedItem();

            registro.setDescripcion(txtDescripcionCompra.getText());
            registro.setTotalDocumento(Double.parseDouble(txtTotalCompra.getText()));

            LocalDate fechaCompra = FechaDatePicker.getValue();
            if (fechaCompra != null) { // Verificar si se seleccionó una fecha
                registro.setFechaDocumento(fechaCompra.toString());
            }

            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());

            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtNumeroDocumento.setEditable(false);
        FechaDatePicker.setEditable(false);
        txtDescripcionCompra.setEditable(false);
        txtTotalCompra.setEditable(false);
    }

    public void activarControles() {
        txtNumeroDocumento.setEditable(true);
        FechaDatePicker.setEditable(true);
        txtDescripcionCompra.setEditable(true);
        txtTotalCompra.setEditable(true);
    }

    public void limpiarControles() {
        txtNumeroDocumento.clear();
        FechaDatePicker.setValue(null);
        txtDescripcionCompra.clear();
        txtTotalCompra.clear();
    }

    public void MenuPrincipalView() {
        escenarioPrincipal.menuPrincipalView();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
