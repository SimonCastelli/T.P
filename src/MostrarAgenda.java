package org.example;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MostrarAgenda {

    static class Contacto {
        String nombre, telefono, direccion, email;

        Contacto(String n, String t, String d, String e) {
            nombre = n;
            telefono = t;
            direccion = d;
            email = e;
        }
    }

    public static void main(String[] args) throws IOException {
        String ruta = (args.length > 0) ? args[0] : "agenda.dat";

        int serie, cantCampos, cantReg;
        String fullFilename, fecha;
        List<Contacto> contactos = new ArrayList<>();

        // === 1) LEER Y MOSTRAR ===
        RandomAccessFile raf = new RandomAccessFile(ruta, "r");
        serie = raf.readInt();
        fullFilename = raf.readUTF();
        fecha = raf.readUTF();
        cantCampos = raf.readInt();
        cantReg = raf.readInt();

        System.out.println("Nro. de serie: " + serie);
        System.out.println("Full filename: " + fullFilename);
        System.out.println("Fecha de ultimo acceso: " + fecha);
        System.out.println("Cantidad de campos configurados: " + cantCampos);
        System.out.println("Cantidad de Registros (contactos): " + cantReg);

        for (int i = 0; i < cantReg; i++) {
            String nombre = raf.readUTF();
            String tel = raf.readUTF();
            String dir = raf.readUTF();
            String mail = raf.readUTF();
            contactos.add(new Contacto(nombre, tel, dir, mail));

            System.out.println("-----------------------");
            System.out.println("Nombre: " + nombre);
            if (!tel.isEmpty()) System.out.println("Telefono: " + tel);
            if (!dir.isEmpty()) System.out.println("Direccion: " + dir);
            if (!mail.isEmpty()) System.out.println("EMail: " + mail);
        }

    }
}
