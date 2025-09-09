package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
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

            int cantCampos = 4;

            System.out.print("Cantidad de registros (contactos): ");
            int n = Integer.parseInt(in.nextLine().trim());

            raf.writeInt(serie);
            raf.writeUTF(full);
            raf.writeUTF(fecha);
            raf.writeInt(cantCampos);
            raf.writeInt(n);

            for (int i = 1; i <= n; i++) {
                System.out.println("\n== Contacto #" + i + " ==");
                System.out.print("Nombre: ");
                String nombre = in.nextLine().trim();

                System.out.print("Telefono (enter para vacío): ");
                String tel = in.nextLine().trim();

                System.out.print("Direccion (enter para vacío): ");
                String dir = in.nextLine().trim();

                System.out.print("EMail (enter para vacío): ");
                String mail = in.nextLine().trim();

                if(!nombre.isEmpty()) {
                    raf.writeUTF(nombre);
                }else{raf.writeUTF("");}
                if(!tel.isEmpty()) {
                    raf.writeUTF(tel);
                }else{raf.writeUTF("");}
                if(!dir.isEmpty()){
                    raf.writeUTF(dir);
                }else{raf.writeUTF("");}
                if(!mail.isEmpty()){
                    raf.writeUTF(mail);
                }else{raf.writeUTF("");}
            }
            raf.writeUTF(" hola");
            raf.writeUTF("Prueba")

        raf.close();
    }
}
