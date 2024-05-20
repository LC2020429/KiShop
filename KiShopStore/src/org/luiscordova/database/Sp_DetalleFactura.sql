Use DBKiShop;
-- Detalle Factura
-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleFactura
    (IN v_codigoDetalleFactura INT, IN v_precioUnitario DECIMAL(10, 2), IN v_cantidad INT,
     IN v_numeroFactura INT, IN v_codigoProducto VARCHAR(15))
BEGIN
    INSERT INTO DetalleFactura (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
    VALUES (v_codigoDetalleFactura, v_precioUnitario, v_cantidad, v_numeroFactura, v_codigoProducto);
END $$
DELIMITER ;

-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleFactura()
BEGIN
    SELECT * FROM DetalleFactura;
END $$
DELIMITER ;

-- buscar
DELIMITER $$
CREATE PROCEDURE sp_ObtenerDetalleFacturaPorCodigo
    (IN v_codigoDetalleFactura INT)
BEGIN
    SELECT * FROM DetalleFactura WHERE codigoDetalleFactura = v_codigoDetalleFactura;
END $$
DELIMITER ;

-- actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarDetalleFactura
    (IN v_codigoDetalleFactura INT, IN v_precioUnitario DECIMAL(10, 2), IN v_cantidad INT,
     IN v_numeroFactura INT, IN v_codigoProducto VARCHAR(15))
BEGIN
    UPDATE DetalleFactura
    SET precioUnitario = v_precioUnitario, cantidad = v_cantidad, numeroFactura = v_numeroFactura, codigoProducto = v_codigoProducto
    WHERE codigoDetalleFactura = v_codigoDetalleFactura;
END $$
DELIMITER ;

-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleFactura
    (IN v_codigoDetalleFactura INT)
BEGIN
    DELETE FROM DetalleFactura WHERE codigoDetalleFactura = v_codigoDetalleFactura;
END $$
DELIMITER ;