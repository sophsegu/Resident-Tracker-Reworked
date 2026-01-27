<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type");
header("Access-Control-Allow-Methods: POST, OPTIONS");

error_reporting(E_ALL);
ini_set("display_errors", 0); // IMPORTANT: prevents HTML errors

session_start();

// Handle preflight
if ($_SERVER["REQUEST_METHOD"] === "OPTIONS") {
    http_response_code(200);
    exit;
}

// Read JSON input
$data = json_decode(file_get_contents("php://input"), true);

if (!$data) {
    echo json_encode(["error" => "Invalid input"]);
    exit;
}

// Validate required fields
if (!isset($data["role"], $data["email"], $data["password"])) {
    echo json_encode(["error" => "Missing credentials"]);
    exit;
}

// Call Java auth service (YOU must implement this)
$response = callJavaAuthService([
    "email" => $data["email"],
    "password" => $data["password"]
]);

if (!$response || !isset($response["userId"])) {
    http_response_code(401);
    echo json_encode(["error" => "Invalid credentials"]);
    exit;
}

// Store session data
$_SESSION["user_id"]    = $response["userId"];
$_SESSION["role"]       = $response["role"];
$_SESSION["first_name"] = $response["first_name"];
$_SESSION["last_name"]  = $response["last_name"];

// Respond to frontend
echo json_encode([
    "status" => "success",
    "redirect" => "/dashboard/" . $_SESSION["role"]
]);

exit;
