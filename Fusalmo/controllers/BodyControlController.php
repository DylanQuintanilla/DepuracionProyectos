<?php
require_once __DIR__ . '/../models/BodyControlModel.php';

class BodyControlController {
    private $model;

    public function __construct() {
        $this->model = new BodyControlModel();
    }

    public function showProfile() {
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }
        
        $employeeId = $this->model->getEmployeeIdByUserId($_SESSION['user_id']);
        if ($employeeId) {
            $bmiData = $this->model->getEmployeeBMI($employeeId);
            $routines = $this->model->getAssignedRoutines($employeeId);
            include __DIR__ . '/../views/body_control_profile.php';
        } else {
            echo '<div class="alert alert-warning">No se encontró información del empleado.</div>';
        }
    }

    public function showAdminView() {
        if (!isset($_SESSION['user_id']) || $_SESSION['role'] !== 'admin') {
            echo '<div class="alert alert-danger">No tienes permisos para acceder a esta sección.</div>';
            return;
        }
        $requests = $this->model->getPendingRequests();
        include __DIR__ . '/../views/body_control_admin.php';
    }

    public function saveRoutine($data, $file) {
        require_once __DIR__ . '/../process/saveFiles.php';
        $filePath = saveFile($file, __DIR__ . '/../../assets/routines/', 'routine_' . time());
        
        if (is_string($filePath) && strpos($filePath, 'Error') === false) {
            if ($data['type'] === 'global') {
                $result = $this->model->saveGlobalRoutine($data['title'], $data['imc_range'], $filePath);
            } else {
                $result = $this->model->savePersonalizedRoutine($data['employee_id'], $data['title'], $filePath);
            }
            
            if ($result === 'ok') {
                echo json_encode(['status' => 'success', 'message' => 'Rutina guardada con éxito']);
            } else {
                echo json_encode(['status' => 'error', 'message' => $result]);
            }
        } else {
            echo json_encode(['status' => 'error', 'message' => $filePath]);
        }
    }

    public function requestRoutine() {
    if (!isset($_SESSION['user_id'])) {
        echo json_encode(['status' => 'error', 'message' => 'Debes iniciar sesión para solicitar una rutina']);
        return;
    }
    
    $employeeId = $this->model->getEmployeeIdByUserId($_SESSION['user_id']);
    if ($employeeId) {
        $result = $this->model->saveRoutineRequest($employeeId);
        echo json_encode(['status' => $result === 'ok' ? 'success' : 'error', 'message' => $result === 'ok' ? 'Solicitud enviada' : $result]);
    } else {
        echo json_encode(['status' => 'error', 'message' => 'No se encontró tu información de empleado']);
    }
}

    public function authenticate($username, $password) {
        $authResult = $this->model->authenticateUser($username, $password);

        if ($authResult) {
            $_SESSION['user_id'] = $authResult['id'];
            $_SESSION['role'] = $authResult['role'];
            header('Location: index.php?action=showProfile');
        } else {
            header('Location: index.php?action=login&error=Usuario o contraseña incorrectos');
        }
        exit;
    }

    public function logout() {
        session_destroy();
        header('Location: index.php?action=login');
        exit;
    }
}
?>