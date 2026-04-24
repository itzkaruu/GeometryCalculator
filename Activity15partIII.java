package panelsandlayout;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Activity15partIII {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> layout1.calculator());
    }
}
 
class layout1 {
    static void calculator() {
 
        // ── State ────────────────────────────────────────────────────────────
        // currentField tracks which JTextField is "active" (receiving numpad input)
        final JTextField[] currentField = { null };
 
        JFrame frame = new JFrame("Geometry Calculator");
        frame.setSize(400, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
 
        // ── Display ──────────────────────────────────────────────────────────
        JTextField display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(500, 70));
        display.setEditable(false);
        display.setFont(new Font("Monospaced", Font.BOLD, 22));
        frame.add(display, BorderLayout.NORTH);
 
        // ── Main panel ───────────────────────────────────────────────────────
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
 
        // ── Radio buttons ────────────────────────────────────────────────────
        JPanel radioPanel = new JPanel();
        JRadioButton rbCone     = new JRadioButton("Circular Cone");
        JRadioButton rbCylinder = new JRadioButton("Circular Cylinder");
        JRadioButton rbFrustum  = new JRadioButton("Conical Frustum");
 
        rbCone.setForeground(Color.WHITE);
        rbCylinder.setForeground(Color.WHITE);
        rbFrustum.setForeground(Color.WHITE);
        rbCone.setBackground(Color.DARK_GRAY);
        rbCylinder.setBackground(Color.DARK_GRAY);
        rbFrustum.setBackground(Color.DARK_GRAY);
 
        ButtonGroup group = new ButtonGroup();
        group.add(rbCone); group.add(rbCylinder); group.add(rbFrustum);
        rbCone.setSelected(true); // default
 
        radioPanel.add(rbCone); radioPanel.add(rbCylinder); radioPanel.add(rbFrustum);
        mainPanel.add(radioPanel);
        mainPanel.add(Box.createVerticalStrut(10));
 
        // ── Input panel ──────────────────────────────────────────────────────
        JPanel inputPanel = new JPanel(new GridLayout(3, 3, 5, 6));
 
        JTextField rField = new JTextField();
        JTextField hField = new JTextField();
        JTextField sField = new JTextField();
 
        // Labels — update dynamically based on shape selection
        JLabel rLabel = new JLabel("r (radius)");
        JLabel hLabel = new JLabel("h (height)");
        JLabel sLabel = new JLabel("s (slant)");
 
        Color labelColor = Color.WHITE;
        rLabel.setForeground(labelColor);
        hLabel.setForeground(labelColor);
        sLabel.setForeground(labelColor);
 
        // DEL buttons — clear the matching field
        JButton delR = new JButton("DEL");
        JButton delH = new JButton("DEL");
        JButton delS = new JButton("DEL");
 
        delR.addActionListener(e -> { rField.setText(""); currentField[0] = rField; });
        delH.addActionListener(e -> { hField.setText(""); currentField[0] = hField; });
        delS.addActionListener(e -> { sField.setText(""); currentField[0] = sField; });
 
        inputPanel.add(rLabel); inputPanel.add(rField); inputPanel.add(delR);
        inputPanel.add(hLabel); inputPanel.add(hField); inputPanel.add(delH);
        inputPanel.add(sLabel); inputPanel.add(sField); inputPanel.add(delS);
 
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(15));
 
        // ── Click-to-focus: clicking a field makes it "active" for the numpad ─
        FocusListener focusTracker = new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                currentField[0] = (JTextField) e.getSource();
            }
        };
        rField.addFocusListener(focusTracker);
        hField.addFocusListener(focusTracker);
        sField.addFocusListener(focusTracker);
 
        // ── Numpad + function buttons ─────────────────────────────────────────
        JPanel gridPanel = new JPanel(new GridLayout(4, 4, 5, 5));
 
        String[] buttonLabels = {
            "7",  "8", "9", "Volume",
            "4",  "5", "6", "Base",
            "1",  "2", "3", "Top",
            "0",  ".", "=", "Total"
        };
 
        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
 
            switch (label) {
                // ── Digit / decimal ──────────────────────────────────────────
                case "0": case "1": case "2": case "3": case "4":
                case "5": case "6": case "7": case "8": case "9":
                    btn.addActionListener(e -> {
                        if (currentField[0] != null) {
                            currentField[0].setText(currentField[0].getText() + label);
                        }
                    });
                    break;
 
                case ".":
                    btn.addActionListener(e -> {
                        if (currentField[0] != null) {
                            String cur = currentField[0].getText();
                            if (!cur.contains(".")) {
                                currentField[0].setText(cur.isEmpty() ? "0." : cur + ".");
                            }
                        }
                    });
                    break;
 
                // ── Clear display ────────────────────────────────────────────
                case "=":
                    btn.addActionListener(e -> {
                        display.setText("0");
                        rField.setText(""); hField.setText(""); sField.setText("");
                        currentField[0] = null;
                    });
                    break;
 
                // ── Geometry calculations ─────────────────────────────────────
                case "Volume":
                    btn.addActionListener(e -> {
                        try {
                            double r = parseField(rField, "r");
                            double h = parseField(hField, "h");
                            double result;
                            String shape;
                            if (rbCone.isSelected()) {
                                result = (1.0 / 3.0) * Math.PI * r * r * h;
                                shape = "Cone Volume";
                            } else if (rbCylinder.isSelected()) {
                                result = Math.PI * r * r * h;
                                shape = "Cylinder Volume";
                            } else { // Frustum
                                double r2 = parseField(sField, "r2 (top radius — enter in s)");
                                result = (1.0 / 3.0) * Math.PI * h * (r * r + r * r2 + r2 * r2);
                                shape = "Frustum Volume";
                            }
                            display.setText(shape + " = " + format(result));
                        } catch (NumberFormatException ex) {
                            display.setText("Error: " + ex.getMessage());
                        }
                    });
                    break;
 
                case "Base":
                    btn.addActionListener(e -> {
                        try {
                            double r = parseField(rField, "r");
                            double result = Math.PI * r * r;
                            display.setText("Base Area = " + format(result));
                        } catch (NumberFormatException ex) {
                            display.setText("Error: " + ex.getMessage());
                        }
                    });
                    break;
 
                case "Top":
                    btn.addActionListener(e -> {
                        try {
                            if (rbCone.isSelected()) {
                                display.setText("Cone has no top (point)");
                                return;
                            }
                            if (rbCylinder.isSelected()) {
                                double r = parseField(rField, "r");
                                double result = Math.PI * r * r;
                                display.setText("Top Area = " + format(result));
                            } else { // Frustum — top radius is in sField
                                double r2 = parseField(sField, "r2 (enter top radius in s)");
                                double result = Math.PI * r2 * r2;
                                display.setText("Frustum Top = " + format(result));
                            }
                        } catch (NumberFormatException ex) {
                            display.setText("Error: " + ex.getMessage());
                        }
                    });
                    break;
 
                case "Total":
                    btn.addActionListener(e -> {
                        try {
                            double r = parseField(rField, "r");
                            double result;
                            String shape;
                            if (rbCone.isSelected()) {
                                double s = parseField(sField, "s (slant height)");
                                // Total surface = base + lateral = πr² + πrs
                                result = Math.PI * r * r + Math.PI * r * s;
                                shape = "Cone Total SA";
                            } else if (rbCylinder.isSelected()) {
                                double h = parseField(hField, "h");
                                // Total surface = 2πr² + 2πrh
                                result = 2 * Math.PI * r * r + 2 * Math.PI * r * h;
                                shape = "Cylinder Total SA";
                            } else { // Frustum
                                double r2 = parseField(sField, "r2 (top radius — enter in s)");
                                double h  = parseField(hField, "h");
                                // Slant height computed from r, r2, h
                                double sl = Math.sqrt(h * h + (r - r2) * (r - r2));
                                // Total = π(r1² + r2² + s(r1 + r2))
                                result = Math.PI * (r * r + r2 * r2 + sl * (r + r2));
                                shape = "Frustum Total SA";
                            }
                            display.setText(shape + " = " + format(result));
                        } catch (NumberFormatException ex) {
                            display.setText("Error: " + ex.getMessage());
                        }
                    });
                    break;
            }
 
            gridPanel.add(btn);
        }
 
        mainPanel.add(gridPanel);
 
        // ── Hint label ───────────────────────────────────────────────────────
        JLabel hint = new JLabel(getHint(rbCone));
        hint.setForeground(new Color(180, 180, 180));
        hint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(6));
        mainPanel.add(hint);
 
        // ── Radio button listeners — update label hints ──────────────────────
        ActionListener radioListener = e -> {
            rField.setText(""); hField.setText(""); sField.setText("");
            display.setText("0");
            if (rbFrustum.isSelected()) {
                rLabel.setText("r1 (bot radius)");
                hLabel.setText("h (height)");
                sLabel.setText("r2 (top radius)");
                hint.setText("Frustum: r=bottom, h=height, s=top radius");
            } else if (rbCone.isSelected()) {
                rLabel.setText("r (radius)");
                hLabel.setText("h (height)");
                sLabel.setText("s (slant height)");
                hint.setText("Cone: r=radius, h=height, s=slant height");
            } else {
                rLabel.setText("r (radius)");
                hLabel.setText("h (height)");
                sLabel.setText("s (unused)");
                hint.setText("Cylinder: r=radius, h=height  (s not needed)");
            }
            frame.revalidate();
        };
        rbCone.addActionListener(radioListener);
        rbCylinder.addActionListener(radioListener);
        rbFrustum.addActionListener(radioListener);
 
        // ── Add to frame ──────────────────────────────────────────────────────
        frame.add(mainPanel, BorderLayout.CENTER);
 
        // ── Colors ───────────────────────────────────────────────────────────
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        mainPanel.setBackground(Color.DARK_GRAY);
        radioPanel.setBackground(Color.DARK_GRAY);
        inputPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBackground(Color.DARK_GRAY);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
 
        frame.setVisible(true);
    }
 
    // ── Helpers ───────────────────────────────────────────────────────────────
 
    /** Parse a field's text to double; throws descriptive NumberFormatException if blank/invalid. */
    private static double parseField(JTextField field, String name) throws NumberFormatException {
        String text = field.getText().trim();
        if (text.isEmpty()) throw new NumberFormatException("'" + name + "' is empty");
        return Double.parseDouble(text);
    }
 
    /** Format result to 4 decimal places, trimming trailing zeros. */
    private static String format(double value) {
        String s = String.format("%.4f", value);
        s = s.replaceAll("0+$", "").replaceAll("\\.$", "");
        return s;
    }
 
    private static String getHint(JRadioButton selected) {
        return "Cone: r=radius, h=height, s=slant height";
    }
}