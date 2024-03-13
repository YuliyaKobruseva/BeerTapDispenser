DROP TABLE IF EXISTS DISPENSER;
CREATE TABLE DISPENSER
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(10) DEFAULT 'CLOSED' NOT NULL,
    flow_volume DECIMAL(10, 3) NOT NULL
);

DROP TABLE IF EXISTS HISTORY;
CREATE TABLE HISTORY
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    dispenser_id INT,
    opened_at TIMESTAMP,
    closed_at TIMESTAMP,
    flow_volume DECIMAL(10, 3),
    total_spent DECIMAL(10, 3) DEFAULT 0.00 NOT NULL,
    FOREIGN KEY (dispenser_id) REFERENCES DISPENSER(id)
);



