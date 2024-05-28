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
import org.luiscordova.bean.TipoProducto;
import org.luiscordova.dao.Conexion;
import org.luiscordova.system.Main;

public class TipoProductoVistaController implements Initializable {

    private ObservableList<TipoProducto> listaTipoProducto;
    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnAgregarTipoProducto;
    @FXML
    private Button btnEliminarTipoProducto;
    @FXML
    private Button btnEditarTipoProducto;
    @FXML
    private Button btnReportesTipoProducto;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableView tvTipoProducto;
    @FXML
    private TableColumn colCodigoTipoProducto;
    @FXML
    private TableColumn colDescripconTipoProducto;

    @FXML
    private TextField txtCodigoTipoProducto;
    @FXML
    private TextField txtDescripcionTipoProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
    }

    public void cargarDatos() {
        tvTipoProducto.setItems(getTipoProducto());
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripconTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    public void seleccionarElemento() {
        txtCodigoTipoProducto.setText(String.valueOf(((TipoProducto) tvTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcionTipoProducto.setText(String.valueOf(((TipoProducto) tvTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion()));
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

    public void agregar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarTipoProducto.setText("Guardar");
                btnEliminarTipoProducto.setText("Cancelar");
                btnEditarTipoProducto.setDisable(true);
                btnReportesTipoProducto.setDisable(true);
                //imgAgregar.setImage(new Image("/resources/images/"));
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnAgregarTipoProducto.setText("Agregar");
                btnEliminarTipoProducto.setText("Eiminar");
                btnEditarTipoProducto.setDisable(false);
                btnReportesTipoProducto.setDisable(false);
                tipoOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoProducto.getText()));
        registro.setDescripcion(txtDescripcionTipoProducto.getText());
        try {
            // se usa procedimiento por ser local y solo permite una conexion
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProducto(?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTipoProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarTipoProducto.setText("Agregar");
                btnEliminarTipoProducto.setText("Eiminar");
                btnEditarTipoProducto.setDisable(false);
                btnReportesTipoProducto.setDisable(false);
                tipoOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminacion del registro", "Eliminar Tipo Producto?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tvTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaTipoProducto.remove(tvTipoProducto.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe de seleccionar un tipo de producto para eliminar");
                    }
                    break;
                }
        }
    }

    public void editar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarControles();
                if (tvTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditarTipoProducto.setText("Actualizar");
                    btnReportesTipoProducto.setText("Cancelar");
                    btnAgregarTipoProducto.setDisable(true);
                    btnEliminarTipoProducto.setDisable(true);
                    txtCodigoTipoProducto.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe SELECCIONAR un proveedor para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();

                btnEditarTipoProducto.setText("Editar");
                btnReportesTipoProducto.setText("Reporte");
                btnAgregarTipoProducto.setDisable(false);
                btnEliminarTipoProducto.setDisable(false);

                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoProducto (?,?)}");
            TipoProducto registro = (TipoProducto) tvTipoProducto.getSelectionModel().getSelectedItem();

            registro.setDescripcion(txtDescripcionTipoProducto.getText());

            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());

            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodos de los textfield
    public void desactivarControles() {
        txtCodigoTipoProducto.setEditable(false);
        txtDescripcionTipoProducto.setEditable(false);
    }

    public void activarControles() {
        txtCodigoTipoProducto.setEditable(true);
        txtDescripcionTipoProducto.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoTipoProducto.clear();
        txtDescripcionTipoProducto.clear();
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
