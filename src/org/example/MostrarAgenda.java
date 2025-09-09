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
        try(RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {

            serie = raf.readInt();
            fullFilename = raf.readUTF();
            fecha = raf.readUTF();
            cantCampos = raf.readInt();
            List<String> nombresCampos = new ArrayList<>();
            for(int i=0; i < cantCampos; i++){
                nombresCampos.add(raf.readUTF());
            }

            cantReg = raf.readInt();

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
                    String valor = raf.readUTF();
                    if(!valor.isEmpty()){
                        System.out.println(nombresCampos.get(k) + ": " + valor);
                    }
                }
            }
        }
    }
}