# Java Swing Workshop

| # | Program | Concepts |
| --- | --- | --- |
| 1 | `HelloSwing` | `JFrame`, `JLabel`, EDT, `setDefaultCloseOperation` |
| 2 | `ButtonsAndLabels` | `JPanel`, `FlowLayout`, adding components |
| 3 | `ClickCounter` | Delegation event model, `ActionListener`, lambda |
| 4 | `LayoutTour` | `BorderLayout`, `GridLayout`, `FlowLayout`, nested panels |
| 5 | `RegistrationForm` | Text fields, radio buttons, `ButtonGroup`, checkbox, combo box, `JOptionPane` |
| 6 | `SimpleCalculator` | `GridLayout` keypad, shared listener, `getActionCommand()`, parsing input |

## Installation and setup

### Check whether Java is already installed

```
java -version
javac -version
```

### Install with the MSI installer

1. Open [adoptium.net](https://adoptium.net/) in a browser.
2. Choose **Temurin 21 (LTS)**, Operating System **Windows**, Architecture **x64**, Package **JDK**, and download the **.msi** file. (as per your machine)
3. Double-click the downloaded `.msi`.
4. Click **Next → Install**, accept the prompt, then **Finish**.
5. **Close every open Terminal** New PATH values are picked up only by new windows.

### Verify the installation

```
java -version
javac -version
```

### Compile and run from the terminal

After writing Exercise 1 in Notepad or any editor, open a terminal in the folder where you saved it and run:

```
java HelloSwing.java
```

## Exercises

### Exercise 1 — HelloSwing

**Goal:** open a window correctly.

```java
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class HelloSwing {
    public static void main(String[] args) {
        // All Swing work must run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hello Swing");
            JLabel label = new JLabel("Welcome to Java Swing!", SwingConstants.CENTER);
            frame.add(label);                                     // goes to the content pane
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop the JVM on close
            frame.setLocationRelativeTo(null);                    // centre on screen
            frame.setVisible(true);                               // always last
        });
    }
}
```

**Steps**

1. Save as `HelloSwing.java` (file name must match the public class name exactly).
2. Run it. A 400 × 200 window appears in the centre of the screen.
3. Close the window. In the terminal, the program ends.

**Try it**

1. Remove `setDefaultCloseOperation(...)`, run, and close the window. The terminal does not return — the JVM is still running. Press **Ctrl + C**. Put the line back.
2. Move `setVisible(true)` to just after `new JFrame(...)`. Notice the label may not appear until you resize. Move it back and explain why it must be last.
3. Change the font: `label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));`

### Exercise 2 — ButtonsAndLabels

**Goal:** put several components in a `JPanel` with `FlowLayout`.

```java
import java.awt.FlowLayout;
import javax.swing.*;

public class ButtonsAndLabels {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ButtonsAndLabels::createUI);
    }

    private static void createUI() {
        JFrame frame = new JFrame("Buttons and Labels");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));

        panel.add(new JLabel("Name:"));
        panel.add(new JTextField(12));
        panel.add(new JButton("Save"));
        panel.add(new JButton("Clear"));

        frame.setContentPane(panel);
        frame.pack();                       // size to fit the components
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

**Try it**

1. Drag the window narrower. Components wrap to the next row — that is `FlowLayout`.
2. Change `FlowLayout.CENTER` to `FlowLayout.LEFT` and `RIGHT`.
3. Replace `frame.pack()` with `frame.setSize(600, 120)` and compare. Discuss when each is better.
4. Add a tooltip: create the Save button in a variable and call `save.setToolTipText("Saves the name");`

### Exercise 3 — ClickCounter

**Goal:** handle events using the delegation event model (source → event object → listener).

```java
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClickCounter extends JFrame {
    private int count = 0;
    private final JLabel display = new JLabel("0", SwingConstants.CENTER);

    public ClickCounter() {
        super("Click Counter");
        display.setFont(new Font("Segoe UI", Font.BOLD, 48));

        JButton plus  = new JButton("+1");
        JButton reset = new JButton("Reset");

        // Style 1: anonymous inner class
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                display.setText(String.valueOf(count));
            }
        });

        // Style 2: lambda (ActionListener is a functional interface)
        reset.addActionListener(e -> {
            count = 0;
            display.setText("0");
        });

        JPanel buttons = new JPanel();
        buttons.add(plus);
        buttons.add(reset);

        add(display, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setSize(300, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClickCounter().setVisible(true));
    }
}
```

**Try it**

1. Add a **−1** button using a lambda. Do not let the count go below 0.
2. Make the number turn red when count ≥ 10: `display.setForeground(java.awt.Color.RED);`
3. Print `e.getActionCommand()` and `e.getSource()` inside a listener and read the console output.

### Exercise 4 — LayoutTour

**Goal:** see the three most-used layout managers side by side, and nest panels.

```java
import java.awt.*;
import javax.swing.*;

public class LayoutTour {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LayoutTour::createUI);
    }

    private static void createUI() {
        JFrame frame = new JFrame("Layout Tour");
        frame.setLayout(new BorderLayout(8, 8));       // JFrame default is BorderLayout anyway

        // NORTH: a FlowLayout toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(new JButton("New"));
        toolbar.add(new JButton("Open"));
        toolbar.add(new JButton("Save"));

        // WEST: a GridLayout menu (4 rows, 1 column)
        JPanel side = new JPanel(new GridLayout(4, 1, 5, 5));
        for (String s : new String[]{"Home", "Courses", "Marks", "Logout"}) {
            side.add(new JButton(s));
        }

        // CENTER: a text area that grows with the window
        JTextArea area = new JTextArea("CENTER gets all the extra space.");

        // SOUTH: a status bar
        JLabel status = new JLabel(" Ready");
        status.setBorder(BorderFactory.createEtchedBorder());

        frame.add(toolbar, BorderLayout.NORTH);
        frame.add(side, BorderLayout.WEST);
        frame.add(new JScrollPane(area), BorderLayout.CENTER);
        frame.add(status, BorderLayout.SOUTH);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

**Try it**

1. Resize the window. Note which regions grow (CENTER both ways, NORTH/SOUTH only horizontally, WEST/EAST only vertically).
2. Add a component to `BorderLayout.EAST`.
3. Add two components to `BorderLayout.CENTER`. Only the last one shows — each region holds **one** component. That is why we nest `JPanel`s.
4. Change the side panel to `new GridLayout(2, 2)` and observe.

### Exercise 5 — RegistrationForm

**Goal:** use the common input components and read their values.

```java
import java.awt.*;
import javax.swing.*;

public class RegistrationForm extends JFrame {
    private final JTextField nameField = new JTextField(15);
    private final JTextField rollField = new JTextField(15);
    private final JPasswordField passField = new JPasswordField(15);
    private final JRadioButton male = new JRadioButton("Male");
    private final JRadioButton female = new JRadioButton("Female");
    private final JComboBox<String> branch =
            new JComboBox<>(new String[]{"CSE", "CSE (AI&ML)", "CSE (DS)", "IT", "ECE", "EEE"});
    private final JCheckBox agree = new JCheckBox("I confirm the details are correct");

    public RegistrationForm() {
        super("Student Registration");

        ButtonGroup gender = new ButtonGroup();   // makes the radios mutually exclusive
        gender.add(male);
        gender.add(female);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(male);
        genderPanel.add(female);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        form.add(new JLabel("Name:"));      form.add(nameField);
        form.add(new JLabel("Roll No:"));   form.add(rollField);
        form.add(new JLabel("Password:"));  form.add(passField);
        form.add(new JLabel("Gender:"));    form.add(genderPanel);
        form.add(new JLabel("Branch:"));    form.add(branch);

        JButton submit = new JButton("Submit");
        submit.setEnabled(false);
        agree.addItemListener(e -> submit.setEnabled(agree.isSelected()));
        submit.addActionListener(e -> onSubmit());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        bottom.add(agree, BorderLayout.CENTER);
        bottom.add(submit, BorderLayout.EAST);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void onSubmit() {
        String name = nameField.getText().trim();
        if (name.isEmpty() || rollField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Name and Roll No are required.",
                    "Missing data", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String g = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Not given";
        String msg = "Name: " + name
                + "\nRoll No: " + rollField.getText()
                + "\nGender: " + g
                + "\nBranch: " + branch.getSelectedItem()
                + "\nPassword length: " + passField.getPassword().length;
        JOptionPane.showMessageDialog(this, msg, "Registered", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm().setVisible(true));
    }
}
```

**Try it**

1. Remove the `ButtonGroup` and run. Both radios can be selected — put it back.
2. Add a **Year** `JComboBox` (I, II, III, IV) and include it in the message.
3. Add a **Reset** button that clears all fields and calls `gender.clearSelection()` (make `gender` a field first).
4. Validate that Roll No has exactly 10 characters.

### Exercise 6 — SimpleCalculator

**Goal:** build a keypad with `GridLayout` and use one shared listener for many buttons.

```java
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SimpleCalculator extends JFrame implements ActionListener {
    private final JTextField display = new JTextField("0");
    private double first = 0;
    private String op = "";
    private boolean startNew = true;

    public SimpleCalculator() {
        super("Calculator");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Consolas", Font.BOLD, 28));

        String[] keys = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };
        JPanel pad = new JPanel(new GridLayout(4, 4, 6, 6));
        for (String k : keys) {
            JButton b = new JButton(k);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            b.addActionListener(this);        // one listener for all 16 buttons
            pad.add(b);
        }

        add(display, BorderLayout.NORTH);
        add(pad, BorderLayout.CENTER);
        setSize(300, 380);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String k = e.getActionCommand();      // the button's text
        if (k.matches("[0-9]")) {
            display.setText(startNew ? k : display.getText() + k);
            startNew = false;
        } else if (k.equals("C")) {
            display.setText("0"); first = 0; op = ""; startNew = true;
        } else if (k.equals("=")) {
            display.setText(format(compute(first, Double.parseDouble(display.getText()), op)));
            op = ""; startNew = true;
        } else {                              // + - * /
            first = Double.parseDouble(display.getText());
            op = k; startNew = true;
        }
    }

    private double compute(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> b == 0 ? Double.NaN : a / b;
            default  -> b;
        };
    }

    private String format(double v) {
        return (v == (long) v) ? String.valueOf((long) v) : String.valueOf(v);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleCalculator().setVisible(true));
    }
}
```

**Try it**

1. Divide by zero. Show "Error" instead of `NaN`.
2. Add a decimal point `.` key (change the grid to 5 rows).

## References

- [Oracle Java Tutorials — Creating a GUI with Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
