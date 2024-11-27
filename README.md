
# Java-Turtle
The popular Turtle app, recreated in JAVA to run using LOGO programming commands.

# Introduction
The Turtle app is a graphical program that allows users to control a virtual turtle and draw shapes and designs in a defined interactive arena. On the app, users issue commands to the turtle for various functionalities, which are parsed and implemented into the arena for multiple outputs. The commands are simple, making it easy to learn and use for all levels. It is a helpful tool in introducing beginners to programming and graphical design concepts.


# Features
A Turtle, which the user can control to move across the drawing area and perform actions.
An Arena, which allows us to visually observe the Turtle’s outputs to our commands.
A Command panel, where commands can be entered to be parsed by the backend code. It also includes a view panel that displays the executed command history.
The turtle responds to commands for moving in all directions, pen up-down, changing color, drawing shapes, and custom command loops.
A toolbar, providing functionalities such as:
- To open and save code files from the interface.
- A user guide to understand command syntax and usage.
- An about section for the application.



**This application was built using Java 21.0.5 and libraries supported in this version.**


# Getting Started
To start the application, double-click on the JAR file, or run:

java -jar turtlev1.jar


# System-Requirements
To run the application, you require a Java version 9 or above installed on your PC.
Check your Java version from the Command Terminal:
java - -version



# Architecture
The application is built on a Model-View-Controller design pattern, which has distinct layers for core functions, GUI, and User interactions.


****Model-Layer****
Turtle class, which encapsulates its state properties and provides methods.
Arena class provides the drawing canvas and renders turtle movements.
**Controller-Layer******
Parser class, which interprets user-entered commands and converts them into actions performed by the Turtle and Arena.
Chain compatibility with different command syntaxes.
**View-Layer******
GUI framework, built with Swing and AWT libraries for cross-platform compatibility. Manages all graphical elements.





![mermaid-diagram-2024-11-25-225138](https://github.com/user-attachments/assets/71b7e36c-1c49-42bd-bb27-2582abbc47c6)








