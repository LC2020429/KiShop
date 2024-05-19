use DBKiShop;
-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarTelefonoProveedor
    (IN v_codigoTelefonoProveedor INT, IN v_numeroPrincipal VARCHAR(8), IN v_numeroSecundario VARCHAR(8),
     IN v_observaciones VARCHAR(45), IN v_codigoProveedor INT)
BEGIN
    INSERT INTO TelefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    VALUES (v_codigoTelefonoProveedor, v_numeroPrincipal, v_numeroSecundario, v_observaciones, v_codigoProveedor);
END $$
DELIMITER ;

-- listar o mostrar
DELIMITER $$
CREATE PROCEDURE sp_ListarTelefonosProveedor()
BEGIN
    SELECT * FROM TelefonoProveedor;
END $$
DELIMITER ;

-- buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarTelefonoProveedorPorCodigo
    (IN v_codigoTelefonoProveedor INT)
BEGIN
    SELECT * FROM TelefonoProveedor WHERE codigoTelefonoProveedor = v_codigoTelefonoProveedor;
END $$
DELIMITER ;

-- actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarTelefonoProveedor
    (IN v_codigoTelefonoProveedor INT, IN v_numeroPrincipal VARCHAR(8), IN v_numeroSecundario VARCHAR(8),
     IN v_observaciones VARCHAR(45), IN v_codigoProveedor INT)
BEGIN
    UPDATE TelefonoProveedor
    SET numeroPrincipal = v_numeroPrincipal, numeroSecundario = v_numeroSecundario,
        observaciones = v_observaciones, codigoProveedor = v_codigoProveedor
    WHERE codigoTelefonoProveedor = v_codigoTelefonoProveedor;
END $$
DELIMITER ;

-- eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarTelefonoProveedor
    (IN v_codigoTelefonoProveedor INT)
BEGIN
    DELETE FROM TelefonoProveedor WHERE codigoTelefonoProveedor = v_codigoTelefonoProveedor;
END $$
DELIMITER ;

CALL sp_AgregarTelefonoProveedor(1, '12345678', '98765432', 'Teléfono principal', 1);
CALL sp_AgregarTelefonoProveedor(2, '11111111', '22222222', 'Teléfono secundario', 2);
CALL sp_AgregarTelefonoProveedor(3, '99999999', '88888888', 'Teléfono adicional', 3);