# QAFieldForce_MachineTest_portfolio
comprehensive test suite for the Field Force Connect platform, covering API testing via Postman, UI test automation using Selenium, and structured manual testing documentation.
QA & Automation Engineering Portfolio - Machine Test

Comprehensive QA test suite featuring Postman API automation, Selenium UI test scripts, and manual testing artifacts for the Field Force Connect platform.

---

### 1. API Testing (Postman)
Located in the `API-Testing/` directory.
* **Scenarios Covered:**
  - **Valid Login Authentication:** Evaluates successful credential submission and validates the authorization token response.
  - **Invalid Login Handling:** Verifies appropriate error responses (`401 Unauthorized`) against incorrect credentials.
  - **Customer / Lead Creation:** Executes a `POST` request to `/api/CRM/Lead` ensuring correct payload structure (`leadName`, contact info) and successful record creation.
* **Artifacts:** Postman Collection JSON & Environment variables file.

### 2. UI Automation (Selenium)
Located in the `Selenium-Automation/` directory.
* Automated test scripts covering core frontend workflows and user interactions.

### 3. Manual Testing & Quality Artifacts
Located in the `Manual-Testing/` directory.
* Test cases, edge-case scenarios, and test execution documentation.

---

##  How to Run
1. **API Collection:** Import the Postman collection JSON and environment files into Postman to execute the test suite.
2. **Selenium Automation:** Open the test suite in your preferred IDE, resolve dependencies, and run the test scripts.
