CREATE DATABASE QS

USE QS

CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(255) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(255) NOT NULL,
    phone NVARCHAR(20) NULL,
    role NVARCHAR(20) DEFAULT 'user',
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE membership_plans (
    plan_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX) NULL,
    price DECIMAL(10,2) NULL,
    duration_in_days INT NULL,
    features NVARCHAR(MAX) NULL,
    created_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE user_memberships (
    membership_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    plan_id INT NOT NULL,
    start_date DATE NULL,
    end_date DATE NULL,
    status NVARCHAR(20) DEFAULT 'active',
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_user_memberships_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_user_memberships_plan FOREIGN KEY (plan_id) REFERENCES membership_plans(plan_id)
);

CREATE TABLE payment_transactions (
    transaction_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    membership_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATETIME DEFAULT GETDATE(),
    payment_gateway NVARCHAR(100) NULL,
    transaction_status NVARCHAR(50) NULL,
    CONSTRAINT fk_payment_transactions_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_payment_transactions_membership FOREIGN KEY (membership_id) REFERENCES user_memberships(membership_id)
);

CREATE TABLE smoking_records (
    record_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    record_date DATE NOT NULL,
    cigarettes_count INT NULL,
    frequency NVARCHAR(50) NULL,
    cost_per_cigarette DECIMAL(10,2) NULL,
    total_cost DECIMAL(10,2) NULL,
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_smoking_records_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE quit_plans (
    quit_plan_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    reason NVARCHAR(MAX) NULL,
    start_date DATE NULL,
    target_quit_date DATE NULL,
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_quit_plans_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE progress_records (
    progress_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    record_date DATE NOT NULL,
    no_smoking_days INT NULL,
    money_saved DECIMAL(10,2) NULL,
    health_improvement NVARCHAR(MAX) NULL,
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_progress_records_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE achievement_badges (
    badge_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX) NULL,
    icon_url NVARCHAR(255) NULL,
    criteria NVARCHAR(MAX) NULL,
    created_at DATETIME DEFAULT GETDATE()
);
CREATE TABLE user_badges (
id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    badge_id INT NOT NULL,
    awarded_date DATE NULL,
    comment NVARCHAR(MAX) NULL,
    CONSTRAINT fk_user_badges_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_user_badges_badge FOREIGN KEY (badge_id) REFERENCES achievement_badges(badge_id)
);

CREATE TABLE chat_sessions (
    session_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    coach_id INT NOT NULL,
    start_time DATETIME DEFAULT GETDATE(),
    end_time DATETIME NULL,
    status NVARCHAR(20) DEFAULT 'active',
    CONSTRAINT fk_chat_sessions_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_chat_sessions_coach FOREIGN KEY (coach_id) REFERENCES users(user_id)
);

CREATE TABLE chat_messages (
    message_id INT IDENTITY(1,1) PRIMARY KEY,
    session_id INT NOT NULL,
    sender_id INT NOT NULL,
    message NVARCHAR(MAX) NOT NULL,
    sent_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_chat_messages_session FOREIGN KEY (session_id) REFERENCES chat_sessions(session_id),
    CONSTRAINT fk_chat_messages_sender FOREIGN KEY (sender_id) REFERENCES users(user_id)
);

CREATE TABLE feedback (
    feedback_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    rating INT NULL,
    comment NVARCHAR(MAX) NULL,
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_feedback_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE notifications (
    notification_id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NULL,
    message NVARCHAR(MAX) NULL,
    notification_type NVARCHAR(20) NULL,
    frequency NVARCHAR(20) DEFAULT 'daily',
    created_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE user_notifications (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    notification_id INT NOT NULL,
    is_read BIT DEFAULT 0,
    sent_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_user_notifications_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_user_notifications_notification FOREIGN KEY (notification_id) REFERENCES notifications(notification_id)
);