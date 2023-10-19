package view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Color;

public class Login extends JFrame {
	// Objetos JOBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	// objeto tela principal
	Principal principal = new Principal();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblData;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/9071369_dumbbell_icon.png")));
		setResizable(false);
		setTitle("Target - Login");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// evento ativar janela
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLogin.setBounds(62, 32, 46, 14);
		contentPane.add(lblLogin);

		txtLogin = new JTextField();
		txtLogin.setBounds(62, 51, 200, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSenha.setBounds(62, 82, 46, 14);
		contentPane.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(62, 107, 200, 20);
		contentPane.add(txtSenha);

		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(304, 150, 89, 23);
		contentPane.add(btnAcessar);

		// Substituir o clique pela a tecla enter
		getRootPane().setDefaultButton(btnAcessar);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(0, 210, 434, 51);
		contentPane.add(panel);
		panel.setLayout(null);

		lblStatus = new JLabel("New label");
		lblStatus.setBounds(392, 11, 32, 32);
		panel.add(lblStatus);
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/bdoff.png")));

		lblData = new JLabel("");
		lblData.setForeground(new Color(255, 255, 255));
		lblData.setBounds(10, 13, 317, 30);
		panel.add(lblData);
		lblData.setBackground(new Color(255, 255, 255));
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
	}// Fim do construtor

	/**
	 * Metódo responsável por exibir o status da conexão
	 */
	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdoff.png")));
			} else {
				// System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdon.png")));
			}
			// NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	} // Fim do metódo status

	/**
	 * Metódo respnsável por setar a data no rodapé
	 */
	private void setarData() {
		// criar objeto para trazer a data do sistema
		Date data = new Date();
		// criar objeto para formatar a data
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
	}

	/**
	 * Método para autenticar um usuário
	 */
	private void logar() {
		// criar uma váriavel para capturar a senha
		String capturarSenha = new String(txtSenha.getPassword());

		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login!");
			txtLogin.requestFocus();
		} else if (capturarSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha!");
			txtSenha.requestFocus();
		} else {
			// Logica principal
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturarSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					// Capturar o perfil do usuário
					// System.out.println(rs.getString(5)); //apoio a lógica
					// tratamento do perfil do usuário
					String perfil = rs.getString(5);
					if (perfil.equals("admin")) {
						// logar -> acessar a tela principal
						principal.setVisible(true);
						// setar a label da tela principal com o nome do usuário
						principal.lblUsuario.setText(rs.getString(2));
						// habilitar os botões
						principal.btnRelatorio.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						// mudar a cor do rodapé
						principal.panelRodape.setBackground(new Color(136, 0, 21));
						// fechar a tela de login
						this.dispose();
					} else {
						// logar -> acessar a tela principal
						principal.setVisible(true);
						// setar a label da tela principal com o nome do usuário
						principal.lblUsuario.setText(rs.getString(2));
						// fechar a tela de login
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido(s)!");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}// Fim do código
