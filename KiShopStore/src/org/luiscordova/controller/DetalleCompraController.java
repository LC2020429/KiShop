package org.luiscordova.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.luiscordova.bean.Compra;
import org.luiscordova.bean.DetalleCompra;
import org.luiscordova.bean.ProductoTienda;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

public class DetalleCompraController implements Initializable {

    @FXML
    private TableView tvDetalleCompra;

    @FXML
    private TableColumn colDetalleCompra;

    @FXML
    private TableColumn colCostoU;

    @FXML
    private TableColumn ccolCantidad;

    @FXML
    private TableColumn colCodigoP;

    @FXML
    private TableColumn colNumeroDoc;

    @FXML
    private ComboBox cmbCodigoProducto;

    @FXML
    private ComboBox cmbNumeroDoccumento;

    @FXML
    private TextField txtCodigoDetalleC;

    @FXML
    private TextField txtxCostoU;

    @FXML
    private TextField txtCantidad;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnRegresar;

    private Main escenarioPrincipal;
    private ObservableList<DetalleCompra> listaDetalleCompra;
    private ObservableList<ProductoTienda> listaProductoTienda;
    private ObservableList<Compra> listaCompra;

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
        cmbCodigoProducto.setItems(getProductoTienda());
        cmbNumeroDoccumento.setItems(getCompra());
        desactivarControles();
    }

    public void cargarDatos() {
        tvDetalleCompra.setItems(getDetalleCompra());
        colDetalleCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCostoU.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        ccolCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
        colNumeroDoc.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));

    }

    public void seleccionarElemento() {
        try {
            txtCodigoDetalleC.setText(String.valueOf(((DetalleCompra) tvDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
            txtxCostoU.setText(String.valueOf(((DetalleCompra) tvDetalleCompra.getSelectionModel().getSelectedItem()).getCostoUnitario()));
            txtCantidad.setText(String.valueOf(((DetalleCompra) tvDetalleCompra.getSelectionModel().getSelectedItem()).getCantidad()));
            // Codigo Producto   pertenece a Productos = PorductoTienda
            cmbCodigoProducto.getSelectionModel().select(buscaCodigoProducto(((DetalleCompra) tvDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoProducto()));
            cmbNumeroDoccumento.getSelectionModel().select(buscarNumeroDoc(((DetalleCompra) tvDetalleCompra.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ProductoTienda buscaCodigoProducto(String codigoProducto) {
        ProductoTienda resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarProducto(?)}");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new ProductoTienda(
                        registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Compra buscarNumeroDoc(int numeroDocumento) {
        Compra resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCompraPorNumeroDocumento(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compra(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new DetalleCompra(resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleCompra = FXCollections.observableList(listaP);
    }

    public ObservableList<ProductoTienda> getProductoTienda() {
        ArrayList<ProductoTienda> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new ProductoTienda(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductoTienda = FXCollections.observableList(lista);
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
        switch (tipoDeOperador) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                tipoDeOperador = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleC.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCantidad.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setCodigoProducto(((ProductoTienda) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento((((Compra) cmbNumeroDoccumento.getSelectionModel().getSelectedItem()).getNumeroDocumento()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_AgregarDetalleCompra(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();

            listaDetalleCompra.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperador) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eiminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperador = operador.NINGUNO;
                break;
            default:
                if (tvDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar detalle compra???",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tvDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetalleCompra.remove(tvDetalleCompra.getSelectionModel().getSelectedItem());

                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe de seleccionar un Detalle  para eliminar");
                    }
                    break;
                }
        }
    }

    // LLEVA EL MISMO CONCEPTO QUE AGRAGAR Y ELIMINAR
    public void editar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                if (tvDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodigoDetalleC.setEditable(false);
                    tipoDeOperador = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe SELECCIONAR un producto para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarDetalleCompra (?,?,?,?,?)}");
            DetalleCompra registro = (DetalleCompra) tvDetalleCompra.getSelectionModel().getSelectedItem();

            registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleC.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCantidad.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setCodigoProducto(((ProductoTienda) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroDocumento((((Compra) cmbNumeroDoccumento.getSelectionModel().getSelectedItem()).getNumeroDocumento()));

            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoDetalleC.setEditable(false);
        txtxCostoU.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoProducto.setDisable(true);
        cmbNumeroDoccumento.setDisable(true);
    }

    public void activarControles() {
        txtCodigoDetalleC.setEditable(true);
        txtxCostoU.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoProducto.setDisable(false);
        cmbNumeroDoccumento.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoDetalleC.clear();
        txtxCostoU.clear();
        txtCantidad.clear();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();
        cmbNumeroDoccumento.getSelectionModel().getSelectedItem();

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
