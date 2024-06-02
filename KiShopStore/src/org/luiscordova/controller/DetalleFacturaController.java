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
import org.luiscordova.bean.DetalleFactura;
import org.luiscordova.bean.Factura;
import org.luiscordova.bean.ProductoTienda;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

public class DetalleFacturaController implements Initializable {

    @FXML
    private TableView<DetalleFactura> tvDetealleFactura;
    @FXML
    private TableColumn colDetaFact;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colNumFact;
    @FXML
    private TableColumn colCodProd;

    @FXML
    private TextField txtCodDetFac;
    @FXML
    private ComboBox cmbNumFactura;
    @FXML
    private ComboBox cmbCodProd;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtPrecioU;

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
    private ObservableList<DetalleFactura> listaDetalleFactura;
    private ObservableList<ProductoTienda> listaProductos;
    private ObservableList<Factura> listaFactura;

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
        cmbNumFactura.setItems(getFactura());
        cmbCodProd.setItems(getProducto());
        desactivarControles();
    }

    public void cargarDatos() {
        tvDetealleFactura.setItems(getDetalleFactura());
        colDetaFact.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
        colNumFact.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("numeroFactura"));
        colCodProd.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("codigoProducto"));
    }

    public void seleccionarElemento() {
        Object selectedItem = tvDetealleFactura.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                DetalleFactura detalleFactura = (DetalleFactura) selectedItem;
                txtCodDetFac.setText(String.valueOf(detalleFactura.getCodigoDetalleFactura()));
                txtCantidad.setText(String.valueOf(detalleFactura.getCantidad()));
                txtPrecioU.setText(String.valueOf(detalleFactura.getPrecioUnitario()));
                cmbNumFactura.getSelectionModel().select(buscarNumeroFactura(detalleFactura.getNumeroFactura()));
                cmbCodProd.getSelectionModel().select(buscaCodigoProducto(detalleFactura.getCodigoProducto()));
            } else {
                // Handle the case when no item is selected
                // You can clear the text fields or display a message to the user
                txtCodDetFac.setText("");
                txtCantidad.setText("");
                txtPrecioU.setText("");
                cmbNumFactura.getSelectionModel().clearSelection();
                cmbCodProd.getSelectionModel().clearSelection();
            }
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

    public Factura buscarNumeroFactura(int numeroFactura) {
        Factura resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ObtenerFacturaPorNumero(?)}");
            procedimiento.setInt(1, numeroFactura);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Factura(registro.getInt("numeroFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getString("fechaFactura"),
                        registro.getInt("codigoCliente"),
                        registro.getInt("codigoEmpleado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleFactura = FXCollections.observableList(listaP);
    }

    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFactura = FXCollections.observableList(listaP);
    }

    public ObservableList<ProductoTienda> getProducto() {
        ArrayList<ProductoTienda> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new ProductoTienda(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("CodigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(listaP);
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
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetFac.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
        registro.setNumeroFactura(((Factura) cmbNumFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCodigoProducto((((ProductoTienda) cmbCodProd.getSelectionModel().getSelectedItem()).getCodigoProducto()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_AgregarDetalleFactura(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();

            listaDetalleFactura.add(registro);
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
                if (tvDetealleFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Factura ???",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleFactura(?)}");
                            procedimiento.setInt(1, ((DetalleFactura) tvDetealleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
                            procedimiento.execute();
                            listaDetalleFactura.remove(tvDetealleFactura.getSelectionModel().getSelectedItem());
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
                if (tvDetealleFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodDetFac.setEditable(false);
                    tipoDeOperador = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe SELECCIONAR una factura para editar");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarDetalleFactura (?,?,?,?,?)}");
            DetalleFactura registro = new DetalleFactura();
            registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetFac.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
            registro.setNumeroFactura(((Factura) cmbNumFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
            registro.setCodigoProducto((((ProductoTienda) cmbCodProd.getSelectionModel().getSelectedItem()).getCodigoProducto()));

            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodDetFac.setEditable(false);
        txtCantidad.setEditable(false);
        txtPrecioU.setEditable(false);
        cmbCodProd.setDisable(true);
        cmbNumFactura.setDisable(true);
    }

    public void activarControles() {
        txtCodDetFac.setEditable(true);
        txtCantidad.setEditable(true);
        txtPrecioU.setEditable(true);
        cmbCodProd.setDisable(false);
        cmbNumFactura.setDisable(false);
    }

    public void limpiarControles() {
        txtCodDetFac.clear();
        txtCantidad.clear();
        txtPrecioU.clear();
        cmbCodProd.getSelectionModel().getSelectedItem();
        cmbNumFactura.getSelectionModel().getSelectedItem();

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
