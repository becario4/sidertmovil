package com.sidert.sidertmovil;

import com.sidert.sidertmovil.database.entities.SolicitudCampana;
import com.sidert.sidertmovil.utils.Miscellaneous;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void checkAtomicReference() {
        AtomicReference<SolicitudCampana> solicitudCampanaAtomicReference = new AtomicReference<>();
        SolicitudCampana solicitudCampana = new SolicitudCampana();
        solicitudCampanaAtomicReference.set(solicitudCampana);


        SolicitudCampana solicitudCampana2 = solicitudCampanaAtomicReference.get();
        solicitudCampana2.setNombreReferido("ROBERTO");


        assertEquals(solicitudCampana.getNombreReferido(), solicitudCampana2.getNombreReferido());

    }


    @Test
    public void validarCurp00() {
        HashMap<Integer, String> curp = new HashMap<>();
        curp.put(0, "VICTOR MANUEL");
        curp.put(1, "ALVAREZ");
        curp.put(2, "GIL");
        curp.put(3, "1992-09-29");
        curp.put(4, "HOMBRE");
        curp.put(5, "VERACRUZ");

        String curpGenerada = Miscellaneous.GenerarCurp(curp);
        assertEquals("AAGV920929HVZLLC01", curpGenerada);
        assertTrue(Miscellaneous.CurpValidador(curpGenerada));
    }

    @Test
    public void validarCurp01() {
        HashMap<Integer, String> curp = new HashMap<>();
        curp.put(0, "ROBERTO");
        curp.put(1, "DOMINGUEZ");
        curp.put(2, "CAZARIN");
        curp.put(3, "1989-05-30");
        curp.put(4, "HOMBRE");
        curp.put(5, "VERACRUZ");

        String curpGenerada = Miscellaneous.GenerarCurp(curp);
        assertEquals("DOCR890530HVZMZB02", curpGenerada);
        assertTrue(Miscellaneous.CurpValidador(curpGenerada));
    }

    @Test
    public void validarCurp02() {
        HashMap<Integer, String> curp = new HashMap<>();
        curp.put(0, "MIGUEL");
        curp.put(1, "LOZANO");
        curp.put(2, "DOMINGUEZ");
        curp.put(3, "2016-8-17");
        curp.put(4, "HOMBRE");
        curp.put(5, "VERACRUZ");

        String curpGenerada = Miscellaneous.GenerarCurp(curp);
        assertEquals("LODM160817HVZZMGA4", curpGenerada);
        assertTrue(Miscellaneous.CurpValidador(curpGenerada));

    }

    @Test
    public void validarCurp03() {
        HashMap<Integer, String> curp = new HashMap<>();
        curp.put(0, "ESTEBAN");
        curp.put(1, "DOMINGUEZ");
        curp.put(2, "ENRIQUEZ");
        curp.put(3, "2011-6-5");
        curp.put(4, "HOMBRE");
        curp.put(5, "VERACRUZ");

        String curpGenerada = Miscellaneous.GenerarCurp(curp);
        assertEquals("DOEE110605HVZMNSA4", curpGenerada);
        assertTrue(Miscellaneous.CurpValidador(curpGenerada));
    }

    @Test
    public void validarCurp04() {
        HashMap<Integer, String> curp = new HashMap<>();
        curp.put(0, "ERICK AGUSTIN");
        curp.put(1, "GALINDO");
        curp.put(2, "CASANOVA");
        curp.put(3, "1987-05-22");
        curp.put(4, "HOMBRE");
        curp.put(5, "VERACRUZ");

        String curpGenerada = Miscellaneous.GenerarCurp(curp);
        assertEquals("GACE870522HVZLSR03", curpGenerada);
        assertTrue(Miscellaneous.CurpValidador(curpGenerada));
    }


}