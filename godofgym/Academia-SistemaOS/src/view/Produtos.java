package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;
import utils.Validador;

public class Produtos extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private FileInputStream fis;
	private int tamanho;

	private boolean produtoCadastrado;

	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtBarcode;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtLocal;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JTextArea txtaDescricao;
	private JLabel lblFoto;
	private JButton btnBuscar;
	private JButton btnCarregar;
	private JTextField txtUN;
	private JTextField txtNome;
	private JList listPR;
	private JScrollPane scrollPane;
	private JTextField txtLote;
	private JLabel lblFabricante;
	private JTextField txtFabricante;
	private JLabel lblDataEnt;
	private JLabel lblDataVal;
	private JLabel lblCusto;
	private JTextField txtCusto;
	private JLabel lblLucro;
	private JTextField txtLucro;
	private JScrollPane scrollPaneFor;
	private JList listFor;
	private JTextField txtFornecedor;
	private JLabel lblIDFor;
	private JTextField txtIDFor;
	private JDateChooser dateValidade;
	private JDateChooser dateEntrada;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
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
	public Produtos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/produtos.png")));
		setModal(true);
		setTitle("Produtos");
		setResizable(false);
		setBounds(100, 100, 700, 650);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 102, 131, 32);
		getContentPane().add(scrollPane);

		listPR = new JList();
		listPR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarProdutoLista();
			}
		});
		scrollPane.setViewportView(listPR);

		JLabel lblCodigoPr = new JLabel("Código do produto:");
		lblCodigoPr.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCodigoPr.setBounds(10, 11, 131, 14);
		getContentPane().add(lblCodigoPr);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(10, 29, 131, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setDocument(new Validador(5));
		txtCodigo.setColumns(10);

		JLabel lblBarcode = new JLabel("Código de barras:");
		lblBarcode.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBarcode.setBounds(10, 130, 116, 14);
		getContentPane().add(lblBarcode);

		txtBarcode = new JTextField();
		txtBarcode.setBounds(10, 155, 131, 20);
		getContentPane().add(txtBarcode);
		txtBarcode.setDocument(new Validador(20));
		txtBarcode.setColumns(10);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescricao.setBounds(510, 385, 68, 14);
		getContentPane().add(lblDescricao);

		txtaDescricao = new JTextArea();
		txtaDescricao.setBounds(449, 415, 200, 65);
		getContentPane().add(txtaDescricao);

		JLabel lblFotoo = new JLabel("Foto:");
		lblFotoo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFotoo.setBounds(510, 60, 68, 14);
		getContentPane().add(lblFotoo);

		JLabel lblEstoque = new JLabel("Estoque:");
		lblEstoque.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstoque.setBounds(10, 186, 68, 14);
		getContentPane().add(lblEstoque);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.',";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoque.setBounds(10, 211, 131, 20);
		getContentPane().add(txtEstoque);
		txtEstoque.setDocument(new Validador(20));
		txtEstoque.setColumns(10);

		JLabel lblEstoqueMin = new JLabel("Estoque mínimo:");
		lblEstoqueMin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstoqueMin.setBounds(10, 242, 108, 14);
		getContentPane().add(lblEstoqueMin);

		txtEstoqueMin = new JTextField();
		txtEstoqueMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.',";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBounds(10, 267, 131, 20);
		txtEstoqueMin.setDocument(new Validador(20));
		getContentPane().add(txtEstoqueMin);

		JLabel lblUnidade = new JLabel("Unidade de medida:");
		lblUnidade.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUnidade.setBounds(10, 410, 120, 14);
		getContentPane().add(lblUnidade);

		JLabel lblLocal = new JLabel("Local de armazenagem: ");
		lblLocal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLocal.setBounds(10, 354, 158, 14);
		getContentPane().add(lblLocal);

		txtLocal = new JTextField();
		txtLocal.setColumns(10);
		txtLocal.setBounds(10, 379, 131, 20);
		txtLocal.setDocument(new Validador(50));
		getContentPane().add(txtLocal);

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar Produto");
		btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(55, 540, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/editar4.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(150, 540, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/excluir2.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(250, 540, 64, 64);
		getContentPane().add(btnExcluir);

		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/eraser.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setToolTipText("Limpar campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(345, 540, 64, 64);
		getContentPane().add(btnLimpar);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PesquisarProduto();

			}
		});
		btnBuscar.setIcon(new ImageIcon(Produtos.class.getResource("/img/pesquisar.png")));
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setToolTipText("Excluir");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setBounds(150, 25, 32, 32);
		getContentPane().add(btnBuscar);

		lblFoto = new JLabel("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/camera.png")));
		lblFoto.setBounds(400, 84, 256, 256);
		getContentPane().add(lblFoto);

		btnCarregar = new JButton("Carregar foto");
		btnCarregar.setEnabled(false);
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCarregar.setForeground(SystemColor.desktop);
		btnCarregar.setBounds(480, 346, 116, 23);
		getContentPane().add(btnCarregar);

		txtUN = new JTextField();
		txtUN.setBounds(10, 439, 130, 20);
		getContentPane().add(txtUN);
		txtUN.setColumns(10);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setBounds(10, 60, 46, 14);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarProdutos();
			}
		});
		txtNome.setColumns(10);
		txtNome.setBounds(10, 84, 131, 20);
		getContentPane().add(txtNome);

		JLabel lblLote = new JLabel("Lote:");
		lblLote.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLote.setBounds(200, 60, 46, 14);
		getContentPane().add(lblLote);

		txtLote = new JTextField();
		txtLote.setColumns(10);
		txtLote.setBounds(200, 84, 131, 20);
		getContentPane().add(txtLote);

		lblFabricante = new JLabel("Fabricante:");
		lblFabricante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFabricante.setBounds(198, 130, 116, 14);
		getContentPane().add(lblFabricante);

		txtFabricante = new JTextField();
		txtFabricante.setColumns(10);
		txtFabricante.setBounds(200, 155, 131, 20);
		getContentPane().add(txtFabricante);

		lblDataEnt = new JLabel("Data de entrada:");
		lblDataEnt.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataEnt.setBounds(200, 186, 131, 14);
		getContentPane().add(lblDataEnt);

		lblDataVal = new JLabel("Data de validade:");
		lblDataVal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataVal.setBounds(200, 242, 108, 14);
		getContentPane().add(lblDataVal);

		lblCusto = new JLabel("Custo:");
		lblCusto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCusto.setBounds(10, 298, 68, 14);
		getContentPane().add(lblCusto);

		txtCusto = new JTextField();
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.',";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCusto.setColumns(10);
		txtCusto.setBounds(10, 323, 131, 20);
		getContentPane().add(txtCusto);

		lblLucro = new JLabel("Lucro:");
		lblLucro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLucro.setBounds(200, 298, 82, 14);
		getContentPane().add(lblLucro);

		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.',";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtLucro.setColumns(10);
		txtLucro.setBounds(200, 323, 131, 20);
		getContentPane().add(txtLucro);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(200, 354, 200, 70);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setBounds(10, 40, 180, 25);
		panel.add(scrollPaneFor);
		scrollPaneFor.setVisible(false);
		scrollPaneFor.setBorder(null);

		listFor = new JList();
		listFor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
			}
		});
		listFor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPaneFor.setViewportView(listFor);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtFornecedor.setBounds(10, 20, 180, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		lblIDFor = new JLabel("ID");
		lblIDFor.setBounds(10, 46, 46, 14);
		panel.add(lblIDFor);

		txtIDFor = new JTextField();
		txtIDFor.setEditable(false);
		txtIDFor.setBounds(25, 45, 80, 20);
		panel.add(txtIDFor);
		txtIDFor.setColumns(10);

		dateEntrada = new JDateChooser();
		dateEntrada.setBounds(200, 211, 131, 20);
		getContentPane().add(dateEntrada);

		dateValidade = new JDateChooser();
		dateValidade.setBounds(200, 267, 131, 20);
		getContentPane().add(dateValidade);

		JLabel lblPorcentagem = new JLabel("%");
		lblPorcentagem.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPorcentagem.setBounds(341, 326, 46, 14);
		getContentPane().add(lblPorcentagem);

	} // Fim do construtor

	private void listarFornecedor() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFor.setModel(modelo);
		String readListaFor = "select * from fornecedores where razao like '" + txtFornecedor.getText() + "%'"
				+ "order by razao";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaFor);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneFor.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtFornecedor.getText().isEmpty()) {
					scrollPaneFor.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarFornecedorLista() {
		int linha = listFor.getSelectedIndex();
		if (linha >= 0) {
			String readListaFor = "select * from fornecedores where razao like '" + txtFornecedor.getText() + "%'"
					+ "order by razao limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaFor);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneFor.setVisible(false);
					txtIDFor.setText(rs.getString(1));
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneFor.setVisible(false);
		}
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
				if (produtoCadastrado) {
					btnEditar.setEnabled(true);
					btnAdicionar.setEnabled(false);
				} else {
					btnEditar.setEnabled(false);
					btnAdicionar.setEnabled(true);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void PesquisarProduto() {
		if (txtCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o código!");
			txtCodigo.requestFocus();
		} else {
			String readCodigo = "select * from produtos where codigo = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readCodigo);
				pst.setString(1, txtCodigo.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtBarcode.setText(rs.getString(2));
					// area de texto deve ser utilizado getNString
					txtaDescricao.setText(rs.getNString(3));

					txtEstoque.setText(rs.getString(5));
					txtEstoqueMin.setText(rs.getString(6));
					txtUN.setText(rs.getString(7));
					txtLocal.setText(rs.getString(8));
					txtNome.setText(rs.getString(9));
					txtLote.setText(rs.getString(10));
					txtFabricante.setText(rs.getString(11));
					// Setar a data no JCalendar
					// passo 1 receber a data do mysql
					String setarDataEnt = rs.getString(12);
					// passo 2 formatar a data para o jcalendar
					Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
					// passo 3 exibir o resultado no jcalendar
					dateEntrada.setDate(dataEntrada);
					String setarDataVal = rs.getString(13);
					Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIDFor.setText(rs.getString(16));
					produtoCadastrado = true;
					btnBuscar.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado ou cadastrado");
					produtoCadastrado = false;
					btnBuscar.setEnabled(false);
					txtBarcode.requestFocus();
					btnCarregar.setEnabled(true);
					txtUN.setText(null);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarProdutos() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listPR.setModel(modelo);
		String readListaPr = "select * from produtos where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaPr);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(9));
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarProdutoLista() {
		int linha = listPR.getSelectedIndex();
		if (linha >= 0) {
			String readListaCli = "select * from produtos where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCli);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtCodigo.setText(rs.getString(1));
					txtBarcode.setText(rs.getString(2));
					txtaDescricao.setText(rs.getString(3));
					Blob blob = (Blob) rs.getBlob(4);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					txtEstoque.setText(rs.getString(5));
					txtEstoqueMin.setText(rs.getString(6));
					txtUN.setText(rs.getString(7));
					txtLocal.setText(rs.getString(8));
					txtNome.setText(rs.getString(9));
					txtLote.setText(rs.getString(10));
					txtFabricante.setText(rs.getString(11));
					// passo 1 receber a data do mysql
					String setarDataEnt = rs.getString(12);
					// passo 2 formatar a data para o jcalendar
					Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
					// passo 3 exibir o resultado no jcalendar
					dateEntrada.setDate(dataEntrada);
					String setarDataVal = rs.getString(13);
					Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIDFor.setText(rs.getString(16));
					produtoCadastrado = true;
					btnBuscar.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
					produtoCadastrado = false;
					btnBuscar.setEnabled(false);
					txtNome.requestFocus();
					btnCarregar.setEnabled(true);
					txtUN.setText(null);
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
		txtCodigo.setText(null);
		txtBarcode.setText(null);
		txtEstoque.setText(null);
		txtEstoqueMin.setText(null);
		txtaDescricao.setText(null);
		txtLocal.setText(null);
		txtUN.setText(null);
		txtNome.setText(null);
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/camera.png")));
		txtLote.setText(null);
		txtFabricante.setText(null);
		txtCusto.setText(null);
		txtLucro.setText(null);
		txtFornecedor.setText(null);
		txtIDFor.setText(null);
		produtoCadastrado = false;
		btnCarregar.setEnabled(false);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);
		txtBarcode.requestFocus();
	}

	private void Adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto!");
			txtNome.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque do produto!");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque mínimo do produto!");
			txtEstoqueMin.requestFocus();
		} else if (txtUN.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade do produto!");
			txtUN.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto!");
			txtaDescricao.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto!");
			txtLote.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto!");
			txtCusto.requestFocus();
		}else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha quanto será o lucro!");
			txtLucro.requestFocus();
		} else if (txtIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual será o fornecedor!");
			txtIDFor.requestFocus();
		} else {
			String insert = "insert into produtos (barcode,descricao,foto,estoque,estoquemin,unidade,localarm,nome,lote,fabricante,dataval,custo,lucro,idfor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(insert);
				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtaDescricao.getText());
				pst.setBlob(3, fis, tamanho);
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoqueMin.getText());
				pst.setString(6, txtUN.getText());
				pst.setString(7, txtLocal.getText());
				pst.setString(8, txtNome.getText());
				pst.setString(9, txtLote.getText());
				pst.setString(10, txtFabricante.getText());
				// Obter a data do componente J Date chooser e formatar para o banco de dados mysql 
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateValidade.getDate());
				pst.setString(11, dataFormatada);
				pst.setString(12, txtCusto.getText());
				pst.setString(13, txtLucro.getText());
				pst.setString(14, txtIDFor.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
					LimparCampos();
					txtBarcode.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Produto não cadastrado.");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto!");
			txtNome.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque do produto!");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque mínimo do produto!");
			txtEstoqueMin.requestFocus();
		} else if (txtUN.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade do produto!");
			txtUN.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto!");
			txtaDescricao.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto!");
			txtLote.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto!");
			txtCusto.requestFocus();
		} else if (txtIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual será o fornecedor!");
			txtIDFor.requestFocus();
		} else {
			String update = "update produtos set barcode =?,descricao=?,foto=?,estoque=?,estoquemin=?,unidade=?,localarm=?,nome=?,lote=?,fabricante=?,dataent=?,dataval=?,custo=?,lucro=?,idfor=? where codigo=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtaDescricao.getText());
				pst.setBlob(3, fis, tamanho);
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoqueMin.getText());
				pst.setString(6, txtUN.getText());
				pst.setString(7, txtLocal.getText());
				pst.setString(8, txtNome.getText());
				pst.setString(9, txtLote.getText());
				pst.setString(10, txtFabricante.getText());

				pst.setString(13, txtCusto.getText());
				pst.setString(14, txtLucro.getText());
				pst.setString(15, txtIDFor.getText());
				pst.setString(16, txtCodigo.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do produto alterados com sucesso");
					LimparCampos();
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Não foi possível alterar os dados do produto");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	private void excluir() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o produto?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from produtos where codigo=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());
				pst.executeUpdate();
				LimparCampos();
				JOptionPane.showMessageDialog(null, "Produto excluido!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
} // Fim do código
