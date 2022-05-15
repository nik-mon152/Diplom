package com.example.mycar.ui.Service.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZapravkaTest {

    @Test
   public void getView_Fuel() {
        Zapravka zapravka = new Zapravka("AИ-92",
                "43454", "4454444", "102", "2555", "рврнр", "13.05.2022");
        assertEquals("AИ-92", zapravka.getView_Fuel());
    }

    @Test
    public void getFuel_quantity() {
        Zapravka zapravka = new Zapravka("AИ-92",
                "43454", "4454444", "102", "2555", "рврнр", "13.05.2022");
        assertEquals("43454", zapravka.getFuel_quantity());
    }

    @Test
    public void getRefueling_amount() {
        Zapravka zapravka = new Zapravka("AИ-92",
                "43454", "4454444", "102", "2555", "рврнр", "13.05.2022");
        assertEquals("4454444", zapravka.getRefueling_amount());
    }

    @Test
    public void getPrice_liter() {
        Zapravka zapravka = new Zapravka("AИ-92",
                "43454", "4454444", "102", "2555", "рврнр", "13.05.2022");
        assertEquals("102", zapravka.getPrice_liter());
    }

    @Test
    public void getMileage() {
        Zapravka zapravka = new Zapravka("AИ-92",
                "43454", "4454444", "102", "2555", "рврнр", "13.05.2022");
        assertEquals("2555", zapravka.getMileage());
    }

    @Test
    public void getComment() {
        Zapravka zapravka = new Zapravka("AИ-92",
                "43454", "4454444", "102", "2555", "рврнр", "13.05.2022");
        assertEquals("рврнр", zapravka.getComment());
    }

    @Test
    public void getData() {
        Zapravka zapravka = new Zapravka("AИ-92",
                "43454", "4454444", "102", "2555", "рврнр", "13.05.2022");
        assertEquals("13.05.2022", zapravka.getData());
    }
}