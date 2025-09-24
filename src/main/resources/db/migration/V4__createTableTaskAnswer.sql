CREATE TABLE TaskAnswer (
                            id BIGINT(20) NOT NULL AUTO_INCREMENT,
                            option_text VARCHAR(255) NOT NULL,
                            is_correct BOOLEAN NOT NULL,
                            task_id BIGINT(20) NOT NULL,
                            PRIMARY KEY (id),
                            CONSTRAINT FK_TaskAnswer_Task FOREIGN KEY (task_id) REFERENCES Task(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;