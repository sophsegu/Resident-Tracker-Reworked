<?php
session_start();

$role     = $_POST['role'] ?? null;
$email    = $_POST['email'] ?? null;
$password = $_POST['password'] ?? null;

if (!$role || !$email || !$password) {
    die("Missing credentials");
}

// Java API endpoint
$javaUrl = "http://localhost:8082/authenticate"; // your Java server

// Build JSON payload
$payload = json_encode([
    "email" => $email,
    "password" => $password,
    "role" => $role
]);

// Initialize cURL
$ch = curl_init($javaUrl);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);

$response = curl_exec($ch);
curl_close($ch);

// Decode Java response
$result = file_get_contents(
  "http://localhost:8082/api/auth/login",
  false,
  stream_context_create([
    "http" => [
      "method" => "POST",
      "header" => "Content-Type: application/json",
      "content" => json_encode([
        "email" => $email,
        "password" => $password
      ])
    ]
  ])
);


if (!$result || !isset($result['userId'])) {
    die("Invalid credentials");
}

// Store session
$_SESSION['user_id']    = $result['userId'];
$_SESSION['role']       = $result['role'];
$_SESSION['first_name'] = $result['first_name'];
$_SESSION['last_name']  = $result['last_name'];

// Redirect to dashboard
header("Location: ../Frontend/dashboard.php");
exit;
