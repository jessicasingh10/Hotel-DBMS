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

INSERT INTO hotel           VALUES(584983,0,0,'Test Hotel','Bloor and Yonge','Toronto','5JX65E','ON');
INSERT INTO guest           VALUES(979625,'Alick','Lazare','alazare@ryerson.ca','6471234567');
INSERT INTO roomstyle       VALUES(688088,'Deluxe','King',0,0,1);
INSERT INTO department      VALUES(927220,584983,'Maintainance',0);
INSERT INTO employee        VALUES(638156,927220,'Alick','Lazare',25.6,'Janitor');
INSERT INTO room            VALUES(332364,584983,688088,3,2,'Clean',346.25);
INSERT INTO reservation     VALUES(513418,332364,979625,'Pending',null,null,2);
INSERT INTO payment         VALUES(979625,513418,null,25.50,'Completed');


DESC hotel;
DESC room;
DESC guest;
DESC reservation;
DESC payment;
DESC Roomstyle;
DESC Employee;
DESC Department;



