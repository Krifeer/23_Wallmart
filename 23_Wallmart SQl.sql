CREATE DATABASE tienda_virtual;

USE tienda_virtual;

CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    marca VARCHAR(100),
    tipo VARCHAR(30) NOT NULL
);

INSERT INTO producto
(nombre, precio, stock, marca, tipo)
VALUES
('Laptop Lenovo', 15000, 5, 'Lenovo', 'Electronico'),
('Camisa deportiva', 599.99, 15, 'Nike', 'Ropa'),
('Refresco Coca Cola', 25, 50, 'Coca Cola', 'Comida'),
('Aspiradora', 3500, 8, 'LG', 'Domestico'),
('Paracetamol', 80, 30, 'Genérico', 'Medicamento');