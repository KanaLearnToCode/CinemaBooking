

use CinemaBooking
go


create table Account(
  IDAccount int identity(1,1) primary key,
  Name varchar(50),
  Email varchar(50),
  Password varchar(20),
  PhoneNumber varchar(10),
  DateOfBirth date,
  Role varchar(10),
  Avatar varchar(150)
)
go

create table Movie(
	IDMovie int identity(1,1) primary key,
	MovieName varchar(50),
	Author varchar(50),
	AmoutOfLimit int ,
	TypeOfMovie varchar(50),
	ImagesPoster varchar(150),
	ImagesBackdrop varchar(150)
)
go


create table Client(
	EmailClient varchar(50) primary key,
	PhoneNumber varchar(10),
	Name varchar(50)
)
go

create table Theater(
	IDTheater int identity(1,1) primary key,
	seatQuantity int	
)
go

CREATE TABLE ShowTimes (
    IDShowTimes int identity(1,1) PRIMARY KEY,
    StartTime TIME,
    EndTime TIME,
    date DATE,
    IDMovie int,
    IDTheater int,
	Price float,
    FOREIGN KEY (IDMovie) REFERENCES Movie(IDMovie),
    FOREIGN KEY (IDTheater) REFERENCES Theater(IDTheater)
)
GO


CREATE TABLE Seat (
    IDSeat VARCHAR(10),
    IDShowTime int,
    PRIMARY KEY (IDSeat, IDShowTime),
    FOREIGN KEY (IDShowTime) REFERENCES ShowTimes(IDShowTimes)
);
GO

CREATE TABLE Ticket(
    IDTicket int identity(1,1) PRIMARY KEY,
    DateTimeBook datetime,
    EmailClient VARCHAR(50),
    IDSeat VARCHAR(10),
    IDSeatShowTime int,
    IDAccountBook INT,
    Total FLOAT,
    FOREIGN KEY (EmailClient) REFERENCES Client(EmailClient),
    FOREIGN KEY (IDSeat, IDSeatShowTime) REFERENCES Seat(IDSeat, IDShowTime),
    FOREIGN KEY (IDAccountBook) REFERENCES Account(IDAccount)
);
GO


create table Orders(
	IDOrders int identity(1,1) primary key,
	EmailClient varchar(50) foreign key references Client(EmailClient),
	DateTime datetime,
	IDAdminOrder int foreign key references Account(IDAccount),
	Total float
);
go

create table CategoryProduct(
	IDCategory int identity(1,1) primary key,
	ProductName varchar(20)
)
go

create table Product(
	IDProduct int identity(1,1) primary key,
	Name varchar(20),
	Price float,
	Quantity int,
	ImageProduct varchar(50),
	IDCategory int foreign key references CategoryProduct(IDCategory)
 )
go

create table OrdersDetail(
  IDOrdersDetail int identity(1,1) primary key,
  IDOrder int foreign key references Orders(IDOrders),
  IDProduct int foreign key references Product(IDProduct),
  Quantity int,
  Price float,
  Total float
)
go

INSERT INTO Account (Name,Email,Password,PhoneNumber,DateOfBirth,Role,Avatar) VALUES
('Trinh Hoan Vu', '1', '1', '0398577140', '1998-04-25', 1, 'D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\CinemaPLUSLogo.png'),
('Trinh Hoan Vu', 'llstylishv2@gmail.com', '1', '0398577140', '1998-04-25', 1, 'D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\CinemaPLUSLogo.png');
GO

insert into Movie (MovieName,AmoutOfLimit,Author,TypeOfMovie,ImagesPoster,ImagesBackdrop)
values
('Two Faces',120,'Action','kar1a','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Kungfuphanda4.jpg',null),
('Two Faces',120,'Action','kar1a','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Kungfuphanda4.jpg',null),
('Now You See Me',120,'Action','kar1a','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Kungfuphanda4.jpg',null),
('Kungfu Panda 4',120,'Action','kar1a','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Kungfuphanda4.jpg',null),
('Kungfu Panda 4',120,'Action','kar1a','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Kungfuphanda4.jpg',null)


insert into Theater values
(80),
(80),
(80),
(80)

insert into ShowTimes values
('13:00:00','15:00:00','2024-08-04',1, 1,5),
('16:00:00','18:00:00','2024-06-03',1, 1,6),
('13:00:00','15:00:00','2024-08-06',1, 1,6),
('13:00:00','15:00:00','2024-08-05',1, 2,6),
('13:00:00','15:00:00','2024-08-07',1, 3,6),
('13:00:00','15:00:00','2024-08-08',1, 1,6)

insert into ShowTimes(StartTime,EndTime,date,IDMovie,IDTheater,Price) values
('13:00:00','15:00:00','2024-08-04',2, 1,5),
('16:00:00','18:00:00','2024-06-03',2, 1,6),
('13:00:00','15:00:00','2024-08-06',2, 1,6),
('13:00:00','15:00:00','2024-08-05',2, 2,6),
('13:00:00','15:00:00','2024-08-07',2, 3,6),
('13:00:00','15:00:00','2024-08-08',2, 1,6)
go

insert into ShowTimes(StartTime,EndTime,date,IDMovie,IDTheater,Price) values
('13:00:00','15:00:00','2024-08-04',2, 1,5),
('16:00:00','18:00:00','2024-06-03',2, 1,6),
('13:00:00','15:00:00','2024-08-06',2, 1,6),
('13:00:00','15:00:00','2024-08-05',2, 2,6),
('13:00:00','15:00:00','2024-08-07',2, 3,6),
('13:00:00','15:00:00','2024-08-08',2, 1,6)
go

insert into Client values
('imkar2a@gmail.com','0368577140','Matado')

INSERT INTO Seat (IDSeat, IDShowTime) VALUES
('A2', 6),
('A3', 5),
('A4', 5)
GO

INSERT INTO Ticket (DateTimeBook, EmailClient, IDSeat, IDSeatShowTime, IDAccountBook, Total) VALUES 
('2024-05-30 12:30:00.000', 'imkar2a@gmail.com', 'A2', 6, 1, 5),
('2024-05-30 12:30:00.000', 'imkar2a@gmail.com', 'A3', 5, 1, 5),
('2024-05-30 12:30:00.000', 'imkar2a@gmail.com', 'A4', 5, 1, 5)

GO

SELECT date, startTime, endTime 
FROM ShowTimes 
WHERE IDMovie = 1 
AND IDTheater = 1 
AND (
    (CONVERT(date, ShowTimes.date) = CONVERT(date, '2024/06/03') AND CONVERT(time, ShowTimes.StartTime) >= CONVERT(time, '16:00:00')) 
    OR 
    (CONVERT(date, ShowTimes.date) > CONVERT(date, '2024/06/03'))
)

