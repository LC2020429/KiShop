Use DBKiShop;

-- Detalle Compra
-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleCompra
    (IN v_codigoDetalleCompra INT, IN v_costoUnitario DECIMAL(10, 2), IN v_cantidad INT,
     IN v_codigoProducto VARCHAR(15), IN v_numeroDocumento INT)
BEGIN
    INSERT INTO DetalleCompra (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento)
    VALUES (v_codigoDetalleCompra, v_costoUnitario, v_cantidad, v_codigoProducto, v_numeroDocumento);
END $$
DELIMITER ;

-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleCompra()
BEGIN
    SELECT * FROM DetalleCompra;
END $$
DELIMITER ;

-- busccar
DELIMITER $$
CREATE PROCEDURE sp_ObtenerDetalleCompraPorCodigo
    (IN v_codigoDetalleCompra INT)
BEGIN
    SELECT * FROM DetalleCompra WHERE codigoDetalleCompra = v_codigoDetalleCompra;
END $$
DELIMITER ;

-- actualizar 
DELIMITER $$
CREATE PROCEDURE sp_ActualizarDetalleCompra
    (IN v_codigoDetalleCompra INT, IN v_costoUnitario DECIMAL(10, 2), IN v_cantidad INT,
     IN v_codigoProducto VARCHAR(15), IN v_numeroDocumento INT)
BEGIN
    UPDATE DetalleCompra
    SET costoUnitario = v_costoUnitario, cantidad = v_cantidad, codigoProducto = v_codigoProducto, numeroDocumento = v_numeroDocumento
    WHERE codigoDetalleCompra = v_codigoDetalleCompra;
END $$
DELIMITER ;

-- eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleCompra
    (IN v_codigoDetalleCompra INT)
BEGIN
    -- Obtener el número de documento relacionado
    DECLARE v_numeroDocumento INT;
    SELECT numeroDocumento INTO v_numeroDocumento FROM DetalleCompra WHERE codigoDetalleCompra = v_codigoDetalleCompra;

    -- Actualizar los valores a NULL en la tabla DetalleCompra
    UPDATE DetalleCompra
    SET 
        codigoProducto = NULL,
        numeroDocumento = NULL,
        costoUnitario = NULL,
        cantidad = NULL
    WHERE codigoDetalleCompra = v_codigoDetalleCompra;

    -- Actualizar los valores a NULL en la tabla Compras
    UPDATE Compras
    SET descripcion = NULL,
        totalDocumento = NULL
    WHERE numeroDocumento = v_numeroDocumento;
    
    -- Eliminar el registro de DetalleCompra
	DELETE FROM DetalleCompra WHERE codigoDetalleCompra = v_codigoDetalleCompra;

END $$
DELIMITER ;

CALL sp_AgregarDetalleCompra(1, 10.99, 5, 'P001', 1);
CALL sp_AgregarDetalleCompra(2, 7.5, 2, 'P002', 2);
CALL sp_AgregarDetalleCompra(3, 15.75, 3, 'P003', 2);
CALL sp_EliminarDetalleCompra(3);

call sp_ListarDetalleCompra ();