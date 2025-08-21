# Swing Banking System

## Project structure
BankingSystem/
├── Main.java
├── README.md
├── UserRoles.xlsx
├── bank.db
├── controller
│   ├── AccountController.java
│   ├── DashboardController.java
│   └── LoginController.java
├── lib
│   ├── javafx.base.jar
│   ├── javafx.controls.jar
│   ├── javafx.fxml.jar
│   ├── javafx.graphics.jar
│   └── sqlite-jdbc-3.50.3.0.jar
├── model
│   ├── Account.java
│   ├── Database.java
│   └── User.java
└── view
    ├── AccountFrame.java
    ├── CreateAccountFrame.java
    ├── DashboardFrame.java
    ├── LoginFrame.java
    ├── account.fxml
    ├── dashboard.fxml
    └── login.fxml

## Prerequisites
- JDK 8+ installed and on PATH
- Download `sqlite-jdbc-3.50.3.0.jar` and place it in `lib/` folder

## Compile (Windows/Linux/Mac)
cd BankingSystem
javac -cp ".;lib\sqlite-jdbc-3.50.3.0.jar" model\*.java controller\*.java view\*.java Main.java


## Run For Windows Command Prompt:
java -cp ".;lib\sqlite-jdbc-3.50.3.0.jar" Main

## Default Login
- Username: admin
- Password: admin
- Role: Admin

> The database file `bank.db` is created automatically in the project root on first run.

## Other Login
## Login    Password	Role
admin   admin   	Admin
qqqq	    1111	    Customer
admin1	    Admin1@1	Admin
zzzz	    Zzzz1111@	Manager
xxxx	    Xxxx1111@	Teller
