package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class Clientes extends JDialog {
	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtFone;
	private JTextField txtEmail;
	private JTextField txtID;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JLabel lblEndereço;
	private JLabel lblNumero;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JLabel lblCEP;
	private JTextField txtCep;
	private JLabel lblUF;
	private JLabel lblComplemento;
	private JTextField txtComplemento;
	private JComboBox cboUf;
	private JButton btnBuscarCep;
	private JScrollPane scrollPane;
	private JList listCli;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
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
	public Clientes() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/clientes.png")));
		setModal(true);
		setTitle("Target - Clientes");
		setResizable(false);
		setBounds(100, 100, 655, 500);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(50, 88, 250, 56);
		getContentPane().add(scrollPane);
		
		listCli = new JList();
		scrollPane.setViewportView(listCli);
		listCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		listCli.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 72, 46, 14);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBounds(50, 69, 250, 20);
		getContentPane().add(txtNome);
		txtNome.setDocument(new Validador(50));
		txtNome.setColumns(10);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setBounds(356, 100, 58, 14);
		getContentPane().add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(419, 97, 210, 20);
		txtEndereco.setDocument(new Validador(50));
		getContentPane().add(txtEndereco);

		JLabel lblFoneCli = new JLabel("Telefone");
		lblFoneCli.setBounds(10, 130, 58, 14);
		getContentPane().add(lblFoneCli);

		txtFone = new JTextField();
		txtFone.setColumns(10);
		txtFone.setBounds(68, 131, 180, 20);
		txtFone.setDocument(new Validador(15));
		getContentPane().add(txtFone);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 180, 46, 14);
		getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(50, 181, 232, 20);
		getContentPane().add(txtEmail);
		txtEmail.setDocument(new Validador(50));
		txtEmail.setColumns(10);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdicionarCli();
			}
		});
		btnAdicionar.setToolTipText("Adicionar Cliente");
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/adicionarCli.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(50, 380, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarClientes();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/editarCli.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(150, 380, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExcluirCliente();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setToolTipText("Apagar");
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/deletarCli.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(250, 380, 64, 64);
		getContentPane().add(btnExcluir);

		JButton btnLimparCli = new JButton("");
		btnLimparCli.setToolTipText("Limpar");
		btnLimparCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimparCli.setIcon(new ImageIcon(Clientes.class.getResource("/img/eraser.png")));
		btnLimparCli.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimparCli.setContentAreaFilled(false);
		btnLimparCli.setBorder(null);
		btnLimparCli.setBounds(350, 380, 64, 64);
		getContentPane().add(btnLimparCli);

		JLabel lblIDcli = new JLabel("ID");
		lblIDcli.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIDcli.setBounds(10, 10, 46, 14);
		getContentPane().add(lblIDcli);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(30, 10, 70, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblInformacoes = new JLabel("Informações pessoais:");
		lblInformacoes.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInformacoes.setBounds(10, 35, 160, 14);
		getContentPane().add(lblInformacoes);

		lblEndereço = new JLabel("Endereço:");
		lblEndereço.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEndereço.setBounds(430, 35, 87, 14);
		getContentPane().add(lblEndereço);

		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(356, 130, 58, 14);
		getContentPane().add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(409, 131, 100, 20);
		txtNumero.setDocument(new Validador(10));
		getContentPane().add(txtNumero);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(356, 180, 58, 14);
		getContentPane().add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(409, 181, 200, 20);
		txtBairro.setDocument(new Validador(30));
		getContentPane().add(txtBairro);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(356, 230, 58, 14);
		getContentPane().add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(409, 231, 200, 20);
		txtCidade.setDocument(new Validador(30));
		getContentPane().add(txtCidade);

		lblCEP = new JLabel("CEP:");
		lblCEP.setBounds(356, 72, 58, 14);
		getContentPane().add(lblCEP);

		txtCep = new JTextField();
		txtCep.setColumns(10);
		txtCep.setBounds(396, 69, 153, 20);
		txtCep.setDocument(new Validador(10));
		getContentPane().add(txtCep);

		lblUF = new JLabel("UF:");
		lblUF.setBounds(356, 330, 58, 14);
		getContentPane().add(lblUF);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUf.setBounds(409, 331, 50, 22);
		getContentPane().add(cboUf);

		btnBuscarCep = new JButton("");
		btnBuscarCep.setContentAreaFilled(false);
		btnBuscarCep.setToolTipText("Buscar cep");
		btnBuscarCep.setBorder(null);
		btnBuscarCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarCep.setIcon(new ImageIcon(Clientes.class.getResource("/img/pesquisar.png")));
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(559, 60, 32, 32);
		getContentPane().add(btnBuscarCep);

		lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(356, 280, 87, 14);
		getContentPane().add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(444, 281, 150, 20);
		txtComplemento.setDocument(new Validador(20));
		getContentPane().add(txtComplemento);

	} // fim do construtor

	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listCli.setModel(modelo);
		String readListaCli = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaCli);
			rs = pst.executeQuery();
			while(rs.next()) {
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
	
	private void buscarClienteLista() {
		int linha = listCli.getSelectedIndex();
		if (linha >= 0) {
			String readListaCli = "select * from clientes where nome like '" + txtNome.getText() + "%'"	+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCli);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtFone.setText(rs.getString(3));
					txtCep.setText(rs.getString(4));
					txtEndereco.setText(rs.getString(5));
					txtNumero.setText(rs.getString(6));
					txtComplemento.setText(rs.getString(7));
					txtBairro.setText(rs.getString(8));
					txtCidade.setText(rs.getString(9));
					cboUf.setSelectedItem(rs.getString(10));
					txtEmail.setText(rs.getString(11));
					btnAdicionar.setEnabled(false);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			} 
		} else {
			scrollPane.setVisible(false);
		}
	}
	
	private void LimparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtEndereco.setText(null);
		txtFone.setText(null);
		txtEmail.setText(null);
		txtEndereco.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		txtCep.setText(null);
		txtNumero.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem("");
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		scrollPane.setVisible(false);
	}
		
	private void AdicionarCli() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente!");
			txtNome.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o cep do cliente!");
			txtCep.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone do cliente!");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do cliente!");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do cliente!");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero de residência do cliente!");
			txtNumero.requestFocus();
		}  else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do cliente!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do cliente!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Estado do cliente!");
			cboUf.requestFocus();
		} else {
			String create = "insert into clientes (nome,fone,cep,endereco,numero,complemento,bairro,cidade,uf,email) values (?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);	
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtCep.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCidade.getText());
				pst.setString(9, cboUf.getSelectedItem().toString());
				pst.setString(10, txtEmail.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente adicionado!");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarClientes() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente!");
			txtNome.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o cep do cliente!");
			txtCep.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone do cliente!");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do cliente!");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do cliente!");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero de residência do cliente!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do cliente!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do cliente!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Estado do cliente!");
			cboUf.requestFocus();
		} else {
			String update = "update clientes set nome =?,fone=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,uf=?,email=? where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtCep.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCidade.getText());
				pst.setString(9, cboUf.getSelectedItem().toString());
				pst.setString(10, txtEmail.getText());
				pst.setString(11, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso!");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void ExcluirCliente() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o cliente?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				LimparCampos();
				JOptionPane.showMessageDialog(null, "Cliente excluido!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						System.out.println("OK");
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}// Fim do código
