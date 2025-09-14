package org.example;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MostrarAgenda {

    public static void main(String[] args) throws IOException {
        String ruta = (args.length > 0) ? args[0] : "agenda.dat";

        int serie, cantCampos, cantReg;
        String fullFilename, fecha;

        // === 1) LEER Y MOSTRAR ===
        try(RandomAccessFile raf = new RandomAccessFile(ruta, "r")) {

            serie = raf.readInt();
            fullFilename = ArchivoUtil.leerString(raf);
            fecha = String.valueOf(ArchivoUtil.leerDate(raf));
            cantCampos = ArchivoUtil.leerByte(raf);
            List<String> nombresCampos = new ArrayList<>();
            for(int i=0; i < cantCampos; i++){
                nombresCampos.add(ArchivoUtil.leerString(raf));
            }

            cantReg = ArchivoUtil.leerByte(raf);

            System.out.println("Nro. de serie: " + serie);
            System.out.println("Full filename: " + fullFilename);
            System.out.println("Fecha de ultimo acceso: " + fecha);
            System.out.println("Cantidad de campos configurados: " + cantCampos);
            System.out.print("Campos: ");
            for(int i = 0; i < cantCampos; i++) {
                System.out.print(nombresCampos.get(i) + " ");
            }
            System.out.println("");
            System.out.println("Cantidad de Registros (contactos): " + cantReg);

            for (int i = 0; i < cantReg; i++) {
                System.out.println("\n--------------------");
                System.out.println("Registro #" + (i+1));
                for (int k = 0; k < cantCampos; k++){
                    String valor = ArchivoUtil.leerString(raf);
                    if(!valor.isEmpty()){
                        System.out.println(nombresCampos.get(k) + ": " + valor);
                    }
                }
            }
        }
    }
}
