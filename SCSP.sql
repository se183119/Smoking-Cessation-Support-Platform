CREATE DATABASE SCSP
USE SCSP

-- Tạo bảng users trước
CREATE TABLE users (
    user_id INT IDENTITY PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    role NVARCHAR(20) NOT NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 DEFAULT SYSUTCDATETIME()
);

-- Bảng blog_posts
CREATE TABLE blog_posts (
    post_id INT IDENTITY PRIMARY KEY,
    author_id INT NULL,
    title NVARCHAR(255) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_blog_posts_author FOREIGN KEY (author_id)
      REFERENCES users(user_id) ON DELETE SET NULL ON UPDATE NO ACTION
);

-- Bảng forum_posts
CREATE TABLE forum_posts (
    post_id INT IDENTITY PRIMARY KEY,
    author_id INT NULL,
    title NVARCHAR(255) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_forum_posts_author FOREIGN KEY (author_id)
      REFERENCES users(user_id) ON DELETE SET NULL ON UPDATE NO ACTION
);

-- Bảng comments
CREATE TABLE comments (
    comment_id INT IDENTITY PRIMARY KEY,
    post_id INT NOT NULL,
    author_id INT NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES forum_posts(post_id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_comments_author FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Bảng coach_assignments
CREATE TABLE coach_assignments (
    assignment_id INT IDENTITY PRIMARY KEY,
    member_id INT NOT NULL,
    coach_id INT NOT NULL,
    assigned_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_coach_assignments_member FOREIGN KEY (member_id) REFERENCES users(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_coach_assignments_coach FOREIGN KEY (coach_id) REFERENCES users(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Bảng member_plans
CREATE TABLE member_plans (
    plan_id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    plan_name NVARCHAR(255) NOT NULL,
    reason NVARCHAR(500),
    start_date DATE,
    end_date DATE,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_member_plans_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Bảng smoking_records
CREATE TABLE smoking_records (
    record_id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    date_recorded DATE NOT NULL,
    cigarettes_per_day INT,
    symptoms NVARCHAR(500),
    health_status NVARCHAR(500),
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_smoking_records_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Bảng reminders
CREATE TABLE reminders (
    reminder_id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    message NVARCHAR(500) NOT NULL,
    send_time DATETIME2 NOT NULL,
    channel NVARCHAR(50) NOT NULL, -- push, SMS, email
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_reminders_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Bảng chat_sessions
CREATE TABLE chat_sessions (
    session_id INT IDENTITY PRIMARY KEY,
    member_id INT NOT NULL,
    coach_id INT NOT NULL,
    started_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    ended_at DATETIME2 NULL,
    CONSTRAINT fk_chat_sessions_member FOREIGN KEY (member_id) REFERENCES users(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_chat_sessions_coach FOREIGN KEY (coach_id) REFERENCES users(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Bảng chat_messages
CREATE TABLE chat_messages (
    message_id INT IDENTITY PRIMARY KEY,
    session_id INT NOT NULL,
    sender_id INT NOT NULL,
    message_text NVARCHAR(MAX) NOT NULL,
    sent_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_chat_messages_session FOREIGN KEY (session_id) REFERENCES chat_sessions(session_id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_chat_messages_sender FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Bảng video_call_logs
CREATE TABLE video_call_logs (
    call_id INT IDENTITY PRIMARY KEY,
    session_id INT NOT NULL,
    started_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    ended_at DATETIME2 NULL,
    CONSTRAINT fk_video_call_logs_session FOREIGN KEY (session_id) REFERENCES chat_sessions(session_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Bảng feedbacks
CREATE TABLE feedbacks (
    feedback_id INT IDENTITY PRIMARY KEY,
    session_id INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment NVARCHAR(1000),
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_feedbacks_session FOREIGN KEY (session_id) REFERENCES chat_sessions(session_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Bảng payments
CREATE TABLE payments (
    payment_id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    plan_name NVARCHAR(255) NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    payment_date DATETIME2 DEFAULT SYSUTCDATETIME(),
    payment_method NVARCHAR(50),
    CONSTRAINT fk_payments_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Bảng badges (huy hiệu)
CREATE TABLE badges (
    badge_id INT IDENTITY PRIMARY KEY,
    badge_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(500),
    created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);

-- Bảng user_badges (quan hệ nhiều-nhiều)
CREATE TABLE user_badges (
    user_id INT NOT NULL,
    badge_id INT NOT NULL,
    awarded_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    PRIMARY KEY (user_id, badge_id),
    CONSTRAINT fk_user_badges_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_user_badges_badge FOREIGN KEY (badge_id) REFERENCES badges(badge_id) ON DELETE CASCADE ON UPDATE NO ACTION
);
