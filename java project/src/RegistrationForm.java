import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog{
    private JTextField tfName;
    private JTextField tfreg;
    private JPasswordField pfpass;
    private JPasswordField pfCpass;
    private JButton btnregister;
    private JButton btncancel;
    private JPanel RegisterPannel;

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Create a new account");
        setContentPane(RegisterPannel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


        btnregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = tfName.getText();
        String reg = tfreg.getText();
        String pass = String.valueOf(pfpass.getPassword());
        String confirmpass=String.valueOf(pfCpass.getPassword());

        if(name.isEmpty() || reg.isEmpty() || pass.isEmpty() || confirmpass.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please fill all the fields",
            "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!pass.equals(confirmpass)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        user=addUserToDatabase(name,reg,pass);
        if (user!=null){
            dispose();

        }
        else{
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",JOptionPane.ERROR_MESSAGE);
        }
    }
    public Users user;
    private Users addUserToDatabase(String name, String reg, String pass) {
        Users user = null;
        final String DB_URL="jdbc:mysql://localhost:3306/login";
        final String USER="root";
        final String PASSWORD="";
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USER, PASSWORD);

            Statement stmt=conn.createStatement();
            String sql="INSERT INTO Users(RegNum,Name,Password)"+
                    " VALUES(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, reg);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3,pass);

            int addedRows=preparedStatement.executeUpdate();
            if(addedRows>0){
                user=new Users();
                user.Name=name;
                user.RegNum=reg;
                user.Password=pass;


            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }



            return user;
    }


    public static void main(String[] args) {
        RegistrationForm myform =new RegistrationForm(null);
        Users user=myform.user;
        if(user!=null){
            JOptionPane.showMessageDialog(null,"Succesful registration of : "+user.Name);
           // System.out.println("Succesful registration of : "+user.Name);

        }else{
            System.out.println("Failed registration of : "+user.Name);
        }
    }
}
