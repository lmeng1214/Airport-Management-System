# PJ05-CS180
Project 05 for CS180 for Fall 2019

# Project 05 Handout Link
https://courses.cs.purdue.edu/cs18000:fall19:project:pj05

# Collaborator Emails
Lenny Meng's Email:
meng110@purdue.edu

Jon-Karl Karlis Vuskalns's Email: 

# Project Framework

AirlinePassengerRoster.txt <- FILE I/O ->
ReservationServer (with data validation on roster and input from client, along with concurrency and sync(roster.txt)) <- NETWORK I/O ->
ResponseListener (fast data validation for fast response to client if they input incorrectly, prompt GUI popups if needed) <- EDT ->
ReservationClient (ComplexGUI and SimpleGUI popups) <-> USER

ReservationServer should write the Serializable objects to files.
ReservationClient should create the Serializable objects and send them over the network to ReservationServer.


# Task Splitting
Lenny Meng:

Jon-Karl Karlis Vuskalns:
