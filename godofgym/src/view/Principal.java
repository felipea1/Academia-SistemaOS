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

	DAO dao = new DAO();
	private Connection con;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	private JLabel lblNomeUS;
	public JLabel lblUsuario;
	public JButton btnUsuarios;
	public JButton btnRelatorio;
	public JPanel panelRodape;
	private JLabel lblUsuarios;
	private JLabel lblOrdemOS;
	private JLabel lblFornecedores;
	private JLabel lblRelatorio;
	private JLabel lblClientes;
	private JLabel lblProdutos;

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

	public Principal() {
		setResizable(false);
		setTitle("God of gym - principal");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/128x128 god of gym.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
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
		panelRodape.setBounds(0, 480, 784, 91);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdoff.png")));
		lblStatus.setBounds(740, 32, 32, 32);
		panelRodape.add(lblStatus);

		lblData = new JLabel("");
		lblData.setBounds(431, 32, 300, 30);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNomeUS = new JLabel("Usuário:");
		lblNomeUS.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNomeUS.setForeground(SystemColor.text);
		lblNomeUS.setBounds(10, 32, 69, 32);
		panelRodape.add(lblNomeUS);

		lblUsuario = new JLabel("");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsuario.setBounds(75, 32, 250, 32);
		panelRodape.add(lblUsuario);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/128x128 god of gym.png")));
		lblLogo.setBounds(650, 350, 128, 128);
		contentPane.add(lblLogo);

		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnRelatorio.setBounds(100, 300, 128, 128);
		contentPane.add(btnRelatorio);

		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes.png")));
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(300, 300, 128, 128);
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
		btnProdutos.setBounds(500, 300, 128, 128);
		contentPane.add(btnProdutos);
		
		lblUsuarios = new JLabel("Usuários");
		lblUsuarios.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblUsuarios.setBounds(110, 45, 128, 31);
		contentPane.add(lblUsuarios);
		
		lblOrdemOS = new JLabel("OS");
		lblOrdemOS.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblOrdemOS.setBounds(350, 45, 55, 31);
		contentPane.add(lblOrdemOS);
		
		lblFornecedores = new JLabel("Fornecedores");
		lblFornecedores.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblFornecedores.setBounds(485, 45, 198, 31);
		contentPane.add(lblFornecedores);
		
		lblRelatorio = new JLabel("Relatórios");
		lblRelatorio.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblRelatorio.setBounds(105, 258, 128, 31);
		contentPane.add(lblRelatorio);
		
		lblClientes = new JLabel("Clientes");
		lblClientes.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblClientes.setBounds(315, 258, 117, 31);
		contentPane.add(lblClientes);
		
		lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblProdutos.setBounds(510, 258, 117, 31);
		contentPane.add(lblProdutos);
	}

	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdoff.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdon.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}
}
