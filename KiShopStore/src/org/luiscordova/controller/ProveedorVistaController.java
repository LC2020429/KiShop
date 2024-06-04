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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.luiscordova.bean.Proveedores;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

/**
 *
 * @author Computadora
 */
public class ProveedorVistaController implements Initializable {

    private ObservableList<Proveedores> listaProveedores;
    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView tvProveedores;
    @FXML
    private TableColumn colcodigoProveedor;
    @FXML
    private TableColumn colNITProveedor;
    @FXML
    private TableColumn colnombresProveedor;
    @FXML
    private TableColumn colapellidosProveedor;
    @FXML
    private TableColumn coldireccionProveedor;
    @FXML
    private TableColumn colrazonSocial;
    @FXML
    private TableColumn colcontactoPrincipal;
    @FXML
    private TableColumn colpaginaWeb;

    @FXML
    private TextField txtcodigoProveedor;
    @FXML
    private TextField txtNITProveedor;
    @FXML
    private TextField txtnombresProveedor;
    @FXML
    private TextField txtapellidosProveedor;
    @FXML
    private TextField txtdireccionProveedor;
    @FXML
    private TextField txtrazonSocial;
    @FXML
    private TextField txtcontactoPrincipal;
    @FXML
    private TextField txtpaginaWeb;

    @FXML
    private Button btnAgregarProveedor;
    @FXML
    private Button btnEliminarProveedor;
    @FXML
    private Button btnEditarProveedor;
    @FXML
    private Button btnReportesProveedor;
    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
    }

    public void cargarDatos() {
        tvProveedores.setItems(getProveedores());
        colcodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colnombresProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colapellidosProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        coldireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colrazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colcontactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colpaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        // castear es convertir datos 
        try {
            txtcodigoProveedor.setText(String.valueOf(((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNITProveedor.setText((((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getNITProveedor()));
            txtnombresProveedor.setText((((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getNombresProveedor()));
            txtapellidosProveedor.setText((((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor()));
            txtdireccionProveedor.setText((((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
            txtrazonSocial.setText((((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
            txtcontactoPrincipal.setText((((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal()));
            txtpaginaWeb.setText((((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarProveedor.setText("Guardar");
                btnEliminarProveedor.setText("Cancelar");
                btnEditarProveedor.setDisable(true);
                btnReportesProveedor.setDisable(true);
                //imgAgregar.setImage(new Image("/resources/images/"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eiminar");
                btnEditarProveedor.setDisable(false);
                btnReportesProveedor.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtcodigoProveedor.getText()));
        registro.setNITProveedor(txtNITProveedor.getText());
        registro.setNombresProveedor(txtnombresProveedor.getText());
        registro.setApellidosProveedor(txtapellidosProveedor.getText());
        registro.setDireccionProveedor(txtdireccionProveedor.getText());
        registro.setRazonSocial(txtrazonSocial.getText());
        registro.setContactoPrincipal(txtcontactoPrincipal.getText());
        registro.setPaginaWeb(txtpaginaWeb.getText());
        try {
            // se usa procedimiento por ser local y solo permite una conexion
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedor(?, ?, ?, ?, ?, ?, ?,?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eiminar");
                btnEditarProveedor.setDisable(false);
                btnReportesProveedor.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Proveedor???", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores) tvProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tvProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe de seleccionar Cliente para eliminar");
                    }
                    break;
                }
        }
    }

    // LLEVA EL MISMO CONCEPTO QUE AGRAGAR Y ELIMINAR
    public void editar() {
    switch (tipoDeOperaciones) {
        case NINGUNO:
            if (tvProveedores.getSelectionModel().getSelectedItem() != null) {
                btnEditarProveedor.setText("Actualizar");
                btnReportesProveedor.setText("Cancelar");
                btnAgregarProveedor.setDisable(true);
                btnEliminarProveedor.setDisable(true);
                txtcodigoProveedor.setDisable(true);
                activarControles();
                tipoDeOperaciones = operaciones.ACTUALIZAR;
            } else {
                JOptionPane.showMessageDialog(null, "Debe SELECCIONAR un proveedor para editar");
            }
            break;
        case ACTUALIZAR:
            actualizar();
            btnEditarProveedor.setText("Editar");
            btnReportesProveedor.setText("Reporte");
            btnAgregarProveedor.setDisable(false);
            btnEliminarProveedor.setDisable(false);
            txtcodigoProveedor.setDisable(true);
            desactivarControles();
            limpiarControles();
            tipoDeOperaciones = operaciones.NINGUNO;
            cargarDatos();
            txtcodigoProveedor.setDisable(false); // Habilitar la edición del campo
            break;
    }
}

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProveedor (?,?,?,?,?,?,?,?)}");
            Proveedores registro = (Proveedores) tvProveedores.getSelectionModel().getSelectedItem();

            registro.setNITProveedor(txtNITProveedor.getText());
            registro.setNombresProveedor(txtnombresProveedor.getText());
            registro.setApellidosProveedor(txtapellidosProveedor.getText());
            registro.setDireccionProveedor(txtdireccionProveedor.getText());
            registro.setRazonSocial(txtrazonSocial.getText());
            registro.setContactoPrincipal(txtcontactoPrincipal.getText());
            registro.setPaginaWeb(txtpaginaWeb.getText());

            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // METODOS PARA CONTROLAR DONDE SE INGRESA EL TEXTO
    public void desactivarControles() {
        txtcodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtnombresProveedor.setEditable(false);
        txtapellidosProveedor.setEditable(false);
        txtdireccionProveedor.setEditable(false);
        txtrazonSocial.setEditable(false);
        txtcontactoPrincipal.setEditable(false);
        txtpaginaWeb.setEditable(false);
    }

    public void activarControles() {
        txtcodigoProveedor.setEditable(true);
        txtNITProveedor.setEditable(true);
        txtnombresProveedor.setEditable(true);
        txtapellidosProveedor.setEditable(true);
        txtdireccionProveedor.setEditable(true);
        txtrazonSocial.setEditable(true);
        txtcontactoPrincipal.setEditable(true);
        txtpaginaWeb.setEditable(true);
    }

    public void limpiarControles() {
        txtcodigoProveedor.clear();
        txtNITProveedor.clear();
        txtnombresProveedor.clear();
        txtapellidosProveedor.clear();
        txtdireccionProveedor.clear();
        txtrazonSocial.clear();
        txtcontactoPrincipal.clear();
        txtpaginaWeb.clear();
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
