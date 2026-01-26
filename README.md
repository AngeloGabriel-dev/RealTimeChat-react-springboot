A Real-Time Chat made using React for the client side and Spring Boot for the server side. The project uses Firestore to store messages, Firebase Storage to store files such as profile pictures, and a MySQL database to store users, rooms, and friendships. It also includes a WebSocket API for bidirectional communication between the server and client. The project uses a JWT bearer token for authentication. <br>
<br>
My goal with this project was to learn how to build real-time applications, and I really enjoyed doing that.

# Table Schemes
### usuarios (users)
id	bigint AI PK <br>
criado_por varchar(255) (created by)<br>
data_criacao	datetime(6) (creation date)<br>
data_modificacao	datetime(6) (modification date)<br>
modificado_por	varchar(255) (modificated by)<br>
nome	varchar(100) (name)<br>
password	varchar(200) <br>
role	enum('ROLE_ADMIN','ROLE_CLIENTE')<br>
username	varchar(100)<br>

### amizades (friendships)
id	bigint AI PK <br>
status	enum('ACEITO','PENDENTE') <br>
amigo_id	bigint FK <br>
usuario_id	bigint FK <br>

### rooms
id	bigint AI PK <br>
nome	varchar(30) <br>
users usuarios[] <br>

# Collection Schemes
### messages
 content text; <br>
 receiver_id number; <br>
 sender_id number; <br>
 room_id number; <br>
 timestamp date; <br>

 # How to implement
To implement this system on your machine, you will need a MySQL database where you will create all the tables specified above. You will also need Firestore and Firebase Storage to store the messages and profile pictures.

<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/chat.PNG"/>
<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/chat2.PNG"/>
<img src="https://github.com/AngeloGabriel-dev/RealTimeChat-react-springboot/blob/main/chat3.PNG"/>
