CREATE TABLE Customer (Customer_ID SERIAL NOT NULL, First_Name char(255) NOT NULL, Last_Name char(255) NOT NULL, DOB date NOT NULL, First_Address_Line char(255) NOT NULL, Second_Address_Line char(255), Zip_Code int4 NOT NULL, City char(255));
CREATE TABLE Orders (CustomerCustomer_ID int4, Order_ID SERIAL NOT NULL, Number_Of_Items int4 NOT NULL);
CREATE TABLE Items (Item_ID char(255) NOT NULL, Order_ID char(255) NOT NULL, Item_Description char(255), Quantity numeric(19, 0) NOT NULL, Price numeric(19, 5) NOT NULL, Status char(255), OrdersOrder_ID int4 NOT NULL);
CREATE TABLE Products (Product_ID SERIAL NOT NULL, Product_Name char(255) NOT NULL, Product_Description char(255), "Size" char(255) NOT NULL, Color char(255) NOT NULL, Price numeric(19, 0) NOT NULL, Available char(255) DEFAULT 'Yes' NOT NULL);
CREATE TABLE Products_Customer (ProductsProduct_ID int4 NOT NULL, CustomerCustomer_ID int4 NOT NULL);
