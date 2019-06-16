package com.virros.diplom.message.response;

import com.virros.diplom.model.Event;

public class EventMessage {

    private Event event;
    private Long employer_id;
    private String employer_name;

    public EventMessage() {
    }

    public EventMessage(Event event, Long employer_id, String employer_name) {
        this.event = event;
        this.employer_id = employer_id;
        this.employer_name = employer_name;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Long getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(Long employer_id) {
        this.employer_id = employer_id;
    }

    public String getEmployer_name() {
        return employer_name;
    }

    public void setEmployer_name(String employer_name) {
        this.employer_name = employer_name;
    }

}
