DROP DATABASE IF EXISTS DBKiShop;
CREATE DATABASE DBKiShop;
USE DBKiShop;
SET GLOBAL time_zone = '-06:00';

CREATE TABLE TipoProducto (
    codigoTipoProducto INT,
    descripcion VARCHAR(45),
    PRIMARY KEY (codigoTipoProducto)
);

CREATE TABLE Proveedores (
    codigoProveedor INT,
    NITProveedor VARCHAR(10),
    nombresProveedor VARCHAR(60),
    apellidosProveedor VARCHAR(60),
    direccionProveedor VARCHAR(150),
    razonSocial VARCHAR(60),
    contactoPrincipal VARCHAR(100),
    paginaWeb VARCHAR(50),
    PRIMARY KEY (codigoProveedor)
);

CREATE TABLE Compras (
    numeroDocumento INT,
    fechaDocumento DATE,
    descripcion VARCHAR(60),
    totalDocumento DECIMAL(10, 2),
    PRIMARY KEY (numeroDocumento)
);

CREATE TABLE Clientes (
    codigoCliente INT,
    NITCliente VARCHAR(10),
    nombresCliente VARCHAR(50),
    apellidosCliente VARCHAR(50),
    direccionCliente VARCHAR(150),
    telefonoCliente VARCHAR(8),
    correoCliente VARCHAR(45),
    PRIMARY KEY (codigoCliente)
);

CREATE TABLE CargoEmpleado (
    codigoCargoEmpleado INT,
    nombreCargo VARCHAR(45),
    descripcionCargo VARCHAR(45),
    PRIMARY KEY (codigoCargoEmpleado)
);

CREATE TABLE Productos (
    codigoProducto VARCHAR(15),
    descripcionProducto VARCHAR(45),
    precioUnitario DECIMAL(10, 2),
    precioDocena DECIMAL(10, 2),
    precioMayor DECIMAL(10, 2),
    imagenProducto VARCHAR(45),
    existencia INT,
    codigoTipoProducto INT,
    codigoProveedor INT,
    PRIMARY KEY (codigoProducto),
    FOREIGN KEY (codigoTipoProducto) REFERENCES TipoProducto(codigoTipoProducto),
    FOREIGN KEY (codigoProveedor) REFERENCES Proveedores(codigoProveedor)
);

CREATE TABLE TelefonoProveedor (
    codigoTelefonoProveedor INT,
    numeroPrincipal VARCHAR(8),
    numeroSecundario VARCHAR(8),
    observaciones VARCHAR(45),
    codigoProveedor INT,
    PRIMARY KEY (codigoTelefonoProveedor),
    FOREIGN KEY (codigoProveedor) REFERENCES Proveedores(codigoProveedor)
);

CREATE TABLE DetalleCompra (
    codigoDetalleCompra INT,
    costoUnitario DECIMAL(10, 2),
    cantidad INT,
    codigoProducto VARCHAR(15),
    numeroDocumento INT,
    PRIMARY KEY (codigoDetalleCompra),
    FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto),
    FOREIGN KEY (numeroDocumento) REFERENCES Compras(numeroDocumento)
);

CREATE TABLE Empleados (
    codigoEmpleado INT,
    nombresEmpleado VARCHAR(50),
    apellidosEmpleado VARCHAR(50),
    sueldo DECIMAL(10, 2),
    direccion VARCHAR(150),
    turno VARCHAR(15),
    codigoCargoEmpleado INT,
    PRIMARY KEY (codigoEmpleado),
    FOREIGN KEY (codigoCargoEmpleado) REFERENCES CargoEmpleado(codigoCargoEmpleado)
);

CREATE TABLE Factura (
    numeroFactura INT,
    estado VARCHAR(50),
    totalFactura DECIMAL(10, 2),
    fechaFactura VARCHAR(45),
    codigoCliente INT,
    codigoEmpleado INT,
    PRIMARY KEY (numeroFactura),
    FOREIGN KEY (codigoCliente) REFERENCES Clientes(codigoCliente),
    FOREIGN KEY (codigoEmpleado) REFERENCES Empleados(codigoEmpleado)
);

CREATE TABLE DetalleFactura (
    codigoDetalleFactura INT,
    precioUnitario DECIMAL(10, 2),
    cantidad INT,
    numeroFactura INT,
    codigoProducto VARCHAR(15),
    PRIMARY KEY (codigoDetalleFactura),
    FOREIGN KEY (numeroFactura) REFERENCES Factura(numeroFactura),
    FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto)
);

DELIMITER $$
CREATE TRIGGER CalculoPrecios
AFTER INSERT ON DetalleCompra
FOR EACH ROW
BEGIN
    UPDATE Productos
    SET precioUnitario = 0.00,
        precioDocena = 0.00,
        precioMayor = 0.00
    WHERE codigoProducto = (SELECT codigoProducto FROM Compras WHERE numeroDocumento = NEW.numeroDocumento);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER ActualizarExistencia
AFTER INSERT ON DetalleCompra
FOR EACH ROW
BEGIN
    UPDATE Productos
    SET existencia = existencia + NEW.cantidad
    WHERE codigoProducto = NEW.codigoProducto;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER AplicarGanancia
AFTER UPDATE ON DetalleCompra
FOR EACH ROW
BEGIN
    DECLARE total DECIMAL(10, 2);
    
    SET total = NEW.costoUnitario * NEW.cantidad;

    UPDATE Productos
    SET precioUnitario = total * 1.4,
        precioDocena = total * 1.35 / 12,
        precioMayor = total * 1.25 / 20
    WHERE codigoProducto = NEW.codigoProducto;
END $$
DELIMITER ;