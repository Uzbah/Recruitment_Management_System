package recruitmentManagementSystem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JPanel;
//command pattern


interface Command {
  void execute();
}
class CreateJobCommand implements Command {
    private JobManagementSystem jobManagementSystem;
    private String title;
    private String description;
    private List<String> requirements;
    public CreateJobCommand(JobManagementSystem jobManagementSystem, String title, String description, List<String> requirements) {
        this.jobManagementSystem = jobManagementSystem;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
    }

    @Override
    public void execute() {
        jobManagementSystem.createJob(title, description);
    }
 
 // Abstraction interface
    public interface JobHandler {
        void addHandler(JobHandler handler);
        void removeHandler(JobHandler handler);
        // Additional methods related to the job handling can be added here
		NextJobHandler getImplementation();
    }
 // Implementation interface
    public interface NextJobHandler {
        void addHandler(NextJobHandler handler);
        void removeHandler(NextJobHandler handler);
        // Additional methods related to the next job handling can be added here
    }
 // Concrete implementation class
    public class ConcreteNextJobHandler implements NextJobHandler {
        private NextJobHandler nextHandler;

        public void addHandler(NextJobHandler handler) {
            if (nextHandler == null) {
                nextHandler = handler;
            } else {
                nextHandler.addHandler(handler);
            }}
        public void removeHandler(NextJobHandler handler) {
            if (nextHandler == handler) {
                nextHandler = null;
            } else if (nextHandler != null) {
                nextHandler.removeHandler(handler);
            }
        }
    }
    // Refined abstraction class
 // Refined abstraction class
    public class JobHandlerImpl implements JobHandler {
        private NextJobHandler nextHandler;

        public void addHandler(JobHandler handler) {
            nextHandler.addHandler(handler.getImplementation());
        }

        public void removeHandler(JobHandler handler) {
            nextHandler.removeHandler(handler.getImplementation());
        }

        // Getter for obtaining the implementation
        public NextJobHandler getImplementation() {
            if (nextHandler == null) {
                nextHandler = new ConcreteNextJobHandler();
            }
            return nextHandler;
        }
    }
        }

//adapter pattern
//Existing LegacyJobSystem interface
interface LegacyJobSystem {
 void postJob(String title, String description);
}

//LegacyJobSystem implementation
class LegacyJobSystemImpl implements LegacyJobSystem {
 @Override
 public void postJob(String title, String description) {
//     System.out.println("Posting job to legacy system: " + title + ", " + description );
	 JOptionPane.showMessageDialog(null, "Job Title "+ title+" description "+description+" is Posted on Legacy System");
 }
}

//JobManagementSystem interface
interface JobManagementSystem {
 void createJob(String title, String description);
}

//Adapter class implementing the JobManagementSystem interface and delegating to LegacyJobSystem
class JobManagementSystemAdapter implements JobManagementSystem {
 private LegacyJobSystem legacyJobSystem;

 public JobManagementSystemAdapter(LegacyJobSystem legacyJobSystem) {
     this.legacyJobSystem = legacyJobSystem;
 }

 @Override
 public void createJob(String title, String description) {
     // Adapt the createJob method to call the postJob method of LegacyJobSystem
     legacyJobSystem.postJob(title, description);
 }
}

//JobFactory to create different types of Job objects
class JobFactory {
 public static Job createJob(String title, String description) {
     if (title.contains("Software")) {
         // Create a SoftwareJob object if the title contains "Software"
         return new SoftwareJob(title, description);
     } else if (title.contains("Marketing")) {
         // Create a MarketingJob object if the title contains "Marketing"
         return new MarketingJob(title, description);
     } 
     else if (title.contains("Data Analyst")) {
         // Create a MarketingJob object if the title contains "Marketing"
         return new MarketingJob(title, description);
     }
     else {
         return new NullJob(); // Return a NullJob object for unrecognized titles
     }
 }
}


//Job classes

class Job {
 private String title;
 private String description;
 

 public Job(String title, String description) {
     this.title = title;
     this.description = description;
     
 }

 public String getDescription() {
     return description;
 }
 // Getters and setters
 public String getTitle() {
     return title;
 }

 @Override
 public String toString() {
     return "Job{" +
             "title='" + title + '\'' +
             ", description='" + description + '\'' +
             '}';
 }
}
//Example specialized Job classes

class SoftwareJob extends Job {
 public SoftwareJob(String title, String description) {
     super(title, description);
 }

 // Additional methods or properties specific to SoftwareJob
 public void runCodeReview() {
     System.out.println("Performing code review for software job: " + getTitle());
     // Code review logic...
 }

 public void runUnitTests() {
     System.out.println("Running unit tests for software job: " + getTitle());
     // Unit testing logic...
 }
}

class MarketingJob extends Job {
 public MarketingJob(String title, String description) {
     super(title, description);
 }

 // Additional methods or properties specific to MarketingJob
 public void runMarketResearch() {
     System.out.println("Running market research for marketing job: " + getTitle());
     // Market research logic...
 }

 public void runCampaignAnalysis() {
     System.out.println("Performing campaign analysis for marketing job: " + getTitle());
     // Campaign analysis logic...
 }
}
//Null Object Pattern
class NullJob extends SoftwareJob {
public NullJob() {
    super("No Job Available", "No Job Description Available");
}

// Override the methods in the Job class with default implementations
@Override
public void runCodeReview() {
    // Default implementation does nothing
}

@Override
public void runUnitTests() {
    // Default implementation does nothing
}
}

//Observer interface
interface JobObserver {
 void update(Job job);
}

//Subject class
class JobSubject {
 private List<JobObserver> observers;

 public JobSubject() {
     this.observers = new ArrayList<>();
 }

 public void attach(JobObserver observer) {
     observers.add(observer);
 }

 public void detach(JobObserver observer) {
     observers.remove(observer);
 }

 public void notifyObservers(Job job) {
     for (JobObserver observer : observers) {
         observer.update(job);
     }
 }
}

//Concrete observer classes
class HRObserver implements JobObserver {
 @Override
 public void update(Job job) {
//     System.out.println("HR : New job posted - " + job.getTitle());
     JOptionPane.showMessageDialog(null, "HR Notified: New job posted: " + job.getTitle());
 }
}

class CandidateObserver implements JobObserver {
 @Override
 public void update(Job job) {
//     System.out.println("Candidate : New job posted - " + job.getTitle());
     JOptionPane.showMessageDialog(null, "Candidate Notified: New job posted: " + job.getTitle());

 }
}
class JobBuilder {
     private String title;
     private String description;

     public JobBuilder setTitle(String title) {
         this.title = title;
         return this;
     }

     public JobBuilder setDescription(String description) {
         this.description = description;
         return this;
     }

     public Job build() {
         return new Job(title, description);
     }
 }


 
public class RecruitmentManagementSystem extends JFrame{
	private JTextField candidateNameField;
    private JTextField positionField;
    private JTextField EmailField;
    private JTextField contactField;
    private DefaultTableModel tableModel;
	private JFrame frame;
	private JobManagementSystem jobManagementSystem;
	protected JobSubject jobSubject;
	//singleton pattern
	private static RecruitmentManagementSystem instance;
	public static RecruitmentManagementSystem getInstance() {
        if (instance == null) {
            instance = new RecruitmentManagementSystem();
        }
        return instance;
    }
	
	
	public  RecruitmentManagementSystem() {
		setBackground(new Color(51, 204, 204));
		// Initialize the instance variable frame
		 frame = this;
		// Set properties of the frame
		 setTitle("Recruitment Management System");
	     setSize(800, 600);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    

	// Create an instance of the LegacyJobSystem
    LegacyJobSystem legacyJobSystem = new LegacyJobSystemImpl();

   // Create an adapter instance and pass the LegacyJobSystem instance
   jobManagementSystem = new JobManagementSystemAdapter(legacyJobSystem);
   jobSubject = new JobSubject();
   HRObserver hrObserver = new HRObserver();
   CandidateObserver candidateObserver = new CandidateObserver();
   jobSubject.attach(hrObserver);
   jobSubject.attach(candidateObserver);
   
   
   
	setTitle("Recruitment Management System");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create components
    JLabel candidateNameLabel = new JLabel("Candidate Name:");
    JLabel positionLabel = new JLabel("Position:");
    JLabel EmailLabel = new JLabel("Email:");
    JLabel contactLabel = new JLabel("Contact:");

    candidateNameField = new JTextField(20);
    positionField = new JTextField(20);
    EmailField = new JTextField(20);
    contactField = new JTextField(20);

    JButton addButton = new JButton("Add Candidate");
    JButton createJobButton = new JButton("Create Job");
    createJobButton.setBackground(new Color(102, 153, 153));
    JButton viewApplicantsButton = new JButton("View Applicants");
    viewApplicantsButton.setBackground(new Color(102, 153, 153));
    JButton manageCandidatesButton = new JButton("Manage Candidates");
    manageCandidatesButton.setBackground(new Color(102, 153, 153));
    JButton scheduleInterviewButton = new JButton("Schedule Interview");
    scheduleInterviewButton.setBackground(new Color(102, 153, 153));

    // Create table
    String[] columnNames = {"Candidate Name", "Position","Email", "Contact"};
    tableModel = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(tableModel);
    table.setBackground(new Color(153, 204, 204));

    // Set layout
    getContentPane().setLayout(new BorderLayout());

    // Panel for input fields
    JPanel inputPanel = new JPanel();
    inputPanel.setBackground(new Color(153, 204, 204));
    inputPanel.setForeground(new Color(68, 162, 255));
    inputPanel.setLayout(new FlowLayout());
    inputPanel.add(candidateNameLabel);
    inputPanel.add(candidateNameField);
    inputPanel.add(positionLabel);
    inputPanel.add(positionField);
    inputPanel.add(EmailLabel);
    inputPanel.add(EmailField);
    inputPanel.add(contactLabel);
    inputPanel.add(contactField);
    inputPanel.add(addButton);

    // Panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(153, 204, 204));
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(createJobButton);
    buttonPanel.add(viewApplicantsButton);
    buttonPanel.add(manageCandidatesButton);
    buttonPanel.add(scheduleInterviewButton);

    // Add panels and table to the frame
    getContentPane().add(inputPanel, BorderLayout.NORTH);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBackground(new Color(102, 204, 204));
    scrollPane.setForeground(new Color(0, 153, 153));
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    
    JLabel lblNewLabel_1 = new JLabel("New label");
    scrollPane.setColumnHeaderView(lblNewLabel_1);
    
    JLabel lblNewLabel_2 = new JLabel("New label");
    scrollPane.setColumnHeaderView(lblNewLabel_2);
    
    JLabel lblNewLabel = new JLabel("New label");
    lblNewLabel.setIcon(new ImageIcon("rms.jpg"));
    scrollPane.setColumnHeaderView(lblNewLabel);
    
    JLabel lblNewLabel_3 = new JLabel("New label");
    scrollPane.setColumnHeaderView(lblNewLabel_3);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);

    // Add ActionListener to the Add Candidate button
    addButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String candidateName = candidateNameField.getText();
            String position = positionField.getText();
            String email = EmailField.getText();
            String contact = contactField.getText();

            // Add the data to the table
            Object[] rowData = {candidateName, position,email, contact};
            tableModel.addRow(rowData);

            // Clear the input fields
            candidateNameField.setText("");
            positionField.setText("");
            contactField.setText("");
            EmailField.setText("");
        }
    });

    
 // Add ActionListener to the Create Job button
    createJobButton.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    	     // Display a dialog to get job details from the user
    	     JTextField jobTitleField = new JTextField(20);
    	     JTextField jobDescriptionField = new JTextField(20);

    	     JPanel jobPanel = new JPanel();
    	     jobPanel.setLayout(new GridLayout(2, 2));
    	     jobPanel.add(new JLabel("Job Title:"));
    	     jobPanel.add(jobTitleField);
    	     jobPanel.add(new JLabel("Job Description:"));
    	     jobPanel.add(jobDescriptionField);

    	     int result = JOptionPane.showConfirmDialog(RecruitmentManagementSystem.this, jobPanel, "Create Job", JOptionPane.OK_CANCEL_OPTION);
    	     if (result == JOptionPane.OK_OPTION) {
    	         String jobTitle = jobTitleField.getText();
    	         String jobDescription = jobDescriptionField.getText();
    	         if (jobTitle == null || jobTitle.isEmpty() || jobDescription == null || jobDescription.isEmpty()) {
 	            	JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "No Job created!");
    	         }
 	            else {
 	            	 JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "Job created successfully!");
 	            }
    	      // Use the JobBuilder pattern to create the job
//    	         Job job = new JobBuilder()
//    	                    .setTitle(jobTitle)
//    	                    .setDescription(jobDescription)
//    	                    .build();
    	         
//    	         JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "Job created successfully!");
    	      // Create a job using the JobFactory
    	            Job job = JobFactory.createJob(jobTitle, jobDescription);

    	            // Use the jobManagementSystem to create the job
    	            jobManagementSystem.createJob(job.getTitle(), job.getDescription());
    	           
//    	           JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "Job Title "+ jobTitle+" description "+jobDescription+" is Posted on Legacy System");
    	           jobSubject.notifyObservers(job);
    	           
    	           if (job instanceof SoftwareJob) {
    	                SoftwareJob softwareJob = (SoftwareJob) job;
    	                softwareJob.runCodeReview();
    	                softwareJob.runUnitTests();
    	            } else if (job instanceof MarketingJob) {
    	                MarketingJob marketingJob = (MarketingJob) job;
    	                marketingJob.runMarketResearch();
    	                marketingJob.runCampaignAnalysis();
    	            }

// 	            notifyObservers(job);
    	         
    	     }
    	 }
    	});
 // Add ActionListener to the View Applicants button
    viewApplicantsButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Check if there are any applicants in the table
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "No applicants found.");
                return;
            }

            // Create a dialog to display the applicants
            JDialog applicantsDialog = new JDialog(RecruitmentManagementSystem.this, "Applicants", true);
            applicantsDialog.setSize(600, 400);
            applicantsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            // Create a table to display the applicants
            JTable applicantsTable = new JTable(tableModel);

            // Add the table to the dialog
            applicantsDialog.getContentPane().add(new JScrollPane(applicantsTable));

            // Display the dialog
            applicantsDialog.setVisible(true);
        }
    });
 // Add ActionListener to the Manage Candidates button
    manageCandidatesButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String candidateName = (String) table.getValueAt(selectedRow, 0);
                String position = (String) table.getValueAt(selectedRow, 1);
                String contact = (String) table.getValueAt(selectedRow, 2);

                // Display a dialog to manage the selected candidate
                JTextField candidateNameField = new JTextField(candidateName, 20);
                JTextField positionField = new JTextField(position, 20);
                JTextField contactField = new JTextField(contact, 20);

                JPanel manageCandidatesPanel = new JPanel();
                manageCandidatesPanel.setLayout(new GridLayout(3, 2));
                manageCandidatesPanel.add(new JLabel("Candidate Name:"));
                manageCandidatesPanel.add(candidateNameField);
                manageCandidatesPanel.add(new JLabel("Position:"));
                manageCandidatesPanel.add(positionField);
                manageCandidatesPanel.add(new JLabel("Contact:"));
                manageCandidatesPanel.add(contactField);

                int result = JOptionPane.showConfirmDialog(RecruitmentManagementSystem.this, manageCandidatesPanel, "Manage Candidate", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Update the candidate details in the table
                    table.setValueAt(candidateNameField.getText(), selectedRow, 0);
                    table.setValueAt(positionField.getText(), selectedRow, 1);
                    table.setValueAt(contactField.getText(), selectedRow, 2);
                    JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "Candidate details updated successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "Please select a candidate from the table.");
            }
        }
    });
 // Add ActionListener to the Schedule Interview button
    scheduleInterviewButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String candidateName = (String) table.getValueAt(selectedRow, 0);
                String position = (String) table.getValueAt(selectedRow, 1);

                // Display a dialog to schedule the interview
                JTextField interviewDateField = new JTextField(20);
                JTextField interviewTimeField = new JTextField(20);

                JPanel scheduleInterviewPanel = new JPanel();
                scheduleInterviewPanel.setLayout(new GridLayout(2, 2));
                scheduleInterviewPanel.add(new JLabel("Interview Date:"));
                scheduleInterviewPanel.add(interviewDateField);
                scheduleInterviewPanel.add(new JLabel("Interview Time:"));
                scheduleInterviewPanel.add(interviewTimeField);

                int result = JOptionPane.showConfirmDialog(RecruitmentManagementSystem.this, scheduleInterviewPanel, "Schedule Interview", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String interviewDate = interviewDateField.getText();
                    String interviewTime = interviewTimeField.getText();

                    // Perform further actions for scheduling the interview
                    // For example, store the interview details in a database or trigger notifications

                    JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "Interview scheduled for candidate: " + candidateName + " - Position: " + position + " at date "+ interviewDate + " time " + interviewTime);
                }
            } else {
                JOptionPane.showMessageDialog(RecruitmentManagementSystem.this, "Please select a candidate from the table.");
            }
        }
    });

    setVisible(true);
    }
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecruitmentManagementSystem window = new RecruitmentManagementSystem();
					window.frame.setVisible(true);
					
					//Observer Pattern
			        JobSubject jobSubject = new JobSubject();
			        jobSubject.attach(new HRObserver());
			        jobSubject.attach(new CandidateObserver());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}


