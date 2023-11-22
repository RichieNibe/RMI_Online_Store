# Java RMI Online Store 

## Project Description

This project is a Java application using Remote Method Invocation (RMI) to create a basic online store. It consists of two main components: a server that manages the store's operations and inventory, and a client that interacts with the server to perform actions like user registration, login, and item management.

## Features

- User registration and login
- Browsing items in the store
- Adding items to a shopping cart
- Updating items in inventory
- Removing items
- Admin functionality for managing inventory
- Purchasing items

## Prerequisites

To run this project, you will need:
- Java Development Kit (JDK), version 8 or newer
- Basic knowledge of Java and RMI concepts

## Compiling the Project

The project comes with a `MAKEFILE` for easy compilation. To compile the project, run:

make compile 

make jar

This command compiles the source files and places the output in the `bin` directory. The `MAKEFILE` is configured to handle various tasks related to building and running the application.

## Running the Application
The application can be run in two modes: Server and Client. Use the following commands to start each component:

**Run the Server**

java -jar MyApplication.jar server

**Run the Client**

open a new terminal and run 

java -jar MyApplication.jar client

# Start the server on a specific port
java -jar MyApplication.jar server [port number]



## Usage
Once the server is running, you can interact with the store using the client application. Follow the prompts in the client's terminal to register, login, and perform other actions.
