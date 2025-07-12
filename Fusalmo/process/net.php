<?php
// process/net.php
class Database {
    private $host = 'localhost';
    private $db_name = 'fusalmosif';
    private $username = 'root';
    private $password = '';
    private $conn;

    public function __construct() {
        try {
            $this->conn = new PDO(
                "mysql:host=$this->host;dbname=$this->db_name;charset=utf8",
                $this->username,
                $this->password
            );
            $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            die("Error de conexión: " . $e->getMessage());
        }
    }

    public function prepare($sql) {
        return $this->conn->prepare($sql);
    }

    public function lastInsertId() {
        return $this->conn->lastInsertId();
    }
}

function verifyErrores($query) {
    try {
        $query->execute();
        return 'ok';
    } catch (PDOException $e) {
        return "Error: " . $e->getMessage();
    }
}