use CinemaBooking
go

create table Account(
  IDAccount varchar(10) primary key,
  Name varchar(50),
  Email varchar(50),
  Password varchar(20),
  PhoneNumber varchar(10),
  DateOfBirth date,
  Role bit
)
go

create table Movie(
	IDMovie varchar(10) primary key,
	MovieName varchar(50),
	Actor varchar(50),
	Author varchar(50),
	AmoutOfLimit int ,
	typeOfMovie varchar(50)
)
go

create table Client(
	IDClient varchar(10) primary key,
	phoneNumber varchar(10),
	email varchar(50)
)
go

create table Theater(
	IDTheater varchar(10) primary key,
	seatQuantity int	
)
go

create table Seat(
	IDSeat varchar(10) primary key, 
	reserve bit,
	IDTheater varchar(10) foreign key references Theater(IDTheater),
	IDShowTime varchar(10)
)
go


create table ShowTimes(
	IDShowTimes varchar(10) primary key,
	startTime time,
	endtime time,
	date date,
	IDMovie varchar(10) foreign key references Movie(IDMovie),
	IDTheater varchar(10) foreign key references Theater(IDTheater)
)
go

alter table Seat
add constraint FK_IDShowTimes_ShowTimes
foreign key (IDShowTime) references ShowTimes(IDShowTimes)

create  table Ticket(
	IDTicket varchar(10) primary key,
	movieName varchar(50),
	dateTimeBook datetime,
	IDShowTimes varchar(10) foreign key references ShowTimes(IDShowTimes),
	IDTheater varchar(10) foreign key references Theater(IDTheater),
	IDClient varchar(10) foreign key references Client(IDClient),
	IDSeat varchar(10) foreign key references Seat(IDSeat),
	IDAccountBook varchar(10) foreign key references Account(IDAccount),
	Total float
)
go

create table Orders(
	IDOrders varchar(10) primary key,
	IDClient varchar(10) foreign key references Client(IDClient),
	DateTime datetime,
	IDAdminOrder varchar(10) foreign key references Account(IDAccount),
	Total float
)
go

create table CategoryProduct(
	IDCategory varchar(10) primary key,
	ProductName varchar(20)
)
go

create table Product(
	IDProduct varchar(10) primary key,
	Name varchar(20),
	Price datetime,
	Quantity float,
	ImageProduct varchar(50),
	IDCategory varchar(10) foreign key references CategoryProduct(IDCategory)
 )
go

create table OrdersDetail(
  IDOrdersDetail varchar(10) primary key,
  IDOrder varchar(10) foreign key references Orders(IDOrders),
  IDProduct varchar(10) foreign key references Product(IDProduct),
  Quantity int,
  Price float,
  Total float
)
go


