<?php
// index.php
session_start();
header('Content-Type: text/html; charset=UTF-8');

// Incluir archivos necesarios
require_once 'controllers/BodyControlController.php';

// Inicializar controlador
$controller = new BodyControlController();
$action = $_GET['action'] ?? 'showProfile';
$employeeId = $_GET['employeeId'] ?? 1;

?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Información Institucional FUSALMO - Control Corporal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
    <link href="/assets/css/custom.css" rel="stylesheet">
</head>
<body>
    <header class="bg-sif text-white p-3">
        <div class="container">
            <h1 class="h4">FUSALMO - Módulo Control Corporal</h1>
            <img src="/assets/images/barra-stif.png" alt="Logo SIIF" class="float-end" style="height: 50px;">
        </div>
    </header>

    <div class="container mt-4">
        <?php
        try {
            switch ($action) {
                case 'showProfile':
                    $controller->showProfile($employeeId);
                    break;
                case 'showAdminView':
                    $controller->showAdminView();
                    break;
                case 'saveRoutine':
                    $controller->saveRoutine($_POST, $_FILES['file']);
                    break;
                case 'requestRoutine':
                    $controller->requestRoutine($employeeId);
                    break;
                default:
                    echo '<div class="alert alert-warning">Acción no reconocida.</div>';
                    $controller->showProfile($employeeId);
            }
        } catch (Exception $e) {
            echo '<div class="alert alert-danger">Error: ' . htmlspecialchars($e->getMessage()) . '</div>';
        }
        ?>
    </div>

    <footer class="bg-light text-center p-3 mt-4">
        <p>&copy; <?php echo date('Y'); ?> FUSALMO. Todos los derechos reservados.</p>
    </footer>

    <script src="/assets/js/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
    <script src="/assets/js/datatables.min.js"></script>
    <script src="/assets/js/jquery.toast.min.js"></script>
</body>
</html>