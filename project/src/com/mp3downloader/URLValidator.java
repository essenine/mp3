package com.mp3downloader;

import java.util.regex.Pattern;

public class URLValidator {
    
    // Patrón para URLs de YouTube
    private static final Pattern YOUTUBE_PATTERN = Pattern.compile(
        "^(https?://)?(www\\.)?(youtube\\.com|youtu\\.be)/.+$"
    );
    
    /**
     * Valida si la URL es de YouTube y es segura
     */
    public boolean isValid(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        // Limpiar espacios
        url = url.trim();
        
        // Verificar que sea URL de YouTube
        if (!YOUTUBE_PATTERN.matcher(url).matches()) {
            return false;
        }
        
        // Verificar caracteres peligrosos
        if (containsDangerousCharacters(url)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Detecta caracteres peligrosos para evitar inyección de comandos
     */
    private boolean containsDangerousCharacters(String url) {
        String[] dangerousChars = {";", "&", "|", "`", "$", "(", ")", "<", ">"};
        
        for (String dangerous : dangerousChars) {
            if (url.contains(dangerous)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Extrae el ID del video de YouTube
     */
    public String extractVideoId(String url) {
        // Para youtube.com/watch?v=ID
        if (url.contains("v=")) {
            int start = url.indexOf("v=") + 2;
            int end = url.indexOf("&", start);
            if (end == -1) {
                return url.substring(start);
            }
            return url.substring(start, end);
        }
        
        // Para youtu.be/ID
        if (url.contains("youtu.be/")) {
            int start = url.lastIndexOf("/") + 1;
            return url.substring(start);
        }
        
        return null;
    }
}