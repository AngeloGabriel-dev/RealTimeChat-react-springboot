A Real-Time Chat made using React for the client side and Spring Boot for the server side. The project uses Firestore to store messages, Firebase Storage to store files such as profile pictures, and a MySQL database to store users, rooms, and friendships. It also includes a WebSocket API for bidirectional communication between the server and client. The project uses a JWT bearer token for authentication. <br>
<br>
My goal with this project was to learn how to build real-time applications, and I really enjoyed doing that.

# Domain Diagram
<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/images/DomainDiagram.drawio.png"/>

# ER Diagram
<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/images/ERDiagram.drawio.png"/>

# Concepts Learned in This Project

The concept of dependency injection became clear when I was implementing the message service and needed to query the Firestore database. At first, I instantiated the Firestore connection directly inside the service. After some study, I realized it would be better to define a repository interface (e.g., `MessageRepository`) and provide a Firestore-based implementation. This makes it easy to switch to another database by simply adding a new implementation, improving flexibility and maintainability.

I also learned about prop drilling and how to avoid it. In this project, I used Zustand to manage global state, which simplified data sharing across components.

I explored WebSocket APIs and SockJS to enable real-time communication between the client and the server.

Another important concept I applied was event-driven architecture. Instead of tightly coupling message handling and notification logic, I designed the system so that events (such as “message sent”) are published and then consumed by other parts of the system. This approach improves scalability and decoupling, and it fits well with real-time features like chat and notifications.

My next step will likely be studying Kafka and other messaging platforms to deepen my understanding of event-driven systems.

This project still has room to grow, so this list will probably expand over time.


 # How to implement
To implement this system on your machine, you will need to have MySQL installed on your machine, but you don't need to create the tables mannualy because hibernate will do it for you when you start the application. You will also need Firestore and Firebase Storage to store the messages and profile pictures.

# App Screens
The application is not focused on UI/UX. The front end was built as a minimal interface to support the features I wanted to implement. The main goal of this project was to learn software engineering concepts rather than design or user experience. In the future, I may improve the UI/UX to make the project more complete and closer to a production-ready application.

<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/images/chat.PNG"/>
<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/images/chat2.PNG"/>
<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/images/chat3.PNG"/>
