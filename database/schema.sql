-- ═══════════════════════════════════════════════════════════════
--  BODY SPORT ZONE — Unified Database Schema
--  Database: esprit
--  Covers all 3 modules:
--    MODULE 1 (You)      — User authentication & password reset
--    MODULE 2 (Friend 1) — Nutrition plans, food items, adherence
--    MODULE 3 (Friend 2) — Fitness programs, exercises, sessions, progress
-- ═══════════════════════════════════════════════════════════════

CREATE DATABASE IF NOT EXISTS esprit
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE esprit;

-- ───────────────────────────────────────────────────────────────
-- MODULE 1 — USER AUTH (your project: greenmindtechfx)
-- ───────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS user (
    id            INT          NOT NULL AUTO_INCREMENT,
    email         VARCHAR(180) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    nom           VARCHAR(100) NOT NULL,
    prenom        VARCHAR(100) NOT NULL,
    roles         JSON         NOT NULL DEFAULT ('["ROLE_USER"]'),
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS password_reset (
    id            INT          NOT NULL AUTO_INCREMENT,
    user_id       INT          NOT NULL,
    code          CHAR(6)      NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at    TIMESTAMP    NOT NULL DEFAULT (CURRENT_TIMESTAMP + INTERVAL 15 MINUTE),
    used          TINYINT(1)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_reset_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- ───────────────────────────────────────────────────────────────
-- MODULE 2 — NUTRITION (Friend 1: WorkshopJDBC)
-- ───────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS nutrition_plans (
    id               INT          NOT NULL AUTO_INCREMENT,
    user_id          INT          NOT NULL,                  -- links to user.id
    goal_type        VARCHAR(50)  NOT NULL,                  -- e.g. 'weight_loss', 'muscle_gain'
    bmr_calories     INT          NOT NULL DEFAULT 0,
    daily_calories   INT          NOT NULL DEFAULT 0,
    protein_target   INT          NOT NULL DEFAULT 0,        -- grams/day
    carb_target      INT          NOT NULL DEFAULT 0,        -- grams/day
    fat_target       INT          NOT NULL DEFAULT 0,        -- grams/day
    adherence_score  INT          NOT NULL DEFAULT 0,        -- 0-100
    streak_days      INT          NOT NULL DEFAULT 0,
    total_points     INT          NOT NULL DEFAULT 0,
    level            VARCHAR(30)  NOT NULL DEFAULT 'Beginner',
    status           VARCHAR(20)  NOT NULL DEFAULT 'active', -- 'active' | 'paused' | 'completed'
    start_date       DATE         NOT NULL DEFAULT (CURRENT_DATE),
    PRIMARY KEY (id),
    CONSTRAINT fk_plan_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS food_items (
    id                INT           NOT NULL AUTO_INCREMENT,
    plan_id           INT           NOT NULL,               -- links to nutrition_plans.id
    name              VARCHAR(150)  NOT NULL,
    calories_per_100g INT           NOT NULL DEFAULT 0,
    protein_per_100g  FLOAT         NOT NULL DEFAULT 0,
    carbs_per_100g    FLOAT         NOT NULL DEFAULT 0,
    fat_per_100g      FLOAT         NOT NULL DEFAULT 0,
    quantity_g        FLOAT         NOT NULL DEFAULT 100,
    glycemic_index    INT           NOT NULL DEFAULT 0,
    allergens         VARCHAR(255)  DEFAULT NULL,
    is_recommended    TINYINT(1)    NOT NULL DEFAULT 0,
    meal_type         VARCHAR(50)   DEFAULT NULL,           -- 'breakfast','lunch','dinner','snack'
    meal_date         VARCHAR(20)   DEFAULT NULL,           -- stored as 'YYYY-MM-DD' string
    category          VARCHAR(80)   DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_food_plan FOREIGN KEY (plan_id) REFERENCES nutrition_plans(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS adherence_history (
    id               INT          NOT NULL AUTO_INCREMENT,
    plan_id          INT          NOT NULL,                 -- links to nutrition_plans.id
    check_date       VARCHAR(20)  NOT NULL,                 -- 'YYYY-MM-DD'
    adherence_score  INT          NOT NULL DEFAULT 0,       -- 0-100
    calories_consumed INT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_adherence_plan FOREIGN KEY (plan_id) REFERENCES nutrition_plans(id) ON DELETE CASCADE
);

-- ───────────────────────────────────────────────────────────────
-- MODULE 3 — FITNESS (Friend 2: vf)
-- ───────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS exercise (
    id           INT          NOT NULL AUTO_INCREMENT,
    name         VARCHAR(150) NOT NULL,
    muscle_group VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS program (
    id    INT          NOT NULL AUTO_INCREMENT,
    name  VARCHAR(150) NOT NULL,
    level VARCHAR(50)  NOT NULL,   -- e.g. 'Beginner', 'Intermediate', 'Advanced'
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS session (
    id         INT  NOT NULL AUTO_INCREMENT,
    program_id INT  NOT NULL,                              -- links to program.id
    duration   INT  NOT NULL DEFAULT 0,                   -- minutes
    PRIMARY KEY (id),
    CONSTRAINT fk_session_program FOREIGN KEY (program_id) REFERENCES program(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS progress (
    id          INT          NOT NULL AUTO_INCREMENT,
    user_id     INT          NOT NULL,                     -- links to user.id
    performance VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_progress_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Legacy table used by both friends' original projects (keep for compatibility)
CREATE TABLE IF NOT EXISTS personne (
    id     INT          NOT NULL AUTO_INCREMENT,
    nom    VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- ───────────────────────────────────────────────────────────────
-- SAMPLE DATA — one test user + one record per table
-- (delete this section before production)
-- ───────────────────────────────────────────────────────────────

INSERT IGNORE INTO user (id, email, password, nom, prenom, roles)
VALUES (1, 'test@bodysportzone.com', 'Test1234!', 'Demo', 'User', '["ROLE_USER"]');

INSERT IGNORE INTO nutrition_plans
    (id, user_id, goal_type, bmr_calories, daily_calories,
     protein_target, carb_target, fat_target, start_date)
VALUES
    (1, 1, 'weight_loss', 1800, 1500, 120, 150, 50, CURRENT_DATE);

INSERT IGNORE INTO food_items
    (id, plan_id, name, calories_per_100g, protein_per_100g,
     carbs_per_100g, fat_per_100g, quantity_g, meal_type, meal_date, category)
VALUES
    (1, 1, 'Chicken Breast', 165, 31.0, 0.0, 3.6, 150, 'lunch', DATE_FORMAT(CURDATE(),'%Y-%m-%d'), 'Protein');

INSERT IGNORE INTO exercise (id, name, muscle_group)
VALUES (1, 'Bench Press', 'Chest'), (2, 'Squat', 'Legs'), (3, 'Pull-up', 'Back');

INSERT IGNORE INTO program (id, name, level)
VALUES (1, 'Full Body Beginner', 'Beginner'), (2, 'PPL Intermediate', 'Intermediate');

INSERT IGNORE INTO session (id, program_id, duration)
VALUES (1, 1, 45), (2, 1, 60);

INSERT IGNORE INTO progress (id, user_id, performance)
VALUES (1, 1, 'Bench: 60kg x 8 reps');
