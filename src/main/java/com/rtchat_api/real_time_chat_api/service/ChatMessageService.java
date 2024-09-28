package com.rtchat_api.real_time_chat_api.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

}
