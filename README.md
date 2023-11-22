# Java RMI Online Store 

## Project Description

This project is a Java application using Remote Method Invocation (RMI) to create a basic online store. It consists of two main components: a server that manages the store's operations and inventory, and a client that interacts with the server to perform actions like user registration, login, and item management.

## Features

- User registration and login
- Browsing items in the store
- Adding items to a shopping cart
- Updating items in inventory
- Admin functionality for managing inventory

## Prerequisites

To run this project, you will need:
- Java Development Kit (JDK), version 8 or newer
- Basic knowledge of Java and RMI concepts

## Compiling the Project

The project comes with a `MAKEFILE` for easy compilation. To compile the project, run:
This command compiles the source files and places the output in the `bin` directory. The `MAKEFILE` is configured to handle various tasks related to building and running the application.

### Available Commands

- `make compile`: Compiles the source files and stores the compiled classes in the `bin` directory.
- `make run-server`: Starts the server component of the RMI application. Make sure to run this before starting the client.
- `make run-client`: Launches the client component, allowing interaction with the server through RMI.
- `make start-rmi`: Starts the RMI registry service on the default port (1099). This service is required for the RMI server and client to communicate.
- `make clean`: Cleans up the compiled files from the `bin` directory, effectively resetting the compilation output.

## Usage

Once the server is running, you can interact with the store using the client application. Follow the prompts in the client's terminal to register, login, and perform other actions.



Adjust paths and names according to your project structure.
