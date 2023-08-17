# Recruitment_Management_System
This project was developed for the Software Design and Architecture Course, showcasing the application of various design patterns and an innovative UI to create an efficient Recruitment Management System.
## Introduction
The <<Recruitment Management System>> is a Java-based application that allows HR professionals to manage their recruitment process efficiently. It includes creating jobs, adding candidates, scheduling interviews, and more. The project uses various design patterns like the Adapter pattern, Null Object pattern, Observer pattern, and Factory pattern.

## Features
*Create and post new job openings to the Legacy System.
*Add candidate details to the system.
*View a list of applicants.
* Manage candidate details, including updating contact information.
*Schedule interviews with selected candidates.

# Design Patterns Used
## Adapter Pattern
The Adapter pattern adapts the Legacy Job System to the new Job Management System. The JobManagementSystemAdapter class acts as an adapter, allowing the new system to use the legacy system's postJob method.

## Null Object Pattern
The Null Object pattern is used to handle cases where no job is available. The NullJob class serves as a default job object when there is no valid job.

## Observer Pattern
The Observer pattern is used to notify HR and candidates about new job postings. The JobObserver interface is implemented by HRObserver and CandidateObserver classes, which are notified when a new job is posted.

## Factory Pattern
The Factory pattern is used to create different types of Job objects based on the job title. The JobFactory class contains a method createJob that returns a specific type of job (e.g., SoftwareJob, MarketingJob, or NullJob) based on the job title provided.

## How to Run the Project
* Clone the repository or download the code from GitHub.
* Open the project in your preferred Java IDE (e.g., Eclipse, IntelliJ, etc.).
* Compile and run the RecruitmentManagementSystem class, which contains the main method.

## Usage
- Create Job: Click the "Create Job" button to create a new job posting. Enter the job title and description in the dialog box that appears. The job will be posted to the Legacy System.

- Add Candidate: Enter candidate details such as name, position, email, and contact in the input fields. Click the "Add Candidate" button to add the candidate to the system.

- View Applicants: Click the "View Applicants" button to see a list of candidates who have applied for the jobs.

- Manage Candidates: Select a candidate from the table and click the "Manage Candidates" button to update the candidate's details.

- Schedule Interview: Select a candidate from the table and click the "Schedule Interview" button to schedule an interview. Enter the interview date and time in the dialog box that appears.

##Authors
Uzbah Naseem (uzbahnaseem902gmail.com) 

## 🔗 Link
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/uzbah-naseem-44126a247)


Thank you for using the <<Recruitment Management System!>> Happy recruiting!
