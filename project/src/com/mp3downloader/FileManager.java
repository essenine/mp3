package com.mp3downloader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    
    /**
     * Sanitiza el nombre del archivo (elimina caracteres peligrosos)
     */
    public static String sanitizeFilename(String filename) {
        // Eliminar caracteres no permitidos
        return filename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
    }
    
    /**
     * Verifica si un archivo existe
     */
    public static boolean fileExists(String path) {
        return new File(path).exists();
    }
    
    /**
     * Obtiene el tamaño de un archivo en MB
     */
    public static double getFileSizeMB(String path) {
        File file = new File(path);
        return file.length() / (1024.0 * 1024.0);
    }
    
    /**
     * Elimina archivos temporales antiguos
     */
    public static void cleanOldFiles(String directory, int daysOld) {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        
        if (files == null) return;
        
        long cutoffTime = System.currentTimeMillis() - (daysOld * 24L * 60 * 60 * 1000);
        
        for (File file : files) {
            if (file.lastModified() < cutoffTime) {
                file.delete();
                System.out.println("Eliminado archivo antiguo: " + file.getName());
            }
        }
    }
}