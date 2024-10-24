import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JPanel panel1;
    private JButton btnlog;
    private JButton btncancel;
    private JTextField tfreg;
    private JPasswordField pfpassword;
    private JButton btnsignup;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(panel1);
        setMinimumSize(new Dimension(900, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reg = tfreg.getText();
                String password= String.valueOf(pfpassword.getPassword());

                user=getAuthenticatedUser(reg,password);

                if (user!=null){
                    dispose();

                }
                else{
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Invalid Student Reg Number or password",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
        });

        setVisible(true);
        btnsignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




            }
        });
    }
    public Users user;
    private Users getAuthenticatedUser(String reg, String password) {
        Users user=null;

        final String DB_URL="jdbc:mysql://localhost:3306/login";
        final String USER="root";
        final String PASSWORD="";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USER, PASSWORD);


            Statement stmt=conn.createStatement();
            String sql="SELECT * FROM Users WHERE RegNum=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, reg);
            preparedStatement.setString(2,password);

           ResultSet resultSet=preparedStatement.executeQuery();

           if (resultSet.next()){
               user=new Users ();
               user.Name=resultSet.getString("Name");
               user.Password=resultSet.getString("Password");
               user.RegNum=resultSet.getString("RegNum");
           }
           stmt.close();
           conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;


    }




    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        Users user= loginForm.user;
        if (user != null) {

            JOptionPane.showMessageDialog(null,
                    "Successful Authentication of: " + user.RegNum + " - Student Name: " + user.Name);


            //System.out.println("Successful Authentication of: " + user.RegNum);
            //System.out.println("Student Name: " + user.Name);
        }
        else{
            System.out.println("Authentication canceled");
        }
    }
}
