<?php include 'session-check.php'; ?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Refugee Tracker | Dashboard</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="CSS/dashboard-style.css?version=2">
</head>
<body>

  <div class="dashboard-container">

    <!-- Sidebar -->
    <aside class="sidebar">
      <div class="sidebar-logo">
        <h1>Refugee Tracker</h1>
        <span>Secure access to case progress</span>
      </div>
      <nav class="sidebar-nav">
        <ul>
          <li><a href="#">Home</a></li>
          <li><a href="#">Residents</a></li>
          <li><a href="#">Applications</a></li>
          <li><a href="#">Reports</a></li>
          <li><a href="#">Settings</a></li>
          <li><a href="logout.php">Logout</a></li>
        </ul>
      </nav>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
      <header>
        <h2>Welcome, <?php echo htmlspecialchars($firstName . ' ' . $lastName); ?></h2>
        <p>Role: <?php echo htmlspecialchars(ucfirst($role)); ?></p>
      </header>

      <!-- Dashboard cards and tables go here -->
    </main>

  </div>

</body>
</html>