package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

public class ArchivoUtil {

    // --- MÉTODOS DE ESCRITURA ---

    public static void escribirInt(int v, RandomAccessFile raf) throws IOException {
        raf.writeShort(v);
    }

    public static void writeByte(int v, RandomAccessFile raf) throws IOException {
        raf.writeByte(v);
    }

    public static void escribirString(String s, RandomAccessFile raf) throws IOException {
        if (s.length() < 255) {
            raf.writeByte(s.length());
        } else {
            raf.writeByte(255);
            raf.writeShort(s.length());
        }
        raf.writeBytes(s);
    }

    public static void escribirFecha(LocalDate date, RandomAccessFile raf) throws IOException {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        int yearValue;
        if (year >= 2000) {
            yearValue = year - 2000;
        } else {
            yearValue = 2099 - year;
        }

        int packedDate = (yearValue << 9) | (month << 5) | day;
        raf.writeShort(packedDate);
    }

    // --- MÉTODOS DE LECTURA ---

    public static int leerShort(RandomAccessFile raf) throws IOException {
        return raf.readShort();
    }

    public static int leerByte(RandomAccessFile raf) throws IOException {
        return raf.readByte();
    }

    public static String leerString(RandomAccessFile raf) throws IOException {
        int length = raf.readUnsignedByte();
        if (length == 255) {
            length = raf.readUnsignedShort();
        }
        byte[] bytes = new byte[length];
        raf.readFully(bytes);
        return new String(bytes);
    }

    public static LocalDate leerDate(RandomAccessFile raf) throws IOException {
        int packedDate = raf.readUnsignedShort();

        int day = packedDate & 0x1F; // 5 bits
        int month = (packedDate >> 5) & 0x0F; // 4 bits
        int yearValue = (packedDate >> 9) & 0x7F; // 7 bits

        int year;
        if (yearValue < 100) {
            year = 2000 + yearValue;
        } else {
            year = 2099 - yearValue;
        }

        return LocalDate.of(year, month, day);
    }
}