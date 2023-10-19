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
import java.awt.Font;

@SuppressWarnings("unused")
public class Usuarios extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPesquisar;
	@SuppressWarnings("rawtypes")
	private JList listUsers;
	private JScrollPane scrollPane;
	private JLabel lblPerfil;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUF;
	private JCheckBox chckbxSenha;
	private JLabel lblNewLabel_5;

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users4.png")));
		setTitle("Target - Usuários");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(100, 227, 320, 62);
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
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(26, 11, 46, 20);
		getContentPane().add(lblNewLabel);

		txtID = new JTextField();
		txtID.setForeground(new Color(0, 204, 51));
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtID.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtID.setEditable(false);
		txtID.setBounds(100, 10, 100, 32);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1.setBounds(26, 200, 113, 20);
		getContentPane().add(lblNewLabel_1);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBounds(100, 200, 320, 32);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
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

		btnPesquisar.setBounds(432, 100, 32, 32);
		getContentPane().add(btnPesquisar);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_2.setBounds(26, 100, 60, 20);
		getContentPane().add(lblNewLabel_2);

		txtLogin = new JTextField();
		txtLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtLogin.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLogin.setBounds(100, 100, 320, 32);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(15));

		JLabel lblNewLabel_3 = new JLabel("Senha:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_3.setBounds(26, 300, 100, 20);
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
		btnLimpar.setBounds(500, 490, 64, 64);
		getContentPane().add(btnLimpar);

		txtSenha = new JPasswordField();
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtSenha.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtSenha.setBounds(100, 300, 320, 32);
		getContentPane().add(txtSenha);
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
		btnAdicionar.setBounds(200, 490, 64, 64);
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
		btnEditar.setBounds(300, 490, 64, 64);
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
		btnExcluir.setBounds(400, 490, 64, 64);
		getContentPane().add(btnExcluir);

		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPerfil.setBounds(26, 400, 60, 20);
		getContentPane().add(lblPerfil);

		cboUF = new JComboBox();
		cboUF.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cboUF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboUF.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboUF.setBounds(100, 400, 140, 32);
		getContentPane().add(cboUF);

		chckbxSenha = new JCheckBox("Alterar senha");
		chckbxSenha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSenha.setFont(new Font("Tahoma", Font.BOLD, 17));
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
		chckbxSenha.setBounds(432, 300, 171, 32);
		getContentPane().add(chckbxSenha);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(128, 64, 64));
		lblNewLabel_4.setBounds(0, 480, 784, 91);
		getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Usuarios.class.getResource("/img/os2.png")));
		lblNewLabel_5.setBounds(650, 350, 128, 128);
		getContentPane().add(lblNewLabel_5);
	}

	@SuppressWarnings("unchecked")
	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listUsers.setModel(modelo);
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
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

	private void buscarUsuarioLista() {
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
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
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPane.setVisible(false);
		}
	}

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
		String capturarSenha = new String(txtSenha.getPassword());

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
			String update = "update usuarios set nome =?,login=?,senha=md5(?), perfil=? where id=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturarSenha);
				pst.setString(4, cboUF.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso.");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuariosExcetoSenha() {
		String capturarSenha = new String(txtSenha.getPassword());

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
			String update2 = "update usuarios set nome =?,login=?, perfil=? where id=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboUF.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso.");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void excluirContato() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o contato?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where id=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				LimparCampos();
				JOptionPane.showMessageDialog(null, "Usuario excluido!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
