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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

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
	private JButton btnCarregar;
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
	private JComboBox cboUN;
	private JPanel panelFor;
	private JButton btnBuscar;

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

	public Produtos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/produtos.png")));
		setModal(true);
		setTitle("Produtos");
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 102, 220, 42);
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
		txtCodigo.setForeground(new Color(0, 204, 51));
		txtCodigo.setBounds(10, 29, 131, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setDocument(new Validador(5));
		txtCodigo.setColumns(10);

		JLabel lblBarcode = new JLabel("Código de barras:");
		lblBarcode.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBarcode.setBounds(10, 130, 116, 14);
		getContentPane().add(lblBarcode);

		txtBarcode = new JTextField();
		txtBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisarProdutoBarcode();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.'";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtBarcode.setBounds(10, 155, 220, 20);
		getContentPane().add(txtBarcode);
		txtBarcode.setDocument(new Validador(250));
		txtBarcode.setColumns(10);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDescricao.setBounds(590, 370, 97, 20);
		getContentPane().add(lblDescricao);

		txtaDescricao = new JTextArea();
		txtaDescricao.setBounds(530, 400, 200, 65);
		getContentPane().add(txtaDescricao);

		JLabel lblFotoo = new JLabel("Foto:");
		lblFotoo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFotoo.setBounds(615, 15, 68, 14);
		getContentPane().add(lblFotoo);

		JLabel lblEstoque = new JLabel("Estoque:");
		lblEstoque.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstoque.setBounds(10, 186, 68, 14);
		getContentPane().add(lblEstoque);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
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
				String caracteres = "0123456789";
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
		txtLocal.setBounds(10, 379, 220, 20);
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
		btnAdicionar.setBounds(200, 490, 64, 64);
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
		btnEditar.setBounds(300, 490, 64, 64);
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
		btnExcluir.setBounds(400, 490, 64, 64);
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
		btnLimpar.setBounds(500, 490, 64, 64);
		getContentPane().add(btnLimpar);

		lblFoto = new JLabel("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/camera.png")));
		lblFoto.setBounds(500, 40, 256, 256);
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
		btnCarregar.setBounds(570, 320, 116, 23);
		getContentPane().add(btnCarregar);

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
		txtNome.setBounds(10, 84, 220, 20);
		getContentPane().add(txtNome);

		JLabel lblLote = new JLabel("Lote:");
		lblLote.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLote.setBounds(300, 60, 46, 14);
		getContentPane().add(lblLote);

		txtLote = new JTextField();
		txtLote.setColumns(10);
		txtLote.setBounds(300, 84, 131, 20);
		getContentPane().add(txtLote);

		lblFabricante = new JLabel("Fabricante:");
		lblFabricante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFabricante.setBounds(300, 130, 116, 14);
		getContentPane().add(lblFabricante);

		txtFabricante = new JTextField();
		txtFabricante.setColumns(10);
		txtFabricante.setBounds(300, 155, 180, 20);
		getContentPane().add(txtFabricante);

		lblDataEnt = new JLabel("Data de entrada:");
		lblDataEnt.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataEnt.setBounds(300, 186, 131, 14);
		getContentPane().add(lblDataEnt);

		lblDataVal = new JLabel("Data de validade:");
		lblDataVal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataVal.setBounds(300, 242, 108, 14);
		getContentPane().add(lblDataVal);

		lblCusto = new JLabel("Custo:");
		lblCusto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCusto.setBounds(10, 298, 68, 14);
		getContentPane().add(lblCusto);

		txtCusto = new JTextField();
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
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
		lblLucro.setBounds(300, 298, 82, 14);
		getContentPane().add(lblLucro);

		txtLucro = new JTextField();
		txtLucro.setText("0");
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtLucro.setColumns(10);
		txtLucro.setBounds(300, 323, 131, 20);
		getContentPane().add(txtLucro);

		panelFor = new JPanel();
		panelFor.setBorder(new TitledBorder(null, "Fornecedores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFor.setBounds(280, 354, 240, 70);
		getContentPane().add(panelFor);
		panelFor.setLayout(null);

		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setBounds(10, 39, 220, 26);
		panelFor.add(scrollPaneFor);
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
		txtFornecedor.setBounds(10, 20, 220, 20);
		panelFor.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		lblIDFor = new JLabel("ID");
		lblIDFor.setBounds(10, 46, 46, 14);
		panelFor.add(lblIDFor);

		txtIDFor = new JTextField();
		txtIDFor.setForeground(new Color(0, 204, 51));
		txtIDFor.setEditable(false);
		txtIDFor.setBounds(25, 45, 80, 20);
		panelFor.add(txtIDFor);
		txtIDFor.setColumns(10);

		dateEntrada = new JDateChooser();
		dateEntrada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateEntrada.setBounds(300, 211, 131, 20);
		dateEntrada.setEnabled(false);
		getContentPane().add(dateEntrada);

		dateValidade = new JDateChooser();
		dateValidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateValidade.setBounds(300, 267, 131, 20);
		getContentPane().add(dateValidade);

		JLabel lblPorcentagem = new JLabel("%");
		lblPorcentagem.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPorcentagem.setBounds(450, 326, 46, 14);
		getContentPane().add(lblPorcentagem);

		cboUN = new JComboBox();
		cboUN.setModel(new DefaultComboBoxModel(
				new String[] { "", "Unidade", "Peça", "Caixa", "Kilograma", "Grama", "Metro", "Centímetro" }));
		cboUN.setBounds(10, 439, 131, 22);
		getContentPane().add(cboUN);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PesquisarProduto();
			}
		});
		btnBuscar.setIcon(new ImageIcon(Produtos.class.getResource("/img/pesquisar.png")));
		btnBuscar.setToolTipText("Buscar produto");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setBounds(150, 20, 32, 32);
		getContentPane().add(btnBuscar);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(128, 64, 64));
		lblNewLabel_4.setBounds(0, 480, 784, 91);
		getContentPane().add(lblNewLabel_4);
	}

	private void pesquisarProdutoBarcode() {
		if (txtBarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o código de barras!");
			txtBarcode.requestFocus();
		} else {
			String readBarcode = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where barcode = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBarcode);
				pst.setString(1, txtBarcode.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtCodigo.setText(rs.getString(1));
					txtaDescricao.setText(rs.getNString(3));
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
					cboUN.setSelectedItem(rs.getString(7));
					txtLocal.setText(rs.getString(8));
					txtNome.setText(rs.getString(9));
					txtLote.setText(rs.getString(10));
					txtFabricante.setText(rs.getString(11));
					String setarDataEnt = rs.getString(12);
					Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
					dateEntrada.setDate(dataEntrada);
					String setarDataVal = rs.getString(13);
					Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIDFor.setText(rs.getString(16));
					txtFornecedor.setText(rs.getString(18));
					produtoCadastrado = true;
					btnBuscar.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnBuscar.setEnabled(false);
					dateValidade.setEnabled(false);
					panelFor.setEnabled(false);
					txtFornecedor.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado ou cadastrado");
					produtoCadastrado = false;
					btnBuscar.setEnabled(false);
					txtNome.requestFocus();
					btnCarregar.setEnabled(true);
					cboUN.setSelectedItem("");
					btnBuscar.setEnabled(false);
				}
				con.close();
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
			String readCodigo = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where codigo = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readCodigo);
				pst.setString(1, txtCodigo.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtBarcode.setText(rs.getString(2));
					txtaDescricao.setText(rs.getNString(3));
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
					cboUN.setSelectedItem(rs.getString(7));
					txtLocal.setText(rs.getString(8));
					txtNome.setText(rs.getString(9));
					txtLote.setText(rs.getString(10));
					txtFabricante.setText(rs.getString(11));
					String setarDataEnt = rs.getString(12);
					Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
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
					btnBuscar.setEnabled(false);
					dateValidade.setEnabled(false);
					panelFor.setEnabled(false);
					txtFornecedor.setEnabled(false);
					txtFornecedor.setText(rs.getString(18));
				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado ou cadastrado");
					produtoCadastrado = false;
					btnBuscar.setEnabled(false);
					txtNome.requestFocus();
					btnCarregar.setEnabled(true);
					btnBuscar.setEnabled(false);
					cboUN.setSelectedItem("");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

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
					txtFornecedor.setText(rs.getString(2));
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
			String readListaCli = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where nome like '"
					+ txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
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
					cboUN.setSelectedItem(rs.getString(7));
					txtLocal.setText(rs.getString(8));
					txtNome.setText(rs.getString(9));
					txtLote.setText(rs.getString(10));
					txtFabricante.setText(rs.getString(11));
					String setarDataEnt = rs.getString(12);
					Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
					dateEntrada.setDate(dataEntrada);
					String setarDataVal = rs.getString(13);
					Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIDFor.setText(rs.getString(16));
					txtFornecedor.setText(rs.getString(18));
					produtoCadastrado = true;
					btnCarregar.setEnabled(true);
					btnExcluir.setEnabled(true);
					dateValidade.setEnabled(false);
					panelFor.setEnabled(false);
					txtFornecedor.setEnabled(false);
					btnBuscar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
					produtoCadastrado = false;
					txtNome.requestFocus();
					btnCarregar.setEnabled(true);
					cboUN.setSelectedItem("");
					btnBuscar.setEnabled(false);
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
		cboUN.setSelectedItem("");
		txtNome.setText(null);
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/camera.png")));
		txtLote.setText(null);
		txtFabricante.setText(null);
		txtCusto.setText(null);
		txtLucro.setText("0");
		txtFornecedor.setText(null);
		txtIDFor.setText(null);
		dateValidade.setDate(null);
		dateEntrada.setDate(null);
		produtoCadastrado = false;
		btnCarregar.setEnabled(false);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		txtFornecedor.setEnabled(true);
		txtBarcode.requestFocus();
		dateValidade.setEnabled(true);
		panelFor.setEnabled(true);
		btnBuscar.setEnabled(true);
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
		} else if (cboUN.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade de medidas!");
			cboUN.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto!");
			txtaDescricao.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto!");
			txtLote.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto!");
			txtCusto.requestFocus();
		} else if (dateValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha a data de validade do produto!");
			dateValidade.requestFocus();
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
				pst.setString(6, cboUN.getSelectedItem().toString());
				pst.setString(7, txtLocal.getText());
				pst.setString(8, txtNome.getText());
				pst.setString(9, txtLote.getText());
				pst.setString(10, txtFabricante.getText());
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
		} else if (cboUN.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade de medida!");
			cboUN.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto!");
			txtaDescricao.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto!");
			txtLote.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto!");
			txtCusto.requestFocus();
		} else if (dateValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha a data de validade do produto!");
			dateValidade.requestFocus();
		} else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lucro do produto!");
			txtLucro.requestFocus();
		} else if (txtIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual será o fornecedor!");
			txtIDFor.requestFocus();
		} else {
			String update = "update produtos set descricao=?,foto=?,estoque=?,estoquemin=?,unidade=?,localarm=?,nome=?,lote=?,fabricante=?,custo=?,lucro=? where codigo=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtaDescricao.getText());
				pst.setBlob(2, fis, tamanho);
				pst.setString(3, txtEstoque.getText());
				pst.setString(4, txtEstoqueMin.getText());
				pst.setString(5, cboUN.getSelectedItem().toString());
				pst.setString(6, txtLocal.getText());
				pst.setString(7, txtNome.getText());
				pst.setString(8, txtLote.getText());
				pst.setString(9, txtFabricante.getText());
				pst.setString(10, txtCusto.getText());
				pst.setString(11, txtLucro.getText());
				pst.setString(12, txtCodigo.getText());
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
}
