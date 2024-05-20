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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.luiscordova.bean.CargoEmpleado;
import org.luiscordova.bean.Clientes;
import org.luiscordova.bean.Empleados;
import org.luiscordova.bean.Factura;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

public class FacturaControllerView implements Initializable {

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

    @FXML
    private TableView tvFactura;
    @FXML
    private TextField txtNumeroFactura;
    @FXML
    private TextField txtEstadoF;
    @FXML
    private TextField txtTotalF;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private ComboBox cmbEmpleado;
    @FXML
    private ComboBox cmbCliente;

    @FXML
    private TableColumn colNumeroFactura;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colCliente;
    @FXML
    private TableColumn colTotal;
    @FXML
    private TableColumn colEmpleado;

    private Main escenarioPrincipal;
    private ObservableList<Factura> listaFactura;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Empleados> listaEmpleados;

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
        cmbCliente.setItems(getClientes());
        cmbEmpleado.setItems(getEmpleados());
        desactivarControles();
    }

    public void cargarDatos() {
        tvFactura.setItems(getFactura());
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }

    public void seleccionarElemento() {
        Factura compraSeleccionada = (Factura) tvFactura.getSelectionModel().getSelectedItem();
        txtNumeroFactura.setText(String.valueOf(((Factura) tvFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEstadoF.setText(String.valueOf(((Factura) tvFactura.getSelectionModel().getSelectedItem()).getEstado()));
        txtTotalF.setText(String.valueOf(((Factura) tvFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        LocalDate fechaFactura = LocalDate.parse(compraSeleccionada.getFechaFactura(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpFecha.setValue(fechaFactura);

        cmbEmpleado.getSelectionModel().select(buscarCodigoEmp(((Factura) tvFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        cmbCliente.getSelectionModel().select(buscaCodigoCliente(((Factura) tvFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
    }

    public Clientes buscaCodigoCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(
                        registro.getInt("codigoCliente"),
                        registro.getString("NITCliente"),
                        registro.getString("nombresCliente"),
                        registro.getString("apellidosCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public CargoEmpleado buscarCodigoEmp(int codigoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargoEmpleadoPorCodigo(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
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

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> listaEmp = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaEmp.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getNString("turno"),
                        resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableList(listaEmp);
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
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtNumeroFactura.getText()));
        registro.setEstado(txtEstadoF.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
        registro.setFechaFactura(dpFecha.getValue().toString());
        registro.setCodigoCliente(((Clientes) cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado((((Empleados) cmbEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_AgregarFactura(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();

            listaFactura.add(registro);
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
                if (tvFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Factura ???",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura) tvFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.clearParameters();
                            listaFactura.remove(tvFactura.getSelectionModel().getSelectedItem());
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
                if (tvFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtNumeroFactura.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarFactura (?,?,?,?,?,?)}");
            Factura registro = new Factura();
            registro.setNumeroFactura(Integer.parseInt(txtNumeroFactura.getText()));
            registro.setEstado(txtEstadoF.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
            registro.setFechaFactura(dpFecha.getValue().toString());
            registro.setCodigoCliente(((Clientes) cmbEmpleado.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado((((Empleados) cmbCliente.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));

            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtNumeroFactura.setEditable(false);
        txtEstadoF.setEditable(false);
        txtTotalF.setEditable(false);
        dpFecha.setEditable(false);
        cmbEmpleado.setDisable(true);
        cmbCliente.setDisable(true);
    }

    public void activarControles() {
        txtNumeroFactura.setEditable(true);
        txtEstadoF.setEditable(true);
        txtTotalF.setEditable(true);
        dpFecha.setEditable(true);
        cmbEmpleado.setDisable(false);
        cmbCliente.setDisable(false);
    }

    public void limpiarControles() {
        txtNumeroFactura.clear();
        txtEstadoF.clear();
        txtTotalF.clear();
        dpFecha.setValue(null);
        cmbEmpleado.getSelectionModel().getSelectedItem();
        cmbCliente.getSelectionModel().getSelectedItem();

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
