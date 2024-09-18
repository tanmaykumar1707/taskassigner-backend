CREATE TABLE IF NOT EXISTS tasks (
    task_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    remarks TEXT,
    assigned_to BIGINT,
    assigned_to_type ENUM('i', 'g') NOT NULL,
    assigned_from BIGINT,
    created_at date NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
    FOREIGN KEY (assigned_to) REFERENCES users(user_id),
    FOREIGN KEY (assigned_from) REFERENCES users(user_id)
);
