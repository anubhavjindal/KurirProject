package com.anubhavj.kurir.services;

import com.anubhavj.kurir.infrastructure.ServiceResponse;
import com.anubhavj.kurir.services.entities.ContactRequest;
import com.anubhavj.kurir.services.entities.UserDetails;

import java.util.List;

public final class Contacts {
    public Contacts() {

    }

    public static class GetContactRequestsRequest {
        public boolean fromUs;
    }

    public static class GetContactRequestsResponse extends ServiceResponse {
        public List<ContactRequest> Requests;
    }

    public static class GetContactsRequest {
        public boolean IncludePendingContacts;

        public GetContactsRequest(boolean includePendingContacts) {
            IncludePendingContacts = includePendingContacts;
        }
    }

    public static class GetContactsResponse extends ServiceResponse {
        public List<UserDetails> Contacts;
    }

    public static class SendContactRequestRequest {
        public int userId;

        public SendContactRequestRequest(int userId) {
            this.userId = userId;
        }
    }

    public static class SendContactRequestResponse extends ServiceResponse {

    }

    public static class RespondToContactRequestRequest {
        public int ContactRequestId;
        public boolean Accept;

        public RespondToContactRequestRequest(int contactRequestId, boolean accept) {
            ContactRequestId = contactRequestId;
            Accept = accept;
        }
    }

    public static class RespondToContactRequestResponse extends ServiceResponse {

    }
}
