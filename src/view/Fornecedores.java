package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.ScrollPane;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("unused")
public class Fornecedores extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtFone;
	private JTextField txtRazao;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCnpj;
	private JTextField txtCidade;
	private JTextField txtComplemento;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUf;
	private JButton btnBuscarCep;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	@SuppressWarnings("rawtypes")
	private JList listCli;
	private JScrollPane scrollPane;
	private JTextField txtFantasia;
	private JLabel lblVendedor;
	private JTextField txtVendedor;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblSite;
	private JTextField txtSite;
	private JLabel lblIE;
	private JTextField txtIE;
	private JLabel lblNewLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fornecedores dialog = new Fornecedores();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Fornecedores() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedores.class.getResource("/img/Fornecedores.png")));
		setTitle("Fornecedores");
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(130, 107, 220, 42);
		getContentPane().add(scrollPane);

		listCli = new JList();
		scrollPane.setViewportView(listCli);
		listCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarLista();
			}
		});
		listCli.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblID.setBounds(10, 11, 60, 14);
		getContentPane().add(lblID);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(37, 8, 70, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblInformacoes = new JLabel("Informações:");
		lblInformacoes.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblInformacoes.setBounds(70, 50, 156, 14);
		getContentPane().add(lblInformacoes);

		JLabel lblRazao = new JLabel("Razão social");
		lblRazao.setBounds(10, 90, 86, 14);
		getContentPane().add(lblRazao);

		JLabel lblFone = new JLabel("Telefone");
		lblFone.setBounds(10, 170, 86, 14);
		getContentPane().add(lblFone);

		txtFone = new JTextField();
		txtFone.setBounds(130, 167, 220, 20);
		txtFone.setDocument(new Validador(15));
		getContentPane().add(txtFone);
		txtFone.setColumns(10);

		txtRazao = new JTextField();
		txtRazao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listar();
			}
		});
		txtRazao.setBounds(130, 87, 220, 20);
		getContentPane().add(txtRazao);
		txtRazao.setDocument(new Validador(50));
		txtRazao.setColumns(10);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEndereco.setBounds(570, 50, 100, 14);
		getContentPane().add(lblEndereco);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setBounds(400, 90, 46, 14);
		getContentPane().add(lblCep);

		txtCep = new JTextField();
		txtCep.setBounds(500, 87, 220, 20);
		getContentPane().add(txtCep);
		txtCep.setDocument(new Validador(10));
		txtCep.setColumns(10);

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
		btnBuscarCep.setBounds(735, 85, 32, 32);
		getContentPane().add(btnBuscarCep);

		JLabel lblEnd = new JLabel("Endereço:");
		lblEnd.setBounds(400, 130, 70, 14);
		getContentPane().add(lblEnd);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(500, 127, 220, 20);
		txtEndereco.setDocument(new Validador(50));
		getContentPane().add(txtEndereco);

		JLabel lblNumero = new JLabel("Número:");
		lblNumero.setBounds(400, 170, 60, 14);
		getContentPane().add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(500, 167, 220, 20);
		txtNumero.setDocument(new Validador(10));
		getContentPane().add(txtNumero);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(400, 210, 60, 14);
		getContentPane().add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(500, 207, 220, 20);
		txtBairro.setDocument(new Validador(30));
		getContentPane().add(txtBairro);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(400, 250, 60, 14);
		getContentPane().add(lblCidade);

		JLabel lblCNPJ = new JLabel("Cnpj");
		lblCNPJ.setBounds(10, 330, 46, 14);
		getContentPane().add(lblCNPJ);

		txtCnpj = new JTextField();
		txtCnpj.setColumns(10);
		txtCnpj.setBounds(130, 330, 220, 20);
		txtCnpj.setDocument(new Validador(30));
		getContentPane().add(txtCnpj);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(500, 247, 220, 20);
		txtCidade.setDocument(new Validador(30));
		getContentPane().add(txtCidade);

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(400, 290, 92, 14);
		getContentPane().add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(500, 290, 220, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setDocument(new Validador(20));
		txtComplemento.setColumns(10);

		JLabel lblUF = new JLabel("UF:");
		lblUF.setBounds(400, 330, 46, 14);
		getContentPane().add(lblUF);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(200, 490, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Editar();
			}
		});
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/editar4.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(300, 490, 64, 64);
		getContentPane().add(btnEditar);

		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/eraser.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(500, 490, 64, 64);
		getContentPane().add(btnLimpar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Excluir();
			}
		});
		btnExcluir.setToolTipText("Apagar");
		btnExcluir.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/excluir3.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(400, 490, 64, 64);
		getContentPane().add(btnExcluir);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(500, 326, 70, 22);
		getContentPane().add(cboUf);

		JLabel lblFantasia = new JLabel("Fantasia:");
		lblFantasia.setBounds(10, 130, 70, 14);
		getContentPane().add(lblFantasia);

		txtFantasia = new JTextField();
		txtFantasia.setBounds(130, 127, 220, 20);
		getContentPane().add(txtFantasia);
		txtFantasia.setColumns(10);

		lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setBounds(10, 210, 60, 14);
		getContentPane().add(lblVendedor);

		txtVendedor = new JTextField();
		txtVendedor.setColumns(10);
		txtVendedor.setBounds(130, 207, 220, 20);
		getContentPane().add(txtVendedor);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 250, 60, 14);
		getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(130, 250, 220, 20);
		getContentPane().add(txtEmail);

		lblSite = new JLabel("Site:");
		lblSite.setBounds(10, 290, 60, 14);
		getContentPane().add(lblSite);

		txtSite = new JTextField();
		txtSite.setColumns(10);
		txtSite.setBounds(130, 290, 220, 20);
		getContentPane().add(txtSite);

		lblIE = new JLabel("Inscrição Estadual:");
		lblIE.setBounds(10, 370, 121, 14);
		getContentPane().add(lblIE);

		txtIE = new JTextField();
		txtIE.setColumns(10);
		txtIE.setBounds(130, 370, 220, 20);
		getContentPane().add(txtIE);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/os2.png")));
		lblNewLabel.setBounds(650, 350, 128, 128);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(128, 64, 64));
		lblNewLabel_4.setBounds(0, 480, 784, 91);
		getContentPane().add(lblNewLabel_4);
	}

	@SuppressWarnings("unchecked")
	private void listar() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listCli.setModel(modelo);
		String readListaCli = "select * from fornecedores where razao like '" + txtRazao.getText() + "%'"
				+ "order by razao";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaCli);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtRazao.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarLista() {
		int linha = listCli.getSelectedIndex();
		if (linha >= 0) {
			String readListaCli = "select * from fornecedores where razao like '" + txtRazao.getText() + "%'"
					+ "order by razao limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCli);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtRazao.setText(rs.getString(2));
					txtFantasia.setText(rs.getString(3));
					txtFone.setText(rs.getString(4));
					txtVendedor.setText(rs.getString(5));
					txtEmail.setText(rs.getString(6));
					txtSite.setText(rs.getString(7));
					txtIE.setText(rs.getString(8));
					txtCep.setText(rs.getString(9));
					txtEndereco.setText(rs.getString(10));
					txtNumero.setText(rs.getString(11));
					txtComplemento.setText(rs.getString(12));
					txtBairro.setText(rs.getString(13));
					txtCidade.setText(rs.getString(14));
					cboUf.setSelectedItem(rs.getString(15));
					txtCnpj.setText(rs.getString(16));
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
		txtRazao.setText(null);
		txtFantasia.setText(null);
		txtEndereco.setText(null);
		txtFone.setText(null);
		txtVendedor.setText(null);
		txtEmail.setText(null);
		txtSite.setText(null);
		txtIE.setText(null);
		txtCnpj.setText(null);
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

	private void Adicionar() {
		if (txtRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a razão social!");
			txtRazao.requestFocus();
		} else if (txtFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome Fantasia!");
			txtFantasia.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone!");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email!");
			txtEmail.requestFocus();
		} else if (txtCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CNPJ");
			txtCnpj.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço!");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero de residência!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade federativa!");
			cboUf.requestFocus();
		} else {
			String create = "insert into fornecedores (razao,fantasia,fone,vendedor,email,site,ie,cep,endereco,numero,complemento,bairro,cidade,uf,cnpj) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtRazao.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtIE.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtEndereco.getText());
				pst.setString(10, txtNumero.getText());
				pst.setString(11, txtComplemento.getText());
				pst.setString(12, txtBairro.getText());
				pst.setString(13, txtCidade.getText());
				pst.setString(14, cboUf.getSelectedItem().toString());
				pst.setString(15, txtCnpj.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor adicionado!");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void Excluir() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o fornecedor?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from fornecedores where idfor=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				LimparCampos();
				JOptionPane.showMessageDialog(null, "Fornecedor excluido!");
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Não foi Possível Excluir o Fornecedor!\nHá um Serviço Pendente.");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void Editar() {
		if (txtRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a razão social!");
			txtRazao.requestFocus();
		} else if (txtFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome Fantasia!");
			txtFantasia.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone!");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email!");
			txtEmail.requestFocus();
		} else if (txtCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CNPJ");
			txtCnpj.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço!");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero de residência!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade federativa!");
			cboUf.requestFocus();
		} else {
			String update = "update fornecedores set  razao=?,fantasia=?,fone=?,vendedor=?,email=?,site=?,ie=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,uf=?,cnpj=? where idfor=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtRazao.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtIE.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtEndereco.getText());
				pst.setString(10, txtNumero.getText());
				pst.setString(11, txtComplemento.getText());
				pst.setString(12, txtBairro.getText());
				pst.setString(13, txtCidade.getText());
				pst.setString(14, cboUf.getSelectedItem().toString());
				pst.setString(15, txtCnpj.getText());
				pst.setString(16, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Fornecedor editados com sucesso!");
				LimparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e) {
				JOptionPane.showMessageDialog(null, "Fornecedor não editado, este CNPJ já está sendo utilizado!");
				txtCnpj.setText(null);
				txtCnpj.requestFocus();
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
	}
}