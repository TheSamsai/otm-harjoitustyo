package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinAlkusaldoOikein() {
        assertEquals(10, kortti.saldo());      
    }
    
    @Test
    public void saldonLataaminenToimii() {
        kortti.lataaRahaa(10);
        assertEquals(20, kortti.saldo());         
    }
    
    @Test
    public void rahanOttaminenVahentaaOikein() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.5", kortti.toString());         
    }
    
    @Test
    public void rahanOttaminenEiVieNegatiiviselle() {
        kortti.otaRahaa(400);
        assertEquals("saldo: 0.10", kortti.toString());         
    }
    
    @Test
    public void rahanOttaminenPalauttaaTrue() {
        assertTrue(kortti.otaRahaa(5));         
    }
    
    @Test
    public void rahanOttaminenPalauttaaFalse() {
        assertFalse(kortti.otaRahaa(400));         
    }
}
