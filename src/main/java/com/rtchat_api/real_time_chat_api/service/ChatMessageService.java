package com.rtchat_api.real_time_chat_api.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final Firestore db;

    public ChatMessageService(){
        this.db = FirestoreClient.getFirestore();
    }

    public String salvarChatMessage(ChatMessage chatMessage) throws ExecutionException, InterruptedException {
        CollectionReference collection = db.collection("mensagens");
        DocumentReference docRef = collection.document();

        Map<String, Object> data = new HashMap<>();
        data.put("room_id", chatMessage.getRoom_id());
        data.put("content", chatMessage.getContent());
        data.put("sender_id", chatMessage.getSender_id());
        data.put("receiver_id", chatMessage.getReceiver_id());
        data.put("timestamp", chatMessage.getTimestamp());

        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();

        return docRef.getId();
    }

    public List<ChatMessage> pegarChatMessages(Long room_id) throws ExecutionException, InterruptedException{
        CollectionReference messagesRef = db.collection("mensagens");
        Query query = messagesRef
                .whereEqualTo("room_id",room_id)
                .orderBy("timestamp", Query.Direction.ASCENDING);

        ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshotFuture.get().getDocuments();

        List<ChatMessage> messages = new ArrayList<>();
        for (QueryDocumentSnapshot document: documents){
            messages.add(document.toObject(ChatMessage.class));
        }

        return messages;
    }

}
