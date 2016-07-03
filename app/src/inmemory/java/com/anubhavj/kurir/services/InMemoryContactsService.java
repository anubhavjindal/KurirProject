package com.anubhavj.kurir.services;

import com.anubhavj.kurir.infrastructure.KurirApplication;
import com.anubhavj.kurir.services.entities.ContactRequest;
import com.anubhavj.kurir.services.entities.UserDetails;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class InMemoryContactsService extends BaseInMemoryService {


    public InMemoryContactsService(KurirApplication application) {
        super(application);
    }

    @Subscribe
    public void getContactRequests(Contacts.GetContactRequestsRequest request){
        Contacts.GetContactRequestsResponse response = new Contacts.GetContactRequestsResponse();

        response.Requests = new ArrayList<>();

        for(int i=0;i<3;i++) {
            response.Requests.add(new ContactRequest(i, request.fromUs, createFakeUser(i,false), new GregorianCalendar()));
        }

        postDelayed(response);
    }

    @Subscribe
    public void getContacts(Contacts.GetContactsRequest request){
        Contacts.GetContactsResponse response = new Contacts.GetContactsResponse();
        response.Contacts = new ArrayList<>();

        for (int i=0;i<10;i++) {
            response.Contacts.add(createFakeUser(i,true));
        }

        postDelayed(response);
    }

    @Subscribe
    public void sendContactsRequest(Contacts.SendContactRequestRequest request){
        if (request.userId == 2){
            Contacts.SendContactRequestResponse response = new Contacts.SendContactRequestResponse();
            response.setOperationError("Something bad happened!");
            postDelayed(response);
        }
        else {
            postDelayed(new Contacts.SendContactRequestResponse());
        }
    }

    @Subscribe
    public void respondToContactsRequest(Contacts.RespondToContactRequestRequest request) {
        postDelayed(new Contacts.RespondToContactRequestResponse());
    }

    private UserDetails createFakeUser(int id,boolean isContact) {
        String idString = Integer.toString(id);
        return new UserDetails(
                id,
                isContact,
                "Contact "+idString,
                "Contact"+idString,
                "http://gravatar.com.avatar/"+idString+"id=identicon&s=64");
    }
}
