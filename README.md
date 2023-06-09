# Android-app-for-taxi
A mobile application that consists of 3 parts: a server, 
an application for clients(RouteTaxi[TaxiLink]) 
and an application for drivers(DriverLink[DriverLink]).

# Server
The server is written in Java11+Spring+Hibernate+
MySQL+Maven+Google Cloud(App Engine + Cloud SQL).
Server features:
1. Getting all routes;
2. Obtaining data about the car and driver by route number;
3. Getting all routes on the stops that are contained on this route;
4. Receiving a route according to the driver's data;
5. Getting all stops;
6. Getting all stops by route number;
7. Getting a stop by id;
8. Getting a car by id;
9. Getting car capacity by id;
10. Getting data about the driver by id;
11. Authorization of the driver (since they are entered in the database in advance);
12. Creation of a trip according to the route and user;
13. Deleting a trip by user (the user is also deleted from the database by CASCADE);
14. Creation of the user according to the initial and final stops chosen by him.

# DataBase: MySQL 8.0
Database structure:
<div id="db" align="center">
    <img src="database/db_structure.jpg" width="550" height="350"/>
</div>

# TaxiLink
<p>
    <img src="screens/client1.png" alt="Скриншот клиент 1" width="160" height="340">
    <img src="screens/client2.png" alt="Скриншот клиент 2" width="160" height="340">
    <img src="screens/client3.png" alt="Скриншот клиент 3" width="160" height="340">
    <img src="screens/client4.png" alt="Скриншот клиент 4" width="160" height="340">
    <img src="screens/client5.png" alt="Скриншот клиент 5" width="160" height="340">
</p>

<br>

Trip booking method No. 1
<p>
    <img src="screens/client6.png" alt="Скриншот клиент 6" width="160" height="340">
    <img src="screens/client7.png" alt="Скриншот клиент 7" width="160" height="340">
    <img src="screens/client8.png" alt="Скриншот клиент 8" width="160" height="340">
    <img src="screens/client9.png" alt="Скриншот клиент 9" width="160" height="340">
    <img src="screens/client10.png" alt="Скриншот клиент 10" width="160" height="340">
</p>

<br>

Trip booking method No. 2:
<p>
    <img src="screens/client11.png" alt="Скриншот клиент 11" width="160" height="340">
    <img src="screens/client12.png" alt="Скриншот клиент 12" width="160" height="340">
    <img src="screens/client13.png" alt="Скриншот клиент 13" width="160" height="340">
    <img src="screens/client14.png" alt="Скриншот клиент 14" width="160" height="340">
    <img src="screens/client15.png" alt="Скриншот клиент 15" width="160" height="340">
    <img src="screens/client16.png" alt="Скриншот клиент 16" width="160" height="340">
</p>
<br>

# DriverLink
<p>
    <img src="screens/driver1.png" alt="Скриншот водитель 1" width="160" height="340">
    <img src="screens/driver2.png" alt="Скриншот водитель 2" width="160" height="340">
    <img src="screens/driver3.png" alt="Скриншот водитель 3" width="160" height="340">
    <img src="screens/driver4.png" alt="Скриншот водитель 4" width="160" height="340">
    <img src="screens/driver5.png" alt="Скриншот водитель 5" width="160" height="340">
    <img src="screens/driver6.png" alt="Скриншот водитель 6" width="160" height="340">
    <img src="screens/driver7.png" alt="Скриншот водитель 7" width="160" height="340">
</p>
