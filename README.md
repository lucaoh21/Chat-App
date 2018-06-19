# Chat-App

A java based application that allows users to chat to each other over a local network. The program contains a seperate server-side and client-side application.

To test the application, the server side must be up and running before a client/user can be started. Once the client app starts up, you will be prompted for the IP Address of the server and the port to configure a connection. The user will then be asked to either sign in or create a new account. The user can then send and receive messages from all other clients currently on the server.

The program uses sockets to establish a connection between the server and the clients. The front-end uses the JFrame java class, while the backend uses the MySQL database to store user information.
