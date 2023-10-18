package week7.com.patikadev.View;

import week7.com.patikadev.Helper.Config;
import week7.com.patikadev.Helper.DBConnector;
import week7.com.patikadev.Helper.Helper;
import week7.com.patikadev.Model.Operator;
import week7.com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel label_welcome;
    private JPanel panel_top;
    private JButton exitButton;
    private JPanel panel_userList;
    private JScrollPane scroll_userList;
    private JTable table_userList;
    private JPanel panel_userForm;
    private JTextField field_name;
    private JTextField field_uName;
    private JTextField field_pass;
    private JComboBox combo_userType;
    private JButton button_userAdd;
    private DefaultTableModel model_userList;
    private Object[] row_userList;
    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;
        Helper.setLayout();
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        label_welcome.setText("Welcome, " + operator.getName() + "!");

        // ModelUserList
        model_userList = new DefaultTableModel();
        Object[] col_userList = {"ID", "Name", "Username", "Password", "Type"};
        model_userList.setColumnIdentifiers(col_userList);

        row_userList = new Object[col_userList.length];
        loadUserModel();

        table_userList.setModel(model_userList);
        table_userList.getTableHeader().setReorderingAllowed(false);

        button_userAdd.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_name) || Helper.isFieldEmpty(field_uName) || Helper.isFieldEmpty(field_pass)) {
                Helper.showMsg("fill");
            } else {
                String name = field_name.getText();
                String uname = field_uName.getText();
                String pass = field_pass.getText();
                String type = combo_userType.getSelectedItem().toString();
                if (User.add(name, uname, pass, type)) {
                    Helper.showMsg("done");
                    loadUserModel();
                    field_name.setText(null);
                    field_uName.setText(null);
                    field_pass.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_userList.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getList()) {
            int i = 0;
            row_userList[i++] = obj.getId();
            row_userList[i++] = obj.getName();
            row_userList[i++] = obj.getuName();
            row_userList[i++] = obj.getPass();
            row_userList[i++] = obj.getType();
            model_userList.addRow(row_userList);
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator(1, "Can İşcan", "caniscan", "1234", "operator");
        DBConnector.getInstance();
        OperatorGUI operatorGUI = new OperatorGUI(op);
    }
}