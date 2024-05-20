package org.luiscordova.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.luiscordova.system.Main;

/**
 *
 * @author Luis Cordova
 */
public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnMenuProgramador;
    @FXML
    MenuItem btnMenuProductos;
     @FXML
    MenuItem btnMenuEmpleados;
    @FXML
    MenuItem btnMenuProveedores;
    @FXML
    MenuItem btnMenuTipoProducto;
    @FXML
    MenuItem btnMenuCargoEmpleado;
    @FXML
    MenuItem btnMenuCompras;
    @FXML
     MenuItem btnMenuTelefonos;
    @FXML
     MenuItem btnMenuDetalleCompra;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // getter and setters
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public MenuItem getBtnMenuCompras() {
        return btnMenuCompras;
    }

    public void setBtnMenuCompras(MenuItem btnMenuCompras) {
        this.btnMenuCompras = btnMenuCompras;
    }

    public MenuItem getBtnMenuClientes() {
        return btnMenuClientes;
    }

    public void setBtnMenuClientes(MenuItem btnMenuClientes) {
        this.btnMenuClientes = btnMenuClientes;
    }

    public MenuItem getBtnMenuCargoEmpleado() {
        return btnMenuCargoEmpleado;
    }

    public void setBtnMenuCargoEmpleado(MenuItem btnMenuCargoEmpleado) {
        this.btnMenuCargoEmpleado = btnMenuCargoEmpleado;
    }

    public MenuItem getBtnMenuProgramador() {
        return btnMenuProgramador;
    }

    public void setBtnMenuProgramador(MenuItem btnMenuProgramador) {
        this.btnMenuProgramador = btnMenuProgramador;
    }

    public MenuItem getBtnMenuProveedores() {
        return btnMenuProveedores;
    }

    public void setBtnMenuProveedores(MenuItem btnMenuProveedores) {
        this.btnMenuProveedores = btnMenuProveedores;
    }

    public MenuItem getBtnMenuTipoProducto() {
        return btnMenuTipoProducto;
    }

    public void setBtnMenuTipoProducto(MenuItem btnMenuTipoProducto) {
        this.btnMenuTipoProducto = btnMenuTipoProducto;
    }

    public MenuItem getBtnMenuProductos() {
        return btnMenuProductos;
    }

    public void setBtnMenuProductos(MenuItem btnMenuProductos) {
        this.btnMenuProductos = btnMenuProductos;
    }

    public MenuItem getBtnMenuEmpleados() {
        return btnMenuEmpleados;
    }

    public void setBtnMenuEmpleados(MenuItem btnMenuEmpleados) {
        this.btnMenuEmpleados = btnMenuEmpleados;
    }    

    public MenuItem getBtnMenuTelefonos() {
        return btnMenuTelefonos;
    }

    public void setBtnMenuTelefonos(MenuItem btnMenuTelefonos) {
        this.btnMenuTelefonos = btnMenuTelefonos;
    }

    public MenuItem getBtnMenuDetalleCompra() {
        return btnMenuDetalleCompra;
    }

    public void setBtnMenuDetalleCompra(MenuItem btnMenuDetalleCompra) {
        this.btnMenuDetalleCompra = btnMenuDetalleCompra;
    }

    // Mandamos a la vista
    public void menuClientesView() {
        escenarioPrincipal.menuClienteView();
    }

    public void menuProveedoresView() {
        escenarioPrincipal.menuProveedorView();
    }

    public void menuTipoProductoView() {
        escenarioPrincipal.menuTipoProductoView();
    }

    public void menuCargoEmpleadoView() {
        escenarioPrincipal.menuCargoEmpleadoView();
    }

    public void menuCompraView() {
        escenarioPrincipal.menuCompraView();
    }
    public void menuProductoView() {
        escenarioPrincipal.ProductoView();
    }
     public void menuEmpleadoView() {
        escenarioPrincipal.EmpleadosView();
    }

     public void menuTelefonoView() {
        escenarioPrincipal.TelefonosView();
    }
     
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClienteView();
        }
        if (event.getSource() == btnMenuProgramador) {
            escenarioPrincipal.programadorView();
        }
        if (event.getSource() == btnMenuProveedores) {
            escenarioPrincipal.menuProveedorView();
        }
        if (event.getSource() == btnMenuTipoProducto) {
            escenarioPrincipal.menuTipoProductoView();
        }
        if (event.getSource() == btnMenuCargoEmpleado) {
            escenarioPrincipal.menuCargoEmpleadoView();
        }
        if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.menuCompraView();
        }
        if (event.getSource() == btnMenuProductos) {
            escenarioPrincipal.ProductoView();
        }
        if (event.getSource() == btnMenuEmpleados) {
            escenarioPrincipal.EmpleadosView();
        }
         if (event.getSource() == btnMenuTelefonos) {
            escenarioPrincipal.TelefonosView();
        }
         if (event.getSource() == btnMenuDetalleCompra) {
            escenarioPrincipal.DetalleCompraView();
        }
    }
}
