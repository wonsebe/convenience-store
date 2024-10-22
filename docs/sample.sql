-- 편의점 시뮬레이션 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS convenience_store;
-- drop database convenience_store;

-- 데이터베이스 사용
USE convenience_store;

-- 기존 테이블 삭제 (삭제 순서에 따라 오류 발생할 수 있음)
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS inventory_log;
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS store_balance;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS store;

-- 편의점 (로그인 회원(게임속 점주)마다 기본키 다름)
CREATE TABLE store
(
    id           INT PRIMARY KEY AUTO_INCREMENT, -- 편의점 기본키, 편의점 구분함
    login_id     VARCHAR(20) NOT NULL UNIQUE,    -- 게임 회원가입시 등록했던 아이디
    login_pwd    VARCHAR(20) NOT NULL,           -- 등록한 로그인 비밀번호
    current_turn INT DEFAULT 1,                  -- 저장될 게임 턴수
    balance      INT DEFAULT 1000000             -- 초기 잔고 100만원 설정
);

-- 샘플 계정
INSERT INTO store (id, login_id, login_pwd, current_turn, balance)
VALUES (1, 'admin', '1234', 1, 1000000),
       (2, 'admin2', '1234', 1, 1000000)
ON DUPLICATE KEY UPDATE login_id     = VALUES(login_id),
                        login_pwd    = VALUES(login_pwd),
                        current_turn = VALUES(current_turn),
                        balance      = VALUES(balance);

-- store_balance 테이블 생성
CREATE TABLE store_balance
(
    store_id INT PRIMARY KEY,
    balance  INT NOT NULL,
    FOREIGN KEY (store_id) REFERENCES store (id) ON DELETE CASCADE
);

-- Board 테이블 생성
CREATE TABLE board
(
    bmo      INT AUTO_INCREMENT PRIMARY KEY,
    bcontent VARCHAR(255) NOT NULL,
    bdate    DATE         NOT NULL,
    store_id INT,
    FOREIGN KEY (store_id) REFERENCES store (id)
        ON DELETE CASCADE
);

-- 제품 테이블 생성
CREATE TABLE products
(
    product_id   INT AUTO_INCREMENT PRIMARY KEY, -- 제품 ID, 기본 키
    name         VARCHAR(20) NOT NULL,           -- 제품 이름, NULL 불가
    price        INT         NOT NULL,           -- 제품 가격, NULL 불가
    expiry_turns INT         NOT NULL,           -- 유통기한 (턴 단위), NULL 불가
    store_id     INT,
    FOREIGN KEY (store_id) REFERENCES store (id)
        ON DELETE CASCADE
);

ALTER TABLE products
    DROP FOREIGN KEY products_ibfk_1;
ALTER TABLE products
    DROP COLUMN store_id;

-- 재고 로그 테이블 생성
CREATE TABLE inventory_log
(
    log_id        INT PRIMARY KEY AUTO_INCREMENT,
    game_date     INT NOT NULL,
    product_id    INT,
    quantity      INT NOT NULL,
    description   VARCHAR(20),
    store_id      INT,
    sale_price    INT,
    purchase_date INT,
    FOREIGN KEY (store_id) REFERENCES store (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
);

SET SQL_SAFE_UPDATES = 0;
UPDATE inventory_log
SET purchase_date = game_date
WHERE purchase_date IS NULL;
SET SQL_SAFE_UPDATES = 1;

-- 매출액 테이블 생성, 게임의 각 턴별 매출 정보 저장
CREATE TABLE sales
(
    sale_id     INT AUTO_INCREMENT PRIMARY KEY, -- 각 매출 기록의 고유 식별자
    game_date   INT NOT NULL,                   -- 게임 내 날짜(턴 수)
    total_sales INT NOT NULL,                   -- 해당 날짜(턴)의 총 매출액
    profit      INT NOT NULL,                   -- 해당 날짜(턴)의 순이익
    store_id    INT,
    FOREIGN KEY (store_id) REFERENCES store (id)
        ON DELETE CASCADE
);

-- Products 테이블 샘플 데이터
INSERT INTO products (product_id, name, price, expiry_turns)
VALUES (1, '삼각김밥', 1200, 5),
       (2, '초코파이', 1500, 10),
       (3, '새우깡', 1300, 20),
       (4, '바나나우유', 1400, 7),
       (5, '컵라면', 1100, 15),
       (6, '아이스크림', 1800, 25),
       (7, '생수', 800, 30),
       (8, '도시락', 4500, 3),
       (9, '김치찌개라면', 1300, 12),
       (10, '캔커피', 1000, 18),
       (11, '딸기케익', 2000, 5),
       (12, '과자', 2500, 20),
       (13, '음료수', 1500, 25),
       (14, '샌드위치', 3000, 4),
       (15, '쥬스', 1200, 7),
       (16, '우유', 1600, 6),
       (17, '빨간자몽', 2200, 8),
       (18, '크림빵', 1800, 5),
       (19, '사탕', 500, 50),
       (20, '초콜릿', 2500, 30),
       (21, '감자칩', 1700, 20),
       (22, '햄버거', 4000, 3),
       (23, '소시지', 3500, 10),
       (24, '치즈', 3000, 12),
       (25, '콜라', 1500, 18),
       (26, '사이다', 1500, 18),
       (27, '핫도그', 2700, 7),
       (28, '피자', 5000, 10),
       (29, '닭강정', 4500, 5),
       (30, '포켓몬 빵', 3000, 8);

-- Inventory Log 테이블 샘플 데이터
INSERT INTO inventory_log (game_date, product_id, quantity, description)
VALUES (1, 1, 20, '초기 입고'),  -- 삼각김밥
       (1, 2, 20, '초기 입고'),  -- 초코파이
       (1, 3, 20, '초기 입고'),  -- 새우깡
       (1, 4, 25, '초기 입고'),  -- 바나나우유
       (1, 5, 25, '초기 입고'),  -- 컵라면
       (1, 6, 20, '초기 입고'),  -- 아이스크림
       (1, 7, 30, '초기 입고'),  -- 생수
       (1, 8, 15, '초기 입고'),  -- 도시락
       (1, 9, 20, '초기 입고'),  -- 김치찌개라면
       (1, 10, 20, '초기 입고'), -- 캔커피
       (1, 11, 20, '초기 입고'), -- 딸기케익
       (1, 12, 30, '초기 입고'), -- 과자
       (1, 13, 20, '초기 입고'), -- 음료수
       (1, 14, 25, '초기 입고'), -- 샌드위치
       (1, 15, 25, '초기 입고'), -- 쥬스
       (1, 16, 20, '초기 입고'), -- 우유
       (1, 17, 20, '초기 입고'), -- 빨간자몽
       (1, 18, 15, '초기 입고'), -- 크림빵
       (1, 19, 20, '초기 입고'), -- 사탕
       (1, 20, 20, '초기 입고'), -- 초콜릿
       (1, 21, 20, '초기 입고'), -- 감자칩
       (1, 22, 30, '초기 입고'), -- 햄버거
       (1, 23, 20, '초기 입고'), -- 소시지
       (1, 24, 25, '초기 입고'), -- 치즈
       (1, 25, 25, '초기 입고'), -- 콜라
       (1, 26, 20, '초기 입고'), -- 사이다
       (1, 27, 20, '초기 입고'), -- 핫도그
       (1, 28, 15, '초기 입고'), -- 피자
       (1, 29, 10, '초기 입고'), -- 닭강정
       (1, 30, 20, '초기 입고');
-- 포켓몬 빵

-- Board 테이블 샘플 데이터
INSERT INTO board (bmo, bcontent, bdate, store_id)
VALUES (1, '25% 세일', '2024-07-08', 1),
       (2, '포켓몬빵 입고', '2024-07-09', 1),
       (3, '신메뉴 출시', '2024-07-10', 1);

SELECT *
FROM store;