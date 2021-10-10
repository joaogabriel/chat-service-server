# chat-service-server

A lightweight server for chat messages write in Java.

## Motivation

## Quick Start

## Stack

- Java 14
- Spring Boot

## Technologies

- REST
- WebSocket
- STOMP

## Util links

- https://shivanshugoyal0111.medium.com/pagination-in-cassandra-b7e45ec2656a

## API

### Rooms

#### Create a new room:

- URI: ```rooms/open```
- Payload:

```
{
	"sender": {
		"token": "123",
		"name": "jovem",
		"color": "abc"
	},
	"recipient": {
		"token": "456",
		"name": "outro jovem",
		"color": "abc"
	}
}
```

- Returns: ```token``` of room

#### Close existing room:

- URI: ```rooms/{token}/close```
- Returns: empty
