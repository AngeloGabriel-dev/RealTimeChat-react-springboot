package com.rtchat_api.real_time_chat_api.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.exception.ChatMessagePersistenceException;
import org.springframework.stereotype.Repository;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class ChatMessageFirestoreRepository implements ChatMessageRepository{
    private final Firestore db;

    public ChatMessageFirestoreRepository(Firestore db) {
        this.db = db;
    }

    @Override
    public String save(ChatMessage message){
        CollectionReference collection = db.collection("mensagens");
        DocumentReference docRef = collection.document();

        Map<String, Object> data = new HashMap<>();
        data.put("room_id", message.getRoom_id());
        data.put("content", message.getContent());
        data.put("sender_id", message.getSender_id());
        data.put("receiver_id", message.getReceiver_id());
        data.put("timestamp", message.getTimestamp());
        try{
            ApiFuture<WriteResult> result = docRef.set(data);
            result.get();

            return docRef.getId();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ChatMessagePersistenceException("Operação interrompida", e);
        }
        catch (ExecutionException e) {
            throw new ChatMessagePersistenceException("Erro no Firestore", e);
        }
    }

    @Override
    public List<ChatMessage> findAllChatMessagesByRoomId(Long room_id){
        CollectionReference messagesRef = db.collection("mensagens");
        Query query = messagesRef
                .whereEqualTo("room_id",room_id)
                .orderBy("timestamp", Query.Direction.ASCENDING);
        try{
            ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshotFuture.get().getDocuments();

            List<ChatMessage> messages = new ArrayList<>();
            for (QueryDocumentSnapshot document: documents){
                messages.add(document.toObject(ChatMessage.class));
            }

            return messages;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ChatMessagePersistenceException("Operação interrompida", e);
        }
        catch (ExecutionException e) {
            throw new ChatMessagePersistenceException("Erro no Firestore", e);
        }


    }
}
