use DBKiShop;
-- Procedimientos almacenados
DELIMITER $$
CREATE PROCEDURE sp_AgregarCliente( in _codigoCliente int, in _NITCliente VARCHAR(10), in _nombresCliente VARCHAR(50), 
	in _apellidosCliente VARCHAR(50), in _direccionCliente VARCHAR(150), in _telefonoCliente VARCHAR(8),in _correoCliente VARCHAR(45) )
BEGIN
    INSERT INTO Clientes (codigoCliente, NITCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
    VALUES (_codigoCliente, _NITCliente, _nombresCliente, _apellidosCliente, _direccionCliente, _telefonoCliente, _correoCliente);
END $$
DELIMITER ;

-- Listar
DELIMITER $$
CREATE PROCEDURE sp_ListarClientes()
BEGIN
    SELECT * FROM Clientes;
END $$
DELIMITER ;

-- Buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarClientePorCodigo(
    IN v_codigoCliente INT
)
BEGIN
    SELECT * FROM Clientes WHERE codigoCliente = v_codigoCliente;
END $$
DELIMITER ;

-- Actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarCliente(in _codigoCliente INT, in _NITCliente VARCHAR(10), in _nombresCliente VARCHAR(50), in _apellidosCliente VARCHAR(50),
    in _direccionCliente VARCHAR(150), in _telefonoCliente VARCHAR(8), in _correoCliente VARCHAR(45) )
BEGIN
    UPDATE Clientes
    SET codigoCliente = _codigoCliente,
		NITCliente = _NITCliente,
        nombresCliente = _nombresCliente,
        apellidosCliente = _apellidosCliente,
        direccionCliente = _direccionCliente,
        telefonoCliente = _telefonoCliente,
        correoCliente = _correoCliente
    WHERE codigoCliente = _codigoCliente;
END $$
DELIMITER ;
call sp_ActualizarCliente('2','Jose','Figueroa','Amatitlan','1265289635741','98562471','jose@outlook.com');
-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarCliente(
    IN _codigoCliente INT
)
BEGIN
    DELETE FROM Clientes WHERE codigoCliente = _codigoCliente;
END $$
DELIMITER ;


CALL sp_AgregarCliente(1, '1234567890', 'Juan', 'Perez', 'Calle 123', '12345678', 'juan@example.com');
CALL sp_AgregarCliente(2, '9876543210', 'Maria', 'Gomez', 'Avenida 456', '87654321', 'maria@example.com');
CALL sp_AgregarCliente(3, '6543210987', 'Pedro', 'Lopez', 'Carrera 789', '45678901', 'pedro@example.com');
CALL sp_AgregarCliente(4, '0123456789', 'Laura', 'Torres', 'Calle 456', '89012345', 'laura@example.com');
CALL sp_AgregarCliente(5, '9876543210', 'Carlos', 'Garcia', 'Avenida 789', '67890123', 'carlos@example.com');