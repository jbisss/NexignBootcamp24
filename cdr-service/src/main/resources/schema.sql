CREATE TABLE IF NOT EXISTS transactions
(id INT AUTO_INCREMENT PRIMARY KEY, call_type VARCHAR(2), start_call_time VARCHAR(20), end_call_time VARCHAR(20), abonent_id INT);

CREATE TABLE IF NOT EXISTS abonents
(id INT AUTO_INCREMENT PRIMARY KEY, phone_number VARCHAR(255));