package com.lsl.nature.framework.aop;

public class RailwayStation implements TicketService {

    public void sellTicket(){
        System.out.println("售票............");
        if(System.currentTimeMillis()%2==0){
//            Thread.dumpStack();
            throw new RuntimeException();
        }
    }

    public void inquire() {
        System.out.println("问询.............");
    }

    public void withdraw() {
        System.out.println("退票.............");
    }
}
