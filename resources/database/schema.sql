-- schema.sql

-- 데이터베이스 생성 (만약 존재하지 않는다면)
-- 'convenience_store'라는 이름의 데이터베이스 생성.
-- 'IF NOT EXISTS'는 이미 데이터베이스가 존재할 경우 오류를 방지.
CREATE DATABASE IF NOT EXISTS convenience_store;

-- 데이터베이스 선택, 이 명령어는 이후의 모든 SQL 명령어가
-- 'convenience_store' 데이터베이스에 적용되도록 함.
USE convenience_store;

-- STORE 테이블 생성
-- 편의점 정보를 저장함.
CREATE TABLE IF NOT EXISTS STORE (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- 각 편의점 기본키, 자동 증가.
    name VARCHAR(30) NOT NULL,          -- 편의점 이름, 최대 30자, 필수입력.
    revenue DOUBLE DEFAULT 0.00         -- 편의점 수익, 기본값은 0.
);

-- PRODUCT 테이블 생성
-- 상품 정보를 저장함.
CREATE TABLE IF NOT EXISTS PRODUCT (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- 각 상품 기본키, 자동 증가.
    name VARCHAR(30) NOT NULL,          -- 상품 이름, 최대 30자, 필수입력.
    price DOUBLE NOT NULL,              -- 상품 가격, 필수입력.
    stock INT NOT NULL DEFAULT 0,       -- 재고 수량, 기본값은 0.
    store_id INT,                       -- 이 상품이 속한 편의점의 ID
    FOREIGN KEY (store_id) REFERENCES STORE(id)  -- store_id는 STORE 테이블의 id를 참조.
);

-- EMPLOYEE 테이블 생성
-- 직원 정보를 저장.
CREATE TABLE IF NOT EXISTS EMPLOYEE (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- 각 직원의 고유 식별자, 자동으로 증가.
    name VARCHAR(30) NOT NULL,          -- 직원 이름, 최대 30자, 필수입력.
    salary DOUBLE NOT NULL,             -- 직원 급여, 필수입력.
    store_id INT,                       -- 이 직원이 일하는 편의점의 ID(기본키)
    FOREIGN KEY (store_id) REFERENCES STORE(id)  -- store_id는 STORE 테이블의 id를 참조
);

-- CUSTOMER 테이블 생성
-- 고객 정보를 저장.
CREATE TABLE IF NOT EXISTS CUSTOMER (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- 각 고객의 기본키, 자동 증가
    money DOUBLE NOT NULL DEFAULT 0     -- 고객이 가진 돈, 기본값은 0.
);

-- SALES 테이블 생성
-- 판매 기록을 저장.
    CREATE TABLE IF NOT EXISTS SALES (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- 각 판매 기록의 기본키, 자동 증가.
    customer_id INT,                    -- 구매한 고객의 ID
    product_id INT,                     -- 판매된 상품의 ID
    quantity INT NOT NULL,              -- 판매 수량, 필수입력.
    sale_turn INT NOT NULL,             -- 판매 발생한 게임 턴, 필수입력.
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id),  -- customer_id는 CUSTOMER 테이블의 id 참조.
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id)     -- product_id는 PRODUCT 테이블의 id 참조.
);

-- 1. 특정 편의점의 모든 상품 목록:
--    SELECT p.* FROM PRODUCT p INNER JOIN STORE s ON p.store_id = s.id WHERE s.name = '편의점 이름';
-- 2. 특정 고객의 구매 내역:
--    SELECT p.name, s.quantity, s.sale_turn FROM SALES s
--    INNER JOIN PRODUCT p ON s.product_id = p.id
--    INNER JOIN CUSTOMER c ON s.customer_id = c.id
--    WHERE c.id = 고객ID;