<?php
require_once __DIR__ . '/../process/net.php';

class BodyControlModel {
    private $db;

    public function __construct() {
        $this->db = new Database(); 
    }

    public function getEmployeeIdByUserId($userId) {
        $query = $this->db->prepare("SELECT id FROM employees WHERE user_id = :user_id");
        $query->bindParam(':user_id', $userId, PDO::PARAM_INT);
        $query->execute();
        $result = $query->fetch(PDO::FETCH_ASSOC);
        return $result ? $result['id'] : null;
    }

    public function getEmployeeBMI($employeeId) {
        $query = $this->db->prepare("SELECT weight_lbs, height_m FROM employees WHERE id = :id");
        $query->bindParam(':id', $employeeId, PDO::PARAM_INT);
        $query->execute();
        
        $result = $query->fetch(PDO::FETCH_ASSOC);
        if ($result) {
            $weight_kg = $result['weight_lbs'] * 0.453592; // Convertir libras a kg
            $height_m = $result['height_m'];
            $bmi = $weight_kg / ($height_m * $height_m);
            
            $classification = $this->classifyBMI($bmi);
            return ['bmi' => round($bmi, 2), 'classification' => $classification];
        }
        return null;
    }

    private function classifyBMI($bmi) {
        if ($bmi < 18.5) return 'Bajo peso';
        elseif ($bmi >= 18.5 && $bmi <= 24.9) return 'Peso normal';
        elseif ($bmi >= 25.0 && $bmi <= 29.9) return 'Pre-obesidad';
        elseif ($bmi >= 30.0 && $bmi <= 34.9) return 'Obesidad clase I';
        elseif ($bmi >= 35.0 && $bmi <= 39.9) return 'Obesidad clase II';
        else return 'Obesidad clase III';
    }

    public function saveGlobalRoutine($title, $imcRange, $filePath) {
        $query = $this->db->prepare("INSERT INTO routines (title, imc_range, file_path, is_global) VALUES (:title, :imc_range, :file_path, TRUE)");
        $query->bindParam(':title', $title);
        $query->bindParam(':imc_range', $imcRange);
        $query->bindParam(':file_path', $filePath);
        return verifyErrores($query);
    }

    public function savePersonalizedRoutine($employeeId, $title, $filePath) {
        $query = $this->db->prepare("INSERT INTO routines (title, imc_range, file_path, is_global) VALUES (:title, 'Personalizado', :file_path, FALSE)");
        $query->bindParam(':title', $title);
        $query->bindParam(':file_path', $filePath);
        $result = verifyErrores($query);
        
        if ($result === 'ok') {
            $routineId = $this->db->lastInsertId();
            $query = $this->db->prepare("INSERT INTO routine_assignments (employee_id, routine_id) VALUES (:employee_id, :routine_id)");
            $query->bindParam(':employee_id', $employeeId, PDO::PARAM_INT);
            $query->bindParam(':routine_id', $routineId, PDO::PARAM_INT);
            return verifyErrores($query);
        }
        return $result;
    }

    public function getPendingRequests() {
        $query = $this->db->prepare("SELECT r.id, e.name FROM routine_requests r JOIN employees e ON r.employee_id = e.id WHERE r.status = 'Pendiente'");
        $query->execute();
        return $query->fetchAll(PDO::FETCH_ASSOC);
    }

    public function saveRoutineRequest($employeeId) {
        $query = $this->db->prepare("INSERT INTO routine_requests (employee_id) VALUES (:employee_id)");
        $query->bindParam(':employee_id', $employeeId, PDO::PARAM_INT);
        $result = verifyErrores($query);
        
        if ($result === 'ok') {
            $employee = $this->getEmployee($employeeId);
            $to = 'coordinador@example.com';
            $subject = 'Nueva Solicitud de Rutina Personalizada';
            $message = "El empleado {$employee['name']} ha solicitado una rutina personalizada.";
            $headers = 'From: no-reply@fusalmosif.org';
            mail($to, $subject, $message, $headers);
        }
        return $result;
    }

    public function getAssignedRoutines($employeeId) {
        $query = $this->db->prepare("
            SELECT r.title, r.file_path, ra.assigned_date 
            FROM routine_assignments ra 
            JOIN routines r ON ra.routine_id = r.id 
            WHERE ra.employee_id = :employee_id
            UNION
            SELECT r.title, r.file_path, NULL as assigned_date 
            FROM routines r 
            WHERE r.is_global = TRUE AND r.imc_range = (
                SELECT CASE 
                    WHEN (weight_lbs * 0.453592) / POW(height_m, 2) < 18.5 THEN 'Bajo peso'
                    WHEN (weight_lbs * 0.453592) / POW(height_m, 2) <= 24.9 THEN 'Peso normal'
                    WHEN (weight_lbs * 0.453592) / POW(height_m, 2) <= 29.9 THEN 'Pre-obesidad'
                    WHEN (weight_lbs * 0.453592) / POW(height_m, 2) <= 34.9 THEN 'Obesidad clase I'
                    WHEN (weight_lbs * 0.453592) / POW(height_m, 2) <= 39.9 THEN 'Obesidad clase II'
                    ELSE 'Obesidad clase III'
                END 
                FROM employees WHERE id = :employee_id
            )
        ");
        $query->bindParam(':employee_id', $employeeId, PDO::PARAM_INT);
        $query->execute();
        return $query->fetchAll(PDO::FETCH_ASSOC);
    }

    private function getEmployee($employeeId) {
        $query = $this->db->prepare("SELECT name FROM employees WHERE id = :id");
        $query->bindParam(':id', $employeeId, PDO::PARAM_INT);
        $query->execute();
        return $query->fetch(PDO::FETCH_ASSOC);
    }

    public function authenticateUser($username, $password) {
        $query = $this->db->prepare("SELECT id, password, role FROM users WHERE username = :username");
        $query->bindParam(':username', $username);
        $query->execute();
        $user = $query->fetch(PDO::FETCH_ASSOC);

        if ($user && $password === $user['password']) {
            return ['id' => $user['id'], 'role' => $user['role']];
        }
        return false;
    }
}
?>