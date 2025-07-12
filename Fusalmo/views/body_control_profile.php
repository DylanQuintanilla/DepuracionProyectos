<!DOCTYPE html>
<html lang="es">
<head>
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-toast-plugin@1.3.2/dist/jquery.toast.min.css" integrity="sha256-WolrNTZ9lY0QL5f0/Qi1yw3RGnDLig2HVLYkrshm7Y0=" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h2>Mi Perfil - Salud</h2>
        <div class="card">
            <div class="card-body">
                <h5>Índice de Masa Corporal (IMC)</h5>
                <p>IMC: <?php echo isset($bmiData['bmi']) ? $bmiData['bmi'] : 'No disponible'; ?></p>
                <p>Clasificación: <?php echo isset($bmiData['classification']) ? $bmiData['classification'] : 'No disponible'; ?></p>
            </div>
        </div>
    </div>
    <form id="requestRoutineForm">
        <button type="submit" class="btn btn-sif btn-solid">Solicitar Rutina Personalizada</button>
    </form>
    <div class="card mt-3">
        <div class="card-body">
            <h5>Rutinas Asignadas</h5>
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Título</th>
                        <th>Fecha de Asignación</th>
                        <th>Archivo</th>
                    </tr>
                </thead>
                <tbody>
                    <?php if (!empty($routines) && is_array($routines)): ?>
                        <?php foreach ($routines as $routine): ?>
                            <tr>
                                <td><?php echo htmlspecialchars($routine['title']); ?></td>
                                <td><?php echo $routine['assigned_date'] ?: 'Global'; ?></td>
                                <td><a href="<?php echo htmlspecialchars($routine['file_path']); ?>" target="_blank">Ver PDF</a></td>
                            </tr>
                        <?php endforeach; ?>
                    <?php else: ?>
                        <tr>
                            <td colspan="3">No hay rutinas asignadas.</td>
                        </tr>
                    <?php endif; ?>
                </tbody>
            </table>
        </div>
    </div>

    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script>
    $(document).ready(function() {
        $('table').DataTable();
        $('#requestRoutineForm').submit(function(e) {
            e.preventDefault();
            $.ajax({
                url: 'index.php?action=requestRoutine',
                type: 'POST',
                dataType: 'json', // Asegura que la respuesta se trate como JSON
                success: function(response) {
                    if (response && typeof response === 'object') {
                        $.toast({
                            text: response.message,
                            icon: response.status,
                            position: 'top-right'
                        });
                    } else {
                        $.toast({
                            text: 'Error: Respuesta inesperada del servidor',
                            icon: 'error',
                            position: 'top-right'
                        });
                    }
                },
                error: function(xhr, status, error) {
                    $.toast({
                        text: 'Error en la solicitud: ' + error,
                        icon: 'error',
                        position: 'top-right'
                    });
                }
            });
        });
    });
</script>
</body>
</html>