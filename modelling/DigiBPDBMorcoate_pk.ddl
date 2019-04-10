ALTER TABLE Customer ADD PRIMARY KEY (Customer_ID);
ALTER TABLE Orders ADD PRIMARY KEY (Order_ID);
ALTER TABLE Items ADD PRIMARY KEY (Item_ID, Order_ID);
ALTER TABLE Products ADD PRIMARY KEY (Product_ID);
ALTER TABLE Products_Customer ADD PRIMARY KEY (ProductsProduct_ID, CustomerCustomer_ID);
