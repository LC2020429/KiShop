use DBKiShop;
-- Empleados
-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarEmpleado
    (IN v_codigoEmpleado INT, IN v_nombresEmpleado VARCHAR(50), IN v_apellidosEmpleado VARCHAR(50),
     IN v_sueldo DECIMAL(10, 2), IN v_direccion VARCHAR(150), IN v_turno VARCHAR(15),
     IN v_codigoCargoEmpleado INT)
BEGIN
    INSERT INTO Empleados (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    VALUES (v_codigoEmpleado, v_nombresEmpleado, v_apellidosEmpleado, v_sueldo, v_direccion, v_turno, v_codigoCargoEmpleado);
END $$
DELIMITER ;

-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados()
BEGIN
    SELECT * FROM Empleados;
END $$
DELIMITER ;

-- buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleadoPorCodigo (IN v_codigoEmpleado INT)
BEGIN
    SELECT * FROM Empleados WHERE codigoEmpleado = v_codigoEmpleado;
END $$
DELIMITER ;

-- actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarEmpleado
    (IN v_codigoEmpleado INT, IN v_nombresEmpleado VARCHAR(50), IN v_apellidosEmpleado VARCHAR(50),
     IN v_sueldo DECIMAL(10, 2), IN v_direccion VARCHAR(150), IN v_turno VARCHAR(15),
     IN v_codigoCargoEmpleado INT)
BEGIN
    UPDATE Empleados
    SET nombresEmpleado = v_nombresEmpleado, apellidosEmpleado = v_apellidosEmpleado,
        sueldo = v_sueldo, direccion = v_direccion, turno = v_turno, codigoCargoEmpleado = v_codigoCargoEmpleado
    WHERE codigoEmpleado = v_codigoEmpleado;
END $$
DELIMITER ;

-- eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleado
    (IN v_codigoEmpleado INT)
BEGIN
    DELETE FROM Empleados WHERE codigoEmpleado = v_codigoEmpleado;
END $$
DELIMITER ;

CALL sp_AgregarEmpleado(1, 'John', 'Doe', 5000.00, '123 Main St', 'Mañana', 1);
CALL sp_AgregarEmpleado(2, 'Jane', 'Smith', 6000.00, '456 Elm St', 'Tarde', 2);
CALL sp_AgregarEmpleado(3, 'Michael', 'Johnson', 5500.00, '789 Oak St', 'Noche', 3);