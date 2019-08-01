[![Build Status](https://travis-ci.org/parijatmukherjee/workforce.svg?branch=master)](https://travis-ci.org/parijatmukherjee/workforce)

# Workforce Optimization Tool


## The Problem Statement

Build a workforce optimization tool for one of our cleaning partners! Our partner has
contracts with several structures all around Berlin. These structures are all of varying size
(measured in rooms). The workforce of our partner is made up of Senior Cleaners and
Junior Cleaners. Senior Cleaners have a higher work capacity than Junior Cleaners. Our
partner is free to decide how many Senior and Junior Cleaners he is sending to clean a
structure but there always needs to be at least one Senior cleaner at every structure to lead
the juniors. The goal is to minimize overcapacity at every structure.
Given an array of structure sizes (in no. of rooms) as well as work capacities of Junior and
Senior Cleaners your program should present the optimal numbers of Juniors and Seniors
for every structure.
Input:
- array of rooms (int) for every structure
- cleaning capacity Junior Cleaner (int)
- cleaning capacity Senior Cleaner (int)
We do not have cleaning providers with more than 100 structures in their portfolio. None of
the structures will have more than 100 rooms.

## Sample Input

```json
{ 
	"rooms": [35, 21, 17, 28], 
	"senior" : 10, 
	"junior": 6 
}
```

## Sample Output
```json
[ 
	{
		"senior": 3, 
		"junior": 1
	}, 
	{
		"senior": 1, 
		"junior": 2
	}, 
	{
		"senior": 2, 
		"junior": 0
	}, 
	{
		"senior": 1, 
		"junior": 3
	} 
]
```

## Solution

To minimize the over-capacity, we need to find the minimum total capacity by the set of seniors and juniors which is equal or just above the each sum of the rooms in the array.

For each sum of the rooms in the array,

Let us assume, the optimum combination of seniors (including the minimum count of seniors and juniors) and juniors are X and Y respectively.

Also, let Z be the total capacity, which is contributed by X number of seniors and Y number of juniors.

Z should be equal or greater than the sum of the rooms in the structure array for which we are calculating the combination.

So, we need to find the Min(Z) for each sum of the rooms where,
<br/>
<br/>
Z = (X \* senior cleaning capacity) + (Y \* junior cleaning capacity)
<br/>
and
<br/>
Z >= Each Sum of the Rooms in the Structure Array


------------


The process of getting the optimum combination of senior and junior cleaners, and also to reduce the time of code execution, the logic is implemented through three steps - 

1. The total capacity of minimum number of seniors or juniors are deducted from each sum of the rooms present in the array.
2. It first tries if only the seniors can complete the remaining rooms without over-capacity.
3. Otherwise, it tries to find, if only juniors can complete the remaining rooms without over-capacity.
4. Else, it tries to find the optimum combination of seniors and juniors whose total capacity is the closest number to the total number of rooms.

## The Application

The above solution is implemented using the Spring Boot Framework. The application can be divided into different parts as below :

| Serial Number  | Parts  |  Comments |
| ------------ | ------------ | ------------ |
| 1  |  Controller |  Handles the REST APIs |
|  2 |  Exception | Handles all the custom exceptions including Global Exception Handler  |
| 3 |  Model | Contains the model objects used in the system|
| 4 | Processor | Contains the Interface and the Implementation of the Business Logic |
| 5 | Utility | The utility functions | 

## The Configurations

The application supports configurable number of minimum senior or junior cleaners required for each optimum set. By default, the minimum 
senior count and minimum junior count are set to 1 and 0 respectively.

The configuration can be modified through ../src/main/resource/application.properties
```
workforce.minimumSeniorCount=1
workforce.minimumJuniorCount=0
```

There are two other configurations present in the same file to configure the server port number and the timestamp format.

```
server.port=8999
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
```

## Build & Run

### Prerequisite
1. Java 8
2. Maven

### Commands

This is a Maven project and can be built by using - 
```bash
mvn clean package
```
To run the jar file -
```bash
java -jar workforce-0.0.1-SNAPSHOT.jar
```

As, this is a Spring Boot application, it can be run directly from the source like below -
```bash
mvn spring-boot:run
```
Run the Spring Boot application with configuration as the command line arguments -
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--workforce.minimumSeniorCount=1,--workforce.minimumJuniorCount=0"
```
## REST APIs

The default port is set to 8999. The port can be changed through the "server.port" configuration.

### Request
```
POST http://localhost:8999/workforce/optimization
```
```json
{ 
	"rooms" : [34,39,20], 
	"senior": 10, 
	"junior": 6
}
```

### Response
```json
[
    {
        "senior": 1,
        "junior": 4
    },
    {
        "senior": 4,
        "junior": 0
    },
    {
        "senior": 2,
        "junior": 0
    }
]
```