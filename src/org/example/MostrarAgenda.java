package org.example;

import java.io.EOFException;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MostrarAgenda {

    public static void main(String[] args) throws IOException {
        String ruta = (args.length > 0) ? args[0] : "agenda.dat";

        System.out.println("--- LEYENDO AGENDA: " + ruta + " ---");

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "r")) {

            // === 1. LEER CABECERA ===
            int serie = ArchivoUtil.leerUnsignedShort(raf);
            String fullFilename = ArchivoUtil.leerString(raf);
            LocalDate fecha = ArchivoUtil.leerDate(raf);
            int cantCamposDef = ArchivoUtil.leerUnsignedShort(raf);

            // === 2. LEER DEFINICIÓN DE CAMPOS ===
            Map<Integer, String> camposDefinidos = new HashMap<>();
            System.out.println("\n--- Cabecera ---");
            System.out.println("Nro. de serie: " + serie);
            System.out.println("Full filename: " + fullFilename);
            System.out.println("Fecha de ultimo acceso: " + fecha);
            System.out.println("Cantidad de campos configurados: " + cantCamposDef);
            System.out.println("Definicion de Campos:");
            for (int i = 0; i < cantCamposDef; i++) {
                int codigo = ArchivoUtil.leerByte(raf);
                String descripcion = ArchivoUtil.leerString(raf);
                camposDefinidos.put(codigo, descripcion);
                System.out.println("  Campo #" + codigo + ": " + descripcion);
            }

            // === 3. LEER REGISTROS (CONTACTOS) ===
            int cantReg = ArchivoUtil.leerUnsignedShort(raf);
            System.out.println("\n--- Contactos ---");
            System.out.println("Cantidad de Registros guardados: " + cantReg);

            for (int i = 0; i < cantReg; i++) {
                System.out.println("\n== Contacto #" + (i + 1) + " ==");
                try {
                    int camposEnRegistro = ArchivoUtil.leerByte(raf);
                    if (camposEnRegistro == 0) {
                        System.out.println("(Contacto vacío)");
                        continue;
                    }

                    for (int k = 0; k < camposEnRegistro; k++) {
                        int codigoCampo = ArchivoUtil.leerByte(raf);
                        String valor = ArchivoUtil.leerString(raf);
                        String descripcion = camposDefinidos.getOrDefault(codigoCampo, "Campo desconocido");
                        System.out.println("  " + descripcion + ": " + valor);
                    }
                } catch (EOFException e) {
                    System.out.println("\nERROR: Fin de archivo inesperado. El archivo puede estar corrupto.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        System.out.println("\n--- FIN DEL ARCHIVO ---");
    }
}