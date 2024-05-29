Use DBKiShop;

-- Factura
-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarFactura
    (IN v_numeroFactura INT, IN v_estado VARCHAR(50), IN v_totalFactura DECIMAL(10, 2),
     IN v_fechaFactura VARCHAR(45), IN v_codigoCliente INT, IN v_codigoEmpleado INT)
BEGIN
    INSERT INTO Factura (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado)
    VALUES (v_numeroFactura, v_estado, v_totalFactura, v_fechaFactura, v_codigoCliente, v_codigoEmpleado);
END $$
DELIMITER ;

-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarFactura()
BEGIN
    SELECT * FROM Factura;
END $$
DELIMITER ;

-- buscar
DELIMITER $$
CREATE PROCEDURE sp_ObtenerFacturaPorNumero
    (IN v_numeroFactura INT)
BEGIN
    SELECT * FROM Factura WHERE numeroFactura = v_numeroFactura;
END $$
DELIMITER ;

-- actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarFactura
    (IN v_numeroFactura INT, IN v_estado VARCHAR(50), IN v_totalFactura DECIMAL(10, 2),
     IN v_fechaFactura VARCHAR(45), IN v_codigoCliente INT, IN v_codigoEmpleado INT)
BEGIN
    UPDATE Factura
    SET estado = v_estado, totalFactura = v_totalFactura, fechaFactura = v_fechaFactura,
        codigoCliente = v_codigoCliente, codigoEmpleado = v_codigoEmpleado
    WHERE numeroFactura = v_numeroFactura;
END $$
DELIMITER ;

-- eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarFactura
    (IN v_numeroFactura INT)
BEGIN
    -- Obtener el código de cliente relacionado
    DECLARE v_codigoCliente INT;
    SELECT codigoCliente INTO v_codigoCliente FROM Factura WHERE numeroFactura = v_numeroFactura;

    -- Actualizar los valores a NULL en la tabla Factura
    UPDATE Factura
    SET estado = NULL,
        totalFactura = NULL,
        fechaFactura = NULL,
        codigoCliente = NULL,
        codigoEmpleado = NULL
    WHERE numeroFactura = v_numeroFactura;

    -- Actualizar el valor a NULL en la tabla Clientes
    IF v_codigoCliente IS NOT NULL THEN
    -- Actualizar el valor a NULL en la tabla Clientes
    UPDATE Clientes
    SET codigoCliente = NULL
    WHERE codigoCliente = v_codigoCliente;
	END IF;


    -- Eliminar el registro de Factura
    DELETE FROM Factura WHERE numeroFactura = v_numeroFactura;

END $$
DELIMITER ;
drop procedure sp_EliminarFactura;
call sp_EliminarFactura(1003);

CALL sp_AgregarFactura(1001, 'Pendiente', 150.99, '2021-10-15', 1, 1);
CALL sp_AgregarFactura(1002, 'Pagada', 99.50, '2021-10-16', 2, 1);
CALL sp_AgregarFactura(1003, 'Pendiente', 250.75, '2021-10-17', 3, 2);


call sp_ListarFactura();