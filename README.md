# Flight Management System
Project 05 for CS180 for Fall 2019

# Would you like to book a flight?
This project is intended to imitate common flight booking websites such as Kayak, Priceline, Expedia, and so forth. This project utilizes many different aspects of Java; including, but not limited to:

- Graphical Drawing
- Server-Client communication
- File input/output

# Collaborator Emails
Lenny Meng's Email:
meng110@purdue.edu
lmeng1214@gmail.com

Jon-Karl Karlis Vuskalns's Email:

# Project Framework
AirlinePassengerRoster.txt <- FILE I/O ->
ReservationServer (with data validation on roster and input from client, along with concurrency and sync(roster.txt)) <- NETWORK I/O ->
ResponseListener (fast data validation for fast response to client if they input incorrectly, prompt GUI popups if needed) <- EDT ->
ReservationClient (ComplexGUI and SimpleGUI popups) <-> USER

ReservationServer should write the Serializable objects to files.
ReservationClient should create the Serializable objects and send them over the network to ReservationServer.
