package com.example.lifeisagame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void getBalanceShoudBeEqual1000() {
        User user = new User("John", "john@gmail.com", 1000, 0);

        assertEquals(1000, user.getBalance());
    }

    @Test
    void itemsArrayListTest() {
        User user = new User("Michael", "Ailias", 1000, 0);

        Item item = new Item("Sword", 100);
        Item item2 = new Item("Shield", 200);

        user.addItem(item);
        user.addItem(item2);

        assertEquals(2, user.getItems().size());

        assertEquals("Sword", user.getItems().get(0).getName());
        assertEquals(100, user.getItems().get(0).getPrice());

        assertEquals("Shield", user.getItems().get(1).getName());
        assertEquals(200, user.getItems().get(1).getPrice());
    }
}