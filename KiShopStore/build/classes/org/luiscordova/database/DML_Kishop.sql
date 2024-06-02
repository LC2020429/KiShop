-- DML
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

-- Proveedores
DELIMITER $$
CREATE PROCEDURE sp_AgregarProveedor(
    IN v_codigoProveedor INT,
    IN v_NITProveedor VARCHAR(10),
    IN v_nombresProveedor VARCHAR(60),
    IN v_apellidosProveedor VARCHAR(60),
    IN v_direccionProveedor VARCHAR(150),
    IN v_razonSocial VARCHAR(60),
    IN v_contactoPrincipal VARCHAR(100),
    IN v_paginaWeb VARCHAR(50)
)
BEGIN
    INSERT INTO Proveedores (codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    VALUES (v_codigoProveedor, v_NITProveedor, v_nombresProveedor, v_apellidosProveedor, v_direccionProveedor, v_razonSocial, v_contactoPrincipal, v_paginaWeb);
END $$

DELIMITER ;
-- listar
DELIMITER $$
CREATE PROCEDURE sp_ListarProveedores()
BEGIN
    SELECT * FROM Proveedores;
END $$
DELIMITER ;
-- buscar
DELIMITER $$
CREATE PROCEDURE sp_BuscarProveedorPorCodigo(
    IN v_codigoProveedor INT
)
BEGIN
    SELECT * FROM Proveedores WHERE codigoProveedor = v_codigoProveedor;
END $$
DELIMITER ;
-- actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarProveedor(
    IN v_codigoProveedor INT,
    IN v_NITProveedor VARCHAR(10),
    IN v_nombresProveedor VARCHAR(60),
    IN v_apellidosProveedor VARCHAR(60),
    IN v_direccionProveedor VARCHAR(150),
    IN v_razonSocial VARCHAR(60),
    IN v_contactoPrincipal VARCHAR(100),
    IN v_paginaWeb VARCHAR(50)
)
BEGIN
    UPDATE Proveedores
    SET NITProveedor = v_NITProveedor,
        nombresProveedor = v_nombresProveedor,
        apellidosProveedor = v_apellidosProveedor,
        direccionProveedor = v_direccionProveedor,
        razonSocial = v_razonSocial,
        contactoPrincipal = v_contactoPrincipal,
        paginaWeb = v_paginaWeb
    WHERE codigoProveedor = v_codigoProveedor;
END $$
DELIMITER ;
-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarProveedor(
    IN v_codigoProveedor INT
)
BEGIN
    DELETE FROM Proveedores WHERE codigoProveedor = v_codigoProveedor;
END $$
DELIMITER ;

CALL sp_AgregarProveedor(1, '1234567890', 'Juan', 'Pérez', 'Calle 123', 'Proveedor ABC', 'Juan Pérez', 'www.proveedorabc.com');
CALL sp_AgregarProveedor(2, '9876543210', 'María', 'Gómez', 'Avenida 456', 'Proveedor XYZ', 'María Gómez', 'www.proveedorxyz.com');
CALL sp_AgregarProveedor(3, '6543210987', 'Pedro', 'López', 'Carrera 789', 'Proveedor 123', 'Pedro López', 'www.proveedor123.com');
CALL sp_AgregarProveedor(4, '0123456789', 'Laura', 'Torres', 'Calle 456', 'Proveedor XYZ', 'Laura Torres', 'www.proveedorxyz.com');
CALL sp_AgregarProveedor(5, '9876543210', 'Carlos', 'García', 'Avenida 789', 'Proveedor ABC', 'Carlos García', 'www.proveedorabc.com');

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

-- COMPRAS
-- Agregar
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

call sp_EliminarCompra(3);

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


DELIMITER $$
CREATE PROCEDURE sp_PrepararEliminacion
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

DELIMITER $$
CREATE PROCEDURE sp_EliminarFactura
(IN v_numeroFactura INT)
BEGIN
    CALL sp_PrepararEliminacion(v_numeroFactura);
END $$
DELIMITER ;


CALL sp_AgregarFactura(1001, 'Pendiente', 150.99, '2021-10-15', 1, 1);
CALL sp_AgregarFactura(1002, 'Pagada', 99.50, '2021-10-16', 2, 1);
CALL sp_AgregarFactura(1003, 'Pendiente', 250.75, '2021-10-17', 3, 2);
