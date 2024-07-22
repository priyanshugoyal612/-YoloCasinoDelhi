# Yolo Casino Delhi Game by Priyanshu Goyal

This project implements a betting game called Delhi for Yolo Group. Players send a bet and a whole number between 1 and 100.

## Features
- **Random Number Generation**: Yolo Casino Delhi generates a random whole number between 1 and 100. If the player's number is greater, it calculates the win and sends it back to the player.
- **Win Calculation**: The win is calculated using the formula: `win = bet * (99 / (100 - number))`. For example, if a player selects number 50 and bets 40.5, the win would be 80.19.

## Technical Details
- **Framework**: Spring Boot 3
- **Build Tool**: Maven
- **Programming Language**: Java 21
- **Utilities**: Lombok

## Prerequisites
- Java Development Kit (JDK) version 21
- Git
- Maven

## Installation

To run the Yolo Casino Delhi application, follow these steps:

1. **Clone the repository**:
     git clone https://github.com/priyanshugoyal612/YoloCasinoDelhi.git

2. **Navigate to the project directory:**
cd YoloCasinoDelhi

3. **Start the application:**
      **On Windows:**
          mvnw.cmd spring-boot:run
      **On Unix:**
          mvnw spring-boot:run


Access URL and endpoint - http://localhost:8080/api/v1/game/bet

## Sample Request 

```json
{
    "number": 7,
    "bet": 99
}
```
## Sample Response
```json
{
    "win": 105.3870967710
}
```

