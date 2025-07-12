<?php
require_once __DIR__ . '/../models/BodyControlModel.php';

class BodyControlController {
    private $model;

    public function __construct() {
        $this->model = new BodyControlModel();
    }

    public function showProfile($employeeId) {
        $bmiData = $this->model->getEmployeeBMI($employeeId);
        include __DIR__ . '/../views/body_control_profile.php';
    }
    public function showAdminView() {
        $requests = $this->model->getPendingRequests();
        include __DIR__ . '/../views/body_control_admin.php';
    }
    //----------------------
    public function saveRoutine($data, $file) {
        require_once __DIR__ . '/../process/saveFiles.php';
        $filePath = saveFile($file, __DIR__ . '/../assets/routines/', 'routine_' . time());
        
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
    }
    //-----------------------
    public function requestRoutine($employeeId) {
        $result = $this->model->saveRoutineRequest($employeeId);
        echo json_encode(['status' => $result === 'ok' ? 'success' : 'error', 'message' => $result === 'ok' ? 'Solicitud enviada' : $result]);
    }

    //-----------------------
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
}
?>