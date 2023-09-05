package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import utils.Validador;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class Servicos extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtID;
	private JTextField txtData;
	private JTextField txtEquipamento;
	private JTextField txtDefeito;
	private JTextField txtValor;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JTextField txtCliente;
	private JScrollPane scrollPane;
	private JList listCli;
	private JButton btnOS;
	private JLabel lblNewLabel;
	private JLabel lblDescrio;
	private JTextField txtPeca;
	private JLabel lblResponsavel;
	private JTextField txtResponsavel;
	private JLabel lblNumero;
	private JTextField txtNumero;
	private JTextArea txtaDescricao;
	private JLabel lblDiagnstico;
	private JTextArea txtaDiagnostico;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Servicos() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/OrdemOS.png")));
		setTitle("Serviços");
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JLabel lblOS = new JLabel("OS:");
		lblOS.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblOS.setBounds(10, 20, 50, 32);
		getContentPane().add(lblOS);

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtOS.setBounds(10, 50, 270, 32);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblData.setBounds(10, 80, 60, 20);
		getContentPane().add(lblData);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(10, 110, 270, 32);
		getContentPane().add(txtData);
		txtData.setColumns(10);

		JLabel lblEquipamento = new JLabel("Equipamento:");
		lblEquipamento.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEquipamento.setBounds(10, 140, 119, 20);
		getContentPane().add(lblEquipamento);

		txtEquipamento = new JTextField();
		txtEquipamento.setBounds(10, 170, 270, 32);
		txtEquipamento.setDocument(new Validador(200));
		getContentPane().add(txtEquipamento);
		txtEquipamento.setColumns(10);

		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Buscar();
			}
		});
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/buscarCli.png")));
		btnBuscar.setBounds(300, 50, 32, 32);
		getContentPane().add(btnBuscar);

		JLabel lblDefeito = new JLabel("Defeito: ");
		lblDefeito.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDefeito.setBounds(10, 200, 100, 32);
		getContentPane().add(lblDefeito);

		txtDefeito = new JTextField();
		txtDefeito.setBounds(10, 230, 270, 32);
		txtDefeito.setDocument(new Validador(200));
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblValor.setBounds(10, 260, 100, 20);
		getContentPane().add(lblValor);

		txtValor = new JTextField();
		txtValor.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setBounds(10, 290, 100, 32);
		txtValor.setDocument(new Validador(200));
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		btnAdicionar = new JButton("");
		btnAdicionar.setToolTipText("Adicionar OS");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/adicionarCli.png")));
		btnAdicionar.setBounds(150, 490, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setToolTipText("Editar OS");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Editar();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editarCli.png")));
		btnEditar.setBounds(250, 490, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Excluir OS");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/deletarCli.png")));
		btnExcluir.setBounds(350, 490, 64, 64);
		getContentPane().add(btnExcluir);

		JButton btnLimpar = new JButton("");
		btnLimpar.setToolTipText("Limpar campos");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(450, 490, 64, 64);
		getContentPane().add(btnLimpar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(370, 300, 255, 132);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 220, 57);
		panel.add(scrollPane);
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);

		listCli = new JList();
		scrollPane.setViewportView(listCli);
		listCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		listCli.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtCliente.setBounds(10, 32, 220, 32);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(50, 85, 100, 32);
		panel.add(txtID);
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtID.setColumns(10);

		JLabel lblID = new JLabel("ID:");
		lblID.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblID.setBounds(10, 90, 46, 20);
		panel.add(lblID);

		btnOS = new JButton("");
		btnOS.setToolTipText("Imprimir OS");
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setContentAreaFilled(false);
		btnOS.setBorder(null);
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/PDF2.png")));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(550, 490, 64, 64);
		getContentPane().add(btnOS);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(128, 64, 64));
		lblNewLabel_4.setBounds(0, 480, 784, 91);
		getContentPane().add(lblNewLabel_4);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Servicos.class.getResource("/img/os2.png")));
		lblNewLabel.setBounds(650, 350, 128, 128);
		getContentPane().add(lblNewLabel);

		lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDescrio.setBounds(10, 320, 100, 20);
		getContentPane().add(lblDescrio);

		txtaDescricao = new JTextArea();
		txtaDescricao.setBounds(10, 350, 275, 115);
		getContentPane().add(txtaDescricao);

		JLabel lblPecaSubstituida = new JLabel("Peça substituida:");
		lblPecaSubstituida.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPecaSubstituida.setBounds(380, 20, 167, 32);
		getContentPane().add(lblPecaSubstituida);

		txtPeca = new JTextField();
		txtPeca.setColumns(10);
		txtPeca.setBounds(380, 50, 270, 32);
		getContentPane().add(txtPeca);

		lblResponsavel = new JLabel("Responsável:");
		lblResponsavel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResponsavel.setBounds(380, 80, 167, 32);
		getContentPane().add(lblResponsavel);

		txtResponsavel = new JTextField();
		txtResponsavel.setColumns(10);
		txtResponsavel.setBounds(380, 110, 270, 32);
		getContentPane().add(txtResponsavel);

		lblNumero = new JLabel("Numero de série:");
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNumero.setBounds(380, 140, 167, 32);
		getContentPane().add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(380, 170, 270, 32);
		getContentPane().add(txtNumero);

		lblDiagnstico = new JLabel("Diagnóstico:");
		lblDiagnstico.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiagnstico.setBounds(380, 200, 167, 32);
		getContentPane().add(lblDiagnstico);

		txtaDiagnostico = new JTextArea();
		txtaDiagnostico.setBounds(380, 230, 275, 65);
		getContentPane().add(txtaDiagnostico);
	}

	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listCli.setModel(modelo);
		String readListaCli = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
				+ "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaCli);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtCliente.getText().isEmpty()) {
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
			String readListaCli = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCli);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
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
		txtCliente.setText(null);
		txtID.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		txtaDescricao.setText(null);
		txtPeca.setText(null);
		txtResponsavel.setText(null);
		txtNumero.setText(null);
		txtaDiagnostico.setText(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnAdicionar.setEnabled(true);
	}

	private void Buscar() {
		String numOS = JOptionPane.showInputDialog("Numero da OS");

		String read = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where os = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);

			pst.setString(1, numOS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtEquipamento.setText(rs.getString(3));
				txtDefeito.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtaDescricao.setText(rs.getString(6));
				txtPeca.setText(rs.getString(7));
				txtResponsavel.setText(rs.getString(8));
				txtNumero.setText(rs.getString(9));
				txtaDiagnostico.setText(rs.getString(10));
				txtID.setText(rs.getString(11));
				txtCliente.setText(rs.getString(13));
				txtID.setEnabled(false);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnAdicionar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "OS inexistente");
				btnAdicionar.setEnabled(true);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void Adicionar() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o equipamento do Cliente!");
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo de defeito!");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor da OS!");
			txtDefeito.requestFocus();
		} else if (txtResponsavel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Responsável!");
			txtResponsavel.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor da OS!");
			txtValor.requestFocus();
		} else if (txtaDiagnostico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o diagnostico feito");
			txtaDiagnostico.requestFocus();
		} else {
			String create = "insert into servicos (equipamento,defeito,valor,descricao,substituido,responsavel,numeroserie,diagnostico,idcli) values (?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtaDescricao.getText());
				pst.setString(5, txtPeca.getText());
				pst.setString(6, txtResponsavel.getText());
				pst.setString(7, txtNumero.getText());
				pst.setString(8, txtaDiagnostico.getText());
				pst.setString(9, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "OS realizada!");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void Editar() {
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o equipamento do Cliente!");
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo de defeito!");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor da OS!");
			txtDefeito.requestFocus();
		} else if (txtResponsavel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Responsável!");
			txtResponsavel.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor da OS!");
			txtValor.requestFocus();
		} else if (txtaDiagnostico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o diagnostico feito");
			txtaDiagnostico.requestFocus();
		} else {
			String update = "update servicos set equipamento=?,defeito=?,valor=?, descricao=?, substituido=?, responsavel=?, numeroserie=?, diagnostico=? where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtaDescricao.getText());
				pst.setString(5, txtPeca.getText());
				pst.setString(6, txtResponsavel.getText());
				pst.setString(7, txtNumero.getText());
				pst.setString(8, txtaDiagnostico.getText());
				pst.setString(9, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados da OS editados com sucesso!");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void Excluir() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir a Ordem de Serviço?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from servicos where os=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtOS.getText());
				pst.executeUpdate();
				LimparCampos();
				JOptionPane.showMessageDialog(null, "Cliente excluido!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void imprimirOS() {
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione a ordem de serviço!");
			txtID.requestFocus();
		} else {
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
				document.open();
				String readOS = "select * from servicos where os = ?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());
					rs = pst.executeQuery();
					if (rs.next()) {
						Paragraph assistencia = new Paragraph("Assistência técnica de aparelhos fitness");
						assistencia.setAlignment(Element.ALIGN_CENTER);
						document.add(assistencia);
						document.add(new Paragraph("‎ ‎ "));

						Paragraph cp = new Paragraph("ORDEM DE SERVIÇO");
						cp.setAlignment(Element.ALIGN_RIGHT);
						document.add(cp);
						document.add(new Paragraph("‎ ‎ "));

						Paragraph os = new Paragraph("OS: " + rs.getString(1));
						os.setAlignment(Element.ALIGN_RIGHT);
						document.add(os);

						Paragraph dataOS = new Paragraph("Data: " + rs.getString(2));
						dataOS.setAlignment(Element.ALIGN_RIGHT);
						document.add(dataOS);
						document.add(new Paragraph("‎ ‎ "));
						document.add(new Paragraph("‎ ‎ "));
						document.add(new Paragraph("‎ ‎ "));
						document.add(new Paragraph("‎ ‎ "));

						Paragraph cli = new Paragraph(
								"Nome do cliente:                 ______________________________________________ ");
						cli.setAlignment(Element.ALIGN_LEFT);
						document.add(cli);
						document.add(new Paragraph("‎ ‎ "));

						Paragraph cpfecnpj = new Paragraph(
								"CPF ou CNPJ da empresa: ______________________________________________");
						cpfecnpj.setAlignment(Element.ALIGN_LEFT);
						document.add(cpfecnpj);
						document.add(new Paragraph("‎ ‎ "));

						Paragraph telebairro = new Paragraph(
								"Telefone: _____________________                   Endereço: _____________________");
						telebairro.setAlignment(Element.ALIGN_LEFT);
						document.add(telebairro);
						document.add(new Paragraph("‎ ‎ "));

						Paragraph comemail = new Paragraph(
								"Complemento: _____________________           Email: _________________________");
						comemail.setAlignment(Element.ALIGN_LEFT);
						document.add(comemail);
						document.add(new Paragraph("‎ ‎ "));
						document.add(new Paragraph("‎ ‎ "));
						document.add(new Paragraph("‎ ‎ "));

						Paragraph info = new Paragraph("INFORMAÇÕES DO PRODUTO");
						info.setAlignment(Element.ALIGN_CENTER);
						document.add(info);
						document.add(new Paragraph("‎ ‎ "));

						Paragraph equipamento = new Paragraph("Equipamento: " + rs.getString(3));
						equipamento.setAlignment(Element.ALIGN_LEFT);
						document.add(equipamento);

						Paragraph defeito = new Paragraph("Defeito: " + rs.getString(4));
						defeito.setAlignment(Element.ALIGN_LEFT);
						document.add(defeito);

						Paragraph valor = new Paragraph("Valor: " + rs.getString(5));
						valor.setAlignment(Element.ALIGN_LEFT);
						document.add(valor);

						Paragraph descricao = new Paragraph("Descrição: " + rs.getString(6));
						descricao.setAlignment(Element.ALIGN_LEFT);
						document.add(descricao);

						Paragraph substituido = new Paragraph("Peça substituida: " + rs.getString(7));
						substituido.setAlignment(Element.ALIGN_LEFT);
						document.add(substituido);

						Paragraph numeroserie = new Paragraph("Numero de série do produto: " + rs.getString(9));
						numeroserie.setAlignment(Element.ALIGN_LEFT);
						document.add(numeroserie);

						Paragraph diagnostico = new Paragraph("Diagnóstico: " + rs.getString(10));
						diagnostico.setAlignment(Element.ALIGN_LEFT);
						document.add(diagnostico);
						document.add(new Paragraph(""));

						Paragraph responsavel = new Paragraph("Responsável: " + rs.getString(8));
						responsavel.setAlignment(Element.ALIGN_LEFT);
						document.add(responsavel);
						document.add(new Paragraph(" ‎"));
						document.add(new Paragraph(" ‎"));
						document.add(new Paragraph(
								"_______________________________                      _______________________________"));
						document.add(new Paragraph(" ‎"));
						document.add(new Paragraph(
								"Assinatura do cliente                                            Assinatura do técnico responsável"));
						document.add(new Paragraph(" ‎"));
						document.add(new Paragraph(" ‎"));

						Image imagem = Image.getInstance(Servicos.class.getResource("/img/os2.png"));
						imagem.scaleToFit(128, 128);
						imagem.setAbsolutePosition(30, 705);
						document.add(imagem);

					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			document.close();
			try {
				Desktop.getDesktop().open(new File("os.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
