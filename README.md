A Real Time Chat made using react for the client side and spring boot to the server side. The project contains a firestore to store messages, firestorage to store files such profile pictures and a mysql database to store users, rooms and friendships.
The project contains a web socket api to do the bidirectional comunication between the server and client.

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

### messages
room_id number <Br>