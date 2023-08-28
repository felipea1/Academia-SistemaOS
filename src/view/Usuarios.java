package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class Usuarios extends JDialog {

	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPesquisar;
	private JList listUsers;
	private JScrollPane scrollPane;
	private JLabel lblPerfil;
	private JComboBox cboUF;
	private JCheckBox chckbxSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users4.png")));
		setTitle("Target - Usuários");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 500, 381);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(83, 121, 263, 80);
		getContentPane().add(scrollPane);

		listUsers = new JList();
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		listUsers.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(listUsers);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(26, 11, 46, 14);
		getContentPane().add(lblNewLabel);

		txtID = new JTextField();
		txtID.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtID.setEditable(false);
		txtID.setBounds(83, 8, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setBounds(26, 100, 46, 14);
		getContentPane().add(lblNewLabel_1);

		txtNome = new JTextField();
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBounds(82, 100, 263, 21);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		// uso do validador para limitar o número de caracteres
		txtNome.setDocument(new Validador(50));

		btnPesquisar = new JButton("");
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setBorder(null);
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/pesquisar.png")));
		btnPesquisar.setToolTipText("Buscar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		getRootPane().setDefaultButton(btnPesquisar);

		btnPesquisar.setBounds(370, 45, 32, 32);
		getContentPane().add(btnPesquisar);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(26, 50, 46, 14);
		getContentPane().add(lblNewLabel_2);

		txtLogin = new JTextField();
		txtLogin.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLogin.setBounds(82, 50, 264, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		// uso do validador para limitar o número de caracteres
		txtLogin.setDocument(new Validador(15));

		JLabel lblNewLabel_3 = new JLabel("Senha:");
		lblNewLabel_3.setBounds(26, 150, 46, 14);
		getContentPane().add(lblNewLabel_3);

		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/eraser.png")));
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setBounds(319, 270, 64, 64);
		getContentPane().add(btnLimpar);

		txtSenha = new JPasswordField();
		txtSenha.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtSenha.setBounds(82, 150, 263, 20);
		getContentPane().add(txtSenha);
		// uso do validador para limitar o número de caracteres
		txtSenha.setDocument(new Validador(250));

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/adicionar.png")));
		btnAdicionar.setToolTipText("Adicionar Usuario");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(83, 270, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxSenha.isSelected()) {
					editarUsuarios();
				} else {
					editarUsuariosExcetoSenha();
				}
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/editar4.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(172, 270, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/excluir3.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(245, 270, 64, 64);
		getContentPane().add(btnExcluir);

		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setBounds(26, 220, 46, 14);
		getContentPane().add(lblPerfil);

		cboUF = new JComboBox();
		cboUF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboUF.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboUF.setBounds(83, 220, 67, 22);
		getContentPane().add(cboUF);

		chckbxSenha = new JCheckBox("Alterar senha");
		chckbxSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxSenha.isSelected()) {
					txtSenha.setText(null);
					txtSenha.requestFocus();
					txtSenha.setBackground(Color.YELLOW);
				} else {
					txtSenha.setBackground(Color.white);
				}				
			}
		});
		chckbxSenha.setBounds(26, 178, 143, 23);
		getContentPane().add(chckbxSenha);
	}// fim do construtor

	/*
	 * metódo usado para listar o nome dos usuarios na lista
	 */
	private void listarUsuarios() {
		// a linha abaixo cria um objeto usando como referência um voltar dinamico
		// entre objeto irá temporariamente armazenar as coisas
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o modelo (vetor na linha)
		listUsers.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a query instrução sql
			pst = con.prepareStatement(readLista);
			// executar a query e trazer o resultado para lista
			rs = pst.executeQuery();
			// uso do while para trazer os usuários enquanto existir
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * Método que busca o usuário selecionado da lista
	 */
	private void buscarUsuarioLista() {
		// System.out.println("teste");
		// variável que captura o índice da linha da lista
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o índice 0 e 1 usuário da lista
			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaUsuario);
				// executar e obter o resultado
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane.setVisible(false);
					// setar os campos
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha.setText(rs.getString(4));
					cboUF.setSelectedItem(rs.getString(5));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnPesquisar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
					txtLogin.requestFocus();
				}
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
			scrollPane.setVisible(false);
		}
	}

	/**
	 * Método usado para buscar um usuário no banco
	 */
	private void buscarUsuario() {
		String read = "select * from usuarios where login = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3));
				txtSenha.setText(rs.getString(4));
				cboUF.setSelectedItem(rs.getString(5));
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void LimparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		scrollPane.setVisible(false);
		txtSenha.setBackground(Color.white);
		chckbxSenha.setSelected(false);
		cboUF.setSelectedItem("");
	}

	private void adicionar() {
		// criar uma váriavel para capturar a senha
		String capturarSenha = new String(txtSenha.getPassword());

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Usuario");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuario");
			txtLogin.requestFocus();
		} else if (capturarSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha do Usuario");
			txtSenha.requestFocus();
		} else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do Usuario");
			cboUF.requestFocus();
		} else {

			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				con = dao.conectar();
				// preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturarSenha);
				pst.setString(4, cboUF.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuario adicionado");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuarios() {
		// criar uma váriavel para capturar a senha
		String capturarSenha = new String(txtSenha.getPassword());

		// System.out.println("Teste do botão editar");
		// validação dos campos obrigátorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Usuário!");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Login do Usuário!");
			txtLogin.requestFocus();
		} else if (capturarSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Digite a Senha do Usuário!");
			txtSenha.requestFocus();
		} else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do Usuario");
			cboUF.requestFocus();
		} else {
			// CRUD-Update
			String update = "update usuarios set nome =?,login=?,senha=md5(?), perfil=? where id=?";
			// tratamento de exceções
			try {
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturarSenha);
				pst.setString(4, cboUF.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// Confirmar para o usuario
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso.");
				// limpar os campos
				LimparCampos();
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// Editar usuarios

	private void editarUsuariosExcetoSenha() {
		// criar uma váriavel para capturar a senha
		String capturarSenha = new String(txtSenha.getPassword());

		// System.out.println("Teste do botão editar");
		// validação dos campos obrigátorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Usuário!");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Login do Usuário!");
			txtLogin.requestFocus();
		} else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do Usuario");
			cboUF.requestFocus();
		} else {
			// CRUD-Update
			String update2 = "update usuarios set nome =?,login=?, perfil=? where id=?";
			// tratamento de exceções
			try {
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboUF.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// Confirmar para o usuario
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso.");
				// limpar os campos
				LimparCampos();
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// Editar usuarios

	private void excluirContato() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o contato?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where id=?";
			try {
				// abrir conexão
				con = dao.conectar();
				// preencher a query
				pst = con.prepareStatement(delete);
				// atribuir a ? pelo o id do contato
				pst.setString(1, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// Limpar campos
				LimparCampos();
				// exibir uma mensagem ao usuario
				JOptionPane.showMessageDialog(null, "Usuario excluido!");
				// fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}// fim do código
