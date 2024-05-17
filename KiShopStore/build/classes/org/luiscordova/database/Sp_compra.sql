-- COMPRAS
-- Agregar
use DBKiShop;
DELIMITER $$
CREATE PROCEDURE sp_AgregarCompra(
    IN v_numeroDocumento INT,
    IN v_fechaDocumento DATE,
    IN v_descripcion VARCHAR(60),
    IN v_totalDocumento DECIMAL(10, 2)
)
BEGIN
    INSERT INTO Compras (numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    VALUES (v_numeroDocumento, v_fechaDocumento, v_descripcion, v_totalDocumento);
END $$
DELIMITER ;

-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarCompras()
BEGIN
    SELECT * FROM Compras;
END $$
DELIMITER ;

-- Buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarCompraPorNumeroDocumento(
    IN v_numeroDocumento INT
)
BEGIN
    SELECT * FROM Compras WHERE numeroDocumento = v_numeroDocumento;
END $$
DELIMITER ;

-- Actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarCompra(
    IN v_numeroDocumento INT,
    IN v_fechaDocumento DATE,
    IN v_descripcion VARCHAR(60),
    IN v_totalDocumento DECIMAL(10, 2)
)
BEGIN
    UPDATE Compras
    SET fechaDocumento = v_fechaDocumento,
        descripcion = v_descripcion,
        totalDocumento = v_totalDocumento
    WHERE numeroDocumento = v_numeroDocumento;
END $$
DELIMITER ;

-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarCompra(
    IN v_numeroDocumento INT
)
BEGIN
    DELETE FROM Compras WHERE numeroDocumento = v_numeroDocumento;
END $$
DELIMITER ;
CALL sp_AgregarCompra(1, '2022-01-01', 'Compra de productos electrónicos', 1500.00);
CALL sp_AgregarCompra(2, '2022-02-15', 'Compra de ropa para la temporada', 500.00);
CALL sp_AgregarCompra(3, '2022-03-10', 'Compra de alimentos para la despensa', 200.00);
CALL sp_AgregarCompra(4, '2022-04-20', 'Compra de muebles para la oficina', 1000.00);
CALL sp_AgregarCompra(5, '2022-05-05', 'Compra de artículos para el hogar', 300.00);