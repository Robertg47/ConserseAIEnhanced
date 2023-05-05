package com.example.converseaienhanced.Helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

public class ImageSaver {
    public static void saveImage(WritableImage image, String fileName) throws IOException {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];
        PixelReader pixelReader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> pixelFormat = WritablePixelFormat.getByteBgraInstance();
        pixelReader.getPixels(0, 0, width, height, pixelFormat, buffer, 0, width * 4);
        OutputStream outputStream = new FileOutputStream(new File(fileName));
        outputStream.write(new byte[]{(byte) 137, (byte) 80, (byte) 78, (byte) 71, (byte) 13, (byte) 10, (byte) 26, (byte) 10});
        outputStream.write(getChunk(0, buffer));
        outputStream.write(getChunk(0x49454E44, new byte[] {}));
        outputStream.close();
    }
    
    private static byte[] getChunk(int type, byte[] data) {
        int length = data.length;
        byte[] buffer = new byte[length + 12];
        buffer[0] = (byte) ((length >> 24) & 0xff);
        buffer[1] = (byte) ((length >> 16) & 0xff);
        buffer[2] = (byte) ((length >> 8) & 0xff);
        buffer[3] = (byte) (length & 0xff);
        buffer[4] = (byte) ((type >> 24) & 0xff);
        buffer[5] = (byte) ((type >> 16) & 0xff);
        buffer[6] = (byte) ((type >> 8) & 0xff);
        buffer[7] = (byte) (type & 0xff);
        System.arraycopy(data, 0, buffer, 8, length);
        int crc = getCRC(buffer, 4, length + 4);
        buffer[length + 8] = (byte) ((crc >> 24) & 0xff);
        buffer[length + 9] = (byte) ((crc >> 16) & 0xff);
        buffer[length + 10] = (byte) ((crc >> 8) & 0xff);
        buffer[length + 11] = (byte) (crc & 0xff);
        return buffer;
    }
    
    private static int getCRC(byte[] buffer, int offset, int length) {
        int crc = 0xffffffff;
        for (int i = offset; i < offset + length; i++) {
            crc ^= buffer[i] & 0xff;
            for (int j = 0; j < 8; j++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ 0xedb88320;
                } else {
                    crc = crc >>> 1;
                }
            }
        }
        return ~crc;
    }
    
}