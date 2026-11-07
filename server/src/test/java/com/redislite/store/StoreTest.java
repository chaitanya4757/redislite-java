package com.redislite.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
public class StoreTest {
    @Test
    void getReturnsNullForMissingKey(){
        Store store = new Store();
        assertNull(store.get("x"));
    }

    @Test
    void setThenGetReturnsValue(){
        Store store = new Store();
        store.set("a", "1");
        assertEquals("1",store.get("a"));
    }

    @Test
    void setOverwritesExistingValue(){
        Store store = new Store();
        store.set("a", "1");
        store.set("a", "2");
        assertEquals("2", store.get("a"));
    }

    @Test
    void deleteRemovesKey(){
        Store store = new Store();
        store.set("a", "1");
        assertTrue(store.delete("a"));
        assertNull(store.get("a"));
    }

    @Test 
    void deleteOfMissingKeyReturnsFalse(){
        Store store = new Store();
        assertFalse(store.delete("x"));
    }

    @Test 
    void existsIsFalseBeforeSetAndTrueAfter(){
        Store store = new Store();
        assertFalse(store.exists("a"));
        store.set("a", "1");
        assertTrue(store.exists("a"));
    }
}
