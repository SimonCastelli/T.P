package org.example;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class GenerarAgenda {
    public static void main(String[] args)throws IOException {
        Scanner in = new Scanner(System.in);

        RandomAccessFile raf = new RandomAccessFile("agenda.dat", "rw");
        raf.setLength(0);

        System.out.print("Nro. de serie: ");
        int serie = Integer.parseInt(in.nextLine().trim());

        System.out.print("Full filename (ej: C:/algoritmos/DEMO.dat): ");
        String full = in.nextLine().trim();

        String fecha = LocalDate.now().toString();

        System.out.print("Cantidad de campos: ");
        int cantCampos = Integer.parseInt(in.nextLine().trim());
        List<String> nombresCampos = new ArrayList<>();
        for (int i = 1; i <= cantCampos; i++) {
            System.out.print("Nombre del campo #" + i + ": ");
            String campo = in.nextLine().trim();
            if (campo.isEmpty()) campo = "Campo" + i;
            nombresCampos.add(campo);
        }

        System.out.print("Cantidad de registros (contactos): ");
        int n = Integer.parseInt(in.nextLine().trim());
        ArchivoUtil.escribirInt(serie,raf);
        //raf.writeInt(serie);
        ArchivoUtil.escribirString(full,raf);
        //raf.writeUTF(full);
        ArchivoUtil.escribirFecha(LocalDate.parse(fecha),raf);
        //raf.writeUTF(fecha);
        ArchivoUtil.escribirInt(cantCampos,raf);
        //raf.writeInt(cantCampos);
        for(String Campo: nombresCampos){
        ArchivoUtil.escribirString(Campo,raf);
        }
        ArchivoUtil.escribirInt(n,raf);
        // === Registros ===
        for (int i = 1; i <= n; i++) {
            System.out.println("\n== Contacto #" + i + " ==");
            for (String campo : nombresCampos) {
                System.out.print(campo + " (enter para vacío): ");
                String valor = in.nextLine().trim();
                ArchivoUtil.escribirString(valor,raf);
                //raf.writeUTF(valor);
            }
        }
        raf.close();
    }
}