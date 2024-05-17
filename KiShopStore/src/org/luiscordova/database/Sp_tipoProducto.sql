use DBKiShop;
-- TIPOPRODUCTO
-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarTipoProducto( IN v_codigoTipoProducto INT, IN v_descripcion VARCHAR(45) )
BEGIN
    INSERT INTO TipoProducto (codigoTipoProducto, descripcion)
    VALUES (v_codigoTipoProducto, v_descripcion);
END $$
DELIMITER ;
-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarTipoProducto()
BEGIN
    SELECT * FROM TipoProducto;
END $$
DELIMITER ;
-- buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarTipoProductoPorID( IN v_codigoTipoProducto INT )
BEGIN
    SELECT * FROM TipoProducto WHERE codigoTipoProducto = v_codigoTipoProducto;
END $$
DELIMITER ;
-- actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarTipoProducto( IN v_codigoTipoProducto INT, IN v_descripcion VARCHAR(45) )
BEGIN
    UPDATE TipoProducto
    SET descripcion = v_descripcion
    WHERE codigoTipoProducto = v_codigoTipoProducto;
END $$
DELIMITER ;
-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarTipoProducto( IN v_codigoTipoProducto INT)
BEGIN
    DELETE FROM TipoProducto WHERE codigoTipoProducto = v_codigoTipoProducto;
END $$
DELIMITER ;


CALL sp_AgregarTipoProducto(1, 'Electronica');
CALL sp_AgregarTipoProducto(2, 'Ropa');
CALL sp_AgregarTipoProducto(3, 'Alimentos');
CALL sp_AgregarTipoProducto(4, 'Muebles');
CALL sp_AgregarTipoProducto(5, 'Hogar');
