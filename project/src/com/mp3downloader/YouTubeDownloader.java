package com.mp3downloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

public class YouTubeDownloader {
    
    private static final String OUTPUT_DIR = "downloads";
    private static final String YT_DLP_PATH = "yt-dlp"; // o "yt-dlp.exe" en Windows
    
    public YouTubeDownloader() {
        // Crear carpeta de descargas si no existe
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
    
    /**
     * Descarga el audio de YouTube y lo convierte a MP3
     */
    public String download(String url) throws Exception {
        
        // Comando para yt-dlp
        String[] command = {
            YT_DLP_PATH,
            "-x",                          // Extraer solo audio
            "--audio-format", "mp3",       // Formato MP3
            "--audio-quality", "192K",     // Calidad 192 kbps
            "-o", OUTPUT_DIR + "/%(title)s.%(ext)s",  // Nombre del archivo
            "--no-playlist",               // No descargar playlists
            "--max-filesize", "100M",      // Límite de tamaño: 100MB
            url
        };
        
        // Ejecutar comando
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        
        Process process = processBuilder.start();
        
        // Leer salida del proceso
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
        );
        
        String line;
        String outputPath = null;
        
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            
            // Detectar ruta del archivo descargado
            if (line.contains("Destination:")) {
                outputPath = line.substring(line.indexOf(":") + 1).trim();
            }
        }
        
        // Esperar a que termine el proceso
        int exitCode = process.waitFor();
        
        if (exitCode != 0) {
            throw new Exception("Error al descargar. Código: " + exitCode);
        }
        
        // Si no detectamos la ruta, buscar en el directorio
        if (outputPath == null) {
            outputPath = findLatestMP3();
        }
        
        return outputPath;
    }
    
    /**
     * Encuentra el archivo MP3 más reciente en downloads/
     */
    private String findLatestMP3() {
        File dir = new File(OUTPUT_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".mp3"));
        
        if (files == null || files.length == 0) {
            return null;
        }
        
        // Encontrar el más reciente
        File latest = files[0];
        for (File file : files) {
            if (file.lastModified() > latest.lastModified()) {
                latest = file;
            }
        }
        
        return latest.getAbsolutePath();
    }
}