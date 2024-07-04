-- 편의점 시뮬레이션 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS convenience_store;

-- 데이터베이스 사용
USE convenience_store;

-- 기존 테이블 삭제
DROP TABLE IF EXISTS inventory_log;
DROP TABLE IF EXISTS products;

-- 제품 테이블 생성
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL
);

-- 재고 로그 테이블 생성
CREATE TABLE inventory_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    game_date INT NOT NULL,  -- 게임 내 날짜를 나타내는 정수값
    product_id INT,
    quantity INT NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Products 테이블 샘플 데이터
INSERT INTO products (product_id, name, price) VALUES
    (1, '삼각김밥', 1200),
    (2, '초코파이', 1500),
    (3, '새우깡', 1300),
    (4, '바나나우유', 1400),
    (5, '컵라면', 1100),
    (6, '아이스크림', 1800),
    (7, '생수', 800),
    (8, '도시락', 4500),
    (9, '김치찌개라면', 1300),
    (10, '캔커피', 1000);

-- Inventory Log 테이블 샘플 데이터
INSERT INTO inventory_log (game_date, product_id, quantity, description) VALUES
    (1, 1, 50, '초기 입고'),
    (1, 2, 30, '초기 입고'),
    (1, 3, 40, '초기 입고'),
    (1, 4, 25, '초기 입고'),
    (1, 5, 35, '초기 입고'),
    (1, 6, 20, '초기 입고'),
    (1, 7, 100, '초기 입고'),
    (1, 8, 15, '초기 입고'),
    (2, 9, 30, '초기 입고'),
    (2, 10, 40, '초기 입고'),
    (3, 1, -10, '판매'),
    (3, 2, -5, '판매'),
    (4, 3, -8, '판매'),
    (4, 4, -7, '판매'),
    (5, 5, -15, '판매'),
    (5, 1, 30, '재입고'),
    (6, 6, -6, '판매'),
    (6, 7, -20, '판매'),
    (7, 8, -3, '판매'),
    (7, 9, -10, '판매'),
    (8, 10, -15, '할인 판매'),
    (8, 5, 20, '재입고'),
    (9, 2, 25, '재입고'),
    (9, 3, 15, '재입고'),
    (10, 4, 20, '재입고');