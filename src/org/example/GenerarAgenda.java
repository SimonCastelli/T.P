package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.AbstractMap;
import java.util.Map;

public class GenerarAgenda {

    // Clase interna para mantener la definición de un campo
    private static class CampoDef {
        int codigo;
        String descripcion;

        CampoDef(int codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        try (RandomAccessFile raf = new RandomAccessFile("agenda.dat", "rw")) {
            // Limpiamos el archivo anterior si existe
            raf.setLength(0);

            // === CABECERA ===
            System.out.print("Nro. de serie: ");
            int serie = Integer.parseInt(in.nextLine().trim());
            ArchivoUtil.escribirShort(serie, raf);

            System.out.print("Full filename (ej: C:/algoritmos/DEMO.dat): ");
            String full = in.nextLine().trim();
            ArchivoUtil.escribirString(full, raf);

            // Escribimos la fecha actual del sistema
            ArchivoUtil.escribirFecha(LocalDate.now(), raf);

            // === DEFINICIÓN DE CAMPOS ===
            System.out.print("Cantidad de campos a configurar: ");
            int cantCampos = Integer.parseInt(in.nextLine().trim());
            ArchivoUtil.escribirShort(cantCampos, raf);

            List<CampoDef> camposDefinidos = new ArrayList<>();
            for (int i = 1; i <= cantCampos; i++) {
                System.out.print("Nombre del campo #" + i + ": ");
                String campoDesc = in.nextLine().trim();
                if (campoDesc.isEmpty()) campoDesc = "Campo" + i;
                
                camposDefinidos.add(new CampoDef(i, campoDesc));
                
                // Escribimos la definición en el archivo
                ArchivoUtil.escribirByte(i, raf); // Código del campo
                ArchivoUtil.escribirString(campoDesc, raf); // Descripción
            }

            // === REGISTROS (CONTACTOS) ===
            System.out.print("Cantidad de registros (contactos) a ingresar: ");
            int n = Integer.parseInt(in.nextLine().trim());
            ArchivoUtil.escribirShort(n, raf);

            for (int i = 1; i <= n; i++) {
                System.out.println("\n== Contacto #" + i + " ==");
                
                List<Map.Entry<Integer, String>> camposDelContacto = new ArrayList<>();

                for (CampoDef campoDef : camposDefinidos) {
                    System.out.print(campoDef.descripcion + " (enter para omitir): ");
                    String valor = in.nextLine().trim();
                    if (!valor.isEmpty()) {
                        camposDelContacto.add(new AbstractMap.SimpleEntry<>(campoDef.codigo, valor));
                    }
                }

                // Escribimos los datos de este contacto
                ArchivoUtil.escribirByte(camposDelContacto.size(), raf);

                for (Map.Entry<Integer, String> entry : camposDelContacto) {
                    ArchivoUtil.escribirByte(entry.getKey(), raf);
                    ArchivoUtil.escribirString(entry.getValue(), raf);
                }
            }
        } // El try-with-resources se encarga de cerrar el raf

        System.out.println("\nArchivo 'agenda.dat' generado con éxito.");
    }
}
