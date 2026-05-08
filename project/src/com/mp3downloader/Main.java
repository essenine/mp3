package com.mp3downloader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== DESCARGADOR DE MP3 DE YOUTUBE ===");
        System.out.println();
        
        System.out.print("Ingresa la URL de YouTube: ");
        String url = scanner.nextLine();
        
        try {
            // 1. Validar URL
            URLValidator validator = new URLValidator();
            if (!validator.isValid(url)) {
                System.err.println("❌ URL inválida o no es de YouTube");
                return;
            }
            
            // 2. Descargar
            YouTubeDownloader downloader = new YouTubeDownloader();
            System.out.println("⏳ Descargando...");
            
            String filePath = downloader.download(url);
            
            System.out.println("✅ ¡Descarga completada!");
            System.out.println("📁 Archivo guardado en: " + filePath);
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}