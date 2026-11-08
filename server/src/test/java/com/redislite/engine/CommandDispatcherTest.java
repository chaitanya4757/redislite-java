package com.redislite.engine;

import com.redislite.command.Command;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.redislite.command.Reply;
import com.redislite.store.Store;

public class CommandDispatcherTest {
    
    @Test 
    void pingRepliesPong(){
        CommandDispatcher dispatcher = new CommandDispatcher(new Store());
        Reply reply = dispatcher.execute(new Command("PING",List.of()));
        assertEquals(Reply.simple("PONG"), reply);
    }
    @Test 
    void echoRepliesWithTheSameText(){
        CommandDispatcher dispatcher = new CommandDispatcher(new Store());
        Reply reply = dispatcher.execute(new Command("ECHO", List.of("Hi")));
        assertEquals(Reply.bulk("Hi"), reply);
    }
    @Test 
    void setRepliesOk(){
        CommandDispatcher dispatcher = new CommandDispatcher(new Store());
        Reply reply = dispatcher.execute(new Command("SET", List.of("a","1")));
        assertEquals(Reply.ok(), reply);
    }
    @Test 
    void getReturnsValueAfterSet(){
        CommandDispatcher dispatcher = new CommandDispatcher(new Store());
        dispatcher.execute(new Command("SET", List.of("a","1")));
        Reply reply = dispatcher.execute(new Command("GET", List.of("a")));
        assertEquals(reply.bulk("1"), reply);
    }
    @Test 
    void getMissingKeyReturnNull(){
        CommandDispatcher dispatcher= new CommandDispatcher(new Store());
        Reply reply = dispatcher.execute(new Command("GET", List.of("zzz")));
        assertEquals(Reply.nullBulk(), reply);
    }
    @Test 
    void getWithoutKeyReturnsError(){
        CommandDispatcher dispatcher =  new CommandDispatcher(new Store());
        Reply reply = dispatcher.execute(new Command("GET", List.of()));
        assertEquals(Reply.Type.ERROR, reply.type());
    }
}
