package org.luiscordova.controller;

/**
 *
 * @author Computadora
 */
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.luiscordova.bean.Clientes;
import org.luiscordova.dao.Conexion;
import org.luiscordova.report.GenerarReportes;
import org.luiscordova.system.Main;

public class ClienteVistaController implements Initializable {

    private ObservableList<Clientes> listaClientes;
    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView tvCliente;
    @FXML
    private TableColumn colcodigoCliente;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colApellidosClientes;
    @FXML
    private TableColumn colDireccionClientes;
    @FXML
    private TableColumn colNITCliente;
    @FXML
    private TableColumn colTelefonoClientes;
    @FXML
    private TableColumn colCorreoClientes;

    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnAgregar;

    @FXML
    private TextField txtClienteID;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TextField txtApellidoCliente;
    @FXML
    private TextField txtDireccionCliente;
    @FXML
    private TextField txtNIT;
    @FXML
    private TextField txtTelefonoCli;
    @FXML
    private TextField txtCorreoCliente;
    @FXML
    private Button btnRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();

    }

    public void cargarDatos() {
        try {
            tvCliente.setItems(getClientes());
            colcodigoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
            colNITCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
            colNombreCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombresCliente"));
            colApellidosClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidosCliente"));
            colDireccionClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
            colTelefonoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
            colCorreoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void seleccionarElmento() {
        // castear es convertir datos 
        try {
            txtClienteID.setText(String.valueOf(((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtNIT.setText((((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getNITCliente()));
            txtNombreCliente.setText((((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getNombresCliente()));
            txtApellidoCliente.setText((((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getApellidosCliente()));
            txtDireccionCliente.setText((((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getDireccionCliente()));
            txtTelefonoCli.setText((((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getTelefonoCliente()));
            txtCorreoCliente.setText((((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getCorreoCliente()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila válida", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITCliente"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableList(lista);
    }

    @FXML
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
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
                btnReportes.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtClienteID.getText()));

        // Validar si el código de cliente ya existe
        if (existeCodigoCliente(registro.getCodigoCliente())) {
            JOptionPane.showMessageDialog(null, "El código de cliente ya existe. Por favor, ingrese uno nuevo.");
            return; // Detener el proceso de guardado
        }

        registro.setNITCliente(txtNIT.getText());
        registro.setNombresCliente(txtNombreCliente.getText());
        registro.setApellidosCliente(txtApellidoCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCli.getText());
        registro.setCorreoCliente(txtCorreoCliente.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {

            JOptionPane.showMessageDialog(null, "No puede haber dos clientes con el mismo nit");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean existeCodigoCliente(int codigoCliente) {
        for (Clientes cliente : listaClientes) {
            if (cliente.getCodigoCliente() == codigoCliente) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eiminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvCliente.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tvCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tvCliente.getSelectionModel().getSelectedItem());
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
    @FXML
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvCliente.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtClienteID.setDisable(true);
                    activarControles();
                    txtClienteID.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe SELECCIONAR un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                txtClienteID.setDisable(true);

                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                txtClienteID.setDisable(false);

                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCliente (?,?,?,?,?,?,?)}");
            Clientes registro = (Clientes) tvCliente.getSelectionModel().getSelectedItem();

            registro.setNITCliente(txtNIT.getText());
            registro.setNombresCliente(txtNombreCliente.getText());
            registro.setApellidosCliente(txtApellidoCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCli.getText());
            registro.setCorreoCliente(txtCorreoCliente.getText());

            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void reporte() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;

        }
    }

    public void imprimirReporte() {
        Map parametros = new HashMap();
        parametros.put("codigoCliente", null);
        GenerarReportes.mostrarReportes("ReportCLIENTES.jasper", "Reporte de Clientes", parametros);
    }
    // METODOS PARA CONTROLAR DONDE SE INGRESA EL TEXTO
    public void desactivarControles() {
        txtClienteID.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtNIT.setEditable(false);
        txtTelefonoCli.setEditable(false);
        txtCorreoCliente.setEditable(false);
    }

    public void activarControles() {
        txtClienteID.setEditable(true);
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtNIT.setEditable(true);
        txtTelefonoCli.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }

    public void limpiarControles() {
        txtClienteID.clear();
        txtNombreCliente.clear();
        txtApellidoCliente.clear();
        txtDireccionCliente.clear();
        txtNIT.clear();
        txtTelefonoCli.clear();
        txtCorreoCliente.clear();
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
