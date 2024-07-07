

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
	QuantityLeft int,
	ImageProduct varchar(150),
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
('Trinh Hoan Vu', '1', '1', '0398577140', '1998-04-25', 'staff', 'D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\CinemaPLUSLogo.png'),
('Trinh Hoan Vu', 'llstylishv2@gmail.com', '1', '0398577140', '1998-04-25', 'owner', 'D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\CinemaPLUSLogo.png');
GO

insert into Movie (MovieName,AmoutOfLimit,Author,TypeOfMovie,ImagesPoster,ImagesBackdrop)
values
('Kingdom of the Planet of the Apes',145,'Science Fiction Action','Wes Ball','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Poster\kingdomplanet.jpg','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Backdrop\kingdomplanet.jpg'),
('Monkey Man',121,'Action Thriller','Dev Patel','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Poster\monkeyman.jpg','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Backdrop\monkeyman.jpg'),
('No Way Up',90,'Survival Thriller','Claudio Fäh','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Poster\nowayup.jpg','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Backdrop\nowayup.jpg'),
('The Garfield',101,'Adventure Comedy','Mark Dindal','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Poster\thegrafield.jpg','D:\T1.2308.A0\7. Java\3.JP2\JavaFX\CinemaBooking_2\CinemaBooking_2\src\main\resources\Images\Backdrop\thegrafield.jpg')




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
('13:00:00','15:00:00','2024-08-08',1, 1,6),
('13:00:00','15:00:00','2024-08-04',2, 1,5),
('16:00:00','18:00:00','2024-06-03',2, 1,6),
('13:00:00','15:00:00','2024-08-06',2, 1,6),
('13:00:00','15:00:00','2024-08-05',2, 2,6),
('13:00:00','15:00:00','2024-08-07',2, 3,6),
('13:00:00','15:00:00','2024-08-08',2, 1,6),
('13:00:00','15:00:00','2024-08-04',2, 1,5),
('16:00:00','18:00:00','2024-06-03',2, 1,6),
('13:00:00','15:00:00','2024-08-06',2, 1,6),
('13:00:00','15:00:00','2024-08-05',2, 2,6),
('13:00:00','15:00:00','2024-08-07',2, 3,6),
('13:00:00','15:00:00','2024-08-08',2, 1,6)
go


insert into ShowTimes values
('13:00:00','15:00:00','2024-07-07',1, 1,5),
('16:00:00','18:00:00','2024-07-07',1, 1,6),
('13:00:00','15:00:00','2024-07-07',1, 1,6),
('13:00:00','15:00:00','2024-07-07',1, 2,6),
('13:00:00','15:00:00','2024-07-07',1, 3,6),
('13:00:00','15:00:00','2024-07-07',1, 1,6),
('13:00:00','15:00:00','2024-07-07',2, 1,5)
go


