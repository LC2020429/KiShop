use DBKiShop;

-- Procedimientos almacenados
DELIMITER $$
CREATE PROCEDURE sp_AgregarCargoEmpleado(
    IN v_codigoCargoEmpleado INT,
    IN v_nombreCargo VARCHAR(45),
    IN v_descripcionCargo VARCHAR(45)
)
BEGIN
    INSERT INTO CargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo)
    VALUES (v_codigoCargoEmpleado, v_nombreCargo, v_descripcionCargo);
END $$
DELIMITER ;

-- Listar
DELIMITER $$
CREATE PROCEDURE sp_ListarCargosEmpleado()
BEGIN
    SELECT * FROM CargoEmpleado;
END $$
DELIMITER ;

-- Buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarCargoEmpleadoPorCodigo(
    IN v_codigoCargoEmpleado INT
)
BEGIN
    SELECT * FROM CargoEmpleado WHERE codigoCargoEmpleado = v_codigoCargoEmpleado;
END $$
DELIMITER ;

-- Actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarCargoEmpleado(
    IN v_codigoCargoEmpleado INT,
    IN v_nombreCargo VARCHAR(45),
    IN v_descripcionCargo VARCHAR(45)
)
BEGIN
    UPDATE CargoEmpleado
    SET nombreCargo = v_nombreCargo,
        descripcionCargo = v_descripcionCargo
    WHERE codigoCargoEmpleado = v_codigoCargoEmpleado;
END $$
DELIMITER ;

-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarCargoEmpleado(
    IN v_codigoCargoEmpleado INT
)
BEGIN
    DELETE FROM CargoEmpleado WHERE codigoCargoEmpleado = v_codigoCargoEmpleado;
END $$
DELIMITER ;

CALL sp_AgregarCargoEmpleado(1, 'Gerente', 'Responsable de la gestion del equipo');
CALL sp_AgregarCargoEmpleado(2, 'Analista', 'Encargado de analizar y procesar datos');
CALL sp_AgregarCargoEmpleado(3, 'Desarrollador', 'Encargado de aplicaciones');
CALL sp_AgregarCargoEmpleado(4, 'Contador', 'Responsable de llevar la contabilidad');
CALL sp_AgregarCargoEmpleado(5, 'Asistente Administrativo', 'Brinda apoyo en tareas');