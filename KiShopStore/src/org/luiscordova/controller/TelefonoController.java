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
import org.luiscordova.bean.Proveedores;
import org.luiscordova.bean.TelefonoProveedor;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

/**
 *
 * @author Computadora
 */
public class TelefonoController implements Initializable{
    private Main escenarioPrincipal;
    private ObservableList<TelefonoProveedor> listaTelefonos;
    private ObservableList<Proveedores> listaProveedores;


    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;
     @FXML
    private TableView tvTelefonos;

    @FXML
    private TableColumn  colCodigoTelefono;

    @FXML
    private TableColumn  colObservaciones;

    @FXML
    private TableColumn  colPrincipal;

    @FXML
    private TableColumn colSecundario;

    @FXML
    private TableColumn  colcodigoProveedorRefe;

    @FXML
    private TextField txtnumeroPrincipal;

    @FXML
    private TextField txtnumeroSecundario;

    @FXML
    private TextField txtcodigoTelefonoProveedor;

    @FXML
    private TextField txtobservaciones;
    
        @FXML
    private ComboBox  cmbPorveedor;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
        cmbPorveedor.setItems(getTelefonoProveedor());
        desactivarControles();
    }

    public void cargarDatos() {
        tvTelefonos.setItems(getTelefonoProveedor());
        colCodigoTelefono.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
        colPrincipal.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroSecundario"));
        colSecundario.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("observaciones"));
        colcodigoProveedorRefe.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));
    }

    public void seleccionarElemento() {
        try {
            txtcodigoTelefonoProveedor.setText(String.valueOf(((TelefonoProveedor) tvTelefonos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtnumeroPrincipal.setText((((TelefonoProveedor) tvTelefonos.getSelectionModel().getSelectedItem()).getNumeroPrincipal()));
            txtnumeroSecundario.setText((((TelefonoProveedor) tvTelefonos.getSelectionModel().getSelectedItem()).getNumeroSecundario()));
            txtobservaciones.setText(String.valueOf(((TelefonoProveedor) tvTelefonos.getSelectionModel().getSelectedItem()).getObservaciones()));
            cmbPorveedor.getSelectionModel().select(buscarCodigoProveedor(((TelefonoProveedor) tvTelefonos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Proveedores buscarCodigoProveedor(int codigoEmpleado) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedorPorCodigo(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")                        
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<TelefonoProveedor> getTelefonoProveedor() {
        ArrayList<TelefonoProveedor> listaTel = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTelefonosProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaTel.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTelefonos = FXCollections.observableList(listaTel);
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

    public void agregar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
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
                btnReportes.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtcodigoTelefonoProveedor.getText()));
        registro.setNumeroPrincipal(txtnumeroPrincipal.getText());
        registro.setNumeroSecundario(txtnumeroSecundario.getText());
        registro.setObservaciones(txtobservaciones.getText());
        registro.setCodigoProveedor(((Proveedores) cmbPorveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTelefonoProveedor(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();

            listaTelefonos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                if (tvTelefonos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtcodigoTelefonoProveedor.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTelefonoProveedor(?,?,?,?,?)}");
            TelefonoProveedor registro = (TelefonoProveedor) tvTelefonos.getSelectionModel().getSelectedItem();
            registro.setCodigoTelefonoProveedor(Integer.parseInt(txtcodigoTelefonoProveedor.getText()));
            registro.setNumeroPrincipal(txtnumeroPrincipal.getText());
            registro.setNumeroSecundario(txtnumeroSecundario.getText());
            registro.setObservaciones(txtobservaciones.getText());
            registro.setCodigoProveedor(((Proveedores) cmbPorveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
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
                if (tvTelefonos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Telefonos???",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefonoProveedor(?)}");
                            procedimiento.setInt(1, ((TelefonoProveedor) tvTelefonos.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listaTelefonos.remove(tvTelefonos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe de seleccionar un Telefono para eliminar");
                    }
                    break;
                }
        }
    }
           
    public void reporte() {
        switch (tipoDeOperador) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperador = operador.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtcodigoTelefonoProveedor.setEditable(false);
        txtnumeroPrincipal.setEditable(false);
        txtnumeroSecundario.setEditable(false);
        txtobservaciones.setEditable(false);
        cmbPorveedor.setDisable(true);
    }

    public void activarControles() {
        txtcodigoTelefonoProveedor.setEditable(true);
        txtnumeroPrincipal.setEditable(true);
        txtnumeroSecundario.setEditable(true);
        txtobservaciones.setEditable(true);
        cmbPorveedor.setDisable(false);
    }

    public void limpiarControles() {
        txtcodigoTelefonoProveedor.clear();
        txtnumeroPrincipal.clear();
        txtnumeroSecundario.clear();
        txtobservaciones.clear();
        cmbPorveedor.getSelectionModel().clearSelection();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public void MenuPrincipalView() {
        escenarioPrincipal.menuPrincipalView();
    }
}
