<?php
session_start();

$role     = $_POST['role'] ?? null;
$email    = $_POST['email'] ?? null;
$password = $_POST['password'] ?? null;

if (!$role || !$email || !$password) {
    die("Missing credentials");
}

$javaUrl = "http://localhost:8082/api/auth/login"; // use the correct Java endpoint

$payload = json_encode([
    "email" => $email,
    "password" => $password,
    "role" => $role // include role if required
]);

$ch = curl_init($javaUrl);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);

$response = curl_exec($ch);

var_dump($response);

if ($response === false) {
    die("cURL error: " . curl_error($ch));
}

curl_close($ch);

// Decode response
$result = json_decode($response, true);

if (!$result || !isset($result['agentId'])) {
    die("Invalid credentials");
}

// Store session
$_SESSION['user_id']    = $result['agentId'];
$_SESSION['role']       = $result['role'];
$_SESSION['first_name'] = $result['firstName'];
$_SESSION['last_name']  = $result['lastName'];

// Redirect
header("Location: ../dashboard.php");
exit;
?>