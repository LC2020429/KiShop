/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.luiscordova.bean.ProductoTienda;
import org.luiscordova.bean.Proveedores;
import org.luiscordova.bean.TipoProducto;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

/**
 *
 * @author Computadora
 */
public class ProductosTiendaController implements Initializable {

    @FXML
    private TableView tvProducto;

    @FXML
    private TableColumn colCodigoP;

    @FXML
    private TableColumn colPrecioUnitario;

    @FXML
    private TableColumn colPrecioDocena;

    @FXML
    private TableColumn colPrecioMayor;

    @FXML
    private TableColumn calExisitenciaProducto;

    @FXML
    private TableColumn colCodigoProveedor;

    @FXML
    private TableColumn colTipoProduccto;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colImagenProduccto;

    @FXML
    private TextField txtPrecioUnitario;

    @FXML
    private TextField txtPrecioDocena;

    @FXML
    private TextField txtImagen;

    @FXML
    private TextField txtCodigoProducto;

    @FXML
    private TextField txtPrecioMayor;

    @FXML
    private TextField txtExisistencia;
    
    @FXML
    private TextField txtDescripcion;

    @FXML
    private ComboBox cmbCodigoProveedor;

    @FXML
    private ComboBox cmbCodigoTipoP;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnReporte;

    @FXML
    private Button btnRegresar;

    @FXML
    private Button btnAgregar;

    private Main escenarioPrincipal;
    private ObservableList<ProductoTienda> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProducto> listaTipoProducto;

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
        cmbCodigoTipoP.setItems(getTipoProducto());
        cmbCodigoProveedor.setItems(getProveedores());
        desactivarControles();
    }

    public void cargarDatos() {
        tvProducto.setItems(getProducto());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<ProductoTienda, String>("codigoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<ProductoTienda, String>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<ProductoTienda, Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<ProductoTienda, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<ProductoTienda, Double>("precioMayor"));
        colImagenProduccto.setCellValueFactory(new PropertyValueFactory<ProductoTienda, String>("imagenProducto"));
        calExisitenciaProducto.setCellValueFactory(new PropertyValueFactory<ProductoTienda, Integer>("existencia"));
        colTipoProduccto.setCellValueFactory(new PropertyValueFactory<ProductoTienda, Integer>("CodigoTipoProducto"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<ProductoTienda, Integer>("codigoProveedor"));
    }

    public void seleccionarElemento() {
        txtCodigoProducto.setText((((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescripcion.setText((((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getDescripcionProducto()));
        txtPrecioUnitario.setText(String.valueOf(((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioDocena.setText(String.valueOf(((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioMayor.setText(String.valueOf(((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtImagen.setText((((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getImagenProducto()));
        txtExisistencia.setText(String.valueOf(((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodigoTipoP.getSelectionModel().select(buscarTipoProducto(((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoProductoPorID(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarProveedorPorCodigo(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(
                        registro.getInt("codigoProveedor"),
                        registro.getString("nitProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
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

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableList(lista);
    }

    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoProducto = FXCollections.observableList(lista);
    }

    public void agregarProducto() {
        switch (tipoDeOperador) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperador = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarProducto();
                limpiarControles();
                desactivarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardarProducto() {
        ProductoTienda registro = new ProductoTienda();
        registro.setCodigoProducto(txtCodigoProducto.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto((((TipoProducto) cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        registro.setDescripcionProducto(txtDescripcion.getText());
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setExistencia(Integer.parseInt(txtExisistencia.getText()));
        registro.setImagenProducto(txtImagen.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_AgregarProducto(?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();

            listaProductos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto() {
        switch (tipoDeOperador) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eiminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperador = operador.NINGUNO;
                break;
            default:
                if (tvProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Producto???",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProducto(?)}");
                            procedimiento.setString(1, ((ProductoTienda) tvProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tvProducto.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe de seleccionar un producto para eliminar");
                    }
                    break;
                }
        }
    }

    // LLEVA EL MISMO CONCEPTO QUE AGRAGAR Y ELIMINAR
    public void editarProducto() {
        switch (tipoDeOperador) {
            case NINGUNO:
                if (tvProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodigoProducto.setEditable(false);
                    tipoDeOperador = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe SELECCIONAR un producto para editar");
                }
                break;
            case ACTUALIZAR:
                actualizarProducto();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizarProducto() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProducto (?,?,?,?,?,?,?,?,?)}");
            ProductoTienda registro = (ProductoTienda) tvProducto.getSelectionModel().getSelectedItem();

            registro.setCodigoProducto(txtCodigoProducto.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setCodigoTipoProducto((((TipoProducto) cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
            registro.setDescripcionProducto(txtDescripcion.getText());
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            registro.setExistencia(Integer.parseInt(txtExisistencia.getText()));
            registro.setImagenProducto(txtImagen.getText());
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));

            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
        txtExisistencia.setEditable(false);
        txtImagen.setEditable(false);
        txtPrecioDocena.setEditable(false);
        txtPrecioMayor.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        cmbCodigoTipoP.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
    }

    public void activarControles() {
        txtCodigoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
        txtExisistencia.setEditable(true);
        txtImagen.setEditable(true);
        txtPrecioDocena.setEditable(true);
        txtPrecioMayor.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        cmbCodigoTipoP.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoProducto.clear();
        txtDescripcion.clear();
        txtExisistencia.clear();
        txtImagen.clear();
        txtPrecioDocena.clear();
        txtPrecioMayor.clear();
        txtPrecioUnitario.clear();
        tvProducto.getSelectionModel().getSelectedItem();
        cmbCodigoTipoP.getSelectionModel().getSelectedItem();
        cmbCodigoProveedor.getSelectionModel().getSelectedItem();

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
