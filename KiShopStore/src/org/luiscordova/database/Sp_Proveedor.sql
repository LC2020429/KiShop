-- Proveedores
-- agregar

use DBKiShop;
DELIMITER $$
CREATE PROCEDURE sp_AgregarProveedor(
    IN v_codigoProveedor INT,
    IN v_NITProveedor VARCHAR(10),
    IN v_nombresProveedor VARCHAR(60),
    IN v_apellidosProveedor VARCHAR(60),
    IN v_direccionProveedor VARCHAR(150),
    IN v_razonSocial VARCHAR(60),
    IN v_contactoPrincipal VARCHAR(100),
    IN v_paginaWeb VARCHAR(50)
)
BEGIN
    INSERT INTO Proveedores (codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    VALUES (v_codigoProveedor, v_NITProveedor, v_nombresProveedor, v_apellidosProveedor, v_direccionProveedor, v_razonSocial, v_contactoPrincipal, v_paginaWeb);
END $$

DELIMITER ;
-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarProveedores()
BEGIN
    SELECT * FROM Proveedores;
END $$
DELIMITER ;
-- buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarProveedorPorCodigo(
    IN v_codigoProveedor INT
)
BEGIN
    SELECT * FROM Proveedores WHERE codigoProveedor = v_codigoProveedor;
END $$
DELIMITER ;
-- actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarProveedor(
    IN v_codigoProveedor INT,
    IN v_NITProveedor VARCHAR(10),
    IN v_nombresProveedor VARCHAR(60),
    IN v_apellidosProveedor VARCHAR(60),
    IN v_direccionProveedor VARCHAR(150),
    IN v_razonSocial VARCHAR(60),
    IN v_contactoPrincipal VARCHAR(100),
    IN v_paginaWeb VARCHAR(50)
)
BEGIN
    UPDATE Proveedores
    SET NITProveedor = v_NITProveedor,
        nombresProveedor = v_nombresProveedor,
        apellidosProveedor = v_apellidosProveedor,
        direccionProveedor = v_direccionProveedor,
        razonSocial = v_razonSocial,
        contactoPrincipal = v_contactoPrincipal,
        paginaWeb = v_paginaWeb
    WHERE codigoProveedor = v_codigoProveedor;
END $$
DELIMITER ;
-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarProveedor(
    IN v_codigoProveedor INT
)
BEGIN
    DELETE FROM Proveedores WHERE codigoProveedor = v_codigoProveedor;
END $$
DELIMITER ;

CALL sp_AgregarProveedor(1, '1234567890', 'Juan', 'Pérez', 'Calle 123', 'Proveedor ABC', 'Juan Pérez', 'www.proveedorabc.com');
CALL sp_AgregarProveedor(2, '9876543210', 'María', 'Gómez', 'Avenida 456', 'Proveedor XYZ', 'María Gómez', 'www.proveedorxyz.com');
CALL sp_AgregarProveedor(3, '6543210987', 'Pedro', 'López', 'Carrera 789', 'Proveedor 123', 'Pedro López', 'www.proveedor123.com');
CALL sp_AgregarProveedor(4, '0123456789', 'Laura', 'Torres', 'Calle 456', 'Proveedor XYZ', 'Laura Torres', 'www.proveedorxyz.com');
CALL sp_AgregarProveedor(5, '9876543210', 'Carlos', 'García', 'Avenida 789', 'Proveedor ABC', 'Carlos García', 'www.proveedorabc.com');