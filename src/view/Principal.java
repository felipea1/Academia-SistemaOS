package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.Font;
import java.awt.Color;

public class Principal extends JFrame {

	// Instaciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	private JLabel lblNomeUS;
	// a label abaixo será alterada pela tela de "Login.java" (Por isso está publica)
	public JLabel lblUsuario;
	// o button abaixo será alterada pela tela de "Login.java" (Por isso está publica)
	public JButton btnUsuarios;
	public JButton btnRelatorio;
	public JPanel panelRodape;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setResizable(false);
		setTitle("Target assistencia");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/run.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// evento ativar janela
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// abrir tela de usuários
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/users4.png")));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setBounds(100, 87, 128, 128);
		contentPane.add(btnUsuarios);

		panelRodape = new JPanel();
		panelRodape.setBackground(new Color(0, 0, 0));
		panelRodape.setBounds(0, 507, 784, 54);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdoff.png")));
		lblStatus.setBounds(740, 11, 32, 32);
		panelRodape.add(lblStatus);

		lblData = new JLabel("");
		lblData.setBounds(431, 11, 300, 30);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblNomeUS = new JLabel("Usuário:");
		lblNomeUS.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNomeUS.setForeground(SystemColor.text);
		lblNomeUS.setBounds(10, 11, 69, 32);
		panelRodape.add(lblNomeUS);
		
		lblUsuario = new JLabel("");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsuario.setBounds(75, 11, 250, 32);
		panelRodape.add(lblUsuario);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/os2.png")));
		lblLogo.setBounds(650, 375, 128, 128);
		contentPane.add(lblLogo);

		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// clicar no botão sobre
				// mostrar a janela sobre
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBorder(null);
		btnSobre.setToolTipText("Sobre");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setIcon(new ImageIcon(Usuarios.class.getResource("/img/about.png")));
		btnSobre.setBounds(720, 11, 48, 48);
		contentPane.add(btnSobre);
		
		JButton btnOrdemOS = new JButton("");
		btnOrdemOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnOrdemOS.setIcon(new ImageIcon(Principal.class.getResource("/img/OrdemOS.png")));
		btnOrdemOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOrdemOS.setToolTipText("Ordem de Serviço");
		btnOrdemOS.setBounds(300, 87, 128, 128);
		contentPane.add(btnOrdemOS);
		
		btnRelatorio = new JButton("");
		btnRelatorio.setEnabled(false);
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/relatorio.png")));
		btnRelatorio.setToolTipText("Relátorios");
		btnRelatorio.setBounds(100, 250, 128, 128);
		contentPane.add(btnRelatorio);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// abrir tela de usuários
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes.png")));
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(300, 250, 128, 128);
		contentPane.add(btnClientes);
		
		JButton btnFornecedores = new JButton("");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnFornecedores.setIcon(new ImageIcon(Principal.class.getResource("/img/Fornecedores.png")));
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setToolTipText("Fornecedores");
		btnFornecedores.setBounds(500, 87, 128, 128);
		contentPane.add(btnFornecedores);
		
		JButton btnProdutos = new JButton("");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/produtos.png")));
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.setBounds(500, 250, 128, 128);
		contentPane.add(btnProdutos);
	}// fim do construtor

	/**
	 * Método responsável por exibir o status da conexão
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
	}// fim do método status()

	/**
	 * Método responsável por setar a data no rodapé
	 */
	private void setarData() {
		// criar objeto para trazer a data do sistema
		Date data = new Date();
		// criar objeto para formatar a data
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
	}
}
