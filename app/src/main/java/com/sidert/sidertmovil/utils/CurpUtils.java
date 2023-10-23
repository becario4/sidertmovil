package com.sidert.sidertmovil.utils;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CurpUtils {

    private static final String DICCIONARIO = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private static final Set<String> SEXOS = new HashSet<>(Arrays.asList("HOMBRE", "MUJER"));
    private static final Set<String> EXCLUDED_WORDS = new HashSet<>(Arrays.asList("DA", "DAS", "DE", "DEL", "DER", "DI", "DIE", "DD", "EL", "LA", "LOS", "LAS", "LE", "LES", "MAC", "MC", "VAN", "VON", "Y"));
    private static final Set<Character> VOWELS = new HashSet<>(Arrays.asList('A', 'E', 'I', 'O', 'U'));
    private static final Set<Character> CONSONANTS = new HashSet<>(Arrays.asList('B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'));
    private static final Set<Character> SPECIAL_CHARS = new HashSet<>(Arrays.asList('/', '-', '.', 'Ñ'));
    private static final Set<String> EXCLUDED_FIRST_NAME = new HashSet<>(Arrays.asList("MARIA", "MA.", "MA", "JOSE", "J.", "J"));
    private static final Pattern CURP_DATEPATTERN = Pattern.compile("^(?<year>(19|20|21)\\d{2})-(?<month>0?[1-9]|1[0-2])-(?<day>0?[1-9]|[12]\\d|3[01])$");

    public static boolean curpValidador(String curp) {
        String strVerificador = curp.substring(16);
        strVerificador = strVerificador.replace('A', '0');
        int verificador = Integer.parseInt(strVerificador);
        int suma = 0;
        char[] charArray = curp.toCharArray();

        int i = 18;
        for (int j = 0; j < charArray.length - 1; j++) {
            char c = charArray[j];
            suma += i * DICCIONARIO.indexOf(c);
            i--;
        }

        int mod10suma = suma % 10;
        if (mod10suma != 0) {
            mod10suma = 10 - mod10suma;
        }

        return verificador == mod10suma;
    }

    // Función principal para generar el CURP
    public static String generateCURP(String firstName, String firstSurname, String secondSurname, String birthDate, String sex, String state) {
        LocalDate birthDateKindLocalDate = stringDateToLocalDate(birthDate);
        parametersValidations(firstName, firstSurname, birthDateKindLocalDate, sex, state);

        firstName = removeJoseOrMaria(firstName);
        firstName = normalizarTexto(firstName);

        firstSurname = normalizarTexto(firstSurname);
        secondSurname = normalizarTexto(secondSurname);

        StringBuilder curpConcatenation = new StringBuilder();
        getFirstFourLetters(curpConcatenation, firstName, firstSurname, secondSurname);
        getBirthDate(curpConcatenation, birthDateKindLocalDate);
        getSex(curpConcatenation, sex);
        getStateLetters(curpConcatenation, state);
        calculateConsonants(curpConcatenation, firstName, firstSurname, secondSurname);
        getLastTwoDigits(curpConcatenation, birthDateKindLocalDate);

        // Convertir a mayúsculas y eliminar acentos
        return curpConcatenation.toString();
    }

    private static LocalDate stringDateToLocalDate(String date) {
        Matcher matcher = CURP_DATEPATTERN.matcher(date);
        if (matcher.matches()) {
            String year = matcher.group("year");
            String month = matcher.group("month");
            String day = matcher.group("day");
            if (year == null) {
                throw new IllegalArgumentException("No puede ser nulo el año de la fecha");
            }
            if (month == null) {
                throw new IllegalArgumentException("No puede ser nulo el mes de la fecha");
            }
            if (day == null) {
                throw new IllegalArgumentException("No puede ser nulo el dia de la fecha");
            }
            return LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(month)), Integer.parseInt(day));
        } else {
            throw new IllegalArgumentException("La fecha no tiene el formato adecuado");
        }
    }

    private static String genVerifyNumber(String curp, LocalDate birthday) {
        int suma = 0;
        char[] charArray = curp.toCharArray();

        int i = 18;
        for (char c : charArray) {
            suma += i * DICCIONARIO.indexOf(c);
            i--;
        }

        int mod10suma = suma % 10;
        if (mod10suma != 0) {
            mod10suma = 10 - mod10suma;
        }

        if (birthday.isAfter(LocalDate.of(1999, Month.DECEMBER, 31))) {
            return String.format(Locale.ROOT, "%02d", mod10suma).replace('0', 'A');
        } else {
            return String.format(Locale.ROOT, "%02d", mod10suma);
        }

    }

    private static void parametersValidations(String firstName, String firstSurname, LocalDate birthDate, String sex, String state) {
        // Validaciones de no nulos
        if (firstName == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
        if (firstSurname == null) {
            throw new IllegalArgumentException("El apellido paterno no puede ser nulo");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("La fecha de cumpleaños no puede ser nulo");
        }
        if (sex == null) {
            throw new IllegalArgumentException("El sexo no puede ser nulo");
        }
        if (state == null) {
            throw new IllegalArgumentException("El estado de nacimiento no puede ser nulo");
        }

        //Validaciones de valores
        if (firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar en blanco");
        }
        if (firstSurname.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido parterno no puede estar en blanco");
        }
        if (sex.trim().isEmpty()) {
            throw new IllegalArgumentException("El sexo no puede estar en blanco");
        }
        if (state.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado de nacimiento no puede estar en blanco");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de cumpleaños no puede ser una fecha del futuro");
        }
        // Validaciones de valores en especifico
        if (!SEXOS.contains(sex)) {
            throw new IllegalArgumentException("El sexo solo puede ser HOMBRE O MUJER");
        }
    }

    private static String normalizarTexto(String texto) {
        String result = texto.toUpperCase(Locale.ROOT);
        result = removeDieresis(result);
        result = handleCompoundNames(result);
        return result;
    }

    private static String removeDieresis(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        return str;
    }

    private static String handleCompoundNames(String name) {
        String[] nameParts = name.split("\\s+");
        if (nameParts.length == 1) return name;

        for (String namePart : nameParts) {
            if (!EXCLUDED_WORDS.contains(namePart)) {
                return namePart;
            }
        }
        return "X";
    }

    private static String removeJoseOrMaria(String firstName) {
        // Si el nombre es MARIA o JOSE, usamos la primera letra del segundo nombre

        String[] partesNombre = firstName.split("\\s+");

        if (partesNombre.length == 1) return firstName;

        if (EXCLUDED_FIRST_NAME.contains(partesNombre[0])) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (int i = 1; i < partesNombre.length; i++) {
                stringJoiner.add(partesNombre[i]);
            }
            return stringJoiner.toString();
        } else {
            return firstName;
        }
    }

    // Calcula las primeras 4 letras del CURP
    private static void getFirstFourLetters(StringBuilder curpConcatenation, String firstName, String firstSurname, String secondSurname) {
        // Primera letra del primer apellido
        curpConcatenation.append(handleSpecialCharacters(firstSurname.charAt(0)));
        // Primera vocal interna del primer apellido
        curpConcatenation.append(getFirstVowel(firstSurname.substring(1)));
        // Primera letra del segundo apellido, Si no tenemos segundo apellido se agregara una X
        curpConcatenation.append(secondSurname == null ? 'X' : secondSurname.charAt(0));
        // Primera letra del nombre
        curpConcatenation.append(firstName.charAt(0));
    }


    private static char getFirstVowel(String str) {

        for (char c : str.toUpperCase().toCharArray()) {
            if (VOWELS.contains(c)) {
                return c;
            }
        }
        return 'X'; // Devolver 'X' si no hay vocal
    }


    // Calcula la fecha de nacimiento para el CURP
    private static void getBirthDate(StringBuilder curpConcatenation, LocalDate birthDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMdd");
        curpConcatenation.append(dateTimeFormatter.format(birthDate));
    }

    // Obtiene el sexo
    private static void getSex(StringBuilder curpConcatenation, String sex) {
        switch (sex.toUpperCase(Locale.ROOT)) {
            case "HOMBRE":
                curpConcatenation.append('H');
                break;
            case "MUJER":
                curpConcatenation.append('M');
                break;
            default:
                curpConcatenation.append('X');
                break;

        }

    }

    // Obtiene las letras del estado
    private static void getStateLetters(StringBuilder curpConcatenation, String state) {
        // Define un mapa para los estados especiales
        Map<String, String> specialStates = new HashMap<>();
        specialStates.put("BAJA CALIFORNIA", "BC");
        specialStates.put("BAJA CALIFORNIA SUR", "BS");
        specialStates.put("CIUDAD DE MEXICO", "DF");
        specialStates.put("COAHUILA DE ZARAGOZA", "CL");
        specialStates.put("MICHOACAN DE OCAMPO", "MN");
        specialStates.put("NUEVO LEON", "NL");
        specialStates.put("QUINTANA ROO", "QR");
        specialStates.put("SAN LUIS POTOSI", "SP");

        // Para los nacidos en el extranjero
        specialStates.put("EXTRANJERO", "NE");

        String upperState = state.toUpperCase(Locale.ROOT);

        // Si el estado tiene un código especial, lo retornamos
        if (specialStates.containsKey(upperState)) {
            curpConcatenation.append(specialStates.get(upperState));
            return;
        }

        // Si no, aplicamos la lógica de la primera y última consonante
        curpConcatenation.append(upperState.charAt(0));  // La primera letra siempre está presente

        for (int i = upperState.length() - 1; i >= 0; i--) {
            char c = upperState.charAt(i);
            if ("BCDFGHJKLMNPQRSTVWXYZ".indexOf(c) >= 0) {
                curpConcatenation.append(c);
                break;
            }
        }
    }

    // Calcula las letras 14-16
    private static void calculateConsonants(StringBuilder curpConcatenation, String firstName, String firstSurname, String secondSurname) {
        curpConcatenation.append(getFirstInternalConsonant(firstSurname));
        curpConcatenation.append(secondSurname == null ? 'X' : getFirstInternalConsonant(secondSurname));
        curpConcatenation.append(getFirstInternalConsonant(firstName));
    }

    private static char getFirstInternalConsonant(String str) {

        for (char c : str.substring(1).toUpperCase().toCharArray()) {
            if (CONSONANTS.contains(c)) {
                return c;
            }
        }
        return 'X'; // Devolver 'X' si no hay consonante interna
    }

    // Genera los dos últimos dígitos (aquí puedes ser creativo, dado que no tienes acceso a la base de datos del gobierno)
    private static void getLastTwoDigits(StringBuilder curpConcatenation, LocalDate birthday) {
        // Lógica aquí
        String tempCurp = curpConcatenation.toString();
        String verifyNumber = genVerifyNumber(tempCurp + "00", birthday);
        curpConcatenation.append(verifyNumber);
    }

    private static char handleSpecialCharacters(char c) {
        if (SPECIAL_CHARS.contains(c)) {
            return 'X';
        } else {
            return c;
        }

    }


}
