package com.redislite.engine;

import com.redislite.command.CommandHandler;
import com.redislite.command.Reply;

import com.redislite.command.Command;
import com.redislite.store.Store;

public class CommandDispatcher implements CommandHandler {
    private final Store store;
    public CommandDispatcher (Store store){
        this.store = store;
    }
    @Override 
    public Reply execute(Command command){
        switch (command.name()) {
            case "PING":
                return Reply.simple("PONG");
        
            case "ECHO":
                if(command.args().size() == 0){
                    return Reply.error("Error..! not valid argument!");
                }
                else{
                    String out = command.args().get(0);
                    return Reply.bulk(out);
                }
            case "SET":
                if(command.args().size() == 0 || command.args().size() != 2){
                    return Reply.error("Error..! not valid argument!");
                }
                else {
                    String key = command.args().get(0);
                    String value = command.args().get(1);
                    store.set(key, value);
                    return Reply.ok();
                }
            case "GET":
                if(command.args().size() == 0 || command.args().size() > 1 ){
                    return Reply.error("Error..! not valid argument!");
                }
                String out = store.get(command.args().get(0));
                if(out == null) return Reply.nullBulk();
                else{
                    return Reply.bulk(out);
                }
            case "EXISTS":
                if(command.args().size() != 1){
                    return Reply.error("ERR wrong number of arguments for 'exists' command");
                }
                if(store.exists(command.args().get(0))){
                    return Reply.integer(1);
                }
                else{
                    return Reply.integer(0);
                }
            case "DEL":
                if (command.args().size() != 1) {
                return Reply.error("ERR wrong number of arguments for 'del' command");
                }
                if(store.exists(command.args().get(0))){
                String delOut = command.args().get(0);
                if(store.delete(delOut)){
                    return Reply.integer(1);
                }
                else return Reply.integer(0);
                }
                else return Reply.integer(0);
            default :
            return Reply.error("Invalid command");
        }
    }
}
