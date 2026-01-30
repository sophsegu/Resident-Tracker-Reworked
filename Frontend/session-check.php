<?php
session_start();

// Redirect to login if user is not logged in
if (!isset($_SESSION['user_id'])) {
    header("Location: login.php");
    exit;
}

// Optional: get user info from session
$firstName = $_SESSION['first_name'] ?? 'User';
$lastName  = $_SESSION['last_name'] ?? '';
$role      = $_SESSION['role'] ?? '';
?>