package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GT4500Test {

    private GT4500 ship;

    @BeforeEach
    public void init() {
        this.ship = new GT4500();
    }

    @Test
    void fireTorpedo_Single_Success() {
        // Arrange
        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);
        // Assert
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_All_Success() {
        // Arrange
        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);
        // Assert
        assertEquals(true, result);
    }

    // --- ÚJ TESZTESETEK A 80%+ LEFEDETTSÉGHEZ ---

    @Test
    void fireLaser_AlwaysReturnsFalse() {
        // A lézer metódus lefedése (ami jelenleg csak egy return false;)
        boolean result = ship.fireLaser(FiringMode.SINGLE);
        assertEquals(false, result);
    }

    @Test
    void fireTorpedo_Single_Alternating() {
        // Váltakozó tüzelés ellenőrzése
        ship.fireTorpedo(FiringMode.SINGLE); // Első lövés: Primary
        boolean result = ship.fireTorpedo(FiringMode.SINGLE); // Második lövés: Secondary
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_Single_PrimaryEmpty() {
        // Ha az elsődleges tár üres, a másodlagosból lő
        GT4500 emptyPrimaryShip = new GT4500(0, 0.0, 10, 0.0);
        boolean result = emptyPrimaryShip.fireTorpedo(FiringMode.SINGLE);
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_Single_SecondaryEmpty_FallbackToPrimary() {
        // Ez fedi le a teljesen piros blokkot!
        // Az első lövés az elsődlegesből történik, utána a másodlagos jönne, de az üres, így visszavált az elsődlegesre.
        GT4500 shipWithEmptySecondary = new GT4500(10, 0.0, 0, 0.0);
        shipWithEmptySecondary.fireTorpedo(FiringMode.SINGLE); // Primary kilőve
        boolean result = shipWithEmptySecondary.fireTorpedo(FiringMode.SINGLE); // Secondary üres -> újra Primary
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_Single_BothEmpty() {
        // Mindkét tár üres, nem tud lőni SINGLE módban
        GT4500 emptyShip = new GT4500(0, 0.0, 0, 0.0);
        boolean result = emptyShip.fireTorpedo(FiringMode.SINGLE);
        assertEquals(false, result);
    }

    @Test
    void fireTorpedo_Single_BothEmpty_FiredLast() {
        // Mindkét tár üres, de úgy, hogy a wasPrimaryFiredLast = true állapotot is elérjük
        GT4500 ship1 = new GT4500(1, 0.0, 0, 0.0);
        ship1.fireTorpedo(FiringMode.SINGLE); // Kilövi az egyetlent, wasPrimaryFiredLast = true lesz
        boolean result = ship1.fireTorpedo(FiringMode.SINGLE); // Mindkét tár üres most már
        assertEquals(false, result);
    }

    @Test
    void fireTorpedo_All_Empty() {
        // ALL mód hívása üres tárakkal
        GT4500 emptyShip = new GT4500(0, 0.0, 0, 0.0);
        boolean result = emptyShip.fireTorpedo(FiringMode.ALL);
        assertEquals(false, result);
    }
}