DROP TABLE payment;
DROP TABLE reservation;
DROP TABLE room;
DROP TABLE roomstyle;
DROP TABLE guest;
DROP TABLE employee;
DROP TABLE department;
DROP TABLE hotel;

CREATE TABLE hotel (
    hotel_id                NUMBER(6)       PRIMARY KEY,                 --Integer with at most 10 digits: 9999999999
    rooms_available         NUMBER(4,0)     DEFAULT 0   NOT NULL,        --Integer with at most 5 digits, default value is 0. Cannot be null
    rating                  NUMBER(2,1)     DEFAULT 0   NOT NULL,        --Decimal with at most 2 digits, one of which is a decimal point. Default value is 0, cannot be null
    hotel_name              VARCHAR(100)    NOT NULL,
    street_name             VARCHAR(100),
    city                    VARCHAR(100),
    postal_code             VARCHAR(6),
    province                VARCHAR(100)
);

CREATE TABLE roomstyle
(
    room_style_id           NUMBER(6)    PRIMARY KEY, 
    room_style              VARCHAR(100),
    bed_size                VARCHAR(100)    NOT NULL,
    includesBreakfast       NUMBER(1,0)     DEFAULT 0   NOT NULL,
    isSmoking               NUMBER(1,0)     DEFAULT 0   NOT NULL, 
    isAccessibile           NUMBER(1,0)     DEFAULT 0   NOT NULL
);

CREATE TABLE guest (
    guest_id                NUMBER(6)    PRIMARY KEY,
    first_name              VARCHAR(100)    NOT NULL,
    last_name               VARCHAR(100)    NOT NULL,
    email                   VARCHAR(100)    NOT NULL,
    phone_number            VARCHAR(10)     NOT NULL
);


CREATE TABLE room (
    room_id                     NUMBER(6)    PRIMARY KEY,
    hotel_id                    NUMBER(6)    NOT NULL,
    room_style_id               NUMBER(6)    NOT NULL,
    beds                        NUMBER(1,0)     DEFAULT 1   NOT NULL,
    room_number                 NUMBER(4,0)     NOT NULL,
    room_status                 VARCHAR(100),
    room_price                  NUMBER(6,2)     NOT NULL,      

    CONSTRAINT FK_room_hotel
    FOREIGN KEY (hotel_id)
    REFERENCES hotel(hotel_id),
    
    CONSTRAINT FK_room_room_style
    FOREIGN KEY (room_style_id)
    REFERENCES roomstyle(room_style_id)
);

CREATE TABLE reservation ( 
    reservation_id              NUMBER(6)    PRIMARY KEY,
    room_id                     NUMBER(6)    NOT NULL,
    guest_id                    NUMBER(6)    NOT NULL,
    status                      VARCHAR(100)    NOT NULL,     
    check_in                    DATE,
    check_out                   DATE,
    guests                      NUMBER(2,0),         
    
    CONSTRAINT FK_reservation_room
    FOREIGN KEY (room_id)
    REFERENCES room(room_id),
    
    CONSTRAINT FK_reservation_guest
    FOREIGN KEY (guest_id)
    REFERENCES guest(guest_id)
);

CREATE TABLE payment (
    guest_id                    Number(6)    NOT NULL,         
    reservation_id              Number(6)    NOT NULL,
    payment_date                DATE,
    payment_amount              NUMBER(6,2)     NOT NULL,          
    payment_status              VARCHAR(100)    NOT NULL,

    CONSTRAINT FK_payment_guest
    FOREIGN KEY (guest_id)
    REFERENCES guest(guest_id),
    
    CONSTRAINT FK_payment_reservation
    FOREIGN KEY (reservation_id)
    REFERENCES reservation(reservation_id)
);


CREATE TABLE department (
    department_id               NUMBER(6)    PRIMARY KEY,
    hotel_id                    Number(6)    NOT NULL,
    department_name             VARCHAR(100),
    number_of_employees         NUMBER(4,0)     DEFAULT 1   NOT NULL,
    
    CONSTRAINT FK_department_hotel
    FOREIGN KEY (hotel_id)
    REFERENCES hotel(hotel_id)
);

CREATE TABLE employee (
    employee_id                 NUMBER(6)    PRIMARY KEY,
    department_id               Number(6)    NOT NULL,
    first_name                  VARCHAR(100)    NOT NULL,
    last_name                   VARCHAR(100)    NOT NULL,
    hourly_wage                 NUMBER(6,2)     DEFAULT 15.25   NOT NULL,
    occupation                  VARCHAR(100)    NOT NULL,
    
    CONSTRAINT FK_employee_department
    FOREIGN KEY (department_id)
    REFERENCES department(department_id)
);


--First Hotel
INSERT INTO hotel           VALUES(584983,97,4.3,'Test Hotel','Bloor and Yonge','Toronto','5JX65E','ON');
INSERT INTO guest           VALUES(979625,'Alick','Lazare','alazare@ryerson.ca','6471234567');
INSERT INTO guest           VALUES(983492,'Jessica','Singh','j16singh@ryerson.ca','6472173889');
INSERT INTO guest           VALUES(979632,'Anant','Jawanda','anant@ryerson.ca','6478934567');
INSERT INTO roomstyle       VALUES(688088,'Deluxe','King',1,0,1);
INSERT INTO roomstyle       VALUES(789203,'Deluxe','King',1,0,1);
INSERT INTO roomstyle       VALUES(345782,'Suite','King',1,0,0);
INSERT INTO roomstyle       VALUES(764832,'Double','Queen',0,1,1);
INSERT INTO roomstyle       VALUES(903485,'Deluxe','King',1,1,1);
INSERT INTO department      VALUES(902743,584983,'Maintainance',2);
INSERT INTO department      VALUES(902744,584983,'Administration',5);
INSERT INTO department      VALUES(902745,584983,'Customer Service',5);
INSERT INTO department      VALUES(902746,584983,'Accounts',3);
INSERT INTO employee        VALUES(638156,902743,'Alick','Lazare',25.6,'Janitor');
INSERT INTO employee        VALUES(834953,902743,'Emily','Jenn',25.6,'Janitor');
INSERT INTO employee        VALUES(903452,902744,'John','Doe',36.25,'Manager');
INSERT INTO employee        VALUES(903622,902745,'Anna','Sara',20.80,'Receptionist');
INSERT INTO employee        VALUES(123953,902745,'Bob','Joe',17.7,'Greeter');
INSERT INTO room            VALUES(327583,584983,789203,1,56,'Clean',346.25);
INSERT INTO room            VALUES(390533,584983,764832,2,90,'Clean',320.25);
INSERT INTO reservation     VALUES(513418,327583,979625,'Pending','2021-12-31','2022-1-2',2);
INSERT INTO reservation     VALUES(513419,390533,979632,'Completed','2021-11-16','2021-11-20',4);
INSERT INTO payment         VALUES(979632,513419,'2021-11-10',346.25,'Completed');
INSERT INTO payment         VALUES(979625,513418,'2021-12-1',320.25,'Pending');


--Second Hotel

INSERT INTO hotel           VALUES(523657,70, 4.2,'Hotel B','Jarvis Street','Toronto','G5TL1S','ON');
INSERT INTO guest           VALUES(236222,'Gary','Holand','gholand@gmail.ca','6473457623');
INSERT INTO roomstyle       VALUES(698740,'Standard','Queen',1,1,0);
INSERT INTO roomstyle       VALUES(698745,'Suite','King',1,1,1);
INSERT INTO department      VALUES(121111,523657,'Culinary',1);
INSERT INTO department      VALUES(583999,523657,'Marketing',4);
INSERT INTO employee        VALUES(444555,121111,'Jempo','Hempo',43.1,'Chef');
INSERT INTO employee        VALUES(211659,121111,'Jerry','Fisher',25.6,'Head Chef');
INSERT INTO employee        VALUES(296325,583999,'John','James',25.6,'Math guy');
INSERT INTO employee        VALUES(122612,583999,'Gary','Wary',25.6,'Consultant');
INSERT INTO room            VALUES(889984,523657,698745,2,24,'Clean',346.25);
INSERT INTO reservation     VALUES(632112,889984,236222,'Completed','2022-08-01','2022-08-03',4);
INSERT INTO payment         VALUES(236222,632112,'2022-07-20',265.24,'Completed');




--Queries

--Guest
--Guest
select first_name, last_name, guest.guest_id, reservation.guests
from guest, reservation
where reservation.guests > 2;


--Hotel
select *
from hotel 
where rating>=4
order by rating desc;

--Roomstyle
select *
from roomstyle
where bed_size = 'King' and includesBreakfast=1;

--Department
select *
from department
where number_of_employees>=3
order by number_of_employees asc;

--Employee
select first_name, last_name 
from employee
where department_id=121111
order by last_name desc;

--Room
select distinct room_number
from room
where room_price>200
order by room_number asc;

--Reservation
select *
from reservation 
where reservation.status = 'Completed'
order by check_in asc;

--Payment
select *
from payment
where payment_amount >= 300 and payment_status = 'Pending';

select payment_date,
    SUM(payment_amount) as totalPayments
from payment
group by payment_date;





