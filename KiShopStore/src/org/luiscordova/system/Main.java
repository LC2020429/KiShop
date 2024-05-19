/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package org.luiscordova.system;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.luiscordova.controller.CargoEmpleadoController;
import org.luiscordova.controller.ClienteVistaController;
import org.luiscordova.controller.CompraVistaController;
import org.luiscordova.controller.EmpleadosController;
import org.luiscordova.controller.MenuPrincipalController;
import org.luiscordova.controller.ProductosTiendaController;
import org.luiscordova.controller.ProgramadorViewController;
import org.luiscordova.controller.ProveedorVistaController;
import org.luiscordova.controller.TelefonoController;
import org.luiscordova.controller.TipoProductoVistaController;

/**
 *
 * @author Computadora
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("KiShop");
        menuPrincipalView();
        escenarioPrincipal.getIcons().add(new Image("/org/luiscordova/resource/Logo.png"));
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlname, int width, int height) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/luiscordova/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/luiscordova/view/" + fxmlname));

        escena = new Scene((AnchorPane) loader.load(file), width, height);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();
        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 500, 520);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuCargoEmpleadoView() {
        try {
            CargoEmpleadoController CargoEmpleadoView = (CargoEmpleadoController) cambiarEscena("CargoEmpleadoView.fxml", 1024, 575);
            CargoEmpleadoView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuClienteView() {
        try {
            ClienteVistaController menuClienteView = (ClienteVistaController) cambiarEscena("ClienteVista.fxml", 1024, 575);
            menuClienteView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuProveedorView() {
        try {
            ProveedorVistaController menuProveedoresView = (ProveedorVistaController) cambiarEscena("ProveedorVista.fxml", 1020, 572);
            menuProveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuCompraView() {
        try {
            CompraVistaController menuCompraView = (CompraVistaController) cambiarEscena("CompraVista.fxml", 1020, 572);
            menuCompraView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuTipoProductoView() {
        try {
            TipoProductoVistaController menuTipoProductoView = (TipoProductoVistaController) cambiarEscena("TipoProductoVista.fxml", 1020, 572);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void programadorView() {
        try {
            ProgramadorViewController prograView = (ProgramadorViewController) cambiarEscena("ProgramadorView.fxml", 1020, 572);
            prograView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void ProductoView() {
        try {
            ProductosTiendaController ProductoVista = (ProductosTiendaController) cambiarEscena("ProductoTiendaVista.fxml", 1200, 675);
            ProductoVista.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void EmpleadosView() {
        try {
            EmpleadosController EmpleadoVista = (EmpleadosController) cambiarEscena("EmpleadoView.fxml", 1020, 572);
            EmpleadoVista.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void TelefonosView() {
        try {
            TelefonoController EmpleadoVista = (TelefonoController) cambiarEscena("TelefonosVista.fxml", 1020, 572);
            EmpleadoVista.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
