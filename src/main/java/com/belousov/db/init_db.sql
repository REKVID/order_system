CREATE TABLE clients (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(50) NOT NULL, 
    phone VARCHAR(50), 
    address VARCHAR(50), 
    contact_person VARCHAR(50)         
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,          
    name VARCHAR(50) NOT NULL,                 
    price DECIMAL(10, 2) NOT NULL,              
    description TEXT,                           
    is_delivery_available BOOLEAN NOT NULL      
);

CREATE TABLE delivery_methods (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    name VARCHAR(50) NOT NULL UNIQUE,  
    speed_days INT NOT NULL             
);  

CREATE TABLE available_delivery_Methods (
    product_id INT,                     
    delivery_method_id INT,             
    delivery_cost DECIMAL(10, 2) NOT NULL, 
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (delivery_method_id) REFERENCES delivery_methods(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE document (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    client_id INT NOT NULL,             
    date DATE NOT NULL,                 
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE clientsChoise ( 
    id INT AUTO_INCREMENT PRIMARY KEY,                  
    document_id INT NOT NULL,                           
    product_id INT NOT NULL,                            
    delivery_Methods_id INT NULL,                       
    quantity INT NOT NULL CHECK (quantity > 0),         
    final_delivery_cost DECIMAL(10, 2) NULL,            
    FOREIGN KEY (document_id) REFERENCES document(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (delivery_Methods_id) REFERENCES delivery_methods(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    client_id INT UNIQUE NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE RESTRICT ON UPDATE CASCADE
);