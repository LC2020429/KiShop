use DBKiShop;
-- Procedimientos almacenados para Productos
-- buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarProducto(
    IN v_codigoProducto VARCHAR(15)
)
BEGIN
    SELECT *
    FROM Productos
    WHERE codigoProducto = v_codigoProducto;
END $$
DELIMITER ;

-- listar 
DELIMITER $$
CREATE PROCEDURE sp_ListarProductos()
BEGIN
    SELECT *
    FROM Productos;
END $$
DELIMITER ;

-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarProducto(
    IN v_codigoProducto VARCHAR(15),
    IN v_descripcionProducto VARCHAR(45),
    IN v_precioUnitario DECIMAL(10, 2),
    IN v_precioDocena DECIMAL(10, 2),
    IN v_precioMayor DECIMAL(10, 2),
    IN v_imagenProducto VARCHAR(45),
    IN v_existencia INT,
    IN v_codigoTipoProducto INT,
    IN v_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos (codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor)
    VALUES (v_codigoProducto, v_descripcionProducto, v_precioUnitario, v_precioDocena, v_precioMayor, v_imagenProducto, v_existencia, v_codigoTipoProducto, v_codigoProveedor);
END $$
DELIMITER ;

-- editar
DELIMITER $$
CREATE PROCEDURE sp_EditarProducto(
    IN v_codigoProducto VARCHAR(15),
    IN v_descripcionProducto VARCHAR(45),
    IN v_precioUnitario DECIMAL(10, 2),
    IN v_precioDocena DECIMAL(10, 2),
    IN v_precioMayor DECIMAL(10, 2),
    IN v_imagenProducto VARCHAR(45),
    IN v_existencia INT,
    IN v_codigoTipoProducto INT,
    IN v_codigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = v_descripcionProducto,
        precioUnitario = v_precioUnitario,
        precioDocena = v_precioDocena,
        precioMayor = v_precioMayor,
        imagenProducto = v_imagenProducto,
        existencia = v_existencia,
        codigoTipoProducto = v_codigoTipoProducto,
        codigoProveedor = v_codigoProveedor
    WHERE codigoProducto = v_codigoProducto;
END $$
DELIMITER ;

-- eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarProducto(
    IN v_codigoProducto VARCHAR(15)
)
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = v_codigoProducto;
END $$
DELIMITER ;

CALL sp_AgregarProducto('P001', 'Camiseta', 25.99, 199.99, 499.99, 'camiseta.jpg', 50, 1, 1);
CALL sp_AgregarProducto('P002', 'Pantalón', 39.99, 299.99, 699.99, 'pantalon.jpg', 30, 1, 2);
CALL sp_AgregarProducto('P003', 'Zapatos', 59.99, 499.99, 899.99, 'zapatos.jpg', 20, 2, 2);