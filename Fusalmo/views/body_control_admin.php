<!DOCTYPE html>
<html lang="es">
<head>
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/css/datatables.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Gestión de Rutinas</h2>
        
        <!-- Formulario para Rutina Global -->
        <form id="globalRoutineForm" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="title">Título de la Rutina</label>
                <input type="text" class="form-control" id="title" name="title">
            </div>
            <div class="mb-3">
                <label for="imc_range">Rango IMC</label>
                <select class="form-control" id="imc_range" name="imc_range">
                    <option value="Bajo peso">Bajo peso</option>
                    <option value="Peso normal">Peso normal</option>
                    <option value="Pre-obesidad">Pre-obesidad</option>
                    <option value="Obesidad clase I">Obesidad clase I</option>
                    <option value="Obesidad clase II">Obesidad clase II</option>
                    <option value="Obesidad clase III">Obesidad clase III</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="file">Archivo PDF</label>
                <input type="file" class="form-control" id="file" name="file" accept=".pdf">
            </div>
            <button type="submit" class="btn btn-sif btn-solid">Guardar</button>
        </form>

        <!-- Formulario para Rutina Personalizada -->
        <form id="personalizedRoutineForm" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="employee_id">Empleado</label>
                <select class="form-control" id="employee_id" name="employee_id">
                    <?php foreach ($requests as $request): ?>
                        <option value="<?php echo $request['id']; ?>"><?php echo $request['name']; ?></option>
                    <?php endforeach; ?>
                </select>
            </div>
            <div class="mb-3">
                <label for="title">Título de la Rutina</label>
                <input type="text" class="form-control" id="title" name="title">
            </div>
            <div class="mb-3">
                <label for="file">Archivo PDF</label>
                <input type="file" class="form-control" id="file" name="file" accept=".pdf">
            </div>
            <button type="submit" class="btn btn-sif btn-solid">Guardar</button>
        </form>
    </div>

    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/datatables.min.js"></script>
    <script src="/assets/js/jquery.toast.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#globalRoutineForm').submit(function(e) {
                e.preventDefault();
                let formData = new FormData(this);
                formData.append('type', 'global');
                $.ajax({
                    url: '/controllers/BodyControlController.php?action=saveRoutine',
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(response) {
                        let res = JSON.parse(response);
                        $.toast({
                            text: res.message,
                            icon: res.status,
                            position: 'top-right'
                        });
                    }
                });
            });

            $('#personalizedRoutineForm').submit(function(e) {
                e.preventDefault();
                let formData = new FormData(this);
                formData.append('type', 'personalized');
                $.ajax({
                    url: '/controllers/BodyControlController.php?action=saveRoutine',
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(response) {
                        let res = JSON.parse(response);
                        $.toast({
                            text: res.message,
                            icon: res.status,
                            position: 'top-right'
                        });
                    }
                });
            });
        });
    </script>
</body>
</html>