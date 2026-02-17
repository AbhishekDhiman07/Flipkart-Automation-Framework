# Flipkart Automation Framework 🚀

A robust, Maven-based test automation framework designed to perform end-to-end testing on the Flipkart e-commerce platform.

## 🌟 Key Features
* **Multi-Product Testing:** Automatically iterates through multiple items (e.g., iPhone 16, Samsung S24) in a single execution.
* **Dynamic Data Extraction:** Captures real-time prices using stable, tag-agnostic XPaths.
* **Automated Evidence:** Captures unique screenshots for every product searched and saves them in a dedicated folder.
* **Professional Reporting:** Generates an interactive **ExtentReports** HTML dashboard with pass/fail metrics and embedded screenshots.
* **Wait Strategies:** Implements Explicit Waits to handle dynamic page loads and lazy-loading elements.

## 🛠️ Tech Stack
* **Language:** Java
* **Automation Tool:** Selenium WebDriver (v4.x)
* **Build Tool:** Maven
* **Reporting:** ExtentReports (v5.x)
* **Driver Management:** WebDriverManager

## 📸 Test Results
The framework produces a visual dashboard located at `/reports/TestReport.html`. It provides a breakdown of each test case with timestamps and visual proof.
