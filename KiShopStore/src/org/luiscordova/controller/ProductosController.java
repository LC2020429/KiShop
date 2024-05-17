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
import org.luiscordova.bean.Productos;
import org.luiscordova.bean.Proveedores;
import org.luiscordova.bean.TipoProducto;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

public class ProductosController implements Initializable {

    @FXML
    private TableView tvProducto;

    @FXML
    private TableColumn ColCodigoP;

    @FXML
    private TableColumn colPrecioU;

    @FXML
    private TableColumn colPrecioDoce;

    @FXML
    private TableColumn colPrecioMayor;

    @FXML
    private TableColumn calExisitenciaP;

    @FXML
    private TableColumn colCodigoProveedor;

    @FXML
    private TableColumn colTipoP;

    @FXML
    private TableColumn colDescrip;

    @FXML
    private TableColumn colImagenP;

    @FXML
    private TextField txtPrecioU;

    @FXML
    private TextField txtPrecioDoce;

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
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnReporte;

    @FXML
    private Button btnRegresar;

    private Main escenarioPrincipal;
    private ObservableList<Productos> listaProductos;
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
        ColCodigoP.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioDoce.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenP.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        calExisitenciaP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colTipoP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("CodigoTipoProducto"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }

    public void seleccionarElemento() {
        txtCodigoProducto.setText((((Productos) tvProducto.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescripcion.setText((((Productos) tvProducto.getSelectionModel().getSelectedItem()).getDescripcionProducto()));
        txtPrecioU.setText(String.valueOf(((Productos) tvProducto.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioDoce.setText(String.valueOf(((Productos) tvProducto.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioMayor.setText(String.valueOf(((Productos) tvProducto.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtImagen.setText((((Productos) tvProducto.getSelectionModel().getSelectedItem()).getImagenProducto()));
        txtExisistencia.setText(String.valueOf(((Productos) tvProducto.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodigoTipoP.getSelectionModel().select(buscarTipoProducto(((Productos) tvProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
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

    public Proveedores buscarProveedor(int codigoProveedor, String nitProveedor,
            String nombreProveedor, String apellidosProveedor, String direccionProveedor,
            String razonSocial, String contactoPrincipal, String paginaWeb) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_buscarProveedor(?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, codigoProveedor);
            procedimiento.setString(2, nitProveedor);
            procedimiento.setString(3, nombreProveedor);
            procedimiento.setString(4, apellidosProveedor);
            procedimiento.setString(5, direccionProveedor);
            procedimiento.setString(6, razonSocial);
            procedimiento.setString(7, contactoPrincipal);
            procedimiento.setString(8, paginaWeb);

            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(
                        registro.getInt("codigoProveedor"),
                        registro.getString("nitProveedor"),
                        registro.getString("nombreProveedor"),
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

    public ObservableList<Productos> getProducto() {
        ArrayList<Productos> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_mostrarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Productos(resultado.getString("codigoProducto"),
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

    public void desactivarControles() {
        txtCodigoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
        txtExisistencia.setEditable(false);
        txtImagen.setEditable(false);
        txtPrecioDoce.setEditable(false);
        txtPrecioMayor.setEditable(false);
        txtPrecioU.setEditable(false);
        cmbCodigoTipoP.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
    }

    public void activarControles() {
        txtCodigoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
        txtExisistencia.setEditable(true);
        txtImagen.setEditable(true);
        txtPrecioDoce.setEditable(true);
        txtPrecioMayor.setEditable(true);
        txtPrecioU.setEditable(true);
        cmbCodigoTipoP.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoProducto.clear();
        txtDescripcion.clear();
        txtExisistencia.clear();
        txtImagen.clear();
        txtPrecioDoce.clear();
        txtPrecioMayor.clear();
        txtPrecioU.clear();
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

    private void agregar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                //imgAgregar.setImage(new Image("URL"));
                tipoDeOperador = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                cargarDatos();
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

    public void guardar() {
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodigoProducto.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto((((TipoProducto) cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        registro.setDescripcionProducto(txtDescripcion.getText());
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDoce.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setExistencia(Integer.parseInt(txtExisistencia.getText()));
        registro.setImagenProducto(txtImagen.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));

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

    public void eliminar() {
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
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Producto???", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProducto(?)}");
                            procedimiento.setString(1, ((Productos) tvProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
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
    public void editar() {
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
                actualizar();
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
    
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProducto (?,?,?,?,?,?,?,?,?)}");
            Productos registro = (Productos) tvProducto.getSelectionModel().getSelectedItem();
            
            registro.setCodigoProducto(txtCodigoProducto.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setCodigoTipoProducto((((TipoProducto) cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
            registro.setDescripcionProducto(txtDescripcion.getText());
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDoce.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            registro.setExistencia(Integer.parseInt(txtExisistencia.getText()));
            registro.setImagenProducto(txtImagen.getText());
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
        

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

}
