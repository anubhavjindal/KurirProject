package com.anubhavj.kurir.services;

import com.anubhavj.kurir.infrastructure.ServiceResponse;
import com.anubhavj.kurir.services.entities.Message;

import java.util.List;

/**
 * Created by Udgeeth on 19-10-2016.
 */
public final class Messages {
    private Messages()
    {

    }
    public static class DeleteMessageRequest
    {
        public int MessageId;

        public DeleteMessageRequest(int messageId){
            MessageId = messageId;
        }
    }
    public static class DeleteMessageResponse extends ServiceResponse {
        public int MessageId;
    }
    public static class SearchMessagesRequest {
        public  String FromContactId;
        public  boolean IncludeSentMessages;
        public  boolean IncludeReceivedMessages;

        public SearchMessagesRequest(String fromContactId, boolean includeSentMessages, boolean includeReceivedMessages) {
            FromContactId = fromContactId;
            IncludeSentMessages = includeSentMessages;
            IncludeReceivedMessages = includeReceivedMessages;
        }

        public SearchMessagesRequest(boolean includeSentMessages, boolean includeReceivedMessages) {
            IncludeSentMessages = includeSentMessages;
            IncludeReceivedMessages = includeReceivedMessages;
        }
    }
    public static class SearchMessagesResponse extends ServiceResponse{
        public List<Message> Messages;
    }
}
