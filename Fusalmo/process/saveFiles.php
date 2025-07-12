<?php
// process/saveFiles.php

// Definir constantes para la configuración
define('UPLOAD_DIR', __DIR__ . '/../../assets/routines/');
define('MAX_FILE_SIZE', 10 * 1024 * 1024); // 10 MB en bytes
define('ALLOWED_TYPES', ['application/pdf']);

/**
 * Guarda un archivo subido en el directorio especificado.
 *
 * @param array $file Datos del archivo del arreglo $_FILES
 * @param string $uploadDir Directorio de destino
 * @param string $prefix Prefijo opcional para el nombre del archivo
 * @return string|bool Ruta del archivo guardado o false si falla
 */
function saveFile($file, $uploadDir, $prefix = '') {
    // Verificar si el archivo fue subido correctamente
    if (!isset($file['error']) || $file['error'] !== UPLOAD_ERR_OK) {
        return "Error: No se pudo subir el archivo.";
    }

    // Validar tamaño del archivo
    if ($file['size'] > MAX_FILE_SIZE) {
        return "Error: El archivo excede el tamaño máximo de " . (MAX_FILE_SIZE / (1024 * 1024)) . " MB.";
    }

    // Validar tipo de archivo
    $finfo = finfo_open(FILEINFO_MIME_TYPE);
    $mimeType = finfo_file($finfo, $file['tmp_name']);
    finfo_close($finfo);

    if (!in_array($mimeType, ALLOWED_TYPES)) {
        return "Error: Solo se permiten archivos PDF.";
    }

    // Crear directorio si no existe
    if (!file_exists($uploadDir)) {
        mkdir($uploadDir, 0755, true);
    }

    // Generar nombre único para el archivo
    $fileExtension = pathinfo($file['name'], PATHINFO_EXTENSION);
    $fileName = $prefix . '_' . time() . '.' . strtolower($fileExtension);
    $filePath = $uploadDir . $fileName;

    // Mover el archivo al directorio de destino
    if (move_uploaded_file($file['tmp_name'], $filePath)) {
        // Asegurar permisos adecuados
        chmod($filePath, 0644);
        return $filePath;
    } else {
        return "Error: No se pudo mover el archivo al directorio de destino.";
    }
}

// Ejemplo de uso (puedes comentarlo o eliminarlo en producción)
// if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file'])) {
//     $result = saveFile($_FILES['file'], UPLOAD_DIR, 'routine');
//     echo $result;
// }