import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeApp {
    private static JList<String> employeeList;
    private static DefaultListModel<String> model;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee App");
        ImageIcon appIcon = new ImageIcon("book-icon.png");
        // Set the app icon
        frame.setIconImage(appIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1920);
        frame.setLayout(new BorderLayout());

        model = new DefaultListModel<>();
        employeeList = new JList<>(model);

        JButton refreshButton = createButton("Refresh", e -> updateEmployeeList());
        JButton addButton = createButton("Add Employee", e -> showAddEmployeeDialog());
        JButton updateButton = createButton("Update Employee", e -> showUpdateEmployeeDialog());
        JButton deleteButton = createButton("Delete Employee", e -> showDeleteEmployeeDialog());


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x34, 0x57, 0xD5));
        buttonPanel.add(Box.createRigidArea(new Dimension(150, 20)));
        buttonPanel.add(refreshButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(150, 10)));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(150, 10)));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(150, 10)));
        buttonPanel.add(deleteButton);


        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x34, 0x57, 0xD5));
        JScrollPane scrollPane = new JScrollPane(employeeList);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));


        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0x34, 0x57, 0xD5));
        JLabel titleLabel = new JLabel("Employee Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(new Color(0x34, 0x57, 0xD5));

        ImageIcon imageIcon = new ImageIcon("book-icon.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imagePanel.add(imageLabel);
        imagePanel.setSize(200,200);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(imagePanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        updateEmployeeList();

    }

    private static JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0xF0, 0xF8, 0xFF));
        button.addActionListener(listener);
        return button;
    }

    private static void updateEmployeeList() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employees = employeeDAO.getAllEmployees();

        model.clear();
        for (Employee employee : employees) {
            model.addElement(employee.getId() + ": " + employee.getName() + " (Age: " + employee.getAge() + ")"+ " (Job: " + employee.getJob() + ")");
        }
    }

    private static void showAddEmployeeDialog() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField jobField = new JTextField();

        // Set preferred width for the text fields
        nameField.setPreferredSize(new Dimension(200, 25));
        ageField.setPreferredSize(new Dimension(200, 25));
        jobField.setPreferredSize(new Dimension(200, 25));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Job:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(jobField, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Employee",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String job = jobField.getText();

            Employee newEmployee = new Employee(0, name, age, job);
            EmployeeDAO employeeDAO = new EmployeeDAO();
            employeeDAO.addEmployee(newEmployee);

            updateEmployeeList();
        }
    }


    private static void showUpdateEmployeeDialog() {
        String selectedEmployee = (String) JOptionPane.showInputDialog(null,
                "Select an employee to update:", "Update Employee",
                JOptionPane.QUESTION_MESSAGE, null, model.toArray(), model.getElementAt(0));

        if (selectedEmployee != null) {
            String[] parts = selectedEmployee.split(":");
            int employeeId = Integer.parseInt(parts[0].trim());

            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField jobField = new JTextField();

            nameField.setPreferredSize(new Dimension(200, 25));
            ageField.setPreferredSize(new Dimension(200, 25));
            jobField.setPreferredSize(new Dimension(200, 25));

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Name:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("Age:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(ageField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(new JLabel("Job:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(jobField, gbc);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Employee",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String job = jobField.getText();

                Employee updatedEmployee = new Employee(employeeId, name, age, job);
                EmployeeDAO employeeDAO = new EmployeeDAO();
                employeeDAO.updateEmployee(updatedEmployee);

                updateEmployeeList();
            }
        }
    }


    private static void showDeleteEmployeeDialog() {
        String selectedEmployee = (String) JOptionPane.showInputDialog(null,
                "Select an employee to delete:", "Delete Employee",
                JOptionPane.QUESTION_MESSAGE, null, model.toArray(), model.getElementAt(0));

        if (selectedEmployee != null) {
            String[] parts = selectedEmployee.split(":");
            int employeeId = Integer.parseInt(parts[0].trim());

            int result = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this employee?", "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                EmployeeDAO employeeDAO = new EmployeeDAO();
                employeeDAO.deleteEmployee(employeeId);

                updateEmployeeList();
            }
        }
    }
}
