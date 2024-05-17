drop database if exists DBKiShop;
create database DBKiShop;
use DBKiShop;
set global time_zone = '-06:00';


create table TipoProducto(
codigoTipoProducto int,
descripcion varchar(45),
primary key (codigoTipoProducto)
);

create table Proveedores (
codigoProveedor int,
NITProveedor varchar(10),
nombresProveedor varchar(60),
apellidosProveedor varchar(60),
direccionProveedor varchar(150),
razonSocial varchar(60),
contactoPrincipal varchar (100),
paginaWeb varchar(50),
primary key (codigoProveedor)
);

create table Compras(
numeroDocumento int,
fechaDocumento Date,
descripcion varchar(60),
totalDocumento decimal(10,2),
primary key (numeroDocumento)
);

create table Clientes(
codigoCliente int,
NITCliente varchar(10),
nombresCliente varchar(50),
apellidosCliente varchar(50),
direccionCliente varchar(150),
telefonoCliente varchar(8),
correoCliente varchar(45),
primary key (codigoCliente)
);

create table CargoEmpleado (
codigoCargoEmpleado Int,
nombreCargo varchar(45),
descripcionCargo varchar(45),
primary key (codigoCargoEmpleado)
);

create table Productos(
codigoProducto varchar(15),
descripcionProducto varchar(45),
precioUnitario decimal (10,2),
precioDocena decimal (10,2),
precioMayor decimal (10,2),
imagenProducto varchar(45),
existencia int,
codigoTipoProducto int,
codigoProveedor int,
primary key (codigoProducto),
foreign key (codigoTipoProducto)
references TipoProducto(codigoTipoProducto),
foreign key (codigoProveedor)
references Proveedores(codigoProveedor)
);

create table TelefonoProveedro(
codigoTelefonoProveedor int,
numeroPrincipal varchar(8),
numeroSecundario varchar(8),
observaciones varchar(45),

codigoProveedor int,
primary key (codigoTelefonoProveedor),
foreign key (codigoProveedor)
references Proveedores(codigoProveedor)
);

create table DetalleCompra(
codigoDetalleCompra int,
constoUnitario decimal (10,2),
cantidad int,

codigoProducto varchar(15),
numeroDocumento int,
primary key (codigoDetalleCompra),
foreign key (codigoProducto)
references Productos(codigoProducto),
foreign key (numeroDocumento)
references Compras(numeroDocumento)
);




