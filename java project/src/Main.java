import javax.swing.*;
import java.awt.event.ComponentAdapter;

public class Main {
    private JPanel MainPanel;
    private JTextArea textArea1;

    public Main() {
        textArea1.addComponentListener(new ComponentAdapter() {
            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }
        });
    }

    public static void main(String[] args) {

    }
}

