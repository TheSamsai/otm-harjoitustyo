/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sami
 */
public class KassapaateTest {
    Kassapaate paate;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(400);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void kassaPaatteessaOikeaRahamaara() {
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void kateisMaksuEdullinenToimii() {
        paate.syoEdullisesti(240);
        assertEquals(paate.kassassaRahaa(), 100240);
    }
    
    @Test
    public void kateisMaksuEdullinenVaihtorahaOikein() {
        int vaihtoraha = paate.syoEdullisesti(260);
        assertEquals(vaihtoraha, 20);
    }
    
    @Test
    public void kateisMaksuMaukasVaihtorahaOikein() {
        int vaihtoraha = paate.syoMaukkaasti(410);
        assertEquals(vaihtoraha, 10);
    }
    
    @Test
    public void kateisMaksuEdullinenEiRiittavaKassassaOikeaRahamaara() {
        int palautus = paate.syoEdullisesti(230);
        assertEquals(paate.kassassaRahaa(), 100000);
        
    }
    
    @Test
    public void kateisMaksuEdullinenEiRiittavaOikeaVaihtoraha() {
        int palautus = paate.syoEdullisesti(230);
        assertEquals(palautus, 230);
    }
    
    @Test
    public void kateisMaksuEdullinenEiRiittavaMyydytEiMuutu() {
        int palautus = paate.syoEdullisesti(230);
        assertEquals(paate.edullisiaLounaitaMyyty(), 0);
    }
    
    
    
    @Test
    public void kateisMaksuMaukasToimii() {
        paate.syoMaukkaasti(400);
        assertEquals(paate.kassassaRahaa(), 100400);
    }
    
    @Test
    public void kateisMaksuMaukasEiRiittavaKassassaOikeaRahamaara() {
        int palautus = paate.syoMaukkaasti(230);
        assertEquals(paate.kassassaRahaa(), 100000);
        
    }
    
    @Test
    public void kateisMaksuMaukasEiRiittavaOikeaVaihtoraha() {
        int palautus = paate.syoMaukkaasti(230);
        assertEquals(palautus, 230);
    }
    
    @Test
    public void kateisMaksuMaukasEiRiittavaMyydytEiMuutu() {
        int palautus = paate.syoMaukkaasti(230);
        assertEquals(paate.maukkaitaLounaitaMyyty(), 0);
    }
    
    @Test
    public void maksukorttiEdullinenOnnistuu() {
        boolean onnistui = paate.syoEdullisesti(kortti);
        
        assertTrue(onnistui);
    }
    
    @Test
    public void maksukorttiEdullinenEiMuutaKassanRahamaaraa() {
        boolean onnistui = paate.syoEdullisesti(kortti);
        
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void maksukorttiEdullinenMuuttaaSaldoa() {
        boolean onnistui = paate.syoEdullisesti(kortti);
        
        assertEquals(kortti.saldo(), 400-240);
    }
    
    @Test
    public void maksukorttiEdullinenMuuttaaMyytyja() {
        boolean onnistui = paate.syoEdullisesti(kortti);
        
        assertEquals(paate.edullisiaLounaitaMyyty(), 1);
    }
    
    @Test
    public void maksukorttiMaukasOnnistuu() {
        boolean onnistui = paate.syoMaukkaasti(kortti);
        
        assertTrue(onnistui);
    }
    
    @Test
    public void maksukorttiMaukasEiMuutaKassanRahamaaraa() {
        boolean onnistui = paate.syoMaukkaasti(kortti);
        
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void maksukorttiMaukasMuuttaaSaldoa() {
        boolean onnistui = paate.syoMaukkaasti(kortti);
        
        assertEquals(kortti.saldo(), 0);
    }
    
    @Test
    public void maksukorttiMaukasMuuttaaMyytyja() {
        boolean onnistui = paate.syoMaukkaasti(kortti);
        
        assertEquals(paate.maukkaitaLounaitaMyyty(), 1);
    }
    
    @Test
    public void maksukorttiEdullinenEiRiittavaEiOnnistu() {
        paate.syoMaukkaasti(kortti);
        
        boolean onnistui = paate.syoEdullisesti(kortti);
        
        assertFalse(onnistui);
    }
    
    @Test
    public void maksukorttiEdullinenEiRiittavaEiMuutaSaldoa() {
        paate.syoMaukkaasti(kortti);
        
        boolean onnistui = paate.syoEdullisesti(kortti);
        
        assertEquals(kortti.saldo(), 0);
    }
    
    @Test
    public void maksukorttiEdullinenEiRiittavaEiMuutaMyytyja() {
        paate.syoMaukkaasti(kortti);
        
        boolean onnistui = paate.syoEdullisesti(kortti);
        
        assertEquals(paate.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void maksukorttiMaukasEiRiittavaEiOnnistu() {
        paate.syoMaukkaasti(kortti);
        
        boolean onnistui = paate.syoMaukkaasti(kortti);
        
        assertFalse(onnistui);
    }
    
    @Test
    public void maksukorttiMaukasEiRiittavaEiMuutaSaldoa() {
        paate.syoMaukkaasti(kortti);
        
        boolean onnistui = paate.syoMaukkaasti(kortti);
        
        assertEquals(kortti.saldo(), 0);
    }
    
    @Test
    public void maksukorttiMaukasEiRiittavaEiMuutaMyytyja() {
        paate.syoMaukkaasti(kortti);
        
        boolean onnistui = paate.syoMaukkaasti(kortti);
        
        assertEquals(paate.maukkaitaLounaitaMyyty(), 1);
    }
    
    @Test
    public void myydytEdullisetLounaatKasvavat() {
        paate.syoEdullisesti(240);
        assertEquals(paate.edullisiaLounaitaMyyty(), 1);
    }
    
    @Test
    public void myydytMaukkaatLounaatKasvavat() {
        paate.syoMaukkaasti(400);
        assertEquals(paate.maukkaitaLounaitaMyyty(), 1);
    }
    
    @Test
    public void maksuKortilleLataaminenKasvattaaKassanRahamaaraa() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(paate.kassassaRahaa(), 100100);
    }
    
    @Test
    public void maksuKortilleLataaminenMuuttaaSaldoa() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(kortti.saldo(), 500);
    }
    
    @Test
    public void maksuKortilleNegatiivisenLataaminenEiMuutaSaldoa() {
        paate.lataaRahaaKortille(kortti, -5);
        assertEquals(kortti.saldo(), 400);
    }
    
    @Test
    public void maksuKortilleNegatiivisenLataaminenEiMuutaKassanRahamaaraa() {
        paate.lataaRahaaKortille(kortti, -5);
        assertEquals(paate.kassassaRahaa(), 100000);
    }
}
